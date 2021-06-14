package contracts.providerContract

import org.springframework.cloud.contract.spec.Contract

// Because the pom specifies packageWithBaseClasses, the base class is based on the name of directory containing this groovy file
// This test uses ProviderContractBase.java
Contract.make {
    description "Same as getProducts_ICEMONGO.groovy, but using an alternative way of defining the request and response"

    request {
        method POST()
        url '/provider/products/ICEMONGO2'
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
        headers {
            contentType(applicationJson())
        }

        // alternative to using body(file(...))
        body(
                "products": [
                        "category": "ICEMONGO2",
                        "code"    : "CONTRACT-CHOCOLATE",
                        "name"    : "Chocolate ice cream",
                        "price"   : "99"

                ]
        )
    }
}