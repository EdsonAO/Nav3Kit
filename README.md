# Nav3Kit

A lightweight toolkit for setting up **[Navigation 3](https://developer.android.com/guide/navigation/navigation-3)** in Jetpack Compose with **ViewModel-driven navigation**, **typed results**, and **modular, multibound destinations**.

Navigation 3 gives you a `NavDisplay` backed by an observable back stack. Nav3Kit wraps that primitive in a small set of building blocks so that:

- **ViewModels — not Composables — drive navigation.** Screens emit events; ViewModels call a `Router`. No `NavController` plumbed through your composables.
- **Each feature owns its destination.** A feature contributes an entry to the back stack host through your DI container, so the host never imports your screens.
- **Results are type-safe.** Pass a value back to a previous screen through a typed `ResultKey` instead of stringly-typed bundles.
- **DI-agnostic core.** The library only depends on a `Router` and a `Set<EntryProviderInstaller>` — wire them with **Hilt** or **Koin** (or anything else). Nothing in the core couples to a DI framework.

> [!NOTE]
> The examples below wire everything with **Hilt**. A **Koin** setup works identically — see [Wiring with Koin](#wiring-with-koin).

## Why

Navigation 3's `NavDisplay` is intentionally minimal — it renders a back stack you own. That leaves a few recurring questions for every app:

1. **Who mutates the back stack?** Calling `backStack.add(...)` from inside a Composable couples navigation logic to the UI and makes it hard to test.
2. **How do features register their screens without the host depending on them?**
3. **How do you return a result to a previous screen** (the old `SavedStateHandle`/`previousBackStackEntry` dance) in a type-safe way?

Nav3Kit answers all three with `Router`, `EntryProviderInstaller`, and `ResultKey`.

## Features

- 🧭 **`Router`** — a singleton, injectable command bus for navigation. Call it from ViewModels.
- 🔌 **`RouterEffect`** — bridges router commands to the Navigation 3 back stack.
- 🏠 **`AppHostNav`** — a ready-made `NavDisplay` host wired with ViewModel + saveable-state decorators.
- 🧩 **`EntryProviderInstaller`** — register destinations per feature module; multibind them with Hilt `@IntoSet` or Koin.
- 🎯 **Typed results** — `ResultKey<T>` + `ResultValue` wrappers (`IntResult`, `BoolResult`, `StringResult`, `FloatResult`).
- ↩️ Full back-stack operations: navigate, go back, replace, pop-to-route, clear-and-navigate.

## Requirements

| | |
|---|---|
| `minSdk` | 30+ |
| Navigation 3 | `androidx.navigation3` 1.0.1 |
| DI | Hilt **or** Koin (your choice — the core is DI-agnostic) |
| Routes | `kotlinx.serialization` for `@Serializable` `NavKey`s |

## Installation

Add the Navigation 3 and serialization dependencies the core needs (see `gradle/libs.versions.toml` for the exact versions used here):

```kotlin
dependencies {
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.lifecycle.viewmodel.navigation3)
    implementation(libs.kotlinx.serialization.core)
}
```

```kotlin
plugins {
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}
```

Then add your DI framework. **Pick one:**

```kotlin
// Hilt
dependencies {
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)
}
plugins {
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}
```

```kotlin
// Koin
dependencies {
    implementation("io.insert-koin:koin-androidx-compose:<version>")
}
```

## Quick start

### 1. Annotate your `Application` for Hilt

```kotlin
@HiltAndroidApp
class MyApp : Application()
```

### 2. Define a route and register its destination

Each feature declares a `@Serializable` `NavKey` and contributes an `EntryProviderInstaller` to a Hilt set. The host collects all of them — it never imports your screen directly.

```kotlin
@Serializable
data object HomeRoute : NavKey

@Module
@InstallIn(ActivityRetainedComponent::class)
object HomeModule {

    @IntoSet
    @Provides
    fun provideEntryProviderInstaller(): EntryProviderInstaller = {
        entry<HomeRoute> {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(viewModel = viewModel)
        }
    }
}
```

Routes can carry arguments, and destinations can use assisted injection to pass them into the ViewModel:

```kotlin
@Serializable
data class DetailRoute(val id: String) : NavKey

@IntoSet
@Provides
fun provideEntryProviderInstaller(): EntryProviderInstaller = {
    entry<DetailRoute> { key ->
        val viewModel = hiltViewModel<DetailViewModel, DetailViewModel.Factory>(
            creationCallback = { factory -> factory.create(key) }
        )
        DetailScreen(viewModel = viewModel)
    }
}
```

### 3. Host the back stack in your Activity

`AppHostNav` injects the set of installers and the `Router`, and renders a `NavDisplay` with ViewModel-store and saveable-state decorators already applied.

```kotlin
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var entryProviderScopes: Set<@JvmSuppressWildcards EntryProviderInstaller>
    @Inject lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyTheme {
                Scaffold(Modifier.fillMaxSize()) { padding ->
                    AppHostNav(
                        router = router,
                        entryScopes = entryProviderScopes,
                        modifier = Modifier.fillMaxSize().padding(padding),
                    )
                }
            }
        }
    }
}
```

### 4. Navigate from a ViewModel

Inject the `Router` and issue commands. The UI stays declarative — it just emits events.

```kotlin
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val router: Router
) : ViewModel() {

    fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.GoToDetailClicked -> router.navigateTo(DetailRoute(id = "42"))
            HomeEvent.GoToProfileClicked -> router.navigateTo(ProfileRoute)
        }
    }
}
```

## Wiring with Koin

The core is identical — only the registration mechanism changes. Instead of Hilt's `@IntoSet` you contribute each `EntryProviderInstaller` to a multibound set, and you provide `Router` as a singleton.

### 1. Provide the `Router` and contribute destinations

```kotlin
val homeModule = module {
    viewModel { HomeViewModel(router = get()) }

    // Contribute this feature's destination to the shared set.
    single<EntryProviderInstaller> {
        {
            entry<HomeRoute> {
                val viewModel: HomeViewModel = koinViewModel()
                HomeScreen(viewModel = viewModel)
            }
        }
    }
}

val appModule = module {
    single { Router() }
}
```

> Koin resolves multiple `single<EntryProviderInstaller>` bindings as a `List<EntryProviderInstaller>` via `getAll()`. Convert to a `Set` when handing them to `AppHostNav`.

### 2. Host the back stack

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val router: Router = koinInject()
            val installers: Set<EntryProviderInstaller> = getKoin().getAll<EntryProviderInstaller>().toSet()

            MyTheme {
                AppHostNav(
                    router = router,
                    entryScopes = installers,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}
```

Everything else — `Router` commands, `RouterEffect`, typed results — is exactly the same regardless of DI framework.

## Returning results

Nav3Kit passes results back through a **typed `ResultKey`** registered on the `Router`. No `SavedStateHandle`, no string bundles.

### 1. Declare a result key

```kotlin
object ProfileResult {
    val RandomNumber = ResultKey<IntResult>("profile_random_number")
}
```

### 2. Listen on the screen that wants the result

Register in `init` and clean up in `onCleared`:

```kotlin
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val router: Router
) : ViewModel() {

    val uiState = MutableStateFlow(HomeUiState())

    init {
        router.expectResult(ProfileResult.RandomNumber) { result ->
            uiState.update { it.copy(profileResult = result.value) }
        }
    }

    override fun onCleared() {
        router.dismissResult(ProfileResult.RandomNumber)
        super.onCleared()
    }
}
```

### 3. Send the result while navigating back

`goBack`, `replaceWith`, and `popToRoute` all accept a result-builder block:

```kotlin
router.goBack {
    result(ProfileResult.RandomNumber, randomNumber.asResult())
}
```

The listener fires synchronously with the typed value. Supported result wrappers out of the box: `IntResult`, `BoolResult`, `StringResult`, `FloatResult` — each with an `asResult()` extension. Add your own by implementing `ResultValue`.

## API reference

### `Router`

A `@Singleton` command bus. Commands are buffered on an unlimited `Channel` and drained by `RouterEffect`.

| Method | Description |
|---|---|
| `navigateTo(route)` | Push a destination onto the back stack. |
| `goBack(results)` | Pop the top destination, optionally emitting results. |
| `replaceWith(route, results)` | Pop the top destination and push a new one. |
| `popToRoute(route, inclusive, results)` | Pop back to a given route (optionally removing it too). |
| `clearStackAndNavigate(route)` | Clear the whole stack and start fresh at `route`. |
| `expectResult(key, listener)` | Register a typed result listener. |
| `dismissResult(key)` | Remove a result listener (call in `onCleared`). |

### `RouterAction`

The sealed command set the `Router` emits: `NavigateTo`, `GoBack`, `ReplaceWith`, `PopToRoute`, `ClearStackAndNavigate`.

### `RouterEffect(router, backStack)`

A `@Composable` `LaunchedEffect` that consumes router commands and applies them to a `NavBackStack<NavKey>`. Used internally by `AppHostNav`; use it directly if you build a custom host.

### `AppHostNav(router, entryScopes, modifier)`

The back-stack host. Builds the back stack, attaches `RouterEffect`, and renders a `NavDisplay` with `rememberSaveableStateHolderNavEntryDecorator` and `rememberViewModelStoreNavEntryDecorator`. The start destination is the initial back-stack entry — change it to your app's entry route.

### `EntryProviderInstaller`

```kotlin
typealias EntryProviderInstaller = EntryProviderScope<NavKey>.() -> Unit
```

A unit of destination registration. Provide one per feature — multibound with Hilt `@IntoSet` or Koin `single` — so the host can assemble the full `entryProvider`.

### `ResultKey<T>` / `ResultValue`

`ResultKey<T : ResultValue>(key: String)` identifies a typed result channel. `ResultValue` is a sealed marker; the bundled value-class wrappers cover the common primitives.

## How it fits together

```
ViewModel ──router.navigateTo(route)──▶ Router ──Channel──▶ RouterEffect ──▶ NavBackStack
                                          ▲                                       │
                                          │                                   NavDisplay
   expectResult(key) ◀──result(key,value)─┘                                       │
                                                              entryProvider ◀──────┘
                                                                    ▲
                              Set<EntryProviderInstaller>  (Hilt @IntoSet / Koin multibinding)
```

## Contributing

Contributions are welcome! See [`CONTRIBUTING.md`](CONTRIBUTING.md) for how to set up the project, branch, and open a pull request. Maintainers are listed in [`.github/CODEOWNERS`](.github/CODEOWNERS); copyright holders in [`AUTHORS`](AUTHORS) and contributors in [`CONTRIBUTORS`](CONTRIBUTORS).

## License

Nav3Kit is released under the [MIT License](LICENSE).

```
Copyright (c) 2026 The Nav3Kit Authors
```
