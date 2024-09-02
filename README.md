# SpringWork3

## Project Description
**SpringWork3** is a comprehensive food ordering system developed using Java and Spring Boot.
It offers a seamless experience for users to manage their cart items, place orders, and process payments.
The system leverages Kafka for robust messaging and a SQL database for reliable data persistence.
This application is designed with scalability and modularity in mind, making it suitable for both learning and production environments.

## Features
- **User Management**: Register and manage users within the system.
- **Cart Management**: Add, update, and remove items in a user’s cart.
- **Order Processing**: Create orders from cart items and handle payment processing.
- **Payment Integration**: Process payments securely and handle different payment methods.
- **Kafka Messaging**: Utilizes Kafka for handling asynchronous order and payment processing.
- **Database Persistence**: Stores user, order, payment, and cart data in a relational database.
- **Notification Service**: Will be added in the future to notify users about order status and updates.


## Technologies Used
- **Java**: Core language for the application.
- **Spring Boot**: Framework for building the application.
- **Maven**: Build automation tool.
- **SQL**: Relational database for data storage.
- **Kafka**: Distributed streaming platform for messaging.
- **Lombok**: To reduce boilerplate code for models.
- **Docker**: For containerizing the application.

## Setup Instructions

### Prerequisites
- **Java 17+**
- **Maven 3.6+**
- **Kafka** (Locally or on Docker)
- **MySQL/PostgreSQL/Any SQL Database** (Locally or on Docker)

### 1. Clone the repository
```sh
git clone https://github.com/sogutemir/SpringWork3.git
cd SpringWork3
```

### 2. Build the project
```sh
mvn clean install -DskipTests
```

### 3. Database Setup
- **Configure the SQL database**:
    - Open the `src/main/resources/application.properties` file.
    - Update the following properties with your database credentials:
      ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/springwork3
      spring.datasource.username=root
      spring.datasource.password=yourpassword
      ```
- **Run SQL schema scripts**:
    - Initialize the database schema using the provided SQL scripts located in the `src/main/resources` directory.

### 4. Kafka Setup
- **Ensure Kafka is running**:
    - If using Docker, you can start Kafka with the following Docker command:
      ```sh
      docker-compose up -d kafka
      ```
    - **Create Kafka topics**:
        - `order-topic`
        - `payment-processed-topic`
        - `order-update-topic`

### 5. Run the application
```sh
mvn spring-boot:run
```

### 6. (Optional) Running with Docker
- **Build Docker image**:
  ```sh
  docker build -t springwork3 .
  ```
- **Run the application**:
  ```sh
  docker run -p 8080:8080 springwork3
  ```

## Usage

### Adding Products to Cart
- **Endpoint**: `POST /api/cart-items/add`
- **Description**: Adds products to the user's cart.
- **Request Param**: `userId` (Long)
- **Request Body**: List of `ProductQuantity` objects.
- **Example Request**:
  ```json:
  POST http://localhost:8082/api/cart-items/add?userId=1
  Content-Type: application/json
  
  [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 2,
      "quantity": 1
    }
  ]
  ```

### Creating Order from Cart
- **Endpoint**: `POST /api/cart-items/create-order`
- **Description**: Creates an order from the user's cart and processes the payment.
- **Request Param**: `userId` (Long)
- **Request Body**: `PaymentDTO`
- **Example Request**:
  ```json:
  POST http://localhost:8082/api/cart-items/create-order?userId=1
  Content-Type: application/json
  
  {
    "paymentMethod": "CREDIT_CARD",
    "paymentDate": "2024-09-02T12:34:56",
    "amount": 0.0
  }
  ```

### Removing Products from Cart
- **Endpoint**: `POST /api/cart-items/remove`
- **Description**: Removes products from the user's cart.
- **Request Param**: `userId` (Long)
- **Request Body**: List of `ProductQuantity` objects.
- **Example Request**:
  ```json:
  POST http://localhost:8082/api/cart-items/remove?userId=1
  Content-Type: application/json
  
  [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 2,
      "quantity": 1
    }
  ]
  ```

### Getting Cart Items by User ID
- **Endpoint**: `GET /api/cart-items/{userId}`
- **Description**: Retrieves all cart items for a specific user.
- **Path Variable**: `userId` (Long)
- **Example Request**:
  ```sh
  GET http://localhost:8082/api/cart-items/1
  ```

## Database Schema
- **Users**: Stores user information.
- **Orders**: Stores order details linked to users.
- **Payments**: Stores payment details linked to orders.
- **Cart Items**: Stores products added to the user's cart.

### Entity Relationships:
- **One-to-Many**: `User` -> `Orders`
- **One-to-One**: `Order` -> `Payment`
- **Many-to-Many**: `CartItem` with `Product`

## Kafka Topics
- **order-topic**: Receives messages when an order is created.
- **payment-processed-topic**: Sends messages when a payment is processed successfully.
- **order-update-topic**: Sends messages when there is an update to an order's status.

## Contributing
1. **Fork the repository**.
2. **Create a new branch** (`git checkout -b feature-branch`).
3. **Make your changes**.
4. **Commit your changes** (`git commit -m 'Add some feature'`).
5. **Push to the branch** (`git push origin feature-branch`).
6. **Open a pull request**.

## License
This project is licensed under the MIT License. See the `LICENSE` file for more details.

---
