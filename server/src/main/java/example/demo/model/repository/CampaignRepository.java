package example.demo.model.repository;

import example.demo.model.entity.Campaign;
import example.demo.model.entity.Cap;
import example.demo.model.entity.Payload;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class CampaignRepository {

    private ConcurrentHashMap<Long, Campaign> campaigns = new ConcurrentHashMap<>();

    public List<Campaign> getAll() {
        return campaigns.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
    }


    public Campaign insert(Campaign c) {
        Long id = campaigns.entrySet().stream().map(e -> e.getKey()).max(Long::compareTo).orElse(0L);
        c.setId(id + 1);
        return update(c);
    }

    public Campaign update(Campaign c) {
        campaigns.put(c.getId(), c);
        return c;
    }

    @PostConstruct
    public void populateDb() {
        Campaign campaign1 = new Campaign(null, "name1", new Payload("message1"), new Cap(2,100));
        Campaign campaign2 = new Campaign(null, "name2", new Payload("message2"), new Cap(4,110));
        Campaign campaign3 = new Campaign(null, "name3", new Payload("message3"), new Cap(6,120));
        Campaign campaign4 = new Campaign(null, "name4", new Payload("message4"), new Cap(8,130));

        Stream.of(campaign1, campaign2, campaign3, campaign4)
                .map(c ->  insert(c))
                .forEach(c -> System.out.println("inserted " + c));
    }
}
