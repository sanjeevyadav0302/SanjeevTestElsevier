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
java doc plugin to generate java docs for the project. To generate java doc run mvn javadoc:javadoc

**Execution**

To get up and running with the code:

Import and setup the project in your chosen IDE

Before running this program please make sure you put user repository OAuth git hub token in application.properties.
You can see the place holder for the same as  github.OAuth.token=<token>
for e.x = github.OAuth.token=1234

Run the Maven 'clean' and 'install' life cycles, this will run all the tests in Maven and build you a .jar
**Maven:**

This will run a clean, run all of the tests and package a .jar file.
mvn clean install

**Java:**

This will run the executable .jar file you just packaged with Maven.
java -jar target\SanjeevTestElsevier-0.0.1-SNAPSHOT.jar --user.name={userName}
for e.x.

java -jar target\SanjeevTestElsevier-0.0.1-SNAPSHOT.jar --user.name=abc

Alternatively you can run this application using below SpringBoot command 

mvn spring-boot:run -Dspring-boot.run.arguments=--user.name=abc


**Input Request :**

You need to pass input parameter as user.name via commandline and OAuth token it will get from application.properties

Please make sure you do pass user.name else application wont start ,you will get UserNameNotFound exception.


**OutPut Response:**

OutPut you can see in the console in json format ,that will contain all public repositories of the user, and for each
 repository—list collaborators ordered by the number of commits per collaborator in descending order.
 
 
 

