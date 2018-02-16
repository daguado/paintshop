#Paint Shop - Coding challenge

# Build and run tests

The application is developed using maven 3 and Java 8

```mvn clean install``` 


# Running the jar from command line

```java -jar paintshop-0.0.1-SNAPSHOT.jar {inputFile} [outputFile]```

Please not that inputFile is a mandatory argument, while outputFile is optional. 
If no outputFile is provided the application will generate an outputFile with the name paintShopOutput-{currentTimestamp}.txt. In both cases the result is printed in the console

