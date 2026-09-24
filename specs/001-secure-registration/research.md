# Research: Secure Registration

## Decision

The registration feature will use a backend-authoritative registration flow with a semantic HTML5 frontend and a PostgreSQL-backed user store.

## Rationale

The project constitution requires security by default, explicit validation, and a server-side source of truth. The frontend can provide immediate field-level feedback, but the backend must enforce required schema rules, unique email constraints, and password protection. This matches the secure-registration requirement and reduces the risk of bypassing validation.

## Alternatives Considered

- Frontend-only validation only: rejected because it creates a trust boundary violation; the backend is the canonical validator.
- No unique-email enforcement: rejected because duplicate account creation undermines identity integrity and fails the data integrity requirement.
- Password storage in plaintext: rejected because it violates the constitution and would create a major security risk.

## Decisions Captured

### 1. Registration entry point

The feature exposes a single registration route at `/api/v1/auth/register` that accepts a JSON payload with `username`, `email`, and `password` fields.

### 2. Validation model

- `username` is required and constrained to a practical account length.
- `email` is required, unique, and structurally valid.
- `password` is required and must satisfy a minimum complexity policy.
- Matching confirmation is validated before submission is accepted.

### 3. Persistence model

User records are stored in a `users` table with a unique email column and a secure password hash field. The database enforces uniqueness and required-not-null behavior for the critical identity columns.

### 4. Output behavior

The API returns a server-generated success or validation error payload. Error responses remain consistent and do not disclose sensitive security internals beyond the required validation messages.

### 5. Test strategy

The feature will be validated through contract checks for the registration endpoint, schema enforcement checks for the data model, and end-to-end validation for the registration form and success/error states.
