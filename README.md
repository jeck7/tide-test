# Test project for Tide
Tide test aplication - Spring RESTful services!

## Setup the Application

1. Create a database named `tide_demo` in MySQL database.

2. Open `src/main/resources/application.properties` and change `spring.datasource.username` and `spring.datasource.password` properties as per your MySQL installation.

3. Creat script for the tables is also available here:  https://github.com/jeck7/tide-test/blob/master/src/main/resources/tide_demo.sql

4. Type `mvn spring-boot:run` from the root directory of the project to run the application.

5. For proper load balansing that use Spring Cloud Netflix Ribbon on client-side in calls to another microservice project is also available here: https://github.com/jeck7/SpringCloudRibbon


<img src="https://github.com/jeck7/tide-test/blob/master/src/main/resources/Screen1.png" width="600" height="400" />

<img src="https://github.com/jeck7/tide-test/blob/master/src/main/resources/Screen2.png" width="600" height="400" />

<img src="https://github.com/jeck7/tide-test/blob/master/src/main/resources/tide_demo.jpg" width="600" height="400" />





