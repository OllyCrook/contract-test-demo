package contracts.providerContract

import org.springframework.cloud.contract.spec.Contract

// Because the pom specifies packageWithBaseClasses, the base class is based on the name of directory containing this groovy file
// This test uses ProviderContractBase.java
Contract.make {
    description "Generate a random category that does not exist in the DB, so will return an empty body"

    request {
        method POST()
        // Accept a category of the form "ICEMONGO_[0-9]{3}" from the consumer (see mappiongs file in stub.jar)
        // and generate a Junit test using a random url. For exmaple /provider/products/ICEMONGO_243
        url value(consumer(regex('/provider/products/ICEMONGO_[0-9]{3}')))

        headers {
            contentType(applicationJson())
        }
        // alternative to using body(file(...))
        body([
                "consumerId": "Api Test from Consumer"
        ])
    }

    response {
        status OK()
        body(
                "products": []
        )
    }
}