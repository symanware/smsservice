# Simple SMS routing service 

This is a simple SMS routing service that handles opt-outs and carrier selection.

##  About the service 

### Application flow for POST /message

1. A message is sent via a Swagger/Postman call using the POST /messages endpoint.
2. The controller receives the request and forwards the message to the service layer.
3. The service layer first checks whether the phone number exists in the Opt-Out list:
4. If the number is in the Opt-Out list, the message status is set to BLOCKED, and the message is saved to the repository.
5. If the number is not in the Opt-Out list, the request is routed to the respective carrier.
6. The carrier processes the message and sends a delivery status (e.g., DELIVERED) back to the service layer.
7. The service layer sets the status received from the carrier, saves the message to the repository, and then returns the final response to the controller, which in turn responds to the original API caller.

### Validations Rules

destinationNumber:

    Must not be null or blank.
    Must match the international phone number format, starting with a plus sign (+) and containing only digits.
    The length must be between 10 to 15 digits (excluding the + sign).
    Pattern Example: ^\+\\d{10,15}$

Opt-Out phoneNumber:

    The phoneNumber added to the Opt-Out list must be unique.
    The same number cannot be added more than once.


##  Technologies Used

- Java 24
- Spring Boot 3.5.0
- Maven 3.9.9
- H2 database
- JUnit 5
- Mockito


### Clone the Repository

    clone https://github.com/symanware/smsservice.git
    cd sms

    Build the Project
    With Maven: mvn clean install

    Run test cases
    With Maven: mvn test

    Run the Application
    mvn spring-boot:run

    Once the app starts, access the API at:

    http://localhost:8080/swagger-ui/index.html

### Core Features

1. **Send Message API** - `POST api/messages`
2. **Get Message Status** - `GET api/messages/{id}`
3. **Opt-out Management** - `POST api/optout/{phoneNumber}`

Send Message

    POST /api/messages
    Accept: application/json
    Content-Type: application/json

    {
        "destinationNumber": "+61491570156",
        "content": "First AU message",
        "format": "SMS"
    }

    RESPONSE: HTTP 201 (Created)
    Response body

    {
        "messageId": 1,
        "status": "DELIVERED"
    }


Get Message Status

    GET /api/messages/1
    Accept: application/json
    Content-Type: application/json

    RESPONSE: HTTP 200 (Ok)
    Response body

    {
        "id": 1,
        "destinationNumber": "+61234567890",
        "content": "First AU message",
        "format": "SMS",
        "status": "DELIVERED"
    }


Opt out phone number

    POST /api/optout/+61234567890
    Accept: application/json
    Content-Type: application/json

    RESPONSE: HTTP 201 (Created)
    Response body

    {
        Phone number +61234567890 opted out successfully.
    }


Send message for optout number

    POST /api/messages
    Accept: application/json
    Content-Type: application/json

    {
        "destinationNumber": "+61491570156",
        "content": "Second AU message",
        "format": "SMS"
    }

    RESPONSE: HTTP 201 (Created)
    Response body

    {
        "messageId": 2,
        "status": "BLOCKED"
    }

Validation Test cases

Validation for sending message

    POST /api/messages
    Accept: application/json
    Content-Type: application/json

    {
        "destinationNumber": " ",
        "content": "First AU message",
        "format": "SMS"
    }

    RESPONSE: HTTP 400 (Bad request)
    Response body

    {
        "statusCode": 400,
        "timestamp": "2025-06-09T10:20:13.135+00:00",
        "description": "uri=/api/messages",
        "messages": [
            "destinationNumber Phone number must be in international format (e.g., +1234567890)",
            "destinationNumber Phone number is required"
        ]
    }

Validation for optout number

    POST /api/optout/abcdfg
    Accept: application/json
    Content-Type: application/json

    Error message
    Please correct the following validation errors and try again.
    For 'phoneNumber': Value must follow pattern ^\+\d{10,15}$.



