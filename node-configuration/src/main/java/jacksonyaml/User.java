package jacksonyaml;

import lombok.Value;

import java.util.Map;

@Value
public class User {
    private final String name;
    private final int age;
    private final Map<String, String> address;
    private final String[] roles;
}

