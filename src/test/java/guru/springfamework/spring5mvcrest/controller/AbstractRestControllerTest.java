package guru.springfamework.spring5mvcrest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AbstractRestControllerTest {

    public static String asJsonString(final Object ob) {
        try {
            return new ObjectMapper().writeValueAsString(ob);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
