🚀 Automation Test Framework

A Java + Selenium + TestNG + Maven based automation framework designed for UI and API testing with support for multiple environments (local, grid, jenkins).

📂 Project Structure

your-project/
│── pom.xml                   # Maven dependencies & build
│── README.md                 # Project documentation
│── .gitignore                # Git ignore rules
│
│── src/
│   ├── main/java/
│   │   ├── base/             # Base classes (WebDriver, Hooks)
│   │   ├── utils/            # Utility classes (Waits, Helpers, ExtentManager)
│   │   └── config/           # Config reader classes
│   │
│   └── test/java/
│       ├── pages/            # Page Object Model classes
│       ├── tests/            # Test cases
│       └── runners/          # TestNG / Cucumber runners
│
│── src/test/resources/
│   ├── testng.xml            # TestNG suite definition
│   ├── config-local.properties   # Local environment configs
│   ├── config-jenkins.properties # Jenkins/CI configs
│   └── log4j2.xml            # Logging configuration
│
│── target/extent-report/      # Extent Reports (ignored in Git)
│── target/screenshots/        # Failure screenshots (ignored in Git)

⚙️ Config Variables

The framework uses environment-specific configuration files under src/test/resources/.

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
mvn clean test -Denv=local -Dbrowser=chrome

Run on Jenkins/CI environment:
mvn clean test -Denv=jenkins -Dbrowser=chrome

Run on Selenium Grid:
mvn clean test -Denv=grid -Dbrowser=firefox

Run with a specific TestNG suite:
mvn clean test -Dsurefire.suiteXmlFiles=testng.xml -Denv=local

Override config values at runtime:
mvn clean test -Denv=local -Dbrowser=edge -Dheadless=true

🛠 Tech Stack

Java 21
Selenium WebDriver
TestNG
Maven
Extent Reports
Jenkins (CI/CD)
Docker + Selenium Grid

📊 Reports & Logs

Extent Reports → target/extent-report/index.html
Screenshots for failures → target/screenshots/
Both folders are excluded from Git via .gitignore
In Jenkins → Extent Report is auto-published (see sidebar link)

🔧 Jenkins CI/CD

Jenkinsfile Highlights:
- Parameters:
    - ENV → local | grid | jenkins
    - BROWSER → chrome | firefox
- Runs Maven tests with chosen parameters
- Publishes Extent Report
- Archives screenshots and JUnit XMLs

Example: Jenkins build with parameters
- ENV=grid, BROWSER=firefox → Runs on Selenium Grid Firefox
- ENV=jenkins, BROWSER=chrome → Runs headless inside Jenkins

🔒 Git Hygiene

Reports, screenshots, logs → excluded via .gitignore
Secrets & credentials → never stored in Git
Use Jenkins Credentials plugin or environment variables
Configurations → environment-specific (config-local.properties, config-jenkins.properties)

✅ Next Steps

- Extend environments: config-qa.properties, config-prod.properties
- Add API Test Module (RestAssured / SOAP-WS)
- Integrate Allure Reports for richer analytics
