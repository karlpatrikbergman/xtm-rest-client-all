package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.infinera.metro.dnam.acceptance.test.node.mib.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import java.io.IOException;

@Slf4j
public class ConfigurationDeserializationTest {

    @Test
    public void test() throws IOException {
        ObjectFromFileUtil objectFromFileUtil = ObjectFromFileUtilFactory.getObjectFromFileUtil();
        Configuration configuration = objectFromFileUtil.getObject("configuration/configuration.yaml", Configuration.class);
        log.info(ReflectionToStringBuilder.toString(configuration, ToStringStyle.MULTI_LINE_STYLE));
    }
}
