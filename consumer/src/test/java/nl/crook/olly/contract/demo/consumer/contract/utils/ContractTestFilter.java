package nl.crook.olly.contract.demo.consumer.contract.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Component
public class ContractTestFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        // store the test-case in MDC to help with log analysis and for later use in the TestClientInterceptor
        final String testCase = req.getHeader(TestConstants.TEST_CASE);
        MDC.put(TestConstants.TEST_CASE, testCase);

        log.info("Starting test for testCase:{} and URL: {}", testCase, req.getRequestURI());
        chain.doFilter(request, response);
        log.info("Stopping test for testCase;{} and URL : {}", testCase, req.getRequestURI());
    }
}

