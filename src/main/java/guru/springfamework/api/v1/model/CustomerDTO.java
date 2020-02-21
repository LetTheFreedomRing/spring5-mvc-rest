package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CustomerDTO {
    Long id;
    @JsonProperty("firstname")
    String firstName;
    @JsonProperty("lastname")
    String lastName;
    @JsonProperty("customer_url")
    String customerUrl;
}
