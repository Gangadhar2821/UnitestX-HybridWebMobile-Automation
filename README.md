
#UniTestX 🚀

---

UniTestX is an **hybrid automation framework** built both for **mobile app (Android)** and **Web app** testing.It leverages **TestNG** with a **data‑driven approach** to enable flexible, parameterized test execution.  The design follows the **Page Object Model (POM)**, ensuring clean separation of concerns and reusable components.With integrated **reporting, logging**, it delivers **scalable, maintainable, and reliable** automation.
  
---

## 📑 Table of Contents

- [Overview](#overview)  
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)  
- [Cloning the Project](#cloning-the-project)  
- [Installation](#installation)  
- [Project Structure](#project-structure)  
- [Usage](#usage)  
- [Data-Driven Approach](#data-driven-approach)  
- [Reporting](#reporting)  
- [Future Enhancements](#future-enhancements)  
- [Contribution Guidelines](#contribution-guidelines)  


---

## 🔎 Overview

This framework enables automated testing of **Android mobile apps** using **Appium** and **Web apps** using **Selenium**.
It follows a **Page Object Model (POM)** design pattern and leverages **data-driven testing** for flexibility.

---

## ✨ Features

- ✅ Cross-platform automation (Web + Android)
- ✅ Centralized driver factory
- ✅ Page Object Model (POM) design
- ✅ Reusable utilities (data generators, logging, assertions)  
- ✅ Integrated logs and reports  
- ✅ TestNG-based test management

---
## 🛠 Tech Stack

![Java](https://img.shields.io/badge/Java-21-blue.svg) 
![Maven](https://img.shields.io/badge/Maven-3.6+-red.svg) 
![Appium](https://img.shields.io/badge/Appium-9.2.2-purple.svg) 
![Selenium](https://img.shields.io/badge/Selenium-4.23.0-brightgreen.svg) 
![TestNG](https://img.shields.io/badge/TestNG-7.10.2-orange.svg) 
![Apache POI](https://img.shields.io/badge/Apache_POI-5.2.5-lightgrey.svg) 
![Extent Reports](https://img.shields.io/badge/Extent_Reports-5.1.1-yellow.svg) 
![Log4j](https://img.shields.io/badge/Log4j-2.20.0-darkred.svg) 
![WebDriverManager](https://img.shields.io/badge/WebDriverManager-5.5.3-teal.svg)

---


## ⚙️ Prerequisites

- Java JDK (>= 17)  
- Maven (>= 3.6)  
- Appium server
- Appium Inspector
- Android SDK (Android Studio)  
- Emulator  
- Node.js (for Appium)  
- Selenium WebDriver  
- IDE (Eclipse)  
- Git (for Github repo management)  

---

## 🔽 Cloning the Project

To clone the repository from **GitHub**:

1. Open your terminal or Git client (Git Bash).  
2. Navigate to the directory where you want the project to be cloned.  
   **Example**  
   ```bash
   cd D:/Eclipse-Workspace
   
3. Run the `git clone` command with your repository URL:

"git clone https://github.com/Gangadhar2821/UniTestX.git"
  ```

---


## 📥 Installation

Follow the steps below to set up the project after cloning:

---
 **Note :**
After cloning, the project will be available in your local workspace.  
For example:  
[D:\Eclipse - Workspace](D:\Eclipse - Workspace)

#####1. Import Project into Eclipse IDE

1. Open Eclipse IDE.
2. Go to File → Import.
3. Select Existing Maven Projects.
4. Browse to the cloned project folder (ensure that pom.xml is visible).
5. Check the project and click Finish.

This step ensures Eclipse recognizes the project as a Maven project.

#####2. Install Maven Dependencies
Once the project is imported, you need to download and update all Maven dependencies.

1. Open Command Prompt (CMD).
2. Navigate to the project directory where pom.xml is located.

Run the following command:

```bash
mvn clean install

```
✅ Now the Framework is successfully cloned, ready to use and contribute.

---

## 📂 Project Structure

- **src/**
  - **main/java/**
    - **webbase/** → Setup & Teardown
    - **mobilebase/** → Setup & Teardown
    - **driverfactory/** → Driver setup (Selenium,Appium)
    - **datagenerator/** → Random Test data generators
    - **listener/** → Test Listener
    - **webpages/** → Page Object classes (web)
    - **mobilepages/** → Page Object classes (mobile)
    - **utils/** → Helpers (Action utils, Excel reader, Logger util)    
  - **main/resources/** → log4j2.xml file

- **src/**
  - **test/java/**
    - **tests/** → Test cases (Web & Mobile)
- **resources/** → Excel data file , Test apk

- **screenshots/** → error screenshots
- **configs/** → property file
- **reports/** → Test execution reports 
- **suitefiles/** → Test suite files  
- **pom.xml** → Maven dependencies  

---


## ▶️ Usage

You can run different workflows using their dedicated **TestNG suite XML files**:
 
   **Example**   

```bash
# Run unitestXMobile suite file
mvn test -DsuiteXmlFile=unitestXMobile.xml
```
---

## 📊 Data-Driven Approach

- Test data is stored in **UnitestX_Testdata.xlsx** under  **src\test\resources**.  
- The first column contains **TestCaseIDs** (e.g., `TC001_LoginDemoTest`).  
- The first row contains **Column Names** (e.g., `Username`, `Password`).  
- The utility method `getTestData(testCaseID, columnName)` fetches the required value.

**Example Usage:**

```bash
String username = ExcelUtil.getTestData("TC001", "Username");
String password = ExcelUtil.getTestData("TC001", "Password");

```
---

## 📈 Reporting

- Extent Reports integrated 
- Reports generated in `/reports` after execution 

---

## 🪄 Future Enhancements

- CI/CD pipeline setup in **Azure DevOps**  
- Automated report publishing as pipeline artifacts  
- Integration with **Allure Reports** for advanced visualization  

---

## 🤝 Contribution Guidelines

- Fork the repo  
- Create a feature branch  
- Commit changes with meaningful messages  
- Raise a pull request  

---
