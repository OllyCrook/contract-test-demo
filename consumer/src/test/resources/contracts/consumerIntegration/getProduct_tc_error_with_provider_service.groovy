package contracts.consumerIntegration

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "This tests the getProduct service, and returns an HTTP 500 because of error calling getProducts in marketing-service"

    request {
        method GET()
        url '/consumer/products/ICE'
        headers {
            header 'test-case': 'tc_error_with_provider_service'
        }
    }

    response {
        status INTERNAL_SERVER_ERROR()
        headers {
            // calls should be made to these wiremock services
            header("checkCallMadeProvider", execute('verifyPostRequestFor("/provider/products/ICE", 1)'))

            // no calls should be made to these wiremock services
            header("checkCallMadeMarketing", execute('verifyGetRequestFor("/marketing/promotion/ICE", 0)'))
        }
    }
}