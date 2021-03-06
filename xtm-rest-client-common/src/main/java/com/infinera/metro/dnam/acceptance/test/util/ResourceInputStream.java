package com.infinera.metro.dnam.acceptance.test.util;

import com.google.common.base.Preconditions;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
public class ResourceInputStream extends InputStream {
    private final InputStream inputStream;

    public ResourceInputStream(String pathToResource) throws IOException {
        Preconditions.checkNotNull(pathToResource);
        this.inputStream = (new ClassPathResource(pathToResource)).getInputStream();
    }

    public int read() throws IOException {
        return this.inputStream.read();
    }
}