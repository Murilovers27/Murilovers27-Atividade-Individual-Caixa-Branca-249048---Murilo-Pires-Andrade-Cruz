## Goal
Help an AI coding agent be immediately productive in this repository: a small Java exercise that contrasts a vulnerable original `User` class with a corrected version. Focus on safe JDBC use, resource management, and small repo conventions.

## Quick facts (what you'll find)
- Purpose: white‑box analysis and refactor for a user-auth Java class (see `README.MD`).
- Key sources:
  - `versãoOriginal/User.java` — vulnerable implementation (hardcoded SQL concatenation, empty catches, no resource cleanup).
  - `VersãoCorrigida/User.java` — refactored implementation (constant DB_URL, PreparedStatement, try-with-resources, SQLException handling).
- Assets: images and inspection worksheets in `pubilc/` (note: folder name is misspelled `pubilc`).

## Important repo-specific patterns
- Use of PreparedStatement and try-with-resources is the desired pattern (see `VersãoCorrigida/User.java`). When changing code, mirror the exact try-with-resources structure used there.
- Avoid empty catch blocks — the corrected file logs to `System.err`. Prefer small, local logging statements when modifying behavior.
- Passwords and credentials are intentionally hardcoded for the exercise (constant `DB_URL` in `VersãoCorrigida/User.java`). Do not 'fix' this by removing them silently; instead, propose replacing them with environment-driven configuration and show a concrete code change (example patch or new helper).

## Platform and naming caveats
- Package and directory names use accented characters (`versãoOriginal`, `VersãoCorrigida`) and mixed case. These work on Windows but will break on case-sensitive filesystems or some tooling. If you propose renaming, update package declarations and directory names consistently and provide compilation commands.
- The repo has no build system (no Maven/Gradle). Keep changes minimal and provide `javac`/`java` instructions for testing, or optionally add a simple Maven `pom.xml` as a separate change.

## Build / run / debug notes
- There is no main entrypoint. The classes are library-style; to compile locally (PowerShell on Windows):

  javac -d out .\versãoOriginal\User.java .\VersãoCorrigida\User.java

  (No runnable main is present; open files in an IDE or create a small test harness to run methods.)

## Typical change requests and how to handle them
- If asked to fix security issues:
  - Replace string concatenated SQL in `versãoOriginal/User.java` with a PreparedStatement and try-with-resources. Use `VersãoCorrigida` as the canonical example.
  - Do not remove the DB credential lines silently. Instead, replace with a clear environment-based approach and show how to set the env var in Windows PowerShell.
- If asked to refactor packages: propose ASCII-only names (`versaoOriginal`, `versaoCorrigida`), include the exact rename patch, and list compile commands to validate.
- If asked to add tests: create a small JUnit test that calls `verificarUsuario` with a mocked Connection (or factor a Connection provider) — prefer dependency injection over static calls to DriverManager.

## Examples (cite exact files/lines)
- Vulnerable SQL concatenation: `versãoOriginal/User.java` builds SQL with `"Where login = '" + login + "'"` (search for "Where login" to find it).
- Secure example: `VersãoCorrigida/User.java` — SQL uses `login = ? and senha = ?` and sets values via `st.setString(...)`.
- Resource cleanup: see try-with-resources block in `VersãoCorrigida/User.java` surrounding Connection, PreparedStatement, and ResultSet.

## When creating patches
- Keep diffs minimal and preserve original API unless request asks to change it.
- When changing packages/paths, update package declarations inside `.java` files to match directory renames.

## Do not assume
- There is no CI, build tool, or tests in repo — do not add large tooling without noting it as an explicit improvement.

---
If any section is unclear or you want me to expand examples (rename packages, add a simple Maven pom, or provide a test harness), tell me which and I will create a focused patch.
