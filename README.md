# SanjeevTestElsevier

You can clone this project via using git

git clone {repo uri)

**Development Environment**

I developed this application using below stack:

SpringBoot 2.0.6.RELEASE

Java SE 1.8

IntelliJ IDEA 2018.2.5 Community Edition(Editor)

**3rd Party Libraries Used:**

Build Tool: Apache Maven 3.5.4

Test Tool: JUnit 4.12

Json Binding to POJO : jackson-databind 

Override ToString for beautiful json format : commons-lang3  


**Execution**

To get up and running with the code:

Import and setup the project in your chosen IDE

Before running this program please make sure you put user repository OAuth git hub token in application.properties.

You can see the place holder for the same in application.properties  github.OAuth.token= {token} , replace token with actual token value.

for e.x github.OAuth.token=1234

Run the Maven 'clean' and 'install' life cycles, this will run all the tests in Maven and build you a .jar

**Maven:**

Below command will clean and run all the tests and package a .jar file in your target folder.

mvn clean install

**Java:**

This will run the executable .jar file you just packaged with Maven.

java -jar target\SanjeevTestElsevier-0.0.1-SNAPSHOT.jar --user.name={userName}

for e.x.

java -jar target\SanjeevTestElsevier-0.0.1-SNAPSHOT.jar --user.name=abc

Alternatively you can run this application using below SpringBoot command 

mvn spring-boot:run -Dspring-boot.run.arguments=--user.name=abc


**Input Request :**

You need to pass input parameter as user.name via command line and OAuth token it will get from application.properties

Please make sure you do pass user.name else application will not start.


**Output Response:**

Output you can see in the console in json format ,that will contain all public repositories of the user, and for each
 repository—list collaborators ordered by the number of commits per collaborator in descending order.
 
 [
   {
     "repositoryName": "repo1",
     "collaborators": [
       {
         "name": "abx",
         "commitsCount": 6
       },
       {
         "name": "cde",
         "commitsCount": 5
       },
       {
         "name": "def",
         "commitsCount": 2
       }
     ]
   },
   {
     "repositoryName": "repo2",
     "collaborators": [
       {
         "name": "abc",
         "commitsCount": 6
       }
     ]
   }
 ]
 
 
 
 Please do let me for any question/clarification on my mail id sanjeev.yadav3290@gmail.com.
 
 I would be happy to assist you.
 
 Happy coding:)
 

