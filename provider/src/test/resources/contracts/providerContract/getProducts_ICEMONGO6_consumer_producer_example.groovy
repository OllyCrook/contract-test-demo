package contracts.providerContract

import org.springframework.cloud.contract.spec.Contract

// Because the pom specifies packageWithBaseClasses, the base class is based on the name of directory containing this groovy file
// This test uses ProviderContractBase.java
Contract.make {
    description "Same as getProducts_ICEMONGO.groovy, but using an alternative way of defining the request and response"

    request {
        method POST()
        url '/provider/products/ICEMONGO6'
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

        // generate a price of 77 in the response to the consumer (see the mappings file in the stub.jar file)
        // Also generate Junit code in ProviderCOntractTest that verifies that the price is 2 digits
        // https://cloud.spring.io/spring-cloud-contract/2.0.x/multi/multi__contract_dsl.html
        //
        body(
                "products": [
                        "category": "ICEMONGO6",
                        "code"    : "CONTRACT-CHOCOLATE",
                        "name"    : "Chocolate ice cream",
                        "price"   : $(consumer('77'), producer(regex('[0-9]{2}')))
                ]
        )
    }
}