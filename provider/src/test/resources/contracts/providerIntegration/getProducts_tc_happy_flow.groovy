package contracts.providerIntegration

import org.springframework.cloud.contract.spec.Contract

// Because the pom specifies packageWithBaseClasses, the base class is based on the name of directory containing this groovy file
// This test uses ProviderIntergrationBase.java
Contract.make {
    description "Integration test using in-memory database, for category ICE, test case = tc_happy_flow"

    request {
        method POST()
        url '/provider/products/ICE'
        headers {
            contentType(applicationJson())
            header 'test-case': 'tc_happy_flow'
        }
        body(file("request/getProducts_tc_happy_flow.json"))
    }

    response {
        status OK()
        body(file("response/getProducts_tc_happy_flow" +
                ".json"))
        headers {
            contentType(applicationJson())
        }
    }
}