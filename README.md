# NexLearn Microservice

This project implements a Course Enrollment Microservice using Spring Boot, managing Students, Courses, and Enrollments. It includes RESTful APIs for course management and student enrollment, with proper error handling and MySQL database integration via Docker.

## Project Structure

The project follows a standard Spring Boot structure with a clear separation of concerns:

- `com.nexlearn.microservice.model`: Contains the JPA entities (Student, Course, Enrollment).
- `com.nexlearn.microservice.repository`: Defines JpaRepository interfaces for data access.
- `com.nexlearn.microservice.service`: Implements the business logic for Courses and Enrollments.
- `com.nexlearn.microservice.controller`: Exposes RESTful endpoints for the microservice.

## Required Features Implemented

- **Add a course**: `POST /api/courses`
- **Get all courses**: `GET /api/courses`
- **Enroll a student in a course**: `POST /api/enrollments/enroll`
- **View all enrollments**: `GET /api/enrollments`
- **View student-specific enrollments**: `GET /api/enrollments/student/{studentId}`
- **Add a student**: `POST /api/students` (for testing purposes)
- **Get all students**: `GET /api/students` (for testing purposes)

## Error Handling

- **Course full**: Returns `400 Bad Request` with message "Course is full".
- **Course not found**: Returns `404 Not Found` with message "Course not found".
- **Student not found**: Returns `404 Not Found` with message "Student not found".

## Database Configuration (MySQL)

The `application.properties` file is configured to connect to a MySQL database. The `docker-compose.yml` sets up a MySQL container and links it to the Spring Boot application, ensuring proper database connectivity within the Dockerized environment.

## Docker

- `Dockerfile`: Builds the Spring Boot application into a Docker image.
- `docker-compose.yml`: Orchestrates the Spring Boot application and MySQL database containers.

## Steps to Run the Project

1.  **Navigate to the project root directory** (where `pom.xml` and `docker-compose.yml` are located).

2.  **Build the Spring Boot application**: 
    ```bash
    ./mvnw clean install
    ```
    (If `mvnw` is not executable, run `chmod +x ./mvnw` first)

3.  **Start the Docker containers**: This will build the application image and start both the Spring Boot app and MySQL database.
    ```bash
    docker-compose up --build -d
    ```

4.  **Verify containers are running**: 
    ```bash
    docker-compose ps
    ```
    You should see `nexlearn-app` and `nexlearn-mysql` in a healthy state.

5.  The application will be accessible at `http://localhost:8080`.

## Postman Request Examples

Here are some Postman examples to test the API endpoints.

### 1. Create a Student (POST /api/students)

-   **URL**: `http://localhost:8080/api/students`
-   **Method**: `POST`
-   **Headers**: 
    -   `Content-Type`: `application/json`
-   **Body (raw, JSON)**:
    ```json
    {
        "name": "Alice Smith"
    }
    ```
-   **Expected JSON Response (201 Created)**:
    ```json
    {
        "id": 1,
        "name": "Alice Smith"
    }
    ```

### 2. Get All Students (GET /api/students)

-   **URL**: `http://localhost:8080/api/students`
-   **Method**: `GET`
-   **Expected JSON Response (200 OK)**:
    ```json
    [
        {
            "id": 1,
            "name": "Alice Smith"
        }
    ]
    ```

### 3. Add a Course (POST /api/courses)

-   **URL**: `http://localhost:8080/api/courses`
-   **Method**: `POST`
-   **Headers**: 
    -   `Content-Type`: `application/json`
-   **Body (raw, JSON)**:
    ```json
    {
        "courseName": "Introduction to AI",
        "credits": 3,
        "maxCapacity": 2
    }
    ```
-   **Expected JSON Response (201 Created)**:
    ```json
    {
        "id": 1,
        "courseName": "Introduction to AI",
        "credits": 3,
        "maxCapacity": 2,
        "currentEnrollment": 0
    }
    ```

### 4. Get All Courses (GET /api/courses)

-   **URL**: `http://localhost:8080/api/courses`
-   **Method**: `GET`
-   **Expected JSON Response (200 OK)**:
    ```json
    [
        {
            "id": 1,
            "courseName": "Introduction to AI",
            "credits": 3,
            "maxCapacity": 2,
            "currentEnrollment": 0
        }
    ]
    ```

### 5. Enroll a Student in a Course (POST /api/enrollments/enroll)

