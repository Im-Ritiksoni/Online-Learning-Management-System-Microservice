# EduConnect: Course Management Microservices

A microservices-based course management system built with Spring Boot and Spring Cloud. Each business area runs as an independent REST service, and the services find and call each other through Eureka and OpenFeign.

## Architecture

| Service | Port | Responsibility | Calls |
|---|---|---|---|
| eureka-server | 6060 | Service registry | - |
| student-service | 6061 | Student CRUD | - |
| course-service | 6062 | Course CRUD | - |
| instructor-service | 6063 | Instructor CRUD | - |
| enrollment-service | 6064 | Enroll a student in a course | student, course |
| payment-service | 6065 | Payments for enrollments | student, enrollment |
| Assignment-service | 6066 | Assignments for a course | course, instructor |
| result-service | 6067 | Marks and grades | assignment, enrollment, student |

## Tech Stack
- Java 21, Spring Boot 4.1.1
- Spring Cloud (Netflix Eureka, OpenFeign)
- Spring Data JPA, Hibernate
- MySQL
- Maven

## Key Features
- Service discovery with Eureka Server
- Declarative inter-service calls with OpenFeign (service name, no hardcoded URLs)
- Validation across services before saving data (returns 404 if a student, course, instructor, etc. does not exist)
- Full CRUD REST APIs for every service

## API Endpoints
| Service | Base path |
|---|---|
| Student | `/students` |
| Course | `/courses` |
| Instructor | `/instructors` |
| Enrollment | `/enrollments` |
| Payment | `/payments` |
| Assignment | `/assignments` |
| Result | `/results` |

Each supports `POST`, `GET`, `GET /{id}`, `PUT /{id}` and `DELETE /{id}`.

## How to Run
1. Install Java 21, Maven and MySQL.
2. Create a MySQL database named `eurekaserver` and update the username/password in each service's `application.properties`.
3. Start `eureka-server` first (http://localhost:6060).
4. Start `student-service`, `course-service` and `instructor-service`.
5. Start `enrollment-service`, `Assignment-service`, `payment-service` and `result-service`.
6. Open http://localhost:6060 to see all registered services.

## Example
POST `/enrollments?studentId=1&courseId=1&status=ACTIVE`
Enrollment service checks the student and the course through Feign, and saves the enrollment only if both exist.

## Future Improvements
- API Gateway
- Separate database per service
- Circuit breaker (Resilience4j)
- Centralized config and security (JWT)
- Docker and docker-compose

## Explain your project

- My project is EduConnect, a course management system built with microservices using Spring Boot and Spring Cloud.

- Instead of one big application, I created separate services for student, course, instructor, enrollment, payment, assignment and result.
- Each service is a REST API with its own controller, repository and entity, and runs on its own port.

- I also created a Eureka Server.
- Every service registers itself there, so services can find each other by name and I don't need to hardcode any URL.

- For communication between services I used OpenFeign.
- For example, when I create an enrollment, the enrollment service calls the student service and the course service.
- If either one returns 404, I return a 'not found' response and don't save anything.
  
- In the same way, the payment service checks the student and the enrollment,
  the assignment service checks the course and the instructor,
  and the result service checks the assignment, the enrollment and the student.

- For the database I used MySQL with Spring Data JPA.

- Through this project I understood service discovery, how services talk to each other,
  and how to keep each business function separate.
