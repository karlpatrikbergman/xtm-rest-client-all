package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.fasterxml.jackson.databind.ObjectReader;
import com.infinera.metro.dnam.acceptance.test.node.mib.BoardType;
import com.infinera.metro.dnam.acceptance.test.util.ResourceInputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class BoardTypeDeserializationTest {
    @Test
    public void test() throws IOException {
        ObjectReader reader = JacksonYamlUtil.INSTANCE.getReader().forType(BoardType.class);
        InputStream in = new ResourceInputStream("configuration/board.yaml");
        BoardType boardType = reader.readValue(in);
        log.info(ReflectionToStringBuilder.toString(boardType, ToStringStyle.MULTI_LINE_STYLE));
    }
}
