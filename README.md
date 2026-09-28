# Enterprise Authentication System — Secure Registration Prototype

A small, enterprise-style authentication prototype built to demonstrate
database-centric backend engineering, layered application architecture,
HTTP/JSON API integration, automated testing, and Spec-Driven Development
with AI-assisted engineering.

> **Project Type:** Portfolio / Learning / Engineering Demonstration  
> **Current Scope:** Secure User Registration  
> **Status:** Working Prototype  
> **Primary Focus:** Database Engineering + Backend Architecture + API Integration

---

## Overview

This project is a locally running, decoupled 3-tier application that
implements a user registration workflow from frontend input through to
persistent PostgreSQL storage.

The current request flow is:

**Browser → Frontend → Java HTTP API → Application Services → JDBC Repository → PostgreSQL**

The project was built as a practical engineering exercise to extend a
database-focused development background into broader application-tier
architecture.

The objective was not simply to build a form that inserts a record into a
database. The project was structured to demonstrate how a business
requirement can be translated into specifications, architectural decisions,
implementation tasks, application code, database persistence, and automated
verification.

AI tools are used throughout the development process as engineering
assistants, while architecture, specifications, constraints, and acceptance
criteria remain explicitly controlled through a Spec-Driven Development
workflow.

---

# What Does the Application Do?

The current implementation focuses on the **Secure Registration** capability.

A user submits registration information through the frontend. The request is
sent as JSON to the Java backend, validated by the application layer, the
credential is transformed before persistence, and the user record is stored
through JDBC in PostgreSQL.

### Current capabilities

- Username validation
- Email validation
- Required-field validation
- Password confirmation matching
- Duplicate email detection
- Credential transformation before persistence
- Structured user persistence
- HTTP/JSON API handling
- HTTP method validation
- Browser CORS handling
- JDBC-based PostgreSQL persistence
- In-memory repository support for isolated tests
- Unit-level validation testing
- HTTP endpoint testing
- JDBC/PostgreSQL registration-flow testing

---

# Architecture

The application follows a decoupled layered architecture.

```mermaid
flowchart TD
    A[Browser / Frontend] -->|HTTP POST + JSON| B[Java HTTP Server]
    B --> C[AuthController]
    C --> D[RegistrationService]
    D --> E[RegistrationValidator]
    D --> F[PasswordHash]
    D --> G[UserRepository]
    G --> H[JdbcUserRepository]
    H -->|JDBC / SQL| I[(PostgreSQL)]
````

### Request lifecycle

```text
User
  │
  ▼
Frontend HTML / JavaScript
  │
  │ HTTP POST + JSON
  ▼
Server
  │
  ▼
AuthController
  │
  ▼
RegistrationService
  │
  ├── RegistrationValidator
  │
  ├── PasswordHash
  │
  ▼
UserRepository
  │
  ▼
JdbcUserRepository
  │
  │ JDBC / SQL
  ▼
PostgreSQL
```

The application deliberately separates HTTP handling, business/service logic,
validation, credential processing, persistence, and database interaction
instead of placing the entire registration flow inside a single component.

---

# Technology Stack

| Layer / Concern    | Technology                          |
| ------------------ | ----------------------------------- |
| Frontend           | HTML5, JavaScript                   |
| Backend            | Java                                |
| HTTP Server        | `com.sun.net.httpserver.HttpServer` |
| Application Layer  | Controller + Service + Validator    |
| Persistence        | Native JDBC                         |
| Database           | PostgreSQL 18.6                     |
| Build Tool         | Apache Maven 3.9.9                  |
| Testing            | JUnit 5                             |
| Containerization   | Docker Desktop                      |
| IDE                | Visual Studio Code                  |
| Version Control    | Git / GitHub                        |
| AI Assistance      | GitHub Copilot + Claude / Anthropic |
| Development Method | Spec-Driven Development             |

### Local development environment

The project has been developed and tested on:

* Windows 11 64-bit
* OpenJDK 21
* Apache Maven 3.9.9
* Docker Desktop
* PostgreSQL 18.6
* PostgreSQL exposed locally on port `5432`

The development machine uses an OpenJDK 21 runtime, while the Maven project
currently targets Java 17 bytecode compatibility.

---

# Project Structure

```text
enterprise-auth-system/
│
├── backend/
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   │   └── com/enterprise/auth/
│       │   │       ├── ApiResponse.java
│       │   │       ├── AuthController.java
│       │   │       ├── InMemoryUserRepository.java
│       │   │       ├── JdbcUserRepository.java
│       │   │       ├── JsonUtil.java
│       │   │       ├── PasswordHash.java
│       │   │       ├── RegistrationRequest.java
│       │   │       ├── RegistrationService.java
│       │   │       ├── RegistrationValidator.java
│       │   │       ├── Server.java
│       │   │       ├── User.java
│       │   │       ├── UserRepository.java
│       │   │       └── ValidationResult.java
│       │   │
│       │   └── resources/
│       │
│       └── test/
│           └── java/
│               └── com/enterprise/auth/
│                   ├── JdbcRegistrationFlowTest.java
│                   ├── RegistrationServerTest.java
│                   └── RegistrationValidatorTest.java
│
├── database/
│
├── frontend/
│
├── spec/
│   ├── constitution.md
│   ├── instructions.md
│   └── spec.md
│
├── specs/
│   └── 001-secure-registration/
│       ├── checklists/
│       ├── contracts/
│       ├── data-model.md
│       ├── plan.md
│       ├── quickstart.md
│       ├── research.md
│       ├── spec.md
│       └── tasks.md
│
├── tests/
│
└── README.md
```

---

# Spec-Driven Development

A significant part of this project is the **development process itself**.

The project uses a Spec-Driven Development approach so that implementation is
not driven only by ad-hoc coding or AI-generated changes.

The intended workflow is:

```text
Business Requirement
        │
        ▼
