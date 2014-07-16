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

public class VeraSwitchDeserializer implements JsonDeserializer<VeraSwitch[]> {

    @Override
    public VeraSwitch[] deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = element.getAsJsonObject();
        
        List<VeraSwitch> veraSwitches = new ArrayList<VeraSwitch>();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            // For individual City objects, we can use default deserialisation:
        	VeraSwitch veraSwitch = context.deserialize(entry.getValue(), VeraSwitch.class); 
        	veraSwitches.add(veraSwitch);
        }
        
        if (veraSwitches.isEmpty()) {
        	return new VeraSwitch[0];
        }
        
        VeraSwitch[] out = (VeraSwitch[]) veraSwitches.toArray(new VeraSwitch[veraSwitches.size()]);
        return out;
    }
}