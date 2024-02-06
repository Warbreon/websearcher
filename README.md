# Web Product Searcher

WebProductSearcher is a Java-based application designed to perform real-time searches for products across various online marketplaces, starting with eBay and planning to expand to Amazon and potentially others. It uses OAuth for secure API interactions and manages data persistence through a PostgreSQL database.

## Completed Features

- [x] Real-time product search using eBay's API.
- [x] OAuth 2.0 integration for secure API requests with eBay.
- [x] Token refresh scheduler to automate the renewal of eBay API tokens.
- [x] RESTful API endpoints for interaction with the front end.
- [x] PostgreSQL database integration with JPA/Hibernate for efficient data persistence.
- [x] Design patterns to structure the application effectively.
- [x] Scheduler for updating product listings and tokens automatically.

## Planned Features

- [ ] Integration with Amazon's Product Advertising API for a broader product search capability.
- [ ] Expansion to include additional online marketplaces for comprehensive product searches.
- [ ] Implementation of advanced search filters and sorting options for better search results.
- [ ] Development of a user authentication and authorization system to enable personalized search experiences and secure profile management.
- [ ] Creation of a shopping cart functionality with login requirements to enhance user interaction with the platform.
- [ ] User registration feature to allow new users to sign up and manage their profiles.
- [ ] Profile setup and editing capabilities for a personalized user experience.
- [ ] Comprehensive unit testing across the application using Mockito to ensure robustness and reliability.
- [ ] Adoption of Docker for containerization, enabling easy deployment and scaling.
- [ ] Implementation of GitHub CI/CD pipelines for streamlined development and deployment processes.
- [ ] Development of a React-based front end to provide a modern and user-friendly interface.
- [ ] Design and implementation of a visually appealing user interface to enhance user engagement.

## Technologies

- Spring Boot
- Java
- REST API
- eBay API, Amazon Product Advertising API (planned)
- OAuth 2.0
- PostgreSQL
- JPA/Hibernate
- Lombok
- Spring Scheduler
- Mockito (for unit testing)
- Docker
- GitHub Actions (for CI/CD)
- React (for front-end development)

## Project Status

The project is in active development, with foundational features like eBay integration complete and future enhancements like Amazon integration, authentication mechanisms, and front-end development planned.

## Acknowledgments

- Thanks to eBay for providing comprehensive API documentation and OAuth guidelines.
- Gratitude towards the open-source community for invaluable resources and tools.
