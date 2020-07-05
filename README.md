# spring-distributed-transactions-demo
Spring distributed transactions demo

This is a non-functional Spring application, meant for demonstrating the usage of a ChainedTransactionManager, when 
working with multiple JPA DataSources.

### Get up and running in no time:

Since this is a simple Maven project, all you need is a working Maven-based Java 11 development environment.
Clone the project, cd into its folder and execute
 
    git clone git@github.com:dribnif/spring-distributed-transactions-demo.git
    cd spring-distributed-transactions-demo
    mvn clean package  
    
The tests should run and you should be golden.


### The secret sauce

The special configuration for the chained Transaction Manager can be found in the three classes located in the 
package

    src/main/javade/metamorphant/demo/spring/transactions/config/*Config.java
    
Testing that the chained transaction manager behaves as expected is also a bit tricky. The example is located here:

    src/test/java/de/metamorphant/demo/spring/transactions/service/OrderProcessorServiceTransactionHandlingTest.java
