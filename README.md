Project Documentation
=====================

Table of Contents
-----------------

1.  [Overview](#overview)
2.  [Setting Up the Application](#setting-up-the-application)
3.  [Running the Application](#running-the-application)
4.  [API Endpoints](#api-endpoints)
   *   [Authentication](#authentication)
   *   [Books](#books)
   *   [Patrons](#patrons)
   *   [Borrowing Records](#borrowing-records)
   *   [Registration](#registration)
5.  [Obtaining a Token](#obtaining-a-token)
6.  [Using the Token](#using-the-token)
7.  [Running Tests](#running-tests)
8.  [Contribution](#contribution)
9.  [License](#license)
10.  [Contact](#contact)

Overview
--------

This project is a library management system built using Spring Boot. It includes features for managing books, borrowing records, and patrons.

Setting Up the Application
--------------------------

### Prerequisites

*   **Java 17** or later
*   **Maven** for dependency management and build
*   **Git** for version control

### Configuration

Ensure that your `application.properties` file in the `src/main/resources` directory is set up correctly. Here’s a basic example:

    server.port=8080
    
    # Database configuration
    spring.datasource.url=jdbc:mysql://localhost:3306/library_db
    spring.datasource.username=root
    spring.datasource.password=password
    spring.jpa.hibernate.ddl-auto=create-drop
    spring.jpa.show-sql=true
    
    # JWT Configuration
    security.jwt.secret-key=your-secret-key
    security.jwt.expiration-time=3600000
    
    # Other configurations
    logging.level.org.springframework=INFO


Replace `your-secret-key` with a strong secret key for JWT and adjust the database URL, username, and password as per your database setup.

Running the Application
-----------------------

1.  **Clone the Repository**

        git clone git@github.com:ahmedham/library-management-system.git
        cd library-management-system


2.  **Build the Project**

        mvn clean install


3.  **Run the Application**

        mvn spring-boot:run


    The application will start and be available at `http://localhost:8080`.


API Endpoints
-------------

### Authentication

Authentication is managed using JWT (JSON Web Tokens). Ensure you have a valid token to access secured endpoints.

### Registration

*   **Register a New User**

    **POST** `/api/auth/register`

    Request Body:

        {
            "fullName": "fullName",
            "email": "email",
            "password": "password"
        }


Registers a new user with the specified fullName, email, and password.


Obtaining a Token
-----------------

You must authenticate to obtain a JWT token. Use the following endpoint:

*   **Login**

    **POST** `/api/auth/login`

    Request Body:

        {
            "email": "your-email",
            "password": "your-password"
        }


    Response:
    
        {
            "token": "your-jwt-token",
            "expiresIn": expiration-time
        }



Using the Token
---------------

Include the token in the `Authorization` header for protected endpoints:

    Authorization: your-jwt-token



### Books

*   **Get All Books**

    **GET** `/api/books`

    Returns a list of all books.

*   **Get Book by ID**

    **GET** `/api/books/{id}`

    Retrieve a specific book by its ID.

*   **Add a Book**

    **POST** `/api/books`

    Request Body:

        {
            "title": "Book Title",
            "author": "Author Name",
            "isbn": "Book ISBN",
            "publicationYear": "Book publication Year"
        }


    Adds a new book to the library.

*   **Update a Book**

    **PUT** `/api/books/{id}`

    Request Body:

        {
            "title": "Updated Title",
            "author": "Updated Author",
            "isbn": "Updated ISBN",
            "publicationYear": "Updated Year"
        }


    Updates the details of an existing book.

*   **Delete a Book**

    **DELETE** `/api/books/{id}`

    Deletes a book from the library.


### Patrons

*   **Get All Patrons**

    **GET** `/api/patrons`

    Returns a list of all patrons.

*   **Get Patron by ID**

    **GET** `/api/patrons/{id}`

    Retrieve a specific patron by its ID.

*   **Add a Patron**

    **POST** `/api/patrons`

    Request Body:

        {
            "name": "Patron name",
            "email": "Patron email",
            "phone": "Patron phone"
        }


    Adds a new patron to the library.

*   **Update a Patron**

    **PUT** `/api/patrons/{id}`

    Request Body:

        {
            "name": "Updated name",
            "email": "Updated email",
            "phone": "Updated phone"
        }


    Updates the details of an existing patron.

*   **Delete a Patron**

    **DELETE** `/api/patrons/{id}`

    Deletes a patron from the library.


### Borrowing Records

*   **Borrow a Book**

    **POST** `/api/borrow/{bookId}/patron/{patronId}`

    Request Body:

        {
            "message": "Book borrowed successfully"
        }


    Creates a new borrowing record for a book and patron.

*   **Return a Book**

    **PUT** `/api/return/{bookId}/patron/{patronId}`

    Request Body:

        {
            "message": "Book returned successfully"
        }


    Updates the borrowing record to indicate that the book has been returned.


**Note**: A Postman collection named ``Library.postman_collection.json`` is included with this project to help you test the API endpoints.



Running Tests
-------------

### Unit Tests

Run unit tests with Maven:

    mvn test

Contribution
------------

Feel free to contribute to this project by submitting issues or pull requests. Ensure you follow the project's coding standards and review the contribution guidelines.

License
-------

This project is licensed under the [MIT License](LICENSE).

Contact
-------

For any questions or support, contact:

*   **Email:** ahmedhamada015@gmail.com
*   **LinkedIn:** [LinkedIn](https://www.linkedin.com/in/ahmed-hamada-014807110/)