Constitution
        │
        ▼
Feature Specification
        │
        ▼
Architecture / Plan
        │
        ▼
Task Breakdown
        │
        ▼
Implementation
        │
        ▼
Testing
        │
        ▼
Convergence / Review
```

The repository keeps the specification and planning artifacts alongside the
source code so that the intended behavior and engineering decisions remain
inspectable.

The current registration specification defines:

* Registration scenarios
* Acceptance criteria
* Required fields
* Password confirmation rules
* Duplicate account behavior
* Credential handling expectations
* Edge cases
* Success criteria
* Future-oriented security expectations

The project structure also includes supporting plans, contracts, research,
data-model documentation, checklists, and implementation tasks.

---

# AI-Assisted Engineering

AI is used as an engineering accelerator, not as an unrestricted source of
application code.

The development workflow combines human architectural decisions with AI tools
for implementation, analysis, troubleshooting, and documentation.

## GitHub Copilot

Used primarily inside VS Code for:

* Java implementation assistance
* SQL development
* Query improvement
* Debugging
* Code explanation
* Understanding existing or legacy logic
* Comments and documentation
* Exploring alternative implementation approaches

## Claude / Anthropic Models

Used for deeper engineering activities such as:

* Architecture analysis
* Specification design
* Requirement analysis
* SQL reasoning
* Root-cause analysis
* Structural review
* Comparing implementation approaches
* Identifying cross-layer inconsistencies
* Technical documentation and prompt design

The objective is to demonstrate a controlled **AI-assisted software
development lifecycle** in which the engineer remains responsible for the
architecture, specifications, technical decisions, and final verification.

---

# API

## Register User

```http
POST /api/v1/auth/register
Content-Type: application/json
```

### Example request

```json
{
  "username": "jane.doe",
  "email": "jane.doe@example.com",
  "password": "StrongPass123!",
  "confirmPassword": "StrongPass123!"
}
```

### Example success response

```json
{
  "status": "success",
  "message": "User registered successfully"
}
```

The API also handles invalid methods, validation failures, malformed requests,
and browser CORS pre-flight requests.

---

# Registration Processing

The current registration flow can be summarized as:

```text
Required username
        ↓
Valid email
        ↓
Required password
        ↓
Required password confirmation
        ↓
Password values must match
        ↓
Email must not already exist
        ↓
Credential representation generated
        ↓
User object created
        ↓
JDBC repository persists user
        ↓
Success response returned
```

Validation is performed before persistence, and the raw password is not passed
to the repository as the value stored in the database.

---

# Database Design

The application persists user account information in PostgreSQL.

The current user data model includes:

* User identifier
* Username
* Email
* Password representation
* Registration timestamp

The email column has a database-level `UNIQUE` constraint so that duplicate
email registrations cannot create multiple records for the same email.

Database interaction is performed through JDBC using prepared statements.

The repository currently initializes the required user table when it starts,
allowing the local prototype to work against a fresh PostgreSQL database.

---

# Testing

The backend currently contains three test classes covering different layers
of the registration workflow.

## `RegistrationValidatorTest`

Validates application-level business rules including:

* Successful registration validation
* Password mismatch rejection
* Duplicate email rejection

## `RegistrationServerTest`

Exercises the registration API through an actual HTTP request against the
embedded Java HTTP server.

This verifies the HTTP layer and confirms that a valid registration request
can travel through the server and produce the expected response.

## `JdbcRegistrationFlowTest`

Exercises the registration service with the real JDBC repository and
PostgreSQL persistence path.

This verifies the database-backed registration flow rather than relying only
on an in-memory implementation.

## Current test result

```text
Tests run: 5
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

Run the backend test suite with:

```powershell
cd backend
mvn test
```

---

# Engineering Decisions

## Lightweight Java HTTP Server

The backend currently uses:

```java
com.sun.net.httpserver.HttpServer
```

instead of introducing a full web application framework.

This keeps the prototype intentionally lightweight and makes the HTTP,
controller, service, validation, and persistence boundaries easy to inspect.

