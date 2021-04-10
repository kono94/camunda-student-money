#bin/bash
mvn clean package
docker cp ./target/student-money.war camunda:/camunda/webapps/