-   **URL**: `http://localhost:8080/api/enrollments/enroll?studentId=1&courseId=1`
-   **Method**: `POST`
-   **Expected JSON Response (201 Created)**:
    ```json
    {
        "id": 1,
        "student": {
            "id": 1,
            "name": "Alice Smith"
        },
        "course": {
            "id": 1,
            "courseName": "Introduction to AI",
            "credits": 3,
            "maxCapacity": 1,
            "currentEnrollment": 1
        },
        "enrollmentDate": "2026-04-02"
    }
    ```
    *(Note: `enrollmentDate` will reflect the current date)*

### 6. Attempt to Enroll in a Full Course (POST /api/enrollments/enroll)

-   **URL**: `http://localhost:8080/api/enrollments/enroll?studentId=2&courseId=1` (Assuming course 1 has maxCapacity 1 and is already full)
-   **Method**: `POST`
-   **Expected JSON Response (400 Bad Request)**:
    ```json
    {
        "timestamp": "2026-04-02T10:00:00.000+00:00",
        "status": 400,
        "error": "Bad Request",
        "message": "Course is full",
        "path": "/api/enrollments/enroll"
    }
    ```
    *(Note: `timestamp` will reflect the current time)*

### 7. View All Enrollments (GET /api/enrollments)

-   **URL**: `http://localhost:8080/api/enrollments`
-   **Method**: `GET`
-   **Expected JSON Response (200 OK)**:
    ```json
    [
        {
            "id": 1,
            "student": {
                "id": 1,
                "name": "Alice Smith"
            },
            "course": {
                "id": 1,
                "courseName": "Introduction to AI",
                "credits": 3,
                "maxCapacity": 1,
                "currentEnrollment": 1
            },
            "enrollmentDate": "2026-04-02"
        }
    ]
    ```

### 8. View Enrollments for a Specific Student (GET /api/enrollments/student/{studentId})

-   **URL**: `http://localhost:8080/api/enrollments/student/1`
-   **Method**: `GET`
-   **Expected JSON Response (200 OK)**:
    ```json
    [
        {
            "id": 1,
            "student": {
                "id": 1,
                "name": "Alice Smith"
            },
            "course": {
                "id": 1,
                "courseName": "Introduction to AI",
                "credits": 3,
                "maxCapacity": 1,
                "currentEnrollment": 1
            },
            "enrollmentDate": "2026-04-02"
        }
    ]
    ```

## Explanation of Changes

1.  **Service Layer Introduction**: Introduced `CourseService` and `EnrollmentService` interfaces and their `ServiceImpl` implementations. This separates business logic from controllers, making the code cleaner, more testable, and easier to maintain.
2.  **Error Handling**: Implemented custom error handling using `ResponseStatusException` in the service layer for cases like "Course not found", "Student not found", and "Course is full". This provides more descriptive error messages and appropriate HTTP status codes.
3.  **Controller Refactoring**: Updated `CourseController` and `EnrollmentController` to delegate business logic to the newly created service layer. This keeps controllers lean and focused on handling HTTP requests and responses.
4.  **New Endpoints**: Added `GET /api/enrollments` to view all enrollments, fulfilling a project requirement. Also added basic `StudentController` with `POST /api/students` and `GET /api/students` for easier testing and setup of student data.
5.  **Docker Configuration**: Modified `application.properties` to use an environment variable `MYSQL_HOST` for the database URL, defaulting to `localhost` for local runs and allowing `docker-compose.yml` to inject `mysql` as the host when running in Docker. This ensures seamless connectivity in both environments.
6.  **`docker-compose.yml` Enhancements**: Added `container_name` for easier identification and defined a custom `nexlearn-network` for better isolation and communication between services.

## List of Project Files

```
. 
├── Dockerfile
├── README.md
├── docker-compose.yml
├── pom.xml
└── src
    └── main
        ├── java
        │   └── com
        │       └── nexlearn
        │           └── microservice
        │               ├── NexlearnMicroserviceApplication.java
        │               ├── controller
        │               │   ├── CourseController.java
        │               │   ├── EnrollmentController.java
        │               │   ├── StudentController.java
        │               ├── model
        │               │   ├── Course.java
        │               │   ├── Enrollment.java
        │               │   └── Student.java
        │               ├── repository
        │               │   ├── CourseRepository.java
        │               │   ├── EnrollmentRepository.java
        │               │   └── StudentRepository.java
        │               └── service
        │                   ├── CourseService.java
        │                   ├── EnrollmentService.java
        │                   └── impl
        │                       ├── CourseServiceImpl.java
        │                       └── EnrollmentServiceImpl.java
        └── resources
            └── application.properties
```
