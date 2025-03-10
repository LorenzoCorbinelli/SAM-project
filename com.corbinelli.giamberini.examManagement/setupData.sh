#!/bin/bash

# Create teachers
curl -X POST http://localhost:8080/teachers \
     -H "Content-Type: application/json" \
     -d '{"name": "Vieri", "surname": "Derri", "email": "vieri.derri@example.com"}'

curl -X POST http://localhost:8080/teachers \
     -H "Content-Type: application/json" \
     -d '{"name": "John", "surname": "Smith", "email": "john.smith@example.com"}'

curl -X POST http://localhost:8080/teachers \
     -H "Content-Type: application/json" \
     -d '{"name": "Emily", "surname": "Johnson", "email": "emily.johnson@example.com"}'

curl -X POST http://localhost:8080/teachers \
     -H "Content-Type: application/json" \
     -d '{"name": "Robert", "surname": "Miller", "email": "robert.miller@example.com"}'

curl -X POST http://localhost:8080/teachers \
     -H "Content-Type: application/json" \
     -d '{"name": "Linda", "surname": "Davis", "email": "linda.davis@example.com"}'

# Create students
curl -X POST http://localhost:8080/students \
     -H "Content-Type: application/json" \
     -d '{"name": "Alice", "surname": "Brown", "email": "alice.brown@example.com"}'

curl -X POST http://localhost:8080/students \
     -H "Content-Type: application/json" \
     -d '{"name": "Bob", "surname": "Taylor", "email": "bob.taylor@example.com"}'

curl -X POST http://localhost:8080/students \
     -H "Content-Type: application/json" \
     -d '{"name": "Charlie", "surname": "Wilson", "email": "charlie.wilson@example.com"}'

curl -X POST http://localhost:8080/students \
     -H "Content-Type: application/json" \
     -d '{"name": "David", "surname": "Moore", "email": "david.moore@example.com"}'

curl -X POST http://localhost:8080/students \
     -H "Content-Type: application/json" \
     -d '{"name": "Eve", "surname": "White", "email": "eve.white@example.com"}'

# Create courses and assign teachers
curl -X POST http://localhost:8080/courses \
     -H "Content-Type: application/json" \
     -d '{"name": "Algebra", "description": "Introduction to Algebra", "teacher": {"id": 1}}'

curl -X POST http://localhost:8080/courses \
     -H "Content-Type: application/json" \
     -d '{"name": "Calculus", "description": "Calculus and its applications", "teacher": {"id": 2}}'

curl -X POST http://localhost:8080/courses \
     -H "Content-Type: application/json" \
     -d '{"name": "Physics", "description": "Basic Physics", "teacher": {"id": 3}}'

curl -X POST http://localhost:8080/courses \
     -H "Content-Type: application/json" \
     -d '{"name": "Chemistry", "description": "Organic Chemistry", "teacher": {"id": 4}}'

curl -X POST http://localhost:8080/courses \
     -H "Content-Type: application/json" \
     -d '{"name": "Biology", "description": "General Biology", "teacher": {"id": 5}}'

# Create exams for each course
curl -X POST http://localhost:8080/exams \
     -H "Content-Type: application/json" \
     -d '{"date": "2024-06-15", "course": {"id": 1, "name": "Algebra"}}'

curl -X POST http://localhost:8080/exams \
     -H "Content-Type: application/json" \
     -d '{"date": "2024-06-15", "course": {"id": 2, "name": "Calculus"}}'

curl -X POST http://localhost:8080/exams \
     -H "Content-Type: application/json" \
     -d '{"date": "2024-06-16", "course": {"id": 3, "name": "Physics"}}'

curl -X POST http://localhost:8080/exams \
     -H "Content-Type: application/json" \
     -d '{"date": "2024-06-17", "course": {"id": 4, "name": "Chemistry"}}'

curl -X POST http://localhost:8080/exams \
     -H "Content-Type: application/json" \
     -d '{"date": "2024-06-18", "course": {"id": 5, "name": "Biology"}}'

# Create another exam with the same date as the first one
curl -X POST http://localhost:8080/exams \
     -H "Content-Type: application/json" \
     -d '{"date": "2024-06-15", "course": {"id": 1, "name": "Algebra"}}'

# Create enrollments for each student with full student and exam JSON
curl -X POST http://localhost:8080/enrollments \
     -H "Content-Type: application/json" \
     -d '{
           "student": {"id": 1, "name": "Alice", "surname": "Brown", "email": "alice.brown@example.com"},
           "exam": {"id": 1, "date": "2024-06-15", "course": {"id": 1, "name": "Algebra"}}
         }'
echo "\n"
curl -X POST http://localhost:8080/enrollments \
     -H "Content-Type: application/json" \
     -d '{
           "student": {"id": 2, "name": "Bob", "surname": "Taylor", "email": "bob.taylor@example.com"},
           "exam": {"id": 2, "date": "2024-06-15", "course": {"id": 2, "name": "Calculus"}}
         }'
echo "\n"
curl -X POST http://localhost:8080/enrollments \
     -H "Content-Type: application/json" \
     -d '{
           "student": {"id": 3, "name": "Charlie", "surname": "Wilson", "email": "charlie.wilson@example.com"},
           "exam": {"id": 3, "date": "2024-06-16", "course": {"id": 3, "name": "Physics"}}
         }'

curl -X POST http://localhost:8080/enrollments \
     -H "Content-Type: application/json" \
     -d '{
           "student": {"id": 4, "name": "David", "surname": "Moore", "email": "david.moore@example.com"},
           "exam": {"id": 4, "date": "2024-06-17", "course": {"id": 4, "name": "Chemistry"}}
         }'
echo "\n"
curl -X POST http://localhost:8080/enrollments \
     -H "Content-Type: application/json" \
     -d '{
           "student": {"id": 5, "name": "Eve", "surname": "White", "email": "eve.white@example.com"},
           "exam": {"id": 5, "date": "2024-06-18", "course": {"id": 5, "name": "Biology"}}
         }'
echo "\n"
echo "Database setup completed!"


# Wait for input before closing
echo "Press any key to exit..."
read -n 1  # Wait for user to press a key