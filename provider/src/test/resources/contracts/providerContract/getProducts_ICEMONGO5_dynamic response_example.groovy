package contracts.providerContract

import org.springframework.cloud.contract.spec.Contract

// Because the pom specifies packageWithBaseClasses, the base class is based on the name of directory containing this groovy file
// This test uses ProviderContractBase.java
Contract.make {
    description "Same as getProducts_ICEMONGO.groovy, but using an alternative way of defining the request and response"

    request {
        method POST()
        url '/provider/products/ICEMONGO5'
        headers {
            contentType(applicationJson())
        }

        body([
                "consumerId": "Api Test from Consumer"
        ])
    }

    response {
        status OK()
        headers {
            contentType(applicationJson())
        }

        // Use value(consumer(..)) or value(producer(...)) to generate a random 2 digit price for the response to the consumer (see the mappings file in the stub.jar file)
        // Also generate Junit code in ProviderCOntractTest that verifies that the price is 2 digits.  Useful for example if you want to generate current timestamp in the response
        // https://cloud.spring.io/spring-cloud-contract/2.0.x/multi/multi__contract_dsl.html
        //
        body(
                "products": [
                        "category": "ICEMONGO5",
                        "code"    : "CONTRACT-CHOCOLATE",
                        "name"    : "Chocolate ice cream",
                        "price"   : value(consumer(regex('[0-9]{2}')))
                ]
        )
    }
}