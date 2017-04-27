package com.infinera.metro.dnam.acceptance.test.util;

import com.google.common.base.Preconditions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.stream.Collectors;

public enum FileHelper {
    INSTANCE;

    public String getProperty(String pathToPropertiesFile, String key) {
        Preconditions.checkNotNull(pathToPropertiesFile);
        Preconditions.checkNotNull(key);
        return getProperties(pathToPropertiesFile).getProperty(key);
    }

    public String readFileAsString(String pathToPropertiesFile) {
        Preconditions.checkNotNull(pathToPropertiesFile);
        try {
            return readAsString(getInputStream(pathToPropertiesFile));
        } catch (IOException e) {
            String errorMessage = "Failed to read properties file: " +
                    pathToPropertiesFile +
                    " as String. " +
                    e.getMessage();
            throw new RuntimeException(errorMessage);
        }
    }

    public Properties getProperties(String pathToPropertiesFile) {
        Preconditions.checkNotNull(pathToPropertiesFile);
        try {
            return getProperties(getInputStream(pathToPropertiesFile));
        } catch (IOException e) {
            String errorMessage = "Failed to read properties file: " +
                    pathToPropertiesFile +
                    e.getMessage();
            throw new RuntimeException(errorMessage);
        }
    }

    public Properties getProperties(InputStream inputStream) throws IOException {
        Preconditions.checkNotNull(inputStream, "Cound not load properties since provided inputstream was null");
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }

    public InputStream getInputStream(String pathToFile) {
        Preconditions.checkNotNull(pathToFile);
        InputStream inputStream = FileHelper.class.getClassLoader().getResourceAsStream(pathToFile);
        Preconditions.checkNotNull(inputStream, "Failed to GET inputstream for path " + pathToFile);
        return FileHelper.class.getClassLoader().getResourceAsStream(pathToFile);
    }

    public String readAsString(InputStream inputStream) throws IOException {
        Preconditions.checkNotNull(inputStream);
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }
}
