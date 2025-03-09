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
read -n 1