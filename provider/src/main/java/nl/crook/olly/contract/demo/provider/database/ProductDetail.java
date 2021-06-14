package nl.crook.olly.contract.demo.provider.database;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Data
@Document(collection = "#{@databaseProperties.getCollectionName()}")
public class ProductDetail {

    @Id
    private String id;

    @Field("product-category")
    @Indexed
    private String productCategory;

    @Field("product-code")
    @Indexed
    private String productCode;

    @Field("product-name")
    @Indexed
    private String productName;

    @Field("product-description")
    @Indexed
    private String productDescription;

    @Field("product-price")
    @Indexed
    private BigDecimal productPrice;


    @Field("created-at")
    private String createdAt;

    @Field("updated-at")
    private String updatedAt;

    @Field("ttl")
    private int ttl;

    @Field("_ts")
    @Indexed(expireAfterSeconds = 1)
    private int ts;

    // A non-sequential unique id
    @Field("unique-id")
    private String uniqueId;
}
