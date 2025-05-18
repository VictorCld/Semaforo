package com.example.CidadeJson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.example.simulation.datastructure.LinkedList;

public class LinkedListModule extends SimpleModule {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public LinkedListModule() {
        addDeserializer((Class) LinkedList.class, new LinkedListDeserializer<>(Object.class)); // fallback genérico
    }
}