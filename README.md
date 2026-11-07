# WorkPilot

**AI-Native Business Operations & Customer Support Platform**

WorkPilot is a multi-tenant SaaS platform designed to help businesses manage customer support operations through ticket management, team collaboration, workflow automation and AI-powered assistance.

The project is being developed as a microservices-based application, with core business functionality implemented first and AI capabilities integrated in a later development phase.

## Project Goals

- Build a production-oriented, multi-tenant SaaS application.
- Implement a microservices architecture using Spring Boot and Spring Cloud.
- Develop secure REST APIs and service-to-service communication.
- Integrate Large Language Models and Retrieval-Augmented Generation (RAG).
- Demonstrate backend engineering, frontend development, database design and AI integration.

## Core Features

### Core SaaS Features

- User registration and authentication.
- Workspace creation and team management.
- Role-based access control.
- Customer support ticket management.
- Ticket assignment, priorities and status tracking.
- Conversations and message history.
- Document and knowledge base management.
- Workflow automation.
- Support analytics and reporting.

### AI Features — Planned

- AI-powered ticket classification.
- AI-generated response suggestions.
- Document-based AI assistant.
- Semantic document search.
- Retrieval-Augmented Generation (RAG).
- AI ticket summarization.
- Sentiment and priority detection.
- AI-assisted workflow automation.

## Technology Stack

### Frontend

- React
- TypeScript
- Vite
- Tailwind CSS
- shadcn/ui
- React Router
- Axios
- TanStack Query

### Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Spring Cloud Gateway
- Spring Cloud Consul
- Spring Cloud LoadBalancer
- Resilience4j
- WebClient

### Database

- PostgreSQL
- pgvector (AI phase)

### AI and RAG

- Spring AI
- Large Language Model API
- Embedding models
- Vector similarity search
- Retrieval-Augmented Generation (RAG)

## Architecture

WorkPilot follows a **microservices architecture**.

The React frontend communicates with backend services through Spring Cloud Gateway. Consul provides service discovery, while Spring Cloud LoadBalancer supports service communication.

### Backend Services

| Service | Responsibility |
|---|---|
| API Gateway | Central entry point, routing and security filters |
| Auth Service | Users, authentication, workspaces and memberships |
| Ticket Service | Tickets, assignments, conversations and messages |
| Knowledge Service | Document uploads, extraction and knowledge management |
| AI Service | LLM integration, RAG and AI-powered classification |
| Workflow Service | Business rules, triggers and automated actions |
| Analytics Service | Reports, metrics and operational insights |

Each service owns its data and exposes APIs for communication with other services.

PostgreSQL is used for persistent application data. pgvector will be introduced for vector storage and semantic retrieval during AI implementation.

## Development Strategy

Development is divided into two major stages.

**Phase 1 — Core SaaS Development**

Build the complete business application, including authentication, workspaces, ticket management, knowledge management, workflows and analytics.

**Phase 2 — AI Integration**

Introduce LLM integration, embeddings, vector search, RAG, AI classification, response suggestions and AI-assisted automation.

The architecture is designed to accommodate AI functionality from the beginning, even though AI implementation comes later.

## Project Structure

```text
Workpilot/
├── frontend/
├── backend/
│   ├── api-gateway/
│   ├── auth-service/
│   ├── ticket-service/
│   ├── knowledge-service/
│   ├── ai-service/
│   ├── workflow-service/
│   └── analytics-service/
├── docs/
│   ├── architecture/
│   ├── database/
│   └── api/
├── scripts/
├── .gitignore
└── README.md
```

## Security Considerations

- Password hashing and secure authentication.
- JWT-based authentication and authorization.
- Role-based access control.
- Tenant-level data isolation.
- Service-level authorization checks.
- Secure handling of API keys and environment variables.
- AI retrieval restricted to authorized workspace data.
- Validation of AI-triggered actions.

## Development Roadmap

1. Project foundation and infrastructure.
2. Authentication and workspace management.
3. Ticket management services.
4. Frontend and dashboard.
5. Knowledge management.
6. AI and RAG integration.
7. Workflow automation and analytics.
8. Testing, security review and deployment.

## Project Status

**Currently under active development.**

The initial focus is building the core microservices-based SaaS application before introducing AI functionality.

## License

To be determined.