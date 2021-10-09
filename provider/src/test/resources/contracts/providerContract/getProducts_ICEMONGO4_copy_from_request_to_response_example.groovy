package contracts.providerContract

import org.springframework.cloud.contract.spec.Contract

// Because the pom specifies packageWithBaseClasses, the base class is based on the name of directory containing this groovy file
// This test uses ProviderContractBase.java
Contract.make {
    description "Same as getProducts_ICEMONGO.groovy, but using an alternative way of defining the request and response"

    request {
        method POST()
        url '/provider/products/ICEMONGO4'
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
        // copy the category from the request path to the response
        // See https://cloud.spring.io/spring-cloud-contract/2.0.x/multi/multi__contract_dsl.html
        body(
                products: [
                        category: fromRequest().path(2),  // ICOMONGO4
                        code    : "CONTRACT-CHOCOLATE  ${ fromRequest().body('\$.consumerId') }",
                        name    : fromRequest().body('\$.consumerId'),
                        price   : "99"

                ]
        )
    }
}