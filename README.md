# Suwasahana Medical Laboratory Management System

A comprehensive desktop application developed using **JavaFX** that streamlines all laboratory operations—from patient registration to report generation. Designed with a layered architecture, this system supports multilingual operations (Sinhala and English) and robust user role management.

## 🧪 Features

### ✅ Patient & Sample Management
- Register new patients and sample collection centers.
- Manage patient details and test preparation instructions in both **Sinhala and English**.

### ✅ Test Management
- Define and categorize lab tests.
- Assign machines and chemical usage to specific tests.
- Generate and store final reports, which can be emailed as soft copies.

### ✅ User Roles & Access Control
- **Admins** can:
  - Manage users and employees
  - Assign tests, monitor lab stock
  - Approve new admin registrations

- **Users (Lab Staff)** can:
  - Manage patient data
  - Enter test results
  - View attendance and generate reports

### ✅ Admin Registration Security
- When a new admin is registered:
  - A reminder is emailed to the current admin.
  - New account activation is delayed by **12 hours** unless approved by the existing admin.

### ✅ HR and Finance Module
- Track lab employee attendance and salary.
- Manage supplier orders and chemical stock.

### ✅ Reports & Notifications
- Generate and store test reports.
- Send reports via **email** using `javax.mail`.

---

## 🛠️ Technologies Used

- **Java**
- **JavaFX** with **SceneBuilder** for UI design
- **MySQL** for backend database
- **javax.mail** for sending emails
- **MVC + Layered Architecture**

---

## 🔧 Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/deshinikanchana/layered_architecture_Laboratory_System.git

## 🧑‍💻 Contributor
- Deshini Kanchana – Developer & Designer

## 📜 License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
