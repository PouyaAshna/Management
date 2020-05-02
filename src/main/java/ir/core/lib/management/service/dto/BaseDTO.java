package ir.core.lib.management.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;

@Getter
@Setter
@ToString
public class BaseDTO implements Serializable {

    private Long id;
    @JsonProperty("_include")
    private HashMap<String, Object> include = new HashMap<>();

    public void addInclude(String key, Object value) {
        include.put(key, value);
    }
}
