package com.example.CidadeJson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RuaJson {
    public String id;
    public String source;
    public String target;
    public double length;
    public double travel_time;
    public boolean oneway;
}
