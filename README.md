# Online Shopping Cart - Java Spring Boot Project

Welcome to the Online Shopping Cart project! This project is built using Java Spring Boot and MySQL database. It consists of several modules including User Management, Product Management, Cart Management, and Order Management.

## Technologies Used

- Java
- Spring Boot
- Spring Security
- MySQL
- Maven
- Swagger

## Modules Overview

### User Management module:
This module is responsible for user authentication and registration. It includes functionalities like user signup and login. Spring Security has been integrated for authentication and authorization purposes.

### Product Management module:
The Product Management module deals with managing the products available for sale. It includes functionalities such as adding new products, updating existing products, deleting products, and listing products.

### Cart Management module:
The Cart Management module handles the shopping cart functionality. It includes functionalities like adding products to the cart, removing products from the cart, and calculating the total price of the items in the cart.

### Order Management module:
The Order Management module manages the process of placing orders. It includes functionalities like creating orders, updating order status, and tracking orders.

## Installation

To run the project locally, follow these steps:

1. Clone the repository:

```bash
git clone <repository_url>
```

2. Navigate to the project directory:

```bash
cd <project_directory>
```

3. Configure the MySQL database:
  - Create a MySQL database named `ecommerce_app`.
  - Update the database configuration in `application.properties` file located in `src/main/resources`.

4. Build the project:

```bash
./mvnw clean install
```

5. Run the project:

```bash
./mvnw spring-boot:run
```

6. Access Swagger UI:
  - Once the project is running, you can access the Swagger UI by navigating to `http://localhost:8080/swagger-ui/index.html#/` in your web browser. Swagger UI provides interactive documentation for the RESTful APIs.

## Usage

Once the project is up and running, you can interact with the different modules using RESTful endpoints. Here are some sample endpoints:

## API Documentation

### Product Endpoints

- **GET** `/api/products/{id}`: Find Product by ID - Retrieve product details by ID.
- **PUT** `/api/products/{id}`: Update Product - Update existing product details.
- **DELETE** `/api/products/{id}`: Delete Product - Delete a product by ID.
- **GET** `/api/products`: Find All Products - Retrieve a paginated list of all products.
- **POST** `/api/products`: Add Product - Add a new product.

### Order Endpoints

- **PUT** `/api/orders/changestatus`: Change Order Status - Change the status of an order.
- **GET** `/api/orders`: Get Order - Retrieve a list of orders.
- **POST** `/api/orders`: Create Order - Create a new order.

### User Endpoints

- **POST** `/api/users/signup`: Register User - Register a new user.
- **POST** `/api/users/login`: Login User - Authenticate and log in a user.

### Cart Endpoints

- **GET** `/api/carts`: Get Cart - Retrieve the user's cart details.
- **POST** `/api/carts`: Add Products to Cart - Add products to the user's cart.
- **DELETE** `/api/carts/removeproduct`: Remove Products from Cart - Remove products from the user's cart.


## Docker Compose Instructions

To run the application using Docker Compose, follow these steps:

1.Run the following command to start the application:

```bash
docker-compose up
```


