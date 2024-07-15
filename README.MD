# Cart Service

## Requirements

If you are running the application locally, please ensure you have the following:

1. **Java**: Version 17 or higher
2. **PostgreSQL**: Version 15
3. **Database**: Manually create a database named `cart` if running locally. Flyway will take care of creating tables
   and inserting data.
4. **Gradle**: Version 8.5 or higher

## Approach

1. **Pre-filled Data**:
    - The `product`, `users`, and `address` tables have pre-filled values since the cart service does not cover external
      service scope.
    - The cart service uses this data to add items, delete items, update items, get items, and calculate the total for
      the cart.

2. **External Service and Files**:
    - External services and files are located inside the `ext` package, which is separate from the actual service
      package. They can also be within the service and controller marked as `ext`.

3. **API Documentation**:
    - SwaggerUI has been added to display the APIs.

4. **Authentication Service**:
    - An authentication service has not been implemented as it was not part of the requirements and due to time
      constraints.

5. **Testing**:
    - Used JaCoCo for testing both integration and unit tests.
    - Excluded packages that are models and non-relevant from core logic from coverage.
    - You can see test results in build/reports/jacoco/test/html/index.html

6. **Database Migrations**:
    - Used Flyway to push tables and data into the database.

7. **Docker**:
    - Used Docker container to handle project execution.

8. **Accessing SwaggerUI**
    - Once the application is running, open your browser and navigate to: http://localhost:8089/swagger-ui/index.html#/.

## Running the Application

To run the application using Docker, use the following command:

```sh
docker-compose build && docker-compose up
