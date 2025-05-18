package com.example.CidadeJson.Deserializers;

import com.example.CidadeJson.LinkedListDeserializer;
import com.example.CidadeJson.TrafficLightJson;

public class LinkedListDeserializerSemaforo extends LinkedListDeserializer<TrafficLightJson> {
    public LinkedListDeserializerSemaforo() {
        super(TrafficLightJson.class);
    }
}