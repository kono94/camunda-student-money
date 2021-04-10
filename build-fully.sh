docker-compose up -d
echo "Trying to delete old container..."
docker stop maven
docker rm maven
docker run -d --name maven maven:3.6.0-jdk-11-slim sh -c 'tail -f /dev/null'
echo "Detached docker run command, waiting for container to be fully runnable... (10 SECONDS)"
sleep 10
docker exec maven sh -c 'mkdir /docker'
docker cp ./pom.xml maven:/docker/
docker cp src/ maven:/docker/
docker exec maven sh -c 'cd /docker/ && mvn clean package'
echo "Waiting for camunda to be fully up and running... (15 SECONDS)"
sleep 15
docker cp maven:/docker/target/student-money.war ./
docker cp ./student-money.war camunda:/camunda/webapps/
docker stop maven
docker rm maven
sleep 5
start chrome "http://wwww.localhost:8080/camunda/"
