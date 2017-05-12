package com.infinera.metro.dnam.acceptance.test.node.mib;

import lombok.NonNull;
import lombok.Value;

@Value
public class Configuration {
    @NonNull private final String key;
    private final String value;

    @java.beans.ConstructorProperties({"key", "value"})
    Configuration(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static ConfigurationBuilder builder() {
        return new ConfigurationBuilder();
    }

    public String asParameters() {
        return (value == null) ? key : key + "=" + value;
    }

    public static class ConfigurationBuilder {
        private String key;
        private String value;

        ConfigurationBuilder() {
        }

        public Configuration.ConfigurationBuilder key(String key) {
            this.key = key;
            return this;
        }

        public Configuration.ConfigurationBuilder value(String value) {
            this.value = value;
            return this;
        }

        public Configuration.ConfigurationBuilder value(int value) {
            this.value = Integer.toString(value);
            return this;
        }

        public Configuration build() {
            return new Configuration(key, value);
        }

        public String toString() {
            return "com.infinera.metro.dnam.acceptance.test.mib.Configuration.ConfigurationBuilder(key=" + this.key + ", value=" + this.value + ")";
        }
    }
}
