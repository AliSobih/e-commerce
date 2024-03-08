# e-commerce

## description:
- The E-Commerce Application is built using Java and Spring Boot, with security, scalability, and ease of maintenance. The backend uses Spring Data JPA to interact with a MySQL database, making it easy to manage and store important entities such as users, products, categories, orders, and more. User authentication is handled by Auth0, providing secure and reliable means of REST APIs.

- The APIs are well-documented and easily accessible through Swagger UI, making it simple for developers to test and understand the various endpoints. Overall, this project provides secure Rest APIs to create a scalable platform for businesses to sell their products to customers.

## Features
### User:-
  - Registration & Login
  - Fetch categories and products based on category
 - Adding & deleting products to cart
 - Managing address and products quantity
  - Ordering products and fetching order status

### Security
 - The API is secured using JSON Web Tokens (JWT). To access the API, you will need to obtain a JWT by authenticating with the /login endpoint.

## Technologies:
- Java 17 or above
- Spring Boot 3.0
- Maven
- MySQL
- Spring Data JPA
- Spring Security
- JSON Web Tokens (JWT)
## ER-Diagrame:-
![image](https://github.com/AliSobih/e-commerce/assets/43109825/6761eff6-b5c4-42fa-9744-82cdce84bc3b)
## API Controllers:-
## POST `/api/auth`
### `/api/auth/register` -  Add a new user details.
![image](https://github.com/AliSobih/e-commerce/assets/43109825/4fc6889d-46ae-45c1-8738-68041f8c60b4)
### POST `/api/auth/authentication` - sign in.
 - if the password or email is wrong then 403 forbidden response will return.
      
      - ![Screenshot 2024-01-24 095140](https://github.com/AliSobih/spring-boot-spring-security-jwt-authentication/assets/43109825/83707a5f-28eb-47a1-9bcb-9b850f3d60a3)
    - if the email and password correct 200 ok will return and token.
      
   - ![Screenshot 2024-01-24 095155](https://github.com/AliSobih/spring-boot-spring-security-jwt-authentication/assets/43109825/3647ca46-739c-48de-8933-ad90763e3182)

