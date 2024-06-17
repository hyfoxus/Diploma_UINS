# Diploma_UINS

This is a Spring Boot project for managing navigation data using JPA and exposing web endpoints.

## Running with Docker Compose

To run the project using Docker Compose, follow these steps:

1. Ensure Docker and Docker Compose are installed on your machine.
2. Navigate to the project directory.
3. Run the following command to start the services:

    ```sh
    docker-compose up --build
    ```

## Running Locally for Development

To run the project locally for development with a separate PostgreSQL container, follow these steps:

1. Ensure Docker is installed on your machine.
2. Start a PostgreSQL container:

    ```sh
    docker run --name postgres-dev -e POSTGRES_DB=mydb -e POSTGRES_USER=myuser -e POSTGRES_PASSWORD=mypassword -v $(pwd)/init.sql:/docker-entrypoint-initdb.d/init.sql -p 5432:5432 -d postgres:latest
    ```
3. List all running Docker containers to verify that the PostgreSQL container is up and running:

    ```sh
    docker ps
    ```

4. Check the logs of the PostgreSQL container to ensure it started correctly and the initialization script was executed:

    ```sh
    docker logs postgres-dev
    ```

5. Starting navigation application

    ```sh
    ./gradlew :navigation:bootRun
    ```

6. Starting navigation-ui application

    ```sh
    ./gradlew :navigation-ui:bootRun --args='--spring.profiles.active=dev'
    ```