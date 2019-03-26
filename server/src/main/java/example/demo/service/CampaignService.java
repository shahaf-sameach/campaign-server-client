package example.demo.service;

import example.demo.model.entity.Campaign;
import example.demo.model.repository.CampaignRepository;
import example.demo.model.repository.ImpressionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampaignService {

    @Autowired
    private ImpressionRepository allUsersImpressionRepository;

    @Autowired
    private ImpressionRepository userImpressionRepository;

    @Autowired
    private CampaignRepository campaignRepository;


    public List<Campaign> getValidCampaigns(String userId) {
        return campaignRepository.getAll()
                .stream().map(c -> {
                    Long impression = userImpressionRepository.get(campaignByUserKey(c, userId));
                    if (impression == null || impression < c.getCap().getMaxCountPerUser​()) {
                        userImpressionRepository.increment(campaignByUserKey(c, userId));

                        Long allImpressions = allUsersImpressionRepository.get(c.getId().toString());
                        if (allImpressions == null || allImpressions < c.getCap().getMaxCount()) {
                            allUsersImpressionRepository.increment(c.getId().toString());
                            return c;
                        }
                    }
                    return null;

                }).filter(c -> c != null).collect(Collectors.toList());
    }

    private String campaignByUserKey(Campaign campaign, String userId) {
        return campaign.getId() + ":" + userId;
    }

    public Campaign upsert(Campaign c){
        Long id = c.getId();
        if (id == null)
            return campaignRepository.insert(c);

        return campaignRepository.update(c);
    }

    public List<Campaign> getAll() {
        return campaignRepository.getAll();
    }
}
