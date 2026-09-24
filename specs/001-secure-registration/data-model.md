# Data Model: Secure Registration

## Entities

### User

Represents the account holder for the enterprise auth system.

| Field | Type | Constraints | Notes |
|-------|------|-------------|-------|
| id | integer | primary key, auto-generated | stable internal identifier |
| username | string | required, max 50 chars | human-readable account name |
| email | string | required, unique, max 100 chars | canonical login identifier |
| password_hash | string | required | stores a one-way hash only |
| created_at | timestamp | required, default current timestamp | auditability and onboarding trace |

### Registration Request

Represents the payload received by the registration endpoint before validation and persistence.

| Field | Type | Constraints | Notes |
|-------|------|-------------|-------|
| username | string | required | submitted by user |
| email | string | required | valid email format |
| password | string | required | must satisfy policy and match confirmation |
| confirm_password | string | required | client and server confirmation |

### Registration Result

Represents the server response after processing the request.

| Field | Type | Constraints | Notes |
|-------|------|-------------|-------|
| status | string | required | success or error |
| message | string | required | user-facing outcome |

## Validation Rules

- Username must be provided and remain within the configured maximum length.
- Email must be valid and unique across active user records.
- Password must meet the minimum security standard and match its confirmation field.
- The system must reject malformed or duplicate submissions before persisting user data.
- Only password hashes are stored; raw credentials are never stored or exposed.

## Relationships

- One user record corresponds to one unique email identity.
- Each registration request is processed once and results in either a created account or an error outcome.
- The system does not create additional user records when the same email is submitted more than once.
