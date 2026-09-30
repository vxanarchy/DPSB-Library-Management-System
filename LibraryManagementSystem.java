package com.mycompany.librarymanagementsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

/*
 * Library Management System
 * School: Delhi Public School (DPS), Budgam
 * Teacher: Mrs. Tabassum
 * Project: Library Management System
 */

class Book {
    private String bookId;
    private String title;
    private String author;
    private int publicationYear;
    private String category;
    private String isbn;
    private String shelfNumber;
    private boolean available;

    public Book(String bookId, String title, String author, int publicationYear,
                String category, String isbn, String shelfNumber) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.category = category;
        this.isbn = isbn;
        this.shelfNumber = shelfNumber;
        this.available = true;
    }

    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPublicationYear() { return publicationYear; }
    public String getCategory() { return category; }
    public String getIsbn() { return isbn; }
    public String getShelfNumber() { return shelfNumber; }
    public boolean isAvailable() { return available; }

    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setPublicationYear(int year) { this.publicationYear = year; }
    public void setCategory(String category) { this.category = category; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setShelfNumber(String shelfNumber) { this.shelfNumber = shelfNumber; }

    public void setAvailable(boolean available) { this.available = available; }

    public void display() {
        System.out.printf(
            "%-7s %-38s %-28s %-6s %-18s %-12s %-8s%n",
            bookId, title, author, publicationYear, category,
            available ? "Available" : "Issued", shelfNumber
        );
    }
}


class Student {
    private String studentId;
    private String rollNumber;
    private String name;
    private String className;

    public Student(String studentId, String rollNumber, String name, String className) {
        this.studentId = studentId;
        this.rollNumber = rollNumber;
        this.name = name;
        this.className = className;
    }

    public String getStudentId() { return studentId; }
    public String getRollNumber() { return rollNumber; }
    public String getName() { return name; }
    public String getClassName() { return className; }

    public void setName(String name) { this.name = name; }
    public void setClassName(String className) { this.className = className; }

    public void display() {
        System.out.printf(
            "%-7s %-6s %-25s %-8s%n",
            studentId, rollNumber, name, className
        );
    }
}


class IssueRecord {
    private String issueId;
    private String studentId;
    private String bookId;
    private String issueDate;
    private String returnDate;
    private double fine;
    private String status;
    private String issuedBy;
    private String returnedBy;

    public IssueRecord(String issueId, String studentId, String bookId,
                       String issueDate, String returnDate, double fine,
                       String status, String issuedBy, String returnedBy) {
        this.issueId = issueId;
        this.studentId = studentId;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.fine = fine;
        this.status = status;
        this.issuedBy = issuedBy;
        this.returnedBy = returnedBy;
    }

    public String getIssueId() { return issueId; }
    public String getStudentId() { return studentId; }
    public String getBookId() { return bookId; }
    public String getIssueDate() { return issueDate; }
    public String getReturnDate() { return returnDate; }
    public double getFine() { return fine; }
    public String getStatus() { return status; }
    public String getIssuedBy() { return issuedBy; }
    public String getReturnedBy() { return returnedBy; }

    public void closeRecord(String returnDate, double fine, String returnedBy) {
        this.returnDate = returnDate;
        this.fine = fine;
        this.status = "Returned";
        this.returnedBy = returnedBy;
    }
}


class Library {
    private String schoolName;
    private String teacherName;
    private List<Book> books;
    private List<Student> students;
    private List<IssueRecord> issues;

    public Library(String schoolName, String teacherName) {
        this.schoolName = schoolName;
        this.teacherName = teacherName;
        books = new ArrayList<>();
        students = new ArrayList<>();
        issues = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addIssue(IssueRecord issue) {
        issues.add(issue);
    }

    public List<Book> getBooks() { return books; }
    public List<Student> getStudents() { return students; }
    public List<IssueRecord> getIssues() { return issues; }

    public Book findBook(String id) {
        for (Book book : books) {
            if (book.getBookId().equalsIgnoreCase(id)) return book;
        }
        return null;
    }

    public Student findStudent(String id) {
        for (Student student : students) {
            if (student.getStudentId().equalsIgnoreCase(id)) return student;
        }
        return null;
    }

    public IssueRecord findActiveIssue(String studentId, String bookId) {
        for (IssueRecord issue : issues) {
            if (issue.getStudentId().equalsIgnoreCase(studentId)
                    && issue.getBookId().equalsIgnoreCase(bookId)
                    && issue.getStatus().equalsIgnoreCase("Issued")) {
                return issue;
            }
        }
        return null;
    }

    public boolean issueBook(String studentId, String bookId, String date, String issuedBy) {
        Student student = findStudent(studentId);
        Book book = findBook(bookId);

        if (student == null || book == null || !book.isAvailable()) return false;

        int activeBooks = 0;
        for (IssueRecord issue : issues) {
            if (issue.getStudentId().equalsIgnoreCase(studentId)
                    && issue.getStatus().equalsIgnoreCase("Issued")) {
                activeBooks++;
            }
        }

        if (activeBooks >= 3) return false;

        book.setAvailable(false);

        String newId = String.format("IS%03d", issues.size() + 1);
        issues.add(new IssueRecord(
            newId, studentId, bookId, date, "-", 0,
            "Issued", issuedBy, "-"
        ));

        return true;
    }

    public boolean returnBook(String studentId, String bookId,
                              String date, double fine, String returnedBy) {
        Book book = findBook(bookId);
        IssueRecord issue = findActiveIssue(studentId, bookId);

        if (book == null || issue == null) return false;

        book.setAvailable(true);
        issue.closeRecord(date, fine, returnedBy);
        return true;
    }

    public void deleteBook(String bookId) {
        Book book = findBook(bookId);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (!book.isAvailable()) {
            System.out.println("Issued books cannot be deleted.");
            return;
        }

        books.remove(book);
        System.out.println("Book deleted successfully.");
    }

    public void updateBook(String bookId, Scanner scanner) {
        Book book = findBook(bookId);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        System.out.println("Press Enter to keep the current value.");

        System.out.print("New title [" + book.getTitle() + "]: ");
        String title = scanner.nextLine().trim();
        if (!title.isEmpty()) book.setTitle(title);

        System.out.print("New author [" + book.getAuthor() + "]: ");
        String author = scanner.nextLine().trim();
        if (!author.isEmpty()) book.setAuthor(author);

        System.out.print("New category [" + book.getCategory() + "]: ");
        String category = scanner.nextLine().trim();
        if (!category.isEmpty()) book.setCategory(category);

        System.out.print("New shelf [" + book.getShelfNumber() + "]: ");
        String shelf = scanner.nextLine().trim();
        if (!shelf.isEmpty()) book.setShelfNumber(shelf);

        System.out.println("Book updated successfully.");
    }

    public void searchBook(String keyword) {
        boolean found = false;

        for (Book book : books) {
            String text = (
                book.getBookId() + " " +
                book.getTitle() + " " +
                book.getAuthor() + " " +
                book.getCategory()
            ).toLowerCase();

            if (text.contains(keyword.toLowerCase())) {
                if (!found) {
                    System.out.println("\nSearch Results:");
                    System.out.println("-".repeat(125));
                    book.display();
                } else {
                    book.display();
                }
                found = true;
            }
        }

        if (!found) System.out.println("No matching book found.");
    }

    public void searchStudent(String keyword) {
        boolean found = false;

        for (Student student : students) {
            String text = (
                student.getStudentId() + " " +
                student.getRollNumber() + " " +
                student.getName() + " " +
                student.getClassName()
            ).toLowerCase();

            if (text.contains(keyword.toLowerCase())) {
                student.display();
                found = true;
            }
        }

        if (!found) System.out.println("No matching student found.");
    }

    public void viewIssuedBooks() {
        System.out.println("\n================ ISSUED BOOKS ================");
        boolean found = false;

        for (IssueRecord issue : issues) {
            if (issue.getStatus().equalsIgnoreCase("Issued")) {
                Book book = findBook(issue.getBookId());
                Student student = findStudent(issue.getStudentId());

                System.out.printf(
                    "%s | %s | %s | Issued: %s | By: %s%n",
                    issue.getIssueId(),
                    book == null ? issue.getBookId() : book.getTitle(),
                    student == null ? issue.getStudentId() : student.getName(),
                    issue.getIssueDate(),
                    issue.getIssuedBy()
                );

                found = true;
            }
        }

        if (!found) System.out.println("No books are currently issued.");
    }

    public void viewAvailableBooks() {
        System.out.println("\n================ AVAILABLE BOOKS ================");

        boolean found = false;

        for (Book book : books) {
            if (book.isAvailable()) {
                book.display();
                found = true;
            }
        }

        if (!found) System.out.println("No books are currently available.");
    }

    public void statistics() {
        int available = 0;
        int issued = 0;

        for (Book book : books) {
            if (book.isAvailable()) available++;
            else issued++;
        }

        String mostBorrowed = "No issue history";
        int highest = 0;

        for (Book book : books) {
            int count = 0;

            for (IssueRecord issue : issues) {
                if (issue.getBookId().equalsIgnoreCase(book.getBookId())) {
                    count++;
                }
            }

            if (count > highest) {
                highest = count;
                mostBorrowed = book.getTitle();
            }
        }

        IssueRecord recent = null;

        for (IssueRecord issue : issues) {
            if (recent == null || issue.getIssueDate().compareTo(recent.getIssueDate()) > 0) {
                recent = issue;
            }
        }

        System.out.println("\n================ STATISTICS ================");
        System.out.println("School              : " + schoolName);
        System.out.println("Project Teacher     : " + teacherName);
        System.out.println("Total Books         : " + books.size());
        System.out.println("Available Books     : " + available);
        System.out.println("Issued Books        : " + issued);
        System.out.println("Total Students      : " + students.size());
        System.out.println("Most Borrowed Book  : " + mostBorrowed);
        System.out.println(
            "Recently Issued     : " +
            (recent == null ? "None" : findBook(recent.getBookId()).getTitle())
        );
    }
}


public class LibraryManagementSystem {

    private static final Scanner scanner = new Scanner(System.in);

    private static final Library library =
        new Library(
            "Delhi Public School (DPS), Budgam",
            "Mrs. Tabassum"
        );

    public static void main(String[] args) {

        loadDatabase();

        while (true) {
            showMenu();

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> viewBooks();
                case 2 -> searchBook();
                case 3 -> addBook();
                case 4 -> deleteBook();
                case 5 -> updateBook();
                case 6 -> viewStudents();
                case 7 -> searchStudent();
                case 8 -> issueBook();
                case 9 -> returnBook();
                case 10 -> library.viewIssuedBooks();
                case 11 -> library.viewAvailableBooks();
                case 12 -> library.statistics();

                case 13 -> {
                    System.out.println("\nThank you for using the Library Management System.");
                    scanner.close();
                    return;
                }

                default ->
                    System.out.println("Invalid option. Choose a number from 1 to 13.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n=================================================");
        System.out.println("          LIBRARY MANAGEMENT SYSTEM");
        System.out.println("        DELHI PUBLIC SCHOOL, BUDGAM");
        System.out.println("=================================================");
        System.out.println("1.  View Books");
        System.out.println("2.  Search Book");
        System.out.println("3.  Add Book");
        System.out.println("4.  Delete Book");
        System.out.println("5.  Update Book");
        System.out.println("6.  View Students");
        System.out.println("7.  Search Student");
        System.out.println("8.  Issue Book");
        System.out.println("9.  Return Book");
        System.out.println("10. View Issued Books");
        System.out.println("11. View Available Books");
        System.out.println("12. View Statistics");
        System.out.println("13. Exit");
        System.out.println("=================================================");
    }

    private static void viewBooks() {
        System.out.println("\n================ BOOK DATABASE ================");
        System.out.printf(
            "%-7s %-38s %-28s %-6s %-18s %-12s %-8s%n",
            "ID","Title","Author","Year","Category","Status","Shelf"
        );
        System.out.println("-".repeat(125));

        for (Book book : library.getBooks()) {
            book.display();
        }
    }

    private static void searchBook() {
        String keyword = readText("Enter title, author, category or book ID: ");
        library.searchBook(keyword);
    }

    private static void addBook() {
        System.out.println("\n================ ADD BOOK ================");

        String id = readText("Book ID: ");

        if (library.findBook(id) != null) {
            System.out.println("A book with this ID already exists.");
            return;
        }

        String title = readText("Title: ");
        String author = readText("Author: ");
        int year = readInt("Original publication year: ");
        String category = readText("Category: ");
        String isbn = readText("ISBN: ");
        String shelf = readText("Shelf number: ");

        library.addBook(
            new Book(id, title, author, year, category, isbn, shelf)
        );
        saveDatabase();

        System.out.println("Book added successfully.");
    }

    private static void deleteBook() {
        String id = readText("Enter Book ID to delete: ");
        library.deleteBook(id);
        saveDatabase();
    }

    private static void updateBook() {
        String id = readText("Enter Book ID to update: ");
        library.updateBook(id, scanner);
        saveDatabase();
    }

    private static void viewStudents() {
        System.out.println("\n================ STUDENT DATABASE ================");
        System.out.printf(
            "%-7s %-6s %-25s %-8s%n",
            "ID","Roll","Name","Class"
        );
        System.out.println("-".repeat(52));

        for (Student student : library.getStudents()) {
            student.display();
        }
    }

    private static void searchStudent() {
        String keyword = readText("Enter student ID, roll, name or class: ");
        library.searchStudent(keyword);
    }

    private static void issueBook() {
        System.out.println("\n================ ISSUE BOOK ================");

        String studentId = readText("Student ID: ");
        String bookId = readText("Book ID: ");
        String date = readText("Issue date (YYYY-MM-DD): ");

        if (library.issueBook(studentId, bookId, date, "Mrs. Tabassum")) {
            saveDatabase();
            System.out.println("Book issued successfully.");
        } else {
            System.out.println(
                "Book could not be issued. Check the student, book, availability, or 3-book limit."
            );
        }
    }

    private static void returnBook() {
        System.out.println("\n================ RETURN BOOK ================");

        String studentId = readText("Student ID: ");
        String bookId = readText("Book ID: ");
        String date = readText("Return date (YYYY-MM-DD): ");
        double fine = readDouble("Fine amount: ");

        if (library.returnBook(
                studentId, bookId, date, fine, "Mrs. Tabassum")) {
            saveDatabase();
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("Active issue record not found.");
        }
    }

    private static String readText(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) return input;

            System.out.println("Input cannot be empty.");
        }
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String message) {
        while (true) {
            System.out.print(message);

            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }

    private static final Path BOOKS_FILE = Path.of("books.txt");
    private static final Path STUDENTS_FILE = Path.of("students.txt");
    private static final Path ISSUES_FILE = Path.of("issues.txt");

    private static String encode(String value) {
        return Base64.getEncoder().encodeToString(
            value.getBytes(StandardCharsets.UTF_8)
        );
    }

    private static String decode(String value) {
        return new String(
            Base64.getDecoder().decode(value),
            StandardCharsets.UTF_8
        );
    }

    private static String clean(String value) {
        return value == null ? "" : value;
    }

    private static void saveDatabase() {
        try {
            List<String> bookLines = new ArrayList<>();
            for (Book book : library.getBooks()) {
                bookLines.add(
                    encode(book.getBookId()) + "|" +
                    encode(book.getTitle()) + "|" +
                    encode(book.getAuthor()) + "|" +
                    book.getPublicationYear() + "|" +
                    encode(book.getCategory()) + "|" +
                    encode(book.getIsbn()) + "|" +
                    encode(book.getShelfNumber()) + "|" +
                    book.isAvailable()
                );
            }

            List<String> studentLines = new ArrayList<>();
            for (Student student : library.getStudents()) {
                studentLines.add(
                    encode(student.getStudentId()) + "|" +
                    encode(student.getRollNumber()) + "|" +
                    encode(student.getName()) + "|" +
                    encode(student.getClassName())
                );
            }

            List<String> issueLines = new ArrayList<>();
            for (IssueRecord issue : library.getIssues()) {
                issueLines.add(
                    encode(issue.getIssueId()) + "|" +
                    encode(issue.getStudentId()) + "|" +
                    encode(issue.getBookId()) + "|" +
                    encode(clean(issue.getIssueDate())) + "|" +
                    encode(clean(issue.getReturnDate())) + "|" +
                    issue.getFine() + "|" +
                    encode(clean(issue.getStatus())) + "|" +
                    encode(clean(issue.getIssuedBy())) + "|" +
                    encode(clean(issue.getReturnedBy()))
                );
            }

            Files.write(BOOKS_FILE, bookLines, StandardCharsets.UTF_8);
            Files.write(STUDENTS_FILE, studentLines, StandardCharsets.UTF_8);
            Files.write(ISSUES_FILE, issueLines, StandardCharsets.UTF_8);

        } catch (IOException e) {
            System.out.println("Warning: Could not save library data: " + e.getMessage());
        }
    }

    private static boolean hasSavedDatabase() {
        return Files.exists(BOOKS_FILE)
            && Files.exists(STUDENTS_FILE)
            && Files.exists(ISSUES_FILE);
    }

    private static void loadSavedDatabase() {
        try {
            for (String line : Files.readAllLines(BOOKS_FILE, StandardCharsets.UTF_8)) {
                if (line.isBlank()) continue;
                String[] p = line.split("\\|", -1);
                Book book = new Book(
                    decode(p[0]), decode(p[1]), decode(p[2]),
                    Integer.parseInt(p[3]), decode(p[4]), decode(p[5]), decode(p[6])
                );
                book.setAvailable(Boolean.parseBoolean(p[7]));
                library.addBook(book);
            }

            for (String line : Files.readAllLines(STUDENTS_FILE, StandardCharsets.UTF_8)) {
                if (line.isBlank()) continue;
                String[] p = line.split("\\|", -1);
                library.addStudent(new Student(
                    decode(p[0]), decode(p[1]), decode(p[2]), decode(p[3])
                ));
            }

            for (String line : Files.readAllLines(ISSUES_FILE, StandardCharsets.UTF_8)) {
                if (line.isBlank()) continue;
                String[] p = line.split("\\|", -1);
                library.addIssue(new IssueRecord(
                    decode(p[0]), decode(p[1]), decode(p[2]),
                    decode(p[3]), decode(p[4]), Double.parseDouble(p[5]),
                    decode(p[6]), decode(p[7]), decode(p[8])
                ));
            }

        } catch (Exception e) {
            System.out.println("Saved data could not be loaded. Restoring default data.");
            library.getBooks().clear();
            library.getStudents().clear();
            library.getIssues().clear();
            loadDefaultDatabase();
        }
    }

    private static void loadDatabase() {
        if (hasSavedDatabase()) {
            loadSavedDatabase();
            return;
        }

        loadDefaultDatabase();
        saveDatabase();
    }

    private static void loadDefaultDatabase() {

        // ---------------- BOOK DATABASE ----------------
        library.addBook(new Book("BK001", "1984", "George Orwell", 1949, "Fiction", "9780451524935", "S-101"));
        library.addBook(new Book("BK002", "A Brief History of Time", "Stephen Hawking", 1988, "Science", "9780553380163", "S-101"));
        library.addBook(new Book("BK003", "Animal Farm", "George Orwell", 1945, "Fiction", "9780451526342", "S-101"));
        library.addBook(new Book("BK004", "Annihilation of Caste", "B. R. Ambedkar", 1936, "Social Thought", "9788189059637", "S-101"));
        library.addBook(new Book("BK005", "Atomic Habits", "James Clear", 2018, "Self-Help", "9780735211292", "S-101"));
        library.addBook(new Book("BK006", "Beyond Good and Evil", "Friedrich Nietzsche", 1886, "Philosophy", "9780140449235", "S-101"));
        library.addBook(new Book("BK007", "Brave New World", "Aldous Huxley", 1932, "Fiction", "9780060850524", "S-101"));
        library.addBook(new Book("BK008", "Can't Hurt Me", "David Goggins", 2018, "Memoir", "9781544512280", "S-101"));
        library.addBook(new Book("BK009", "Colonizing Kashmir: State-building Under Indian Occupation", "Hafsa Kanjwal", 2023, "History", "9781503636033", "S-101"));
        library.addBook(new Book("BK010", "Crime and Punishment", "Fyodor Dostoevsky", 1866, "Fiction", "9780140449136", "S-101"));
        library.addBook(new Book("BK011", "Curfewed Night", "Basharat Peer", 2010, "Memoir", "9780007350704", "S-102"));
        library.addBook(new Book("BK012", "Das Kapital", "Karl Marx", 1867, "Political Economy", "9780140445688", "S-102"));
        library.addBook(new Book("BK013", "Deep Work", "Cal Newport", 2016, "Productivity", "9781455586691", "S-102"));
        library.addBook(new Book("BK014", "Discovery of India", "Jawaharlal Nehru", 1946, "History", "9780143031031", "S-102"));
        library.addBook(new Book("BK015", "Diwan-e-Ghalib", "Mirza Ghalib", 1791, "Poetry", "SAMPLE-ISBN-DIWAN", "S-102"));
        library.addBook(new Book("BK016", "Fahrenheit 451", "Ray Bradbury", 1953, "Fiction", "9781451678181", "S-102"));
        library.addBook(new Book("BK017", "Freedom at Midnight", "Larry Collins and Dominique Lapierre", 1975, "History", "9780002160551", "S-102"));
        library.addBook(new Book("BK018", "Freedom Is a Constant Struggle", "Angela Y. Davis", 2016, "Social Thought", "9781608465644", "S-102"));
        library.addBook(new Book("BK019", "Gaza", "Joe Sacco", 2009, "Graphic Nonfiction", "9780224072232", "S-102"));
        library.addBook(new Book("BK020", "Glimpses of World History", "Jawaharlal Nehru", 1934, "History", "9780195623604", "S-102"));
        library.addBook(new Book("BK021", "Hind Swaraj", "Mahatma Gandhi", 1909, "Political Thought", "9788172290083", "S-103"));
        library.addBook(new Book("BK022", "Homo Deus", "Yuval Noah Harari", 2015, "History", "9780062464316", "S-103"));
        library.addBook(new Book("BK023", "India After Gandhi", "Ramachandra Guha", 2007, "History", "9780330505543", "S-103"));
        library.addBook(new Book("BK024", "Inglorious Empire", "Shashi Tharoor", 2017, "History", "9781849048088", "S-103"));
        library.addBook(new Book("BK025", "Is Paris Burning?", "Larry Collins and Dominique Lapierre", 1965, "World War II", "9780785812463", "S-103"));
        library.addBook(new Book("BK026", "Kashmir in Conflict", "Victoria Schofield", 2003, "History", "9781860648984", "S-103"));
        library.addBook(new Book("BK027", "Life Narratives from Kashmir and Palestine", "Shumaila Noreen", 2023, "Academic Article", "SAMPLE-RECORD-NOT-A-BOOK", "S-103"));
        library.addBook(new Book("BK028", "Long Walk to Freedom", "Nelson Mandela", 1994, "Autobiography", "9780316548182", "S-103"));
        library.addBook(new Book("BK029", "Man's Search for Meaning", "Viktor E. Frankl", 1946, "Psychology", "9780807014295", "S-103"));
        library.addBook(new Book("BK030", "Mastery", "Robert Greene", 2012, "Self-Help", "9780670024964", "S-103"));
        library.addBook(new Book("BK031", "Meditations", "Marcus Aurelius", 180, "Philosophy", "9780140449334", "S-104"));
        library.addBook(new Book("BK032", "My Experiments with Truth", "Mahatma Gandhi", 1927, "Autobiography", "9788184682045", "S-104"));
        library.addBook(new Book("BK033", "Night", "Elie Wiesel", 1956, "Memoir", "9780374500016", "S-104"));
        library.addBook(new Book("BK034", "Notes from Underground", "Fyodor Dostoevsky", 1864, "Philosophy", "9780553211443", "S-104"));
        library.addBook(new Book("BK035", "O Jerusalem!", "Larry Collins and Dominique Lapierre", 1972, "History", "9780297994596", "S-104"));
        library.addBook(new Book("BK036", "Orientalism", "Edward W. Said", 1978, "History", "9780394428147", "S-104"));
        library.addBook(new Book("BK037", "Our Moon Has Blood Clots", "Rahul Pandita", 2014, "Memoir", "9788184003901", "S-104"));
        library.addBook(new Book("BK038", "Palestine", "Joe Sacco", 1993, "Graphic Nonfiction", "9780224069829", "S-104"));
        library.addBook(new Book("BK039", "Resisting Occupation in Kashmir", "Haley Duschinski, Mona Bhan, Ather Zia and Cynthia Mahmood", 2018, "Anthropology", "9780812249781", "S-104"));
        library.addBook(new Book("BK040", "Rich Dad Poor Dad", "Robert T. Kiyosaki", 1997, "Finance", "9780446691794", "S-104"));
        library.addBook(new Book("BK041", "Sapiens", "Yuval Noah Harari", 2011, "History", "9780062316097", "S-105"));
        library.addBook(new Book("BK042", "Ten Myths About Israel", "Ilan Pappe", 2017, "History", "9781786630193", "S-105"));
        library.addBook(new Book("BK043", "The 48 Laws of Power", "Robert Greene", 1998, "Self-Help", "9780140280197", "S-105"));
        library.addBook(new Book("BK044", "The Alchemist", "Paulo Coelho", 1988, "Fiction", "9780061122415", "S-105"));
        library.addBook(new Book("BK045", "The Art of Seduction", "Robert Greene", 2001, "Self-Help", "9780142001196", "S-105"));
        library.addBook(new Book("BK046", "The Art of War", "Sun Tzu", -500, "Strategy", "9780593314661", "S-105"));
        library.addBook(new Book("BK047", "The Autobiography of Malcolm X", "Malcolm X and Alex Haley", 1965, "Autobiography", "9780802140852", "S-105"));
        library.addBook(new Book("BK048", "The Brothers Karamazov", "Fyodor Dostoevsky", 1880, "Fiction", "9780374528379", "S-105"));
        library.addBook(new Book("BK049", "The Catcher in the Rye", "J. D. Salinger", 1951, "Fiction", "9780316769488", "S-105"));
        library.addBook(new Book("BK050", "The Communist Manifesto", "Karl Marx and Friedrich Engels", 1848, "Political Thought", "9780140447576", "S-105"));
        library.addBook(new Book("BK051", "The Diary of a Young Girl", "Anne Frank", 1947, "Memoir", "9780143455257", "S-106"));
        library.addBook(new Book("BK052", "The Ethnic Cleansing of Palestine", "Ilan Pappe", 2006, "History", "9781851684670", "S-106"));
        library.addBook(new Book("BK053", "The Great Gatsby", "F. Scott Fitzgerald", 1925, "Fiction", "9780743273565", "S-106"));
        library.addBook(new Book("BK054", "The Hobbit", "J. R. R. Tolkien", 1937, "Fantasy", "9780547928227", "S-106"));
        library.addBook(new Book("BK055", "The Jail Notebook and Other Writings", "Bhagat Singh and Bhupendra Hooja", 2007, "History", "9788187496724", "S-106"));
        library.addBook(new Book("BK056", "The Kite Runner", "Khaled Hosseini", 2003, "Fiction", "9781573222457", "S-106"));
        library.addBook(new Book("BK057", "The Laws of Human Nature", "Robert Greene", 2018, "Self-Help", "9780525428145", "S-106"));
        library.addBook(new Book("BK058", "The Old Man and the Sea", "Ernest Hemingway", 1952, "Fiction", "9780684830490", "S-106"));
        library.addBook(new Book("BK059", "The Prince", "Niccolo Machiavelli", 1532, "Political Thought", "9780140449150", "S-106"));
        library.addBook(new Book("BK060", "The Question of Palestine", "Edward W. Said", 1979, "History", "9780812908329", "S-106"));
        library.addBook(new Book("BK061", "The Republic", "Plato", -380, "Philosophy", "9780140455113", "S-107"));
        library.addBook(new Book("BK062", "The Stranger", "Albert Camus", 1942, "Fiction", "9780679720201", "S-107"));
        library.addBook(new Book("BK063", "The Wretched of the Earth", "Frantz Fanon", 1961, "Political Thought", "9780802150844", "S-107"));
        library.addBook(new Book("BK064", "Thus Spoke Zarathustra", "Friedrich Nietzsche", 1883, "Philosophy", "9780140441185", "S-107"));
        library.addBook(new Book("BK065", "To Young Political Workers", "Bhagat Singh", 1931, "Political Thought", "SAMPLE-ISBN-PAMPHLET", "S-107"));
        library.addBook(new Book("BK066", "Understanding Kashmir and Kashmiris", "Christopher Snedden", 2015, "History", "9781849043427", "S-107"));
        library.addBook(new Book("BK067", "Why I Am an Atheist", "Bhagat Singh", 1930, "Essay", "9788178710600", "S-107"));
        library.addBook(new Book("BK068", "Wings of Fire", "A. P. J. Abdul Kalam and Arun Tiwari", 1999, "Autobiography", "9788173711464", "S-107"));

        // ---------------- STUDENT DATABASE ----------------
        library.addStudent(new Student("ST001", "01", "Ahmar Riaz Magrey", "XII-C"));
        library.addStudent(new Student("ST002", "02", "Airaaf Shah", "XII-A"));
        library.addStudent(new Student("ST003", "03", "Fawad Bhat", "XII-A"));
        library.addStudent(new Student("ST004", "04", "Hayaan", "XII-A"));
        library.addStudent(new Student("ST005", "05", "Hyder Wani", "XII-A"));
        library.addStudent(new Student("ST006", "06", "Kafel Shah", "XII-A"));
        library.addStudent(new Student("ST007", "07", "Mummin Khan", "XII-C"));
        library.addStudent(new Student("ST008", "08", "Mussa Khan", "XII-C"));
        library.addStudent(new Student("ST009", "09", "Owais Wani", "XII-C"));
        library.addStudent(new Student("ST010", "10", "Quaid", "XII-B"));
        library.addStudent(new Student("ST011", "11", "Sahil", "XII-B"));
        library.addStudent(new Student("ST012", "12", "Sehran Sheikh", "XII-B"));
        library.addStudent(new Student("ST013", "13", "Tabish Wani", "XII-A"));
        library.addStudent(new Student("ST014", "14", "Zain Rather", "XII-A"));
        library.addStudent(new Student("ST015", "15", "Zamin Mir", "XII-B"));

        // ---------------- DEFAULT ISSUE RECORDS ----------------
        library.addIssue(new IssueRecord("IS001", "ST001", "BK044", "2026-08-03", "2026-08-17", 0, "Returned", "Mrs. Tabassum", "Mrs. Tabassum"));
        library.addIssue(new IssueRecord("IS002", "ST002", "BK005", "2026-08-05", "2026-08-19", 0, "Returned", "Mrs. Tabassum", "Mrs. Tabassum"));
        library.addIssue(new IssueRecord("IS003", "ST003", "BK001", "2026-08-07", "2026-08-21", 0, "Returned", "Mrs. Tabassum", "Mrs. Tabassum"));
        library.addIssue(new IssueRecord("IS004", "ST004", "BK056", "2026-08-10", "2026-08-24", 50, "Returned", "Mrs. Tabassum", "Mrs. Tabassum"));
        library.addIssue(new IssueRecord("IS005", "ST005", "BK068", "2026-08-12", "2026-08-26", 0, "Returned", "Mrs. Tabassum", "Mrs. Tabassum"));
        library.addIssue(new IssueRecord("IS006", "ST006", "BK041", "2026-08-15", "2026-08-29", 0, "Returned", "Mrs. Tabassum", "Mrs. Tabassum"));
        library.addIssue(new IssueRecord("IS007", "ST007", "BK003", "2026-08-18", "2026-09-01", 0, "Returned", "Mrs. Tabassum", "Mrs. Tabassum"));
        library.addIssue(new IssueRecord("IS008", "ST008", "BK023", "2026-08-20", "2026-09-03", 30, "Returned", "Mrs. Tabassum", "Mrs. Tabassum"));
        library.addIssue(new IssueRecord("IS009", "ST009", "BK010", "2026-08-22", "2026-09-05", 0, "Returned", "Mrs. Tabassum", "Mrs. Tabassum"));
        library.addIssue(new IssueRecord("IS010", "ST010", "BK046", "2026-08-24", "2026-09-07", 0, "Returned", "Mrs. Tabassum", "Mrs. Tabassum"));
        library.addIssue(new IssueRecord("IS011", "ST011", "BK028", "2026-08-27", "2026-09-10", 0, "Returned", "Mrs. Tabassum", "Mrs. Tabassum"));
        library.addIssue(new IssueRecord("IS012", "ST012", "BK030", "2026-08-29", "2026-09-12", 20, "Returned", "Mrs. Tabassum", "Mrs. Tabassum"));
        library.addIssue(new IssueRecord("IS013", "ST013", "BK043", "2026-09-01", "2026-09-15", 0, "Returned", "Mrs. Tabassum", "Mrs. Tabassum"));
        library.addIssue(new IssueRecord("IS014", "ST014", "BK066", "2026-09-03", "2026-09-17", 0, "Returned", "Mrs. Tabassum", "Mrs. Tabassum"));
        library.addIssue(new IssueRecord("IS015", "ST015", "BK061", "2026-09-05", "2026-09-19", 0, "Returned", "Mrs. Tabassum", "Mrs. Tabassum"));
        library.addIssue(new IssueRecord("IS016", "ST001", "BK001", "2026-09-10", "2026-09-24", 0, "Returned", "Mrs. Tabassum", "Mrs. Tabassum"));
        library.addIssue(new IssueRecord("IS017", "ST003", "BK037", "2026-09-15", "2026-09-25", 0, "Returned", "Mrs. Tabassum", "Mrs. Tabassum"));
        library.addIssue(new IssueRecord("IS018", "ST006", "BK060", "2026-09-20", "", 0, "Issued", "Mrs. Tabassum", "-"));
        library.addIssue(new IssueRecord("IS019", "ST009", "BK009", "2026-09-22", "", 0, "Issued", "Mrs. Tabassum", "-"));
        library.addIssue(new IssueRecord("IS020", "ST013", "BK057", "2026-09-25", "", 0, "Issued", "Mrs. Tabassum", "-"));

        // Mark the books from active issue records as unavailable.
        markInitialIssue("ST006", "BK060");
        markInitialIssue("ST009", "BK009");
        markInitialIssue("ST013", "BK057");
    }

    private static void markInitialIssue(String studentId, String bookId) {
        Book book = library.findBook(bookId);

        if (book != null) {
            book.setAvailable(false);
        }
    }
}
