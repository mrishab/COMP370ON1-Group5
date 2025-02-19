# ClassPlanner Backend

## Prerequisites

- Docker
- Docker Compose
- Make
- Visual Studio Code (VSCode)
- VSCode Extensions:
  - Lombok
  - Java Extension Pack
  - Spring Boot Extension Pack

## Setup

1. Clone the repository:
    ```sh
    git clone git@github.com:mrishab/COMP370ON1-Group5.git
    cd classplanner/spring-java-backend
    ```

2. Create an `.env` file based on the given sample:
    ```sh
    cp template.env .env
    ```

3. Install dependencies and build the project:
    ```sh
    make install
    ```

4. Run the project:
    ```sh
    make run
    ```

## Accessing the Application

- The application will be running on `http://localhost:8080`.
- PostgreSQL and pgAdmin instances are also set up:
  - PostgreSQL: `localhost:5432`
  - pgAdmin: `https://localhost:8443`

## Environment Variables

The environment variables are defined in the `.env` file:
```
# Spring JDBC
POSTGRES_HOST=classplanner_postgres
POSTGRES_PORT=5432
POSTGRES_DB=postgres
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres

# PG Admin
PGADMIN_DEFAULT_EMAIL=hello@hello.com
PGADMIN_DEFAULT_PASSWORD=pgadmin
```

## Using Postman

Use Postman to call the endpoints of the application. The base URL for the endpoints is `http://localhost:8080`. The endpoints are defined the `*Controller.java` files.

## Database Migrations

Flyway performs migration during boot up. The migration files are stored in `db/migration`. Add a new file to create a new table.
