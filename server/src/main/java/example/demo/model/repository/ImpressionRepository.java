package example.demo.model.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

public class ImpressionRepository {

    private ConcurrentHashMap<String, Long> impressions = new ConcurrentHashMap<>();

    public Long get(String key) {
        return impressions.getOrDefault(key, null);
    }

    public Long increment(String key) {
        Long count = get(key);
        return impressions.put(key, count == null ? 1L : count + 1L);

    }


}
