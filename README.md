# Queue Management System Backend

Student: ABAKAR MAHAMAT ALI
Student ID: 27480
Project: Queue Management System
Course: INSY 8322 Web Technology and Internet (AUCA)

Spring Boot, Spring Data JPA and PostgreSQL.

This submission contains Part A (domain model and CRUD), Part B (derived queries), Part C (JPQL with joins) and Part D (business rules).

## Steps to run the API

1. Install Java 17 or newer and PostgreSQL.
2. Create the database:
   CREATE DATABASE queue_db;
3. Check the username and password in src/main/resources/application.properties.
4. Start the API:
   ./mvnw spring-boot:run
   (on Windows: mvnw.cmd spring-boot:run)
   The first start creates all the tables.
5. Import queue-management-system-midterm.postman_collection.json into Postman.
6. Run the folder "Step 0 - seed data" once, in order. It enters the seed data through the save endpoints.
7. Run the requests of the folders "Part A", "Part B", "Part C" and "Part D".

The API runs on http://localhost:8080.

## Endpoints of Part B and Part C

| ID | Endpoint |
|----|----------|
| B1 | GET /api/customers/by-last-name?lastName=nkurunziza |
| B2 | GET /api/queue-tickets/by-status?status=WAITING |
| B3 | GET /api/queue-tickets/between?start=2026-10-01&end=2026-10-31 |
| C1 | GET /api/agents/by-service-type?name=payment |
| C2 | GET /api/counters/free |
| C3 | GET /api/service-types/unused |
| C4 | GET /api/customers/of-agent/{agentId} |

## Endpoints of Part D

| ID | Endpoint |
|----|----------|
| D1 | POST /api/queue-tickets/save |
| D2 | PATCH /api/queue-tickets/{id}/status?status=CALLED |
| D3 | PUT /api/agents/{agentId}/service-types/{serviceTypeId} |
| D4 | PATCH /api/agents/{agentId}/counter?counterNumber=4 |

Note: since Part D, a new queue ticket cannot have a past date and always starts as WAITING. The seed data of Step 0 was entered before Part D.
