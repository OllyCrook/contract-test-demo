package nl.crook.olly.contract.demo.provider.contract.utils;


import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ContractTestInitialiser {

    @Bean(name="providerFunctionalContractTestClientInterceptor")
    public RequestInterceptor functionalContractTestClientInterceptor() {
        return new ContractTestClientInterceptor();
    }

}

