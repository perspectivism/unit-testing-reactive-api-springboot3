# Unit Testing Reactive RESTful Web Service Methods with Reactive Relational Databases using Spring Boot 3
This project provides a Java port of [Unit Testing Asynchronous Web API Controller Methods with Asynchronous Entity Framework Mocks in .NET 5](https://github.com/perspectivism/unit-testing-api-ef-net5). 

The project is a proof-of-concept RESTful Web API application that interfaces with customer data stored in an in-memory database.

## API Endpoints
The application exposes the following endpoints:

* `GET /api/customers` - Get all customers
* `GET /api/customers/{id}` - Retrieve a single customer by ID
* `POST /api/customers` - Add a new customer
* `PUT /api/customers` - Update an existing customer
* `DELETE /api/customers/{id}` - Delete a customer

## Prerequisites
The application is developed with Java and uses the [Java SE Development Kit 17](https://www.oracle.com/java/technologies/downloads/). It was written using the [VS Code](https://code.visualstudio.com/) editor.

For installing the prerequisites and setting up the development environment, follow the detailed instructions for developing with [Spring Boot in Visual Studio Code](https://code.visualstudio.com/docs/java/java-spring-boot).

## Setup and Installation
From the command line:

```bash
# Clone this repository
$ git clone https://github.com/perspectivism/unit-testing-reactive-api-springboot3.git

# Navigate to the repository
$ cd unit-testing-reactive-api-springboot3

# Install dependencies
$ ./mvnw clean install
```

## Running the application
After successfully installing the dependencies, you can run the application directly from VS Code or by using the following command:

```bash
# Start the application
$ ./mvnw spring-boot:run
```

The application will start running at: `http://localhost:8080`. To view all customers navigate to `http://localhost:8080/api/customers`

## Running the tests
To ensure that the application functions as expected, run the following command to execute the unit tests:

```bash
# Run the tests
$ ./mvnw test
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change. Please make sure to update tests as appropriate.

## License
This project is licensed under version 2.0 of the [Apache License](https://www.apache.org/licenses/LICENSE-2.0).