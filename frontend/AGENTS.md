# Repository Guidelines

## Project Structure & Module Organization
- This is a pnpm workspace with three packages under `packages/`.
- `packages/web/` is the public blog UI (Vue 3 + Vite) with `src/views/`, `src/components/`, `src/router/`, `src/stores/`, and `src/styles/`.
- `packages/admin/` is the admin dashboard (Vue 3 + Vite + Naive UI) with `src/views/`, `src/components/`, and related app wiring.
- `packages/shared/` holds shared API clients, types, composables, and utils (see `packages/shared/src/api/`, `packages/shared/src/types/`, `packages/shared/src/composables/`).
- Root configs live in `pnpm-workspace.yaml` and `tsconfig.base.json`.

## Build, Test, and Development Commands
- Run commands from the repo root using pnpm.
- `pnpm install` installs all workspace dependencies.
- `pnpm dev:web` and `pnpm dev:admin` start the web or admin dev servers.
- `pnpm build` builds all packages; `pnpm build:web` and `pnpm build:admin` target a single app.
- `pnpm preview:web` and `pnpm preview:admin` serve production builds locally.
- `pnpm lint` runs ESLint (with auto-fix); `pnpm format` applies Prettier.

## Coding Style & Naming Conventions
- TypeScript + Vue SFCs; use 2-space indentation.
- Prettier config: single quotes, no semicolons, print width 100, trailing commas (ES5), Vue script/style indentation enabled.
- Components use PascalCase (e.g., `ArticleCard.vue`); views follow `*View.vue`; composables use `useX.ts` in `packages/shared/src/composables/`.
- Tailwind CSS is configured per app via `packages/web/tailwind.config.js` and `packages/admin/tailwind.config.js`, with entry styles in `packages/*/src/styles/main.css`.

## Testing Guidelines
- No test framework or `test` script is configured yet; there are no existing tests.
- If you add tests, document the runner and naming pattern (for example, `*.spec.ts`) here and add a `pnpm test` script.

## Commit & Pull Request Guidelines
- The repository has no commit history, so no convention is established. Use concise, imperative commit messages and add a scope when helpful (for example, `web: add tag filter`).
- PRs should include a short summary, list affected packages, and screenshots for UI changes. Link related issues when applicable.

## Configuration & Environment
- API base URL comes from `VITE_API_BASE_URL`, falling back to `http://localhost:8080` in `packages/shared/src/constants/index.ts`. Provide a local `.env` file when targeting a different backend.
