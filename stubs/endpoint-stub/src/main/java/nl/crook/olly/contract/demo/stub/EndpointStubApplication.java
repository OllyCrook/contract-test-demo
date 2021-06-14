package nl.crook.olly.contract.demo.stub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

@Slf4j
@SpringBootApplication
@AutoConfigureWireMock(port = 0)
public class EndpointStubApplication {

    public static void main(final String[] args) {
        log.info("Starting  Application");
        SpringApplication.run(EndpointStubApplication.class, args);
    }
}

