# Shopping Cart API

A RESTful API for managing shopping carts built with **Kotlin**, **Spring Boot**, and **PostgreSQL**.

## 🚀 Features

- **Cart Management**: Create, retrieve, update, and delete shopping carts
- **Product Management**: Add, update, and remove products from carts
- **Country-specific Pricing**: Support for different pricing based on country codes
- **Data Validation**: Comprehensive validation for all API endpoints
- **Database Integration**: PostgreSQL with JPA/Hibernate
- **RESTful Design**: Clean REST API following best practices

## 🛠️ Tech Stack

- **Language**: Kotlin
- **Framework**: Spring Boot 3.2.0
- **Database**: PostgreSQL 15
- **ORM**: Spring Data JPA
- **Build Tool**: Gradle (Kotlin DSL)
- **Java Version**: 17
- **Testing**: JUnit 5, Mockito

## 📋 Prerequisites

- Java 17 or higher
- Docker and Docker Compose
- Gradle (included via wrapper)

## 🏃‍♂️ Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd shopping
```

### 2. Start PostgreSQL Database
```bash
# Using Docker Compose
docker-compose up -d

# Or using the provided batch file (Windows)
start-postgres.bat
```

### 3. Build and Run the Application
```bash
# Linux/Mac
./gradlew bootRun

# Windows
gradlew.bat bootRun
```

The application will start on `http://localhost:8080`

## 🗄️ Database Setup

The application uses PostgreSQL as the primary database. The Docker Compose configuration provides:

- **Database**: `shopping_cart`
- **Username**: `postgres`
- **Password**: `password`
- **Port**: `5432`

### Database Health Check
Use the provided script to verify database connectivity:
```bash
check-db.bat
```

## 📖 API Documentation

### Base URL
```
http://localhost:8080/api
```

### Available Endpoints

#### Carts
- `GET /carts` - List all carts
- `POST /carts` - Create a new cart
- `GET /carts/{id}` - Get cart by ID
- `PUT /carts/{id}` - Update cart
- `DELETE /carts/{id}` - Delete cart

#### Products
- `GET /carts/{cartId}/products` - Get products in cart
- `POST /carts/{cartId}/products` - Add product to cart
- `PUT /carts/{cartId}/products/{productId}` - Update product in cart
- `DELETE /carts/{cartId}/products/{productId}` - Remove product from cart

### Testing the API

#### Using HTTP Files
The project includes `api-tests.http` file with sample requests. Use your IDE's HTTP client to execute these requests.

#### Using Postman
Import the `postman-collection.json` file into Postman for comprehensive API testing.

#### Sample Request
```bash
curl -X POST http://localhost:8080/api/carts \
  -H "Content-Type: application/json" \
  -d '{
    "countryCode": "US",
    "products": [
      {
        "name": "Laptop",
        "price": 999.99,
        "quantity": 1,
        "category": "ELECTRONICS"
      }
    ]
  }'
```

## 🏗️ Project Structure

```
src/
├── main/
│   ├── kotlin/
│   │   └── com/
│   │       └── shopping/
│   │           ├── config/          # Configuration classes
│   │           ├── controller/      # REST controllers
│   │           ├── dto/             # Data Transfer Objects
│   │           ├── entity/          # JPA entities
│   │           ├── exception/       # Custom exceptions
│   │           ├── repository/      # Data access layer
│   │           ├── service/         # Business logic
│   │           └── ShoppingApplication.kt
│   └── resources/
│       └── application.properties   # Application configuration
└── test/                           # Unit and integration tests
```

## 🧪 Testing

### Run All Tests
```bash
./gradlew test
```

### Test Coverage
The project includes comprehensive unit tests using:
- JUnit 5 for test framework
- Mockito Kotlin for mocking
- H2 in-memory database for testing

## 🔧 Configuration

### Database Configuration
Configure database connection in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/shopping_cart
spring.datasource.username=postgres
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
```

### Environment Variables
You can override configuration using environment variables:
- `DB_URL`: Database URL
- `DB_USERNAME`: Database username
- `DB_PASSWORD`: Database password

## 📦 Building for Production

### Create JAR file
```bash
./gradlew build
```

The executable JAR will be created in `build/libs/`

### Docker Build
```bash
docker build -t shopping-cart-api .
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 API Testing Guide

For detailed API testing examples and sample data, refer to [API-Testing-Guide.md](API-Testing-Guide.md).

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Issues**
   - Ensure PostgreSQL is running: `docker-compose ps`
   - Check database logs: `docker-compose logs postgres`

2. **Port Already in Use**
   - Change the application port in `application.properties`
   - Kill the process using the port: `netstat -ano | findstr :8080`

3. **Build Issues**
   - Clean and rebuild: `./gradlew clean build`
   - Check Java version: `java -version`

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For questions or issues, please open an issue in the repository or contact the development team.
