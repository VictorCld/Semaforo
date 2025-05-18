package com.example.CidadeJson;

import com.example.CidadeJson.Deserializers.LinkedListDeserializerSemaforo;
import com.example.simulation.datastructure.LinkedList;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RootJson {

    @JsonProperty("traffic_lights")
    @JsonDeserialize(using = LinkedListDeserializerSemaforo.class)
    private LinkedList<TrafficLightJson> traffic_lights;

    public LinkedList<TrafficLightJson> getTrafficLights() {
        return traffic_lights;
    }

    public void setTrafficLights(LinkedList<TrafficLightJson> trafficLights) {
        this.traffic_lights = trafficLights;
    }
}