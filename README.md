This is a Gradle project utilizing SpringBoot. 

The file application.properties has information on datasource and other parameterized variables that are used in the project.

1. In order to run the application, use the following command.

./gradlew clean bootRun

This will start tomcat listening on 8080 (default), and can be accessed at localhost:8080.


2. In order to build a jar
./gradlew clean build or ./gradlew clean build -Dskip.tests


