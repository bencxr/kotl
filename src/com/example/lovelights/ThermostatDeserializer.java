package com.example.lovelights;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ThermostatDeserializer implements JsonDeserializer<Thermostat[]> {

    @Override
    public Thermostat[] deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = element.getAsJsonObject();
        
        List<Thermostat> thermostats = new ArrayList<Thermostat>();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
        	
            // For individual thermostats, use the default deserialization
        	Thermostat thermostat = context.deserialize(entry.getValue(), Thermostat.class); 
        	thermostats.add(thermostat);
        }
        
        if (thermostats.isEmpty()) {
        	return new Thermostat[0];
        }
        
        Thermostat[] out = (Thermostat[]) thermostats.toArray(new Thermostat[thermostats.size()]);
        return out;
    }
}