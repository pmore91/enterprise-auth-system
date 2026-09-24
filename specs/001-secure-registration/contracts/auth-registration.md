# Authentication Registration Contract

## Endpoint

`POST /api/v1/auth/register`

## Purpose

Create a new user account from a validated registration payload.

## Request Body

```json
{
  "username": "jane.doe",
  "email": "jane.doe@example.com",
  "password": "SecurePass!123",
  "confirm_password": "SecurePass!123"
}
```

### Request Validation Rules

- `username` is required.
- `email` is required and must be syntactically valid.
- `password` is required and must meet the configured minimum complexity standard.
- `confirm_password` must match `password` exactly.
- Duplicate emails are rejected.

## Success Response

```json
{
  "status": "success",
  "message": "User registered successfully"
}
```

## Error Response

```json
{
  "status": "error",
  "message": "Validation failed: password and confirm password do not match"
}
```

## Notes

- The backend remains the only authority for validating and persisting account data.
- The frontend may provide immediate validation cues, but it cannot replace backend enforcement.
- Raw password values are never returned in API responses or stored in plaintext.
