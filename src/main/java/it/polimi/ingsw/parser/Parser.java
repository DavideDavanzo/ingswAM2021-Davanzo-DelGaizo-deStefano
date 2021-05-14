package it.polimi.ingsw.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.model.resources.Resource;

public class Parser {

    public static String serialize(Object o) {

        String serialized = "";
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            serialized = objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return serialized;
    }

    public static Object deserialize(String object, Class objectClass) {

        ObjectMapper objectMapper = new ObjectMapper();
        Object o = null;

        try {
            o = objectMapper.readValue(object, objectClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return o;
    }

}
