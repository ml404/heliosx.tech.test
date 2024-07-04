### README
# Consultation Management Service

This project is a subscription management service built with Spring Boot. It provides APIs to manage customers, subscriptions, and other related entities. The service uses Swagger for API documentation and H2 for the in-memory database.

## Notes

- **Trade-offs**:
    - For simplicity, an in-memory H2 database is used, which is suitable for development and testing but not for production.
    - Error handling is pretty basic and should be enhanced for better user feedback and debugging.
    - Authentication and authorisation are not implemented in this example. For a production system, would look to integrate security measures like Spring Security and JWT.
    - Metric tracking using something akin to prometheus would be desirable to be able to track things such as traffic amount, response time, and request outcomes in an easily visualised medium of grafana.
    - Would increase logging across the code path for greater observability in elasticsearch/splunk.
    - Docker containerisation would be used
    - Would have made two layers of DTOs, one to be served to the UI, one to be used for the database (could strip out the ids served to the web layer as they may not care)
    - Did not have time to write unit tests for the controllers
    - Extremely simple logic used for whether medication can be prescribed (all answers come back as no), would abstract that out to a service layer for calculation
    - Would implement a question provider layer that could give different answers dynamically dependant on the type of product being asked about

## Instructions to Run the Application Locally

### Prerequisites

- Java 22
- Maven 3.6 or higher

### Steps to Run

1. **Clone the Repository**:
    ```sh
    git clone https://github.com/ml404/heliosx.tech.test
    cd heliosx.tech.test
    ```

2. **Build the Application**:
    ```sh
    mvn clean install
    ```

3. **Run the Application**:
    ```sh
    mvn spring-boot:run
    ```

4. **Access Swagger UI**:
   Open your web browser and navigate to `http://localhost:8080/swagger-ui.html` to explore the APIs.

## API Usage

### Example cURL Commands (tested via windows command prompt)

#### Create a Customer
```sh
curl -X POST "http://localhost:8080/api/customers" -H "Content-Type: application/json" -d "{\"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"password\": \"password123\"}"
```

#### Get All Customers
```sh
curl -X GET "http://localhost:8080/api/customers"
```

#### Get a Customer by ID
```sh
curl -X GET "http://localhost:8080/api/customers/1"
```

#### Update a Customer
```sh
curl -X PUT "http://localhost:8080/api/customers/1" -H "Content-Type: application/json" -d "{\"id\": 1, \"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"password\": \"newPassword\"}"
```

#### Delete a Customer
```sh
curl -X DELETE "http://localhost:8080/api/customers/1"
```

### Evaluation of prescription suitability

```sh
curl -X GET "http://localhost:8080/api/answers/evaluate/1" -H "Content-Type: application/json"
```
### Example Postman Collection

You can import the following Postman collection to test the APIs:

```json
{
    "info": {
        "name": "Subscription Management Service",
        "_postman_id": "abcdef12-3456-7890-abcd-ef1234567890",
        "description": "API collection for Subscription Management Service",
        "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
    },
    "item": [
        {
            "name": "Create Customer",
            "request": {
                "method": "POST",
                "header": [
                    {
                        "key": "Content-Type",
                        "value": "application/json"
                    }
                ],
                "body": {
                    "mode": "raw",
                    "raw": "{\"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"password\": \"password123\"}"
                },
                "url": {
                    "raw": "http://localhost:8080/api/customers",
                    "protocol": "http",
                    "host": [
                        "localhost"
                    ],
                    "port": "8080",
                    "path": [
                        "api",
                        "customers"
                    ]
                }
            },
            "response": []
        },
        {
            "name": "Get All Customers",
            "request": {
                "method": "GET",
                "header": [],
                "url": {
                    "raw": "http://localhost:8080/api/customers",
                    "protocol": "http",
                    "host": [
                        "localhost"
                    ],
                    "port": "8080",
                    "path": [
                        "api",
                        "customers"
                    ]
                }
            },
            "response": []
        },
        {
            "name": "Get Customer by ID",
            "request": {
                "method": "GET",
                "header": [],
                "url": {
                    "raw": "http://localhost:8080/api/customers/1",
                    "protocol": "http",
                    "host": [
                        "localhost"
                    ],
                    "port": "8080",
                    "path": [
                        "api",
                        "customers",
                        "1"
                    ]
                }
            },
            "response": []
        },
        {
            "name": "Update Customer",
            "request": {
                "method": "PUT",
                "header": [
                    {
                        "key": "Content-Type",
                        "value": "application/json"
                    }
                ],
                "body": {
                    "mode": "raw",
                    "raw": "{\"id\": 1, \"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"password\": \"newPassword\"}"
                },
                "url": {
                    "raw": "http://localhost:8080/api/customers/1",
                    "protocol": "http",
                    "host": [
                        "localhost"
                    ],
                    "port": "8080",
                    "path": [
                        "api",
                        "customers",
                        "1"
                    ]
                }
            },
            "response": []
        },
        {
            "name": "Delete Customer",
            "request": {
                "method": "DELETE",
                "header": [],
                "url": {
                    "raw": "http://localhost:8080/api/customers/1",
                    "protocol": "http",
                    "host": [
                        "localhost"
                    ],
                    "port": "8080",
                    "path": [
                        "api",
                        "customers",
                        "1"
                    ]
                }
            },
            "response": []
        }
    ]
}
```

To use this collection:
1. Open Postman.
2. Import the above JSON as a new collection.
3. Execute the requests to interact with the Subscription Management Service.

---