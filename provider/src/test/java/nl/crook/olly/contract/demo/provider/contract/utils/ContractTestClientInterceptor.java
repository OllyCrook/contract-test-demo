package nl.crook.olly.contract.demo.provider.contract.utils;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static nl.crook.olly.contract.demo.provider.contract.utils.TestConstants.TEST_CASE;


@Component
@Profile({"contract-tests"})
public class ContractTestClientInterceptor implements RequestInterceptor {

    @Override
    public void apply(final RequestTemplate template) {
        template.header(TEST_CASE, MDC.get(TEST_CASE));
    }

}