# 📚 DPSB Library Management System

> **A Java-based Library Management System developed as an academic project by Ahmar Riaz Magrey, Class XII Humanities, Delhi Public School (DPS), Budgam.**

---

## 📝 About the Project

The **DPSB Library Management System** is a console-based application developed using **Java** to manage the basic operations of a school library in an organized and efficient manner.

The system provides a structured way to manage **books, students, issue records, returns, fines, availability, and library statistics** through a simple menu-driven interface.

The project was developed to demonstrate the practical application of programming concepts such as **Object-Oriented Programming (OOP), file handling, collections, data validation, exception handling, CRUD operations, and persistent data storage**.

---

## 🎯 Project Objectives

The main objectives of this project are:

* 📚 To maintain organized records of library books.
* 👨‍🎓 To maintain student information.
* 🔍 To provide quick book and student searching.
* ➕ To add new books to the library.
* ✏️ To update existing book records.
* 🗑️ To remove book records when required.
* 🔄 To manage book issue and return transactions.
* 📊 To provide basic library statistics.
* 💰 To maintain fine information.
* 💾 To store data permanently between program sessions.
* 🛡️ To validate user input and handle common errors.
* 💻 To demonstrate practical Java programming concepts.

---

## ✨ Key Features

### 📖 Book Management

The system allows the user to:

* View all books
* Search for books
* Add new books
* Update existing books
* Delete books
* View available books
* View currently issued books
* Track book availability

Each book record can contain:

* 🆔 Book ID
* 📕 Title
* ✍️ Author
* 📅 Publication Year
* 🏷️ Category
* 🔢 ISBN
* 📍 Shelf Number
* ✅ Availability Status

---

### 👨‍🎓 Student Management

The system maintains student records that can be used during library transactions.

Student information includes:

* 🆔 Student ID
* 🔢 Roll Number
* 👤 Student Name
* 🏫 Class

The system also provides options to view and search student records.

---

### 🔄 Book Issue & Return

The application provides a complete basic workflow for borrowing and returning books.

When a book is issued, the system:

1. 🔍 Verifies the student.
2. 📚 Verifies the book.
3. ✅ Checks whether the book is available.
4. 📊 Checks the student's active borrowing limit.
5. 📝 Creates an issue record.
6. 🔒 Marks the book as unavailable.

When a book is returned, the system:

1. 🔍 Identifies the active issue record.
2. 🔄 Updates the transaction.
3. ✅ Marks the book as available.
4. 📝 Records the return information.
5. 💰 Maintains the applicable fine information.

### 📌 Borrowing Limit

A student can have a maximum of **3 active books** at a time.

This prevents unlimited borrowing and demonstrates the use of logical conditions and validation within the application.

---

## 💰 Fine Management

The system supports fine information within library issue records.

Each issue transaction can contain:

* Issue date
* Return date
* Fine
* Transaction status

This allows fine-related information to remain associated with the relevant borrowing transaction.

---

## 🔎 Search System

The application includes search functionality for quickly locating records.

Users can search for:

* 📚 Books
* 👨‍🎓 Students

This makes it easier to work with a larger collection of library records without manually checking every entry.

---

## 📊 Library Statistics

The application provides basic statistics to give an overview of the library.

The statistics system can provide information related to:

* 📚 Total books
* 👨‍🎓 Total students
* ✅ Available books
* 📕 Issued books
* 🔄 Borrowing activity
* 📈 Library transaction information

---

# 💾 Data Storage & Persistence

One of the main features of the project is **persistent local data storage**.

Instead of keeping all information only in temporary memory, the application stores its records in local text files.

### 📁 Storage Files

```text
data/
├── books.txt
├── students.txt
└── issues.txt
```

### 📚 `books.txt`

Stores information related to library books, including details such as book ID, title, author, category, ISBN, shelf number, and availability.

### 👨‍🎓 `students.txt`

Stores registered student information such as student ID, roll number, name, and class.

### 🔄 `issues.txt`

Stores library transaction information such as issue ID, student ID, book ID, issue date, return date, fine, and transaction status.

### 🔐 Data Encoding

The application uses **Base64 encoding** for stored text fields before writing records to the files. This helps safely store text containing characters that could otherwise interfere with the file's delimiter-based structure.

> **Note:** Base64 is an encoding method, not encryption. It is used here for structured data storage rather than security.

### 🔁 Persistence Workflow

The application follows this basic process:

```text
Application Starts
       ↓
Check Stored Files
       ↓
Load Existing Records
       ↓
User Performs Operations
       ↓
Data Is Updated
       ↓
Save Records to Files
       ↓
Application Continues / Exits
```

This means that the records are not limited to a single program session.

---

# 🧱 Object-Oriented Structure

The project is divided into multiple classes according to their responsibilities.

### 📕 `Book`

Represents individual library books and manages their information.

### 👤 `Student`

Represents students registered with the library.

### 🔄 `IssueRecord`

Represents individual book issue and return transactions.

### 🏫 `Library`

Contains the main library operations and manages books, students, and issue records.

### 💻 `LibraryManagementSystem`

Contains the main application logic and provides the menu-driven interface through which the user interacts with the system.

---

# 🧠 Programming Concepts Used

The project demonstrates several fundamental Java programming concepts:

