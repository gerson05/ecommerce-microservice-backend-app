@echo off
echo Starting Jenkins with Docker support...

REM Crear volumen para Jenkins
docker volume create jenkins_home

REM Levantar Jenkins
docker run -d ^
  --name jenkins ^
  -p 8080:8080 ^
  -p 50000:50000 ^
  -v jenkins_home:/var/jenkins_home ^
  -v /var/run/docker.sock:/var/run/docker.sock ^
  -v /usr/bin/docker:/usr/bin/docker ^
  jenkins/jenkins:lts

echo Jenkins started successfully!
echo Access Jenkins at: http://localhost:8080
echo Initial admin password:
docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
