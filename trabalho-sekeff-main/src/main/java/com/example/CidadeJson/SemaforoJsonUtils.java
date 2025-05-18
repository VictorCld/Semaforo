package com.example.CidadeJson;

import com.example.simulation.datastructure.LinkedList;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class SemaforoJsonUtils {

    public static LinkedList<TrafficLightJson> carregarSemaforosDoJson(String caminhoJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            RootJson root = mapper.readValue(new File(caminhoJson), RootJson.class);
            return root.getTrafficLights();
        } catch (Exception e) {
            e.printStackTrace();
            return new LinkedList<>();
        }
    }
}
