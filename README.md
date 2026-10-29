# Email Service

## Overview

Email Service is a Spring Boot microservice that listens for order messages from RabbitMQ. It processes incoming messages, converts them into order objects, and creates an order confirmation message.

The service also includes the `spring-messenger` library, which provides components for sending emails using Spring's `JavaMailSender` and Gmail SMTP.

## Technologies

* Java 17
* Spring Boot 4.0.6
* Spring AMQP
* RabbitMQ
* Spring Mail
* Spring Security
* OAuth2 Resource Server
* Maven
* Jackson
* Lombok
* SpringDoc OpenAPI

## Features

* Listens for messages from the RabbitMQ queue `email-queue`.
* Converts incoming JSON messages into order objects.
* Represents order information, including customer name, order items, and total price.
* Creates an order confirmation message containing the customer's name, ordered items, and total price.
* Includes email-sending components through the `spring-messenger` library.
* Uses environment variables to configure RabbitMQ and email credentials.

## Architecture

The service uses RabbitMQ to receive messages from other microservices.

### Message Flow

1. Another service publishes a message to the RabbitMQ queue `email-queue`.
2. `MessageSubscriber` receives the message.
3. Jackson converts the message into an `Order` object.
4. The service constructs an order confirmation message.
5. The confirmation message is printed to the console.

The `spring-messenger` library provides additional components for sending email messages through Gmail SMTP. However, the current `MessageSubscriber` implementation does not directly invoke these components, so automatic email delivery through this message flow has not been confirmed.

## RabbitMQ Configuration

`RabbitConfig` declares the queue used by the service:

```text
email-queue
```

`MessageSubscriber` listens to this queue using Spring AMQP's `@RabbitListener` annotation.

The application connects to RabbitMQ using environment variables. SSL is enabled in the RabbitMQ configuration.

## Order Models

### Order

The `Order` class represents an order message and contains:

* `orderDate`: The date and time of the order.
* `customerName`: The customer's name.
* `orderItems`: A list of ordered items.
* `totalPrice`: The total order price.

### OrderItem

The `OrderItem` class represents an individual item in an order and contains:

* `name`: The product name.
* `price`: The product price.
* `quantity`: The quantity ordered.

## Email Integration

The project includes the `spring-messenger` library, which provides the following components:

* `EmailController`: Handles email-related requests.
* `MessageService`: Delegates email-sending operations.
* `Messenger`: Defines the email-sending interface.
* `EmailSender`: Creates and sends email messages using Spring's `JavaMailSender`.

The email sender uses `spring.mail.username` as the sender address and accepts a recipient address and message text.

**Current implementation:** The RabbitMQ subscriber constructs and prints an order confirmation message. It does not currently call `MessageService` or `EmailSender` directly.

## Configuration

The application uses environment variables for RabbitMQ and Gmail SMTP configuration.

| Environment Variable | Purpose                                |
| -------------------- | -------------------------------------- |
| `RABBITMQ_HOST`      | RabbitMQ server hostname               |
| `RABBITMQ_PORT`      | RabbitMQ server port                   |
| `RABBITMQ_USERNAME`  | RabbitMQ username                      |
| `RABBITMQ_PASSWORD`  | RabbitMQ password                      |
| `RABBITMQ_VHOST`     | RabbitMQ virtual host                  |
| `MAIL_PORT`          | SMTP server port                       |
| `MAIL_USERNAME`      | Gmail account used as the sender       |
| `MAIL_PASSWORD`      | Email account password or app password |

These values must be configured in the environment before running the application.

Do not commit passwords, tokens, or other sensitive credentials to the repository.

## Dependencies

The application uses Maven to manage its dependencies.

The `spring-messenger` library is included as a Maven dependency and is hosted in GitHub Packages.

The project also uses Spring AMQP for RabbitMQ messaging and Spring Mail for email integration.

## Running the Application

Make sure the required environment variables are configured and that the RabbitMQ server is accessible.

On Windows, run the application using the Maven Wrapper:

```powershell
.\mvnw.cmd spring-boot:run
```

On macOS or Linux:

```bash
./mvnw spring-boot:run
```

## Current Limitations

* The RabbitMQ subscriber currently prints the confirmation message to the console rather than directly sending it by email.
* Automatic email delivery from the order message flow has not been confirmed.
* The RabbitMQ message structure must match the fields expected by the `Order` model for the order information to be processed correctly.
