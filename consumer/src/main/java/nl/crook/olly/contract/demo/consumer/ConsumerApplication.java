package nl.crook.olly.contract.demo.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"nl.crook.olly.contract.demo.consumer", "nl.crook.olly.contract.demo.provider", "nl.crook.olly.contract.demo.marketing"})
@EnableFeignClients({"nl.crook.olly.contract.demo.consumer", "nl.crook.olly.contract.demo.provider", "nl.crook.olly.contract.demo.marketing"})
public class ConsumerApplication {

	public static void main(final String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}
}
