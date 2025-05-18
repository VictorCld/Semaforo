package com.example.CidadeJson.Deserializers;

import com.example.CidadeJson.IntersecaoJson;
import com.example.CidadeJson.LinkedListDeserializer;

public class LinkedListDeserializerIntersecao extends LinkedListDeserializer<IntersecaoJson> {
    public LinkedListDeserializerIntersecao() {
        super(IntersecaoJson.class);
    }
}