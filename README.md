# Getting Started

## We will use the following to build the code
- Maven
- Java 11 (earler versions are also OK)
- Spring Boot 2.4.5
- Spring Cloud Contract 3.0.2
- Lombok - you may need to install the Lombok plugin if using Intellij
- OpenApi - to generate the interfaces from YAML files
- OpenFeign - to generate client APIs from openApi YAML files
- Sleuth - to help with tracing
- Groovy – to write some of the tests
- Wiremock - to stub endpoints
- MongoDB - a Non-SQL (or non relational) database, which stores data in JSON like objects
- Flapdoodle – for testing with an in-memory mongodb database
- This demo does not use Kotlin


## Lombok
You may need to install a Lombok plugin to get this code working on your IDE  
For more details see https://www.baeldung.com/intro-to-project-lombok

## Sleuth
The micro-services use the Sleuth dependency. This dependency adds and forwards traceIds (and other fields) in request message.  If you know the traceId, you can examine log files to see all relevant logs records.  You can also link your services to tools like ZipKin
This enables requests to be easily traced through a micro-service environment

For more details see: https://dzone.com/articles/monitoring-microservices-with-spring-cloud-sleuth

## MongoDB non-SQL database
The Provider service makes use of a non-SQL database  
For more details, see: https://www.baeldung.com/spring-data-mongodb-tutorial

## RestAssuredMockMvc
See https://www.baeldung.com/spring-mock-mvc-rest-assured

## WireMock
See https://www.baeldung.com/introduction-to-wiremock

## OpenAPi
For more details about OpenApi, see https://www.baeldung.com/spring-rest-openapi-documentation

## Intellij trick to run the generated Junit tests
If you are using Intellij, you may need to mark the following directory as "Generated test sources" 
  - /target/generated-test-sources/contracts


## The OpenApi YAML defintitions are used to generate a lot of boilerplate code
The openApi yml files (product.yml, consiumer.yml and marketing.yml) are used to generate code that is used in the controllers and clients.

For example, in the Consumer service, mvn clean install will generate code in target/generated-sources/openapi/src/main
And then
- _ConsumerController_ implements _ComsumerApi_ to handle the REST calls from the front-end 
- _ProductsService_ uses the generated bean _ProviderApiClient_ to handle the REST calls to the provider service
- _MarketingService_ uses the generated bean _MarketingApiClient_ to handle the REST calls to the legacyt marketing service

Note the generated ApiClient code uses OpenFeign to process the REST calls


## Test directory structure
The test directrory structure is extremely important.  
Directory names are used by spring-boot-contract to generate the names of Java classes that get generated from groovy files, and may be used to determine the names of base classs.  
For example, in the provider service, we have the following (note in the Consumer, we directly specify the base class in the pom, so this is not relevant)

### provider/pom.xml
      <packageWithBaseClasses>
        nl.crook.olly.contract.demo.provider.contract.base
      <packageWithBaseClasses>

Because the pom specifed the package containing base classes, Spring Boot Contract will expect the following for each directory:
- src/test/resources/contracts/**providerContract** -> expects to use **ProviderContractBase**.java
- src/test/resources/contracts/**providerIntegration** -> expects to use **providerIntegrationBase**.java

These classes must be present in the package **nl.crook.olly.contract.demo.provider.contract.base** 

### Groovy files get compiled to Java classes, with the Java names based on the directory name by default
For example, if you have
- src/test/resources/contracts/**providerIntegration**/test1.groovy
- src/test/resources/contracts/**providerIntegration**/test2.groovy

Then mvn clean install will generate a test class based on te directory name
- provider/target/generated-test-sources/contracts/nl/crook/olly/contract/demo/provider/contract/base/**providerIntegration**.java  

And providerIntegration.java will contain the following 2 methods  
- validate_test1()
- validate_test2()

You can override the default naming by using the "name" attribute in your groovy (or Java or Kotlin or YAML) contract. 

For example, if test1.groovy contains  
    `"name": "groovy contract"`   

Then providerIntegration.java will contain
- validate_GroovyContract()

You can run and debug providerIntegration.java just like any Junit test.  
Note, you may have to mark the directory **provider/target/generated-test-sources/contracts** as **Test Sources Root** if using Intellij

## The OpenAPi definition in provider.yml
I deliberately made an interface that could be misinterpreted, so that I could demonstrate a couple of points.  
For example, in provider.yml, _getProducts_ and _getProductDetails_ both make use of the _Products_ schema in their response messages

      operationId: getProducts
      response is List<Product>

      operationId: getProductDetails
      response is Product

This means there is a risk that anyone looking at the openApi definition might (incorrectly) assume that _getProducts_ will return a list of _Product_, and that each item contains all the fields from _Product_.  
However, _getProducts_ fills only some of the fields of the Product (for example, it does not fill the description)
Ideally, we would not encounter this, but it is good to be aware of issues like this, and maybe consider refactoring the definition