* 🧩 Classes & Objects
* 🔒 Encapsulation
* 🏗️ Constructors
* ⚙️ Methods
* 🔑 Getters & Setters
* 📋 `ArrayList`
* 📦 `List`
* 🔁 Loops
* 🔀 Conditional Statements
* 🎛️ `switch` Statements
* 🛡️ Exception Handling
* ✅ Input Validation
* 💾 File Handling
* 🔄 Data Persistence
* 📝 CRUD Operations
* 🔗 Object Relationships
* 🧮 Basic Data Processing

---

# 🛠️ Technologies Used

| Technology                 | Purpose                           |
| -------------------------- | --------------------------------- |
| ☕ **Java**                 | Main programming language         |
| 🧱 **Java OOP**            | Application structure             |
| 📋 **ArrayList / List**    | Managing records in memory        |
| 💾 **Java File Handling**  | Persistent data storage           |
| 🛡️ **Exception Handling** | Handling invalid input and errors |
| ⌨️ **Console Interface**   | User interaction                  |
| 🔐 **Base64 Encoding**     | Structured storage of text fields |
| 📄 **UTF-8**               | Text file encoding                |

---

# 🖥️ Application Menu

The system provides a menu-driven interface with the following options:

```text
╔════════════════════════════════════════╗
║       📚 LIBRARY MANAGEMENT SYSTEM     ║
╠════════════════════════════════════════╣
║  1. View Books                         ║
║  2. Search Book                        ║
║  3. Add Book                           ║
║  4. Delete Book                        ║
║  5. Update Book                        ║
║  6. View Students                      ║
║  7. Search Student                     ║
║  8. Issue Book                         ║
║  9. Return Book                        ║
║ 10. View Issued Books                  ║
║ 11. View Available Books               ║
║ 12. View Statistics                    ║
║ 13. Exit                               ║
╚════════════════════════════════════════╝
```

---

# 🛡️ Input Validation & Error Handling

The system includes validation mechanisms to reduce incorrect input and improve reliability.

Examples include:

* Checking whether required records exist.
* Preventing the issue of unavailable books.
* Preventing invalid book or student references.
* Checking borrowing limits.
* Validating numerical input.
* Handling invalid user input.
* Preventing common runtime errors through exception handling.

These features help make the application more stable and easier to use.

---

# 📂 Project Structure

```text
DPSB-Library-Management-System/
│
├── src/
│   └── com/
│       └── mycompany/
│           └── librarymanagementsystem/
│               ├── Book.java
│               ├── Student.java
│               ├── IssueRecord.java
│               ├── Library.java
│               └── LibraryManagementSystem.java
│
├── data/
│   ├── books.txt
│   ├── students.txt
│   └── issues.txt
│
├── screenshots/
│
├── README.md
├── .gitignore
└── LICENSE
```

---

# 📋 Main Operations

| Category        | Operations                           |
| --------------- | ------------------------------------ |
| 📚 Books        | View, Search, Add, Update, Delete    |
| 👨‍🎓 Students  | View, Search                         |
| 🔄 Transactions | Issue, Return                        |
| 📕 Availability | View Issued, View Available          |
| 💰 Fines        | Record and display transaction fines |
| 📊 Statistics   | View library information             |
| 💾 Storage      | Load and save persistent records     |

---

# 🎓 Educational Purpose

This project was developed as an **academic project** to explore the practical application of programming and software development.

Although the developer belongs to the **Humanities stream**, the project demonstrates an independent interest in technology, programming, logical problem-solving, and software development.

The project provides practical experience in designing a structured application, organizing information into classes, handling user input, managing records, and implementing persistent storage.

---

# ⚠️ Project Limitations

The current version is designed primarily as an academic project and therefore has several limitations:

* 💾 Uses local text files instead of a relational database.
* ⌨️ Uses a console-based interface.
* 🌐 Does not provide a web interface.
* 📱 Does not provide a mobile application.
* 👥 Does not support simultaneous multi-user access.
* 🔐 Does not include an authentication system.
* ☁️ Does not use cloud-based storage.

These limitations keep the project focused on demonstrating core Java programming concepts rather than introducing unnecessary complexity.

---

# 🚀 Future Improvements

Possible future improvements could include:

* 🗄️ Integration with MySQL or another database.
* 🖥️ Development of a graphical user interface.
* 🌐 Development of a web-based version.
* 🔐 User authentication and role-based access.
* 📱 Mobile application support.
* 📧 Automated notifications for overdue books.
* 📊 More advanced reporting and analytics.
* ☁️ Cloud-based data storage.
* 🔍 More advanced search and filtering.

---

# 👨‍💻 Project Information

**👤 Developer:** Ahmar Riaz Magrey
**🏫 School:** Delhi Public School (DPS), Budgam
**🎓 Class:** XII Humanities
**📚 Project:** Library Management System
**☕ Programming Language:** Java
**💻 Application Type:** Console-Based Application
**💾 Storage:** Local Text Files
**👩‍🏫 Project Teacher:** Mrs. Tabassum
**🎯 Purpose:** Academic Project

---

## 📌 Conclusion

The **DPSB Library Management System** is a functional Java application developed to demonstrate the practical use of programming concepts through a real-world library management scenario.

The project combines **Object-Oriented Programming, data structures, file handling, persistent storage, input validation, exception handling, CRUD operations, search functionality, and transaction management** into a single application.

Through this project, the developer has applied programming concepts to design and implement a structured system capable of managing books, students, and library transactions in an organized manner.

> 📚 **DPSB Library Management System**
> **Developed by Ahmar Riaz Magrey | Class XII Humanities | Delhi Public School, Budgam**
