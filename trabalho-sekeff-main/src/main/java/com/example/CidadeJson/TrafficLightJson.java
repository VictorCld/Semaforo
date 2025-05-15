package com.example.CidadeJson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrafficLightJson {
    public String id;
    public double latitude;
    public double longitude;
    public Attributes attributes; 

    public static class Attributes {
        public String highway;
        public String destination;
        public String ref;
        @JsonProperty("traffic_signals:direction")
        public String trafficSignalsDirection;
    }
}
