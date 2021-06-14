package contracts.providerContract

import org.springframework.cloud.contract.spec.Contract

// Because the pom specifies packageWithBaseClasses, the base class is based on the name of directory containing this groovy file
// This test uses ProviderContractBase.java
Contract.make {
    description "Contract for Consumer application, using in-memory database, for category ICEMONGO"

    request {
        method POST()
        url '/provider/products/ICEMONGO'
        headers {
            contentType(applicationJson())
        }
        body(file("request/getProducts_ICEMONGO.json"))
    }

    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body(file("response/getProducts_ICEMONGO.json"))
    }
}