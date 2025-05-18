package com.example.CidadeJson;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.example.simulation.datastructure.LinkedList;

public class LinkedListDeserializer<T> extends StdDeserializer<LinkedList<T>> {
    private final Class<T> itemType;

    public LinkedListDeserializer(Class<T> itemType) {
        super(LinkedList.class);
        this.itemType = itemType;
    }

    @Override
    public LinkedList<T> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        ArrayNode arrayNode = mapper.readTree(p);
        LinkedList<T> result = new LinkedList<>();
        for (JsonNode node : arrayNode) {
            T item = mapper.treeToValue(node, itemType);
            result.add(item);
        }
        return result;
    }
}
