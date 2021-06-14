package contracts.providerContract

import org.springframework.cloud.contract.spec.Contract

// Because the pom specifies packageWithBaseClasses, the base class is based on the name of directory containing this groovy file
// This test uses ProviderContractBase.java
Contract.make {
    description "Same as getProducts_ICEMONGO.groovy, but using an alternative way of defining the request and response"

    request {
        method POST()
        url '/provider/products/ICEMONGO3'
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

        body(
                "products": [
                        "category": "ICEMONGO3",
                        "code"    : "CONTRACT-CHOCOLATE",
                        "name"    : "Chocolate ice cream",
                        "price"   : "99"

                ]
        )

        // user-defined rule for validating the response, where elementNotPresent is defined in AbstractContractBase
        bodyMatchers {
            // description field should not be in the response
            jsonPath('$', byCommand('elementNotPresent($it, "description")'))
        }
    }
}