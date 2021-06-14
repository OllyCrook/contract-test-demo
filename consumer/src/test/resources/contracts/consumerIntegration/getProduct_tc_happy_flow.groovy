package contracts.consumerIntegration

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "This tests the getProduct service, and returns an HTTP 200"

    request {
        method GET()
        url '/consumer/products/ICE'
        headers {
            header 'test-case': 'tc_happy_flow'
        }
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