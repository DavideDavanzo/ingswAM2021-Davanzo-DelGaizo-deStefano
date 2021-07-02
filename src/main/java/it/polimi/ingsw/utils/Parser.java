package it.polimi.ingsw.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class implements a generic parser.
 * Current implementation: Json
 */
public class Parser {

    /**
     * Serializes a generic object.
     * @param o
     * @return
     */
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

    /**
     * Deserializes a Json String.
     * @param object is a Json String.
     * @param objectClass Is the class of the Object that has to be deserialized from the String.
     * @return
     */
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
