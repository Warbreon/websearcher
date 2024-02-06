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

- [ ] Integration with Amazon's Product Advertising API.
- [ ] Expanding the product search to include additional online marketplaces.
- [ ] Implementing advanced search filters and sorting options.
- [ ] User authentication and authorization for personalized search experiences.
- [ ] Front-end development for a user-friendly web interface.

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

## Project Status

The project is currently in active development, with eBay integration completed and Amazon integration in the planning stages.

## Acknowledgments

- Thanks to eBay for providing comprehensive API documentation and OAuth guidelines.
- Gratitude towards the open-source community for invaluable resources and tools.
