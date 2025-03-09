
## Test Automation Framework Cucumber TestNG ##
**SOME FEATURES IN FRAMEWORK**

1. Run the parallel Scenario on feature file
2. Cucumber Report
3. Extent Report
4. Allure Report
5. Send Mail after the run test (Report information and HTML file attachment)
6. Write Log to file
7. Record video and Screenshot test case
8. Read data test from Excel file (xlsx, csv, json,...)
9. Base function in the package: utils, helpers
10. Read data test from Json file
11. Main keyword is WebUI
12. Sample test feature

### **SYSTEM REQUIREMENTS**

- **JDK version >= 21**
- Chrome, Edge, Firefox browser
- Setup **Allure environment**:
- **IntelliJ IDEA** is the best choice (easy to change JDK version)



### **HOW TO USE**

**1. Run parallel the test case**

- Run Cucumber TestRunner from **src/test/java/yahoo/com/runners**
- Run Feature file (**src/test/resources/features/**)
- Run Feature in suite XML (**src/test/resources/suites/**)
- Run Feature from Maven pom.xml file
  (**```mvn clean test```**)
- ```mvn clean test -Dbrowser=chrome```
- ```mvn clean test -Dbrowser=edge```
- ```mvn clean test -Dbrowser=firefox```



**2. Cucumber Report**
![image](https://github.com/user-attachments/assets/f1b5de41-1c65-4487-ac1f-af258d6ffb3d)


**3. Extent Report**

- Config from src/test/resources/extent.properties
- Config PDF from src/test/resources/pdf-config.yaml
![image](https://github.com/user-attachments/assets/ae526626-e936-4116-b570-1ab94ca2212b)


**4. Allure Report**

- Open Terminal: **_allure serve target/allure-results_**
or
- ```allure generate --single-file target/allure-results -o allure-report --clean```


**5. Send Mail after the run test**

- Config **true/false** in config.properties
  (**_src/test/resources/config/config.properties_**)

**6. Write Log to file**

- Call class: Log.info , Log.pass, Log.error,... (**Log** is a custom global class from Log4j2)

![image](https://github.com/user-attachments/assets/3a15a6b1-17bf-4225-9ddc-24bd78704e6d)


**7. Record video and Screenshot**

- Setup in **_config.properties_** file
  (**_src/test/resources/config/config.properties_**)
- screenshot_passed_steps=yes or no
- screenshot_failed_steps=yes or no
- screenshot_skipped_steps=yes or no
- screenshot_all_steps=yes or no
![image](https://github.com/user-attachments/assets/71e04799-f801-4007-99ce-91e77122ac1b)



**9. Main keyword is WebUI**

- WebUI class is main keyword in Framework. It contains common functions
- How to use: WebUI.function_name
- Example: WebUI.setWindowSize(1024, 768), WebUI.screenshotElement(By by, String elementName),...

### Project structure
```

```
