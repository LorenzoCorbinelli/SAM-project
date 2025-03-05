#!/bin/bash
# Start the MySQL container
docker-compose up -d

# Wait for MySQL to be fully up and running
sleep 10

# Execute the MySQL command
docker exec -it MySQL-examManagement mysql -u root -p