# 🖥️ BVRITH ECAP Replica Website

## 📌 Project Description

This project is a **replica of the BVRITH ECAP portal**, developed to simulate core functionalities like user login, student information display, complaint management, and academic tracking. The system is built as a full-stack web application using **Java (Servlets + JSP)** and **MySQL**, deployed and tested using **NetBeans IDE** and **Apache Tomcat**.

It is designed to provide a clean, functional interface for **faculty and student users**, replicating the structure and workflows of the original ECAP portal used at **BVRITH Hyderabad**.

---

## 🛠️ Technologies Used

- 🌐 HTML, CSS, JavaScript (Frontend)
- ☕ Java Servlets and JSP (Backend)
- 🗄️ MySQL (Database)
- 💻 NetBeans IDE (Development)
- 🚀 Apache Tomcat (Deployment)

---

## 🎯 Features Implemented

- 🔐 **Faculty Login Authentication**
- 🧑‍🎓 **Student Dashboard View**
- 📚 **Subject Backlogs & Semester Info**
- 📝 **Complaint & Suggestion Submission**
- 🚌 **Transport & Hostel Registration Requests**
- 📄 **PDF Upload & Display Module**
- 🔍 **Project & Book Search Functionality**
- 💬 **User Feedback Interface**
- 📈 **Admin Panel (Optional)**

---

## 📁 File Structure

```

├── src/                         # Java servlets and logic
├── web/                         # HTML, JSP, CSS files
├── build/, dist/                # NetBeans auto-generated folders
├── nbproject/                   # NetBeans project configs
├── database/225d5.sql           # Sample MySQL database (if included)
├── bvrith report.pdf            # Project documentation (optional)
├── README.md                    # Project description
├── LICENSE                      # MIT License

````

---

## 🚀 How to Run the Project

1. Open the project in **NetBeans IDE**.
2. Ensure **Apache Tomcat** and **MySQL** are configured.
3. Import the database schema (e.g., `225d5.sql`) into MySQL.
4. Configure DB credentials in Java servlet connection code:

```java
String url = "jdbc:mysql://localhost:3306/225d5";
String username = "root";
String password = "root123";
````

5. Run the project on the **Tomcat server**.
6. Access via: `http://localhost:8080/<project-name>/login.jsp`

---

## 📄 License

This project is licensed under the **MIT License**.
See the `LICENSE` file for details.

---

## 🙌 Contributions

Pull requests and improvements are welcome.
Feel free to fork the repository and suggest changes or new features.

```
