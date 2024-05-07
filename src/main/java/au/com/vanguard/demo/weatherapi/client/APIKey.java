package au.com.vanguard.demo.weatherapi.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class APIKey {

    @Value("${client.api.key}")
    private List<String> apiKeys = new ArrayList<>();

    private volatile int index = 0;

    public String getNext() {
        var key = apiKeys.get(index++);

        if (index == apiKeys.size()) {
            index = 0;
        }

        return key;
    }
}
