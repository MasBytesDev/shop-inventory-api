# Shop Inventory API

## Overview

This project is a backend API for managing a product inventory system. The API is built using **Java**, **Spring Boot**, and **MongoDB**, with **Swagger** integration for API documentation. The project is designed for educational purposes, aiming to help developers understand backend development, RESTful API design, and the integration of MongoDB with Spring Boot.

## Technologies Used

- **Java JDK 21**  
- **Spring Boot 3.x**  
- **Spring Framework**  
- **MongoDB (NoSQL Database)**  
- **Swagger (OpenAPI)**  
- **Maven** (Dependency Management)  

## Dependencies

Here are the main dependencies used in this project:

- **Spring Web**  
  For building RESTful web services.
  
- **Spring Data MongoDB**  
  For MongoDB integration in Spring applications.
  
- **Spring Boot Starter Web**  
  To support building web applications with Spring MVC and REST.
  
- **Swagger UI**  
  For generating interactive API documentation.
  
- **Spring Boot DevTools**  
  For enhancing development speed by enabling features like automatic restarts.

- **Lombok**  
  To reduce boilerplate code, particularly in model classes.

## Database Information

The project uses a **MongoDB** database to store product information. The database is hosted on **MongoDB Atlas**. You can replicate the project by setting up a MongoDB Atlas account and creating a database with the following settings:

- **Database Name:** `shop_inventory`  
- **Collection Name:** `products`

Make sure to update the connection details in the `application.properties` or `application.yml` to connect to your own MongoDB instance.

## API Endpoints

The API provides the following endpoints to manage products:

- **GET /api/products**  
  Retrieves all products in the inventory.
  
- **GET /api/products/{id}**  
  Retrieves a single product by its unique ID.
  
- **POST /api/products**  
  Creates a new product in the inventory.
  
- **PUT /api/products/{id}**  
  Updates an existing product's details.
  
- **DELETE /api/products/{id}**  
  Deletes a product from the inventory.

These endpoints are fully documented and can be explored using **Swagger UI**, which is integrated into the project.

## Setup Instructions

To set up and run this project on your local machine, follow these steps:

1. **Clone the repository:**

   ```bash
   git clone https://github.com/yourusername/shop-inventory.git
   ```

2. **Navigate to the project folder:**

   ```bash
   cd shop-inventory
   ```

3. **Update application.properties with your MongoDB connection details:**

   You can find the `application.properties` file in the `src/main/resources` directory. Update the MongoDB URI to point to your own MongoDB Atlas cluster.

   Example:

   ```properties
   spring.data.mongodb.uri=mongodb+srv://<username>:<password>@cluster0.mongodb.net/shop_inventory?retryWrites=true&w=majority
   ```

4. **Build the project with Maven:**

   ```bash
   mvn clean install
   ```

5. **Run the application:**

   ```bash
   mvn spring-boot:run
   ```

6. **Access Swagger UI:**  
   Once the application is running, you can access the Swagger UI documentation at:

   ```
   http://localhost:8080/swagger-ui/
   ```

## Contribution

Feel free to fork this repository and contribute. If you find any bugs or would like to request new features, open an issue or submit a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
