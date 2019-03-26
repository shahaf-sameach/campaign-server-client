package example.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Campaign {

    private Long id;
    private String name;
    private Payload Data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Cap cap;

    public static Campaign buildUserResponse(Campaign campaign) {
        return new Campaign(campaign.getId(), campaign.getName(), campaign.getData(), null);
    }

}

