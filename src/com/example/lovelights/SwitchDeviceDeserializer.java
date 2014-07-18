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

public class SwitchDeviceDeserializer implements JsonDeserializer<SwitchDevice[]> {

    @Override
    public SwitchDevice[] deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = element.getAsJsonObject();
        
        List<SwitchDevice> veraSwitches = new ArrayList<SwitchDevice>();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
        	
            // For individual switch objects, we can use default deserialization
        	SwitchDevice veraSwitch = context.deserialize(entry.getValue(), SwitchDevice.class); 
        	veraSwitches.add(veraSwitch);
        }
        
        if (veraSwitches.isEmpty()) {
        	return new SwitchDevice[0];
        }
        
        SwitchDevice[] out = (SwitchDevice[]) veraSwitches.toArray(new SwitchDevice[veraSwitches.size()]);
        return out;
    }
}