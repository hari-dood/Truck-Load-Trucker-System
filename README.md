# Truck Load Tracker System

The **Truck Load Tracker System** is a backend service designed to manage and track truck load-related data. The system is built using **Spring Boot** and integrates with various libraries and tools to provide functionalities like report generation, secure authentication, and caching for performance optimization. It is designed to handle essential trip data for logistics and transportation, including tracking of trip dates, expenses, drivers, and other important metrics.

## Features

### **1. Trip Management**
This feature allows users to manage and track all the details associated with each truck trip, including:
- **Trip Information**: Track trip dates, driver names, loading points, and delivery points.
- **Load Information**: Monitor load quantities, load descriptions, and associated expenses such as toll, fuel, and maintenance.
- **Trip Status**: Update and view the status of each trip (e.g., completed, pending).

All trip data is stored and managed in a PostgreSQL database and can be accessed or updated via secure API endpoints.

### **2. Excel Report Generation**
The system can generate detailed **Excel reports** for trip-related data. These reports can contain:
- Driver and trip details
- Expenses and payment statuses
- Load and destination information

This feature utilizes **Apache POI** to generate `.xlsx` reports, making it easier for stakeholders to analyze and share trip data.

### **3. PDF Report Generation**
In addition to Excel reports, the application supports the generation of **PDF reports**. These reports are generated using:
- **iText7**: A powerful library for PDF creation and manipulation.
- **OpenPDF**: An open-source library for working with PDF documents.

PDF reports can include detailed trip summaries, including associated expenses, dates, and other metrics. This is helpful for formal documentation and sharing with clients or internal teams.

### **4. JWT Authentication**
Security is implemented using **JWT (JSON Web Tokens)**. The system employs JWT to authenticate users and ensure that only authorized individuals can access or manipulate sensitive trip data. After logging in, users are provided with a JWT token, which must be included in the header of subsequent requests.

### **5. Redis Integration (Caching)**
To enhance performance, the system optionally integrates **Redis** for caching frequently accessed data. Caching can significantly reduce database load and improve the speed of response, especially for read-heavy operations such as fetching trip details.

### **6. Email Notifications**
The system sends **email notifications** for key events, such as:
- Trip updates or changes
- Generated reports (Excel/PDF)

Emails are sent using **Spring Boot’s email capabilities**, with SMTP configuration to manage email sending to recipients. This helps keep users and stakeholders updated about important events in real-time.

### **7. PostgreSQL Database**
The application stores all data in a **PostgreSQL database**, ensuring that the data is persistent and can be queried for various reporting and tracking purposes. The database includes tables for storing:
- Trip details (dates, driver, load, etc.)
- Expenses (toll, diesel, broker amount, etc.)
- Client and employee-related data

PostgreSQL is known for its reliability and ability to handle complex queries efficiently, making it an ideal choice for this application.

---

## Technologies Used

- **Spring Boot**: The core framework used for building the RESTful backend service. Spring Boot simplifies the development and deployment of Java-based applications.

- **PostgreSQL**: A relational database that stores trip and load data. It is used to perform efficient queries, manage transactions, and ensure data persistence.

- **Apache POI**: A library that allows the generation of **Excel (.xlsx)** reports. It is used to export trip details in a structured format, making it easy to share and analyze data.

- **iText7 & OpenPDF**: Libraries used for generating **PDF** reports. iText7 is used for creating and manipulating PDF documents, while OpenPDF is used for handling the PDF format in an open-source manner.

- **JWT (JSON Web Tokens)**: Provides secure authentication for API access. JWT is used to authenticate users and ensure that only authorized requests are allowed.

- **Redis**: A powerful in-memory data store that is used for caching frequently accessed data, improving performance by reducing load on the database.

- **MapStruct**: A code generator used to simplify the process of mapping between Java beans. It is used for mapping DTO (Data Transfer Objects) to entities in the application.

- **Spring Security**: A comprehensive security framework used to manage authentication and authorization. Spring Security protects sensitive data and endpoints.

- **Lombok**: A Java library that reduces boilerplate code such as getter and setter methods, making the codebase cleaner and more concise.

- **Jackson**: A popular JSON processing library used for converting Java objects to JSON and vice versa. It’s also used for handling XML data.

- **Flyway** (Optional, commented out): A database migration tool that helps manage schema changes across different environments. Although commented out in this project, it can be enabled for version-controlled database migrations.

---

## Setup Instructions

### Prerequisites

Before you begin, ensure that you have the following installed:
- **Java 17 or higher**: The application is built using Java 17, so make sure it is installed on your machine.
- **Maven**: The project is built using Maven, so you’ll need Maven installed to compile and run the application.
- **PostgreSQL**: A PostgreSQL database is required to store all trip and load data. You can set up a local instance or use a cloud-based solution.
- **Redis (Optional)**: Redis is optional for caching. It’s recommended to install and configure it if you want to enhance the performance of the application.

---

## Installation

1. Clone the repository:
   ```bash
   https://github.com/hari-dood/Truck-Load-Trucker-System.git
