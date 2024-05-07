package au.com.vanguard.demo.weatherapi.client;

import lombok.Data;

@Data
public class Weather {
    private String id;
    private String main;
    private String description;
    private String icon;
}
