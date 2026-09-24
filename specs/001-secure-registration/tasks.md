# Tasks: Secure Registration

**Input**: Design documents from `/specs/001-secure-registration/`

**Prerequisites**: plan.md (required), spec.md (required for user stories), research.md, data-model.md, contracts/

**Organization**: Tasks are grouped by user story to enable independent implementation and testing of each story.

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel (different files, no dependencies)
- **[Story]**: Which user story this task belongs to (e.g., US1, US2, US3)
- Include exact file paths in descriptions

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Create the project skeleton and shared auth-registration structure.

- [x] T001 Create the project structure described in plan.md under backend/, frontend/, and database/
- [x] T002 Initialize the Java backend project structure and standard build configuration in backend/
- [x] T003 [P] Configure backend build and dependency metadata in backend/pom.xml
- [x] T004 [P] Create the semantic registration page scaffold in frontend/index.html
- [x] T005 [P] Create the initial PostgreSQL schema and migration stubs in database/schema/users.sql and database/migrations/001_create_users_table.sql

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Shared validation and persistence layers that all user stories depend on.

**Checkpoint**: Foundation ready - user story implementation can now begin.

- [x] T006 Implement the base user entity and field validation contract in backend/src/models/User.java
- [x] T007 Implement the registration response model in backend/src/api/ApiResponse.java
- [x] T008 [P] Implement password hashing and verification in backend/src/security/PasswordHash.java
- [x] T009 [P] Implement reusable registration validation logic in backend/src/services/RegistrationValidator.java
- [x] T010 Implement the user repository for persistence and duplicate-email checks in backend/src/repository/UserRepository.java

---

## Phase 3: User Story 1 - Create a new account securely (Priority: P1) 🎯 MVP

**Goal**: Allow a valid user to complete a secure registration flow and receive a success confirmation.

**Independent Test**: Submit a valid registration payload and verify a new account is created and a success response is returned.

### Implementation for User Story 1

- [x] T011 [US1] Implement the POST /api/v1/auth/register endpoint in backend/src/api/AuthController.java
- [x] T012 [US1] Add registration service orchestration in backend/src/services/RegistrationService.java
- [x] T013 [US1] Enforce required username, email, and password handling with validation rules in backend/src/services/RegistrationValidator.java
- [x] T014 [US1] Persist the new user record with the required fields and secure hash in backend/src/repository/UserRepository.java
- [x] T015 [US1] Build the semantic registration form and required form fields in frontend/index.html
- [x] T016 [US1] Add client-side matching-password validation and success/error messaging in frontend/index.html
- [x] T017 [US1] Return a success JSON payload with status and message in backend/src/api/AuthController.java

**Checkpoint**: At this point, User Story 1 should be fully functional and independently testable.

---

## Phase 4: User Story 2 - Correct input errors before submission (Priority: P2)

**Goal**: Prevent invalid submissions and provide clear feedback before account creation is accepted.

**Independent Test**: Submit mismatched or invalid registration data and confirm the request is rejected with a user-friendly error message.

### Implementation for User Story 2

- [x] T018 [P] [US2] Add mismatch and required-field validation messaging in frontend/index.html
- [x] T019 [US2] Reject invalid email and missing field payloads in backend/src/services/RegistrationValidator.java
- [x] T020 [US2] Return clear validation errors for malformed or inconsistent registration payloads in backend/src/api/AuthController.java
- [x] T021 [US2] Enforce duplicate-email rejection with a clear error path in backend/src/services/RegistrationService.java

**Checkpoint**: At this point, User Stories 1 and 2 should both work independently.

---

## Phase 5: User Story 3 - Protect account creation from weak or unsafe registration attempts (Priority: P3)

**Goal**: Ensure the registration flow remains secure and auditable even under edge cases or malicious input.

**Independent Test**: Attempt duplicate, weak, or malformed registration requests and confirm they are rejected without storing or exposing sensitive credentials.

### Implementation for User Story 3

- [x] T022 [P] [US3] Add password policy enforcement and secure credential handling in backend/src/security/PasswordPolicy.java
- [x] T023 [US3] Enforce unique-email and required-field database constraints in database/migrations/001_create_users_table.sql
- [x] T024 [US3] Ensure raw password values are never stored or exposed by the service flow in backend/src/services/RegistrationService.java
- [x] T025 [US3] Validate final registration safety and error handling against the quickstart scenarios in specs/001-secure-registration/quickstart.md

**Checkpoint**: All user stories should now be independently functional and secure.

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Final checks that improve confidence, clarity, and operability across the feature.

- [x] T026 [P] Review the registration contract and align the endpoint behavior with specs/001-secure-registration/contracts/auth-registration.md
- [x] T027 Review the user model and service boundaries against the data model in specs/001-secure-registration/data-model.md
- [x] T028 [P] Update the project README or operational notes with the secure-registration workflow in README.md
- [x] T029 Validate the full registration flow end-to-end using the scenarios in specs/001-secure-registration/quickstart.md

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: No dependencies - can start immediately
- **Foundational (Phase 2)**: Depends on Setup completion and blocks all user stories
- **User Stories (Phase 3+)**: Depend on Foundational completion
- **Polish (Phase 6)**: Depends on all desired user stories being complete

### User Story Dependencies

- **User Story 1 (P1)**: Begins after Foundational and has no dependencies on other stories
- **User Story 2 (P2)**: Begins after Foundational and can be implemented independently
- **User Story 3 (P3)**: Begins after Foundational and can be implemented independently

### Parallel Opportunities

- Setup tasks T003, T004, and T005 can run in parallel
- Foundational tasks T008 and T009 can run in parallel
- User Story 2 tasks T018 and T021 can be worked in parallel where staffing allows
- User Story 3 tasks T022 and T025 can be worked in parallel once the foundational tasks are complete

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Complete Phase 1: Setup
2. Complete Phase 2: Foundational
3. Complete Phase 3: User Story 1
4. Validate the happy path and confirm the secure-registration flow works independently

### Incremental Delivery

1. Complete Setup + Foundational to establish validation and storage rules
2. Add User Story 1 for a working registration MVP
3. Add User Story 2 to improve UX and prevent invalid submissions
4. Add User Story 3 to enforce stronger security and database safeguards
5. Finish with cross-cutting polish and end-to-end validation

## Notes

- The project currently has no application code; these tasks define the implementation skeleton needed to satisfy the secure-registration specification.
- All tasks follow the required checklist format with task ID, optional [P] marker, and explicit file paths.
- The implementation is intentionally scoped to the secure registration flow described in the spec, plan, and contract documents.

## Phase 7: Convergence

- [x] T030 CRITICAL Build a runnable Java HTTP registration endpoint and application entrypoint that accepts POST /api/v1/auth/register and returns the required success/error JSON per FR-001, FR-005, US1/AC1, US1/AC2 (missing)
- [x] T031 HIGH Wire the registration flow to a real PostgreSQL-backed repository with unique email enforcement and password hashing before persistence per FR-004, FR-007, FR-008, US2/AC2, Constitution II, Constitution III (missing)
- [x] T032 HIGH Add end-to-end validation for mismatched passwords, missing fields, duplicate emails, and malformed payload handling so the frontend/backend contract and user feedback satisfy FR-003, FR-006, SC-002, SC-004, US2/AC1, US2/AC2 (partial)
