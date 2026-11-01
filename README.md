# Queue Management System Backend

Student: ABAKAR MAHAMAT ALI
Student ID: 27480
Project: Queue Management System
Course: INSY 8322 Web Technology and Internet (AUCA)

Spring Boot, Spring Data JPA and PostgreSQL. This project contains Step 0, Part A, B, C, D, E and F.

## Steps to run the API

1. Install Java 17 or newer and PostgreSQL.
2. Create the database:
   CREATE DATABASE queue_db;
3. Check the username and password in src/main/resources/application.properties.
4. Start the API:
   ./mvnw spring-boot:run
   (on Windows: mvnw.cmd spring-boot:run)
   The first start creates all the tables.
5. Load the seed data (Step 0). Run seed-data.sql in pgAdmin (Query Tool) or with:
   psql -U postgres -d queue_db -f seed-data.sql
6. Import queue-management-system-midterm.postman_collection.json into Postman.
7. Run the collection from the top to the bottom (Runner), or send the requests one by one.

The API runs on http://localhost:8080.

## About the seed data

- seed-data.sql uses fixed ids, so every Postman request works without copying any id.
- The seed has queue tickets dated in October 2026. Rule D1 refuses past dates on the save endpoint, so the seed is loaded with SQL.
- seed-data.sql starts with TRUNCATE, so you can run it again at any time to go back to the starting data.

## Endpoints

| ID | Endpoint |
|----|----------|
| A2 | POST /api/{path}/save, GET /api/{path}/all, GET /api/{path}/{id}, PUT /api/{path}/update/{id}, DELETE /api/{path}/delete/{id} |
| B1 | GET /api/customers/by-last-name?lastName=nkurunziza |
| B2 | GET /api/queue-tickets/by-status?status=WAITING |
| B3 | GET /api/queue-tickets/between?start=2026-10-01&end=2026-10-31 |
| C1 | GET /api/agents/by-service-type?name=payment |
| C2 | GET /api/counters/free |
| C3 | GET /api/service-types/unused |
| C4 | GET /api/customers/of-agent/{agentId} |
| D1 | POST /api/queue-tickets/save |
| D2 | PATCH /api/queue-tickets/{id}/status?status=CALLED |
| D3 | PUT /api/agents/{agentId}/service-types/{serviceTypeId} |
| D4 | PATCH /api/agents/{agentId}/counter?counterNumber=4 |
| E1 | POST /api/service-notes/save?queueTicketId=... |
| E2 | GET /api/service-notes/customer/{nationalId} |
| E3 | DELETE /api/service-notes/delete/{id} and DELETE /api/queue-tickets/delete/{id} |
| F1 | GET /api/queue-tickets/stats/by-status |
| F2 | PATCH /api/queue-tickets/cancel-day?agentId=...&date=2026-10-20 |
