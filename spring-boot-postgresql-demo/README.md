# Getting Started

### Pre-requisites

* Java 1.8 or higher installed
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/maven-plugin/reference/html/)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* postgreSQL 12 installed and configure with database with user name and password

### About the Sample Application
This application is REST API based Spring Boot application integrated with postgreSQL database. You can import this as maven project to your favourite ide and configure data source section under src/resources/application.properties file then can run as Spring Boot Application.

This sample application has embeded postgreSQL database which need a pre-existing database to execute all the JUnit test cases.
You can configure in src/test/resouces/database.properties file under data source configuration section

Following API's are available : 

Add Employee :

* POST : 
API Endpoint URL : http://localhost:8080/api/employees
* Sample request example :   
Headers: Content-Type : application/json   
Request body :
{
	"name" : "jack ryan",
	"email" : "jack.ryan@test.com"
}

Get all the employees :
* GET:
API Endpoint URL : http://localhost:8080/api/employees

Get employee details by id :
* GET:
API Endpoint URL : 
http://localhost:8080/api/employees/1

Update employee details :
* PUT:
API Endpoint URL : http://localhost:8080/api/employees/1  
Headers: Content-Type : application/json

Request body :
{
	"name" : "Jack",
	"email" : "Ryan@test.com"
}

Delete employee by id :
* DELETE:
API Endpoint URL : http://localhost:8080/api/employees/1




