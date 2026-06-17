---
description: Update README and docs to reflect only the user-facing changes on the current branch. Run before committing new changes to a PR.
allowed-tools: Bash(git fetch:*), Bash(git diff:*), Bash(git status:*), Bash(git merge-base:*), Bash(git log:*), Read, Edit, Write
---

Keep Nav3Kit's documentation in sync with the code **before** these changes land in a PR. Review what changed on the current branch and update the docs — but only for changes a *reader of the library* would care about. Internal churn stays out of the README so the docs stay clean and trustworthy.

## What counts as a "relevant" change

The goal is a clean README, not a changelog. Only update docs when a change affects how someone *uses or sets up* the library.

**Document these (user-facing):**
- New, removed, or renamed public API — `Router` methods, `AppHostNav`, `ResultKey`, `EntryProviderInstaller`, result types.
- Changes to a public function's signature, parameters, or behavior (e.g. `AppHostNav` gaining a `startRoute` parameter).
- New setup or wiring steps (Hilt/Koin), new Gradle dependencies, plugins, or version-catalog entries a consumer must add.
- New module structure, package names, or artifact coordinates.
- Changed minimum requirements (minSdk, Navigation 3 version, Compose BOM).
- New capabilities or usage patterns worth a quick-start or example.
- Changes to the contribution workflow that belong in `CONTRIBUTING.md` (new checks, templates, conventions).

**Do NOT document these (internal):**
- Refactors that don't change the public API or usage.
- Renamed private/internal symbols, reformatting, comment-only edits.
- Test-only changes, CI tweaks, dependency bumps with no API impact.
- Work-in-progress scaffolding that isn't usable yet (e.g. an empty module).

If a change is borderline, ask: *would a user copying from the README get something wrong if I skip this?* If yes, document it. If no, leave it out.

## Steps

### 1. Gather the changes on the branch

Determine the base branch (usually `main`) and collect both committed and uncommitted work:

```bash
git fetch origin 2>/dev/null
BASE=$(git merge-base HEAD origin/main 2>/dev/null || git merge-base HEAD main)
git diff --stat "$BASE"...HEAD   # committed changes on this branch
git status --short               # uncommitted/staged changes
git diff "$BASE"...HEAD          # full committed diff
git diff                         # full uncommitted diff
```

Read the actual diffs, not just the file list — you need to see *what* changed in each file to classify it as user-facing or internal.

### 2. Classify each change

Go through the changed files and bucket each one using the rules above. Briefly note which changes are user-facing and which to skip, so your reasoning is transparent.

### 3. Update the docs

For each user-facing change, update the right document so it matches reality:

- **`README.md`** — installation, requirements, quick start, API reference, examples, the data-flow overview.
- **`CONTRIBUTING.md`** — contributor workflow, checks, conventions.
- Other top-level docs (`AUTHORS`, `CONTRIBUTORS`, templates) only if a change directly affects them.

Match the existing tone and structure. Prefer editing the existing section over adding a new one — duplication is how READMEs rot. Keep code snippets copy-paste correct against the new code (right package names, signatures, dependency coordinates).

### 4. Report what you changed (and what you skipped)

Give a short summary: which docs you updated and why (tie each edit to a code change), and which branch changes you deliberately left undocumented because they're internal. This lets the reader catch misclassifications before committing.

## Principles

- **Accuracy over completeness.** A small, correct README beats an exhaustive one full of stale snippets.
- **Don't invent.** Only document what's actually in the code on this branch. If something is planned but not yet implemented, don't describe it as if it works.
- **Leave the README cleaner than you found it.** If updating a section surfaces existing drift, fix it while you're there.
