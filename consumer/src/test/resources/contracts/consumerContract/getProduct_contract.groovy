package contracts.consumerFunctional

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Contract test (for use by external parties) to test the Consumer's getProduct service, and return an HTTP 200"

    request {
        method GET()
        url '/consumer/products/ICE'
    }

    response {
        status OK()
        body(file("response/ICE.json"))
        headers {
            contentType(applicationJson())

            // calls should be made to these wiremock services
            header("checkCallMadeProvider", execute('verifyPostRequestFor("/provider/products/ICE", 1)'))
            header("checkCallMadeMarketing", execute('verifyGetRequestFor("/marketing/promotion/ICE", 1)'))
        }
    }
}