package example.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cap {

    @JsonProperty("max_count_per_user")
    private Integer maxCountPerUser​;

    @JsonProperty("max_count")
    private Integer maxCount;
}
