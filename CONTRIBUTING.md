# Contributing to Nav3Kit

Thanks for helping build Nav3Kit! This guide covers how to propose changes and the conventions we follow.

## Getting started

1. **Fork** the repo and clone your fork.
2. Open the project in Android Studio (it builds with the Gradle wrapper — `./gradlew`).
3. Create a branch off `main`:
   ```bash
   git checkout -b feature/short-description
   ```

## Before you open a pull request

Run the checks locally:

```bash
./gradlew build        # compile everything
./gradlew test         # unit tests
./gradlew lint         # Android lint
```

- Keep changes focused — one logical change per PR.
- Add or update tests for any behavior change.
- Update the `README.md` if you change public API or usage.
- If this is your first contribution, add yourself to the [`CONTRIBUTORS`](CONTRIBUTORS) file in the same PR.

## Commit messages

Use clear, imperative commit subjects (e.g. `Add popToRoute result support`). Reference issues with `#123` where relevant.

## Pull request process

1. Push your branch and open a PR against `main`.
2. Fill in what changed and why. Link any related issue.
3. A maintainer (see [`CODEOWNERS`](.github/CODEOWNERS)) will be auto-requested for review.
4. At least one maintainer approval is required before merge.
5. Keep the PR up to date with `main`; squash-merge is preferred to keep history clean.

## Coding conventions

- Kotlin official code style (the project ships with `.editorconfig`-compatible defaults).
- Keep the **core** (`navigation` package) free of any DI-framework dependency — Hilt/Koin wiring belongs in consumer code or examples only.
- Public API changes should be deliberate and documented.

## Reporting bugs & proposing features

Open a GitHub issue describing the problem or proposal. For bugs, include a minimal repro, the Nav3Kit/Navigation 3 versions, and expected vs. actual behavior.

## License

By contributing, you agree that your contributions will be licensed under the [MIT License](LICENSE) that covers the project, and that you may be added to the [`AUTHORS`](AUTHORS) file as a copyright holder.
