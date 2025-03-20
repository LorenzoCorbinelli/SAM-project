# SAM-project

The **Exam Management System** is a web-based application designed to automate and streamline the management of exams at a university. The system accommodates several functionalities, such as:

- **Exam Registration**: Allows students to register for exams.
- **View Subscriptions**: Allow students to see all their exam subscriptions.
- **View Courses**: Allow viewing the list of the courses offered.
- **Insert a Course**: Allow teachers to create a new course.
- **View Exam Calls**: Allow viewing all the published exam dates.

## Architecture

The application follows a **layered architecture** consisting of the following layers:

- **Domain Model**: Represents the core entities of the system such as `Student`, `Teacher`, `Course`, `Exam`, and `Enrollment`.
- **Data Access Objects (DAOs)**: Interacts with the database and encapsulates the details of data persistence.
- **Service Layer**: Manages business logic and interacts with DAOs to execute necessary operations.
- **Resource Layer**: Exposes services through JAX-RS (Java API for RESTful Web Services), enabling easy integration with client applications or other systems.

The system uses **Java Persistence API (JPA)** to manage interactions with a **MySQL database**, which is hosted in a Docker container for ease of deployment and scalability.

**Contexts and Dependency Injection (CDI)** is used to manage dependencies among different layers of the application to ensure a modular and easy-to-maintain code base.

## Getting Started

### Prerequisites

Before running the application, make sure you have the following installed:

- **Java 17 or later**
- **Maven**
- **Docker**
- **Docker compose**

### Setting up MySQL with Docker

The MySQL database is containerized using **Docker**. To start the database container, run the following command in the project directory:
<code>docker-compose up -d</code>

### Running the Application
1. Build the application JAR file with dependencies:
   <code>mvn clean package</code>
2. Launch the application using the generated JAR file:
   <code>java -jar target/examManagement-0.0.1-SNAPSHOT-jar-with-dependencies.jar</code>

Inside the project directory, there is also the [setupData.sh](com.corbinelli.giamberini.examManagement/setupData.sh) file that can be used to insert some records in the DB.

## API Reference
### 👩‍🎓 Student APIs
- `GET /students/{id}`: Get Student Info by ID
  - *Parameter:*
    - `id` (Long): **Required**. ID of the student to fetch
      
  - `POST /students`: Create New Student
  - *Parameter:*
    - `student` (Student): **Required**. Student object to create

- `DELETE /students/{id}`: Delete Student
  - *Parameter:*
    - `id` (Long): **Required**. ID of the student to delete

### 👨‍🏫 Teacher APIs
- `GET /teachers/{id}`: Get Teacher Info by ID
  - *Parameter:*
    - `id` (Long): **Required**. ID of the teacher to fetch

- `POST /teachers`: Create New Teacher
  - *Parameter:*
    - `teacher` (Teacher): **Required**. Teacher object to create

- `DELETE /teachers/{id}`: Delete Teacher
  - *Parameter:*
    - `id` (Long): **Required**. ID of the teacher to delete
      
### 📚 Course APIs
- `GET /courses/id/{id}`: Get Course Info by ID
  - *Parameter:*
    - `id` (Long): **Required**. ID of the course to fetch

- `GET /courses/name/{name}`: Get Course Info by Name
  - *Parameter:*
    - `name` (String): **Required**. Name of the course to fetch

- `GET /courses/all`: Get All Courses
  - No parameters required.

- `GET /courses/teacher/{id}`: Get Courses of Teacher
  - *Parameter:*
    - `id` (Long): **Required**. ID of the teacher to fetch courses for

- `POST /courses`: Create New Course
  - *Parameter:*
    - `course` (Course): **Required**. Course object to create

- `DELETE /courses/{id}`: Delete Course
  - *Parameter:*
    - `id` (Long): **Required**. ID of the course to delete

### 📝 Exam APIs
- `GET /exams/id/{id}`: Get Exam Info by ID
  - *Parameter:*
    - `id` (Long): **Required**. ID of the exam to fetch

- `GET /exams/all`: Get All Exams
  - No parameters required.

- `GET /exams/course/{id}`: Get Exam Dates of Course
  - *Parameter:*
    - `id` (Long): **Required**. ID of the course to fetch exam dates for

- `GET /exams/date/{date}`: Get Exam Courses on Date
  - *Parameter:*
    - `date` (String): **Required**. Date to fetch exam courses for

- `GET /exams/period?start={start}&end={end}`: Get Exams in a Period
  - *Parameters:*
    - `start` (String): **Required**. Start date of the period
    - `end` (String): **Required**. End date of the period

- `POST /exams`: Create New Exam
  - *Parameter:*
    - `exam` (Exam): **Required**. Exam object to create

- `DELETE /exams/{id}`: Delete Exam
  - *Parameter:*
    - `id` (Long): **Required**. ID of the exam to delete
   
 ### 📋 Enrollment APIs
 - `GET /enrollments/id/{id}`: Get Enrollment Info by ID
  - *Parameter:*
    - `id` (Long): **Required**. ID of the enrollment to fetch

- `GET /enrollments/student/{id}`: Get Enrollments of Student
  - *Parameter:*
    - `id` (Long): **Required**. ID of the student to fetch enrollments for

- `GET /enrollments/exam/{id}`: Get Enrollments of Exam
  - *Parameter:*
    - `id` (Long): **Required**. ID of the exam to fetch enrollments for

- `GET /enrollments/trials?student={studentID}&course={courseID}`: Get Trials for an Exam by Student and Course
  - *Parameters:*
    - `student` (Long): **Required**. ID of the student
    - `course` (Long): **Required**. ID of the course

- `POST /enrollments`: Create New Enrollment
  - *Parameter:*
    - `enrollment` (Enrollment): **Required**. Enrollment object to create

- `DELETE /enrollments/{id}`: Delete Enrollment
  - *Parameter:*
    - `id` (Long): **Required**. ID of the enrollment to delete
