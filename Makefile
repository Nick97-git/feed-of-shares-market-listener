up:
	mvn clean package -DskipTests
	docker-compose up

down:
	docker-compose down
