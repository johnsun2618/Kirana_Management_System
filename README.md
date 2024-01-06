The Kirana Management System is a Java-based application designed to manage transactions and store owner information in a Kirana store.

Project Structure
The project is organized into several packages, each serving a specific purpose:

com.kirana.management.model: Contains the entity classes representing the data model.
com.kirana.management.dto: Contains Data Transfer Objects (DTOs) used for communication.
com.kirana.management.repository: Provides Spring Data JPA repositories for database interaction.
com.kirana.management.service: Defines service interfaces and their implementations for business logic.
com.kirana.management.controller: Contains controllers handling HTTP requests.
How to run
I have use MySQL database so you can directly run this code and spring will automatically establish connection to the database.
start the main spring function and
Use postman for check api endpoint.
Tech and Others
Java 20
Spring boot 3.1.0
MySQL Database
IntellijIDE
Postman
API for currency rate -> https://api.fxratesapi.com/latest
