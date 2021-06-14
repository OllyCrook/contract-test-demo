package nl.crook.olly.contract.demo.provider.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("mongodb")
public class DatabaseProperties {
    private int ttl;
    private int quoteTtl;
    private String collectionName;
}
