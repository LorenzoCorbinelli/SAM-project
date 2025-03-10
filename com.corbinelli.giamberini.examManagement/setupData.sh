#!/bin/bash
BASE_URL="http://localhost:8080"
TEACHERS_ENDPOINT="teachers"
STUDENTS_ENDPOINT="students"
COURSES_ENDPOINT="courses"
EXAMS_ENDPOINT="exams"
ENROLLMENTS_ENDPOINT="enrollments"

# Function to send a POST request
send_post() {
    local endpoint=$1
    local data=$2
    local url="$BASE_URL/$endpoint"
    echo "Sending request to $url with data: $data"
    curl -X POST "$url" \
         -H "Content-Type: application/json" \
         -d "$data"
    echo
    echo "--------------------------------------------------------------------------"
    echo
}

# Create teachers
echo "Creating Teachers..."
echo
send_post "$TEACHERS_ENDPOINT" '{"name": "Vieri", "surname": "Derri", "email": "vieri.derri@example.com"}'
send_post "$TEACHERS_ENDPOINT" '{"name": "John", "surname": "Smith", "email": "john.smith@example.com"}'
send_post "$TEACHERS_ENDPOINT" '{"name": "Emily", "surname": "Johnson", "email": "emily.johnson@example.com"}'
send_post "$TEACHERS_ENDPOINT" '{"name": "Robert", "surname": "Miller", "email": "robert.miller@example.com"}'
send_post "$TEACHERS_ENDPOINT" '{"name": "Linda", "surname": "Davis", "email": "linda.davis@example.com"}'

# Create students
echo "Creating Students..."
echo
send_post "$STUDENTS_ENDPOINT" '{"name": "Alice", "surname": "Brown", "email": "alice.brown@example.com"}'
send_post "$STUDENTS_ENDPOINT" '{"name": "Bob", "surname": "Taylor", "email": "bob.taylor@example.com"}'
send_post "$STUDENTS_ENDPOINT" '{"name": "Charlie", "surname": "Wilson", "email": "charlie.wilson@example.com"}'
send_post "$STUDENTS_ENDPOINT" '{"name": "David", "surname": "Moore", "email": "david.moore@example.com"}'
send_post "$STUDENTS_ENDPOINT" '{"name": "Eve", "surname": "White", "email": "eve.white@example.com"}'

# Create courses and assign teachers
echo "Creating Courses..."
echo
send_post "$COURSES_ENDPOINT" '{"name": "Algebra", "description": "Introduction to Algebra", "teacher": {"id": 1}}'
send_post "$COURSES_ENDPOINT" '{"name": "Calculus", "description": "Calculus and its applications", "teacher": {"id": 2}}'
send_post "$COURSES_ENDPOINT" '{"name": "Physics I", "description": "Basic Physics", "teacher": {"id": 3}}'
send_post "$COURSES_ENDPOINT" '{"name": "Chemistry", "description": "Organic Chemistry", "teacher": {"id": 4}}'
send_post "$COURSES_ENDPOINT" '{"name": "Biology", "description": "General Biology", "teacher": {"id": 5}}'
send_post "$COURSES_ENDPOINT" '{"name": "Mathematics", "description": "Basic Mathematics", "teacher": {"id": 2}}'
send_post "$COURSES_ENDPOINT" '{"name": "Analysis I", "description": "Mathematical Analysis I", "teacher": {"id": 4}}'
send_post "$COURSES_ENDPOINT" '{"name": "Physics II", "description": "Advanced Physics", "teacher": {"id": 3}}'

# Create exams
echo "Creating Exams..."
echo
send_post "$EXAMS_ENDPOINT" '{"date": "2024-06-15", "course": {"id": 1, "name": "Algebra"}}'
send_post "$EXAMS_ENDPOINT" '{"date": "2024-06-15", "course": {"id": 2, "name": "Calculus"}}'
send_post "$EXAMS_ENDPOINT" '{"date": "2024-06-16", "course": {"id": 3, "name": "Physics I"}}'
send_post "$EXAMS_ENDPOINT" '{"date": "2024-06-17", "course": {"id": 4, "name": "Chemistry"}}'
send_post "$EXAMS_ENDPOINT" '{"date": "2024-06-18", "course": {"id": 5, "name": "Biology"}}'
send_post "$EXAMS_ENDPOINT" '{"date": "2024-06-10", "course": {"id": 6, "name": "Mathematics"}}'
send_post "$EXAMS_ENDPOINT" '{"date": "2024-07-11", "course": {"id": 7, "name": "Analysis I"}}'
send_post "$EXAMS_ENDPOINT" '{"date": "2024-07-19", "course": {"id": 8, "name": "Physics II"}}'
send_post "$EXAMS_ENDPOINT" '{"date": "2024-07-20", "course": {"id": 2, "name": "Calculus"}}'
send_post "$EXAMS_ENDPOINT" '{"date": "2024-07-15", "course": {"id": 4, "name": "Chemistry"}}'

# Create enrollments
echo "Creating Enrollments..."
echo
send_post "$ENROLLMENTS_ENDPOINT" '{"student": {"id": 1}, "exam": {"id": 1}}'
send_post "$ENROLLMENTS_ENDPOINT" '{"student": {"id": 1}, "exam": {"id": 2}}'
send_post "$ENROLLMENTS_ENDPOINT" '{"student": {"id": 2}, "exam": {"id": 1}}'
send_post "$ENROLLMENTS_ENDPOINT" '{"student": {"id": 2}, "exam": {"id": 4}}'
send_post "$ENROLLMENTS_ENDPOINT" '{"student": {"id": 3}, "exam": {"id": 5}}'
send_post "$ENROLLMENTS_ENDPOINT" '{"student": {"id": 3}, "exam": {"id": 8}}'
send_post "$ENROLLMENTS_ENDPOINT" '{"student": {"id": 4}, "exam": {"id": 2}}'
send_post "$ENROLLMENTS_ENDPOINT" '{"student": {"id": 4}, "exam": {"id": 7}}'
send_post "$ENROLLMENTS_ENDPOINT" '{"student": {"id": 5}, "exam": {"id": 4}}'
send_post "$ENROLLMENTS_ENDPOINT" '{"student": {"id": 5}, "exam": {"id": 10}}'

# Completion message
echo -e "\nDatabase setup completed!\n"

# Wait for input before closing
read -n 1 -s -r -p "Press any key to exit..."
