# WorkPilot Database Architecture

## Database Technology
- PostgreSQL 18.6
- Database port: 5432
- Local development environment

## Database Ownership

| Microservice | Database | Owner |
|---|---|---|
| Auth Service | workpilot_auth | workpilot_app |
| Ticket Service | workpilot_tickets | workpilot_app |
| Knowledge Service | workpilot_knowledge | workpilot_app |
| AI Service | workpilot_ai | workpilot_app |
| Workflow Service | workpilot_workflow | workpilot_app |
| Analytics Service | workpilot_analytics | workpilot_app |

## Database Design Principles

1. Each microservice owns its database.
2. Services must not directly access another service's tables.
3. Inter-service communication will happen through APIs.
4. Database credentials must not be committed to GitHub.
5. Application services must not connect using the PostgreSQL superuser.

## Local Connection Configuration

- Host: localhost
- Port: 5432
- Username: workpilot_app
- Password: Stored locally and never committed to GitHub.