It also demonstrates that the application architecture is not dependent on a
large framework to establish separation of responsibilities.

## Native JDBC

The persistence layer uses native JDBC instead of an ORM.

This is intentional because the project has a strong database-engineering
focus and keeps SQL execution and persistence behavior explicit.

## Real PostgreSQL

The database-backed integration test communicates with a real PostgreSQL
instance running locally in Docker.

An in-memory repository is also available for tests where database access is
not required.

This combination provides both test isolation and a realistic persistence
path.

## Explicit Layer Separation

The current structure separates responsibilities across:

```text
HTTP Server
     ↓
Controller
     ↓
Service
     ↓
Validation / Credential Processing
     ↓
Repository
     ↓
Database
```

This provides a foundation for expanding the system without putting all logic
inside a single application component.

---

# Security Scope and Current Limitations

This repository is intentionally presented as a **working prototype**, not a
production-ready identity and access-management platform.

The current implementation demonstrates validation, duplicate-account
protection, and transformation of credentials before persistence.

The current password transformation uses SHA-256 in the prototype. This is
documented intentionally and should not be interpreted as a claim of
production-grade password-storage security.

A production authentication system would require additional security
engineering and operational controls.

Potential hardening areas include:

* Password-specific adaptive hashing with salt and appropriate work factors
* Login and password verification flows
* Session or token-based authentication
* Refresh-token management
* Authorization
* Rate limiting
* Account lockout / abuse prevention
* Secret management
* Secure external configuration
* HTTPS and deployment security
* Security-focused integration testing
* Centralized error handling
* Production logging and monitoring
* Auditability and operational observability

These items are intentionally outside the current registration prototype
scope.

---

# What This Project Demonstrates

The business functionality of the project is deliberately small.

The engineering scope is broader.

The repository demonstrates the ability to work across:

```text
Database Schema
       ↕
JDBC Persistence
       ↕
Application Services
       ↕
HTTP API
       ↕
Frontend
       ↕
Automated Tests
       ↕
Specifications
       ↕
AI-Assisted Development
```

The project therefore demonstrates a workflow of:

**Requirement → Specification → Architecture → Implementation → Database
Integration → Automated Verification**

rather than simply demonstrating a standalone Java program or SQL script.

---

# Project Classification

| Dimension           | Description                        |
| ------------------- | ---------------------------------- |
| Business Scope      | Small                              |
| Functional Scope    | Secure Registration                |
| Architectural Scope | Multi-layer application            |
| Database Scope      | Relational persistence with JDBC   |
| API Scope           | HTTP/JSON registration endpoint    |
| Testing Scope       | Unit + HTTP + JDBC integration     |
| Development Method  | Spec-Driven Development            |
| AI Usage            | Structured AI-assisted engineering |
| Intended Use        | Portfolio / Interview / Learning   |
| Production Status   | Prototype                          |

### Small application, broader engineering exercise

This distinction is important.

The application intentionally solves a limited business problem so that the
engineering practices remain understandable and inspectable.

The project should therefore be viewed as a **focused engineering
demonstration**, not as an attempt to present a small prototype as a complete
enterprise IAM product.

---

# Roadmap

Future iterations may expand the registration foundation into a broader
authentication system.

Potential next steps include:

* User login
* Password verification
* Authentication middleware
* Session or token-based authentication
* Authorization
* Stronger password storage
* Configuration externalization
* Database migration management
* Centralized error handling
* Expanded automated test coverage
* API documentation
* Dockerized application services
* CI/CD pipeline
* Security hardening
* Observability and operational tooling

Future capabilities will continue to follow the same Spec-Driven Development
workflow.

---

# Local Development

The project is designed for local execution using Docker for PostgreSQL and
Maven for backend compilation and testing.

### Backend

```powershell
cd backend
mvn test
```

The current Maven configuration targets Java 17 compatibility while the
development environment uses OpenJDK 21.

### Database

PostgreSQL runs locally through Docker and is exposed on:

```text
localhost:5432
```

The application connects to the local PostgreSQL database through JDBC.

---

# About the Author

This project was created by a database and data-engineering-focused technical
professional with extensive experience in:

* PostgreSQL
* MySQL
* Oracle SQL/PLSQL
* Complex SQL development
* Database design
* Query performance tuning
* Data investigation and reconciliation
* Data operations
* Business-rule implementation
* Production issue analysis

The purpose of this project is to extend that database-centric engineering
experience into broader software architecture and application development
while using modern AI-assisted engineering practices.

---

# Final Perspective

This repository is intentionally transparent about what it is:

> **A working enterprise-style registration prototype created as a portfolio
> and learning project to demonstrate database engineering, backend
> architecture, API integration, automated testing, Spec-Driven Development,
> and controlled AI-assisted software engineering.**

It is deliberately not presented as a finished production authentication
platform.

The value of the project lies in demonstrating how a focused business
requirement can be taken through a structured engineering lifecycle and
implemented across multiple application and data layers.