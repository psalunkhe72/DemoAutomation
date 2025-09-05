🚀 Automation Test Framework

A Java + Selenium + TestNG + Maven based automation framework designed for UI and API testing with support for multiple environments (local, jenkins, etc.).

📂 Project Structure

your-project/
│── pom.xml # Maven dependencies & build
│── README.md # Project documentation
│── .gitignore # Git ignore rules
│
│── src/
│ ├── main/java/
│ │ ├── base/ # Base classes (WebDriver, Hooks)
│ │ ├── utils/ # Utility classes (Waits, Helpers)
│ │ └── config/ # Config reader classes
│ │
│ └── test/java/
│ ├── pages/ # Page Object Model classes
│ ├── tests/ # Test cases
│ └── runners/ # TestNG / Cucumber runners
│
│── src/test/resources/
│ ├── testng.xml # TestNG suite definition
│ ├── config-local.properties # Local environment configs
│ ├── config-jenkins.properties # Jenkins/CI configs
│ └── log4j2.xml # Logging configuration
│
│── reports/ # Test execution reports (ignored in Git)
│── screenshots/ # Failure screenshots (ignored in Git)

⚙️ Config Variables

The framework uses environment-specific configuration files stored under src/test/resources/.

Example: config-local.properties
browser=chrome
baseUrl=http://localhost:8080

timeout=20
headless=false

Example: config-jenkins.properties
browser=firefox
baseUrl=https://staging.myapp.com

timeout=30
headless=true

At runtime, the framework automatically loads the correct file based on the -Denv value.

⚡ Running Tests

Run on Local environment:
mvn clean test -Denv=local

Run on Jenkins/CI environment:
mvn clean test -Denv=jenkins

Run with a specific TestNG suite:
mvn clean test -Denv=local -DsuiteXmlFile=testng.xml

Override config values at runtime:
mvn clean test -Denv=local -Dbrowser=edge -Dheadless=true

🛠 Tech Stack

Java 21

Selenium WebDriver

TestNG

Maven

Extent / Allure Reports

Jenkins (CI/CD) Integration

📊 Reports & Logs

Reports are generated under reports/

Screenshots for failures are stored under screenshots/

Both folders are excluded from Git using .gitignore

Reports can be published in Jenkins for better visibility

🔒 Git Hygiene

Reports, screenshots, logs → excluded via .gitignore

Secrets & credentials → never stored in Git

Use Jenkins Credentials plugin or environment variables

Configurations → environment-specific (config-local.properties, config-jenkins.properties)

✅ Next Steps

Setup Jenkins job:

Use Git SCM to pull this repo

Run with mvn clean test -Denv=jenkins

Archive reports as build artifacts

Extend environments:

Add config-qa.properties, config-prod.properties as needed

Improve reporting:

Integrate Allure/Extent Reports into Jenkins