package com.example.CidadeJson;

import com.example.CidadeJson.Deserializers.LinkedListDeserializerIntersecao;
import com.example.CidadeJson.Deserializers.LinkedListDeserializerRua;
import com.example.CidadeJson.Deserializers.LinkedListDeserializerSemaforo;
import com.example.simulation.datastructure.LinkedList;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class GrafoJson {

    @JsonDeserialize(using = LinkedListDeserializerIntersecao.class)
    public LinkedList<IntersecaoJson> nodes;

    @JsonDeserialize(using = LinkedListDeserializerRua.class)
    public LinkedList<RuaJson> edges;

    @JsonDeserialize(using = LinkedListDeserializerSemaforo.class)
    public LinkedList<TrafficLightJson> traffic_lights;
}