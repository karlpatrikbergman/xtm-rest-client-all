package com.infinera.metro.dnam.acceptance.test.node.configuration;

import com.infinera.metro.dnam.acceptance.test.node.mib.MpoIdentifier;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import java.io.IOException;

@Slf4j
public class MpoIdentifierDeserializationTest extends YamlDeserializationTest {

    @Test
    public void test() throws IOException {
        final MpoIdentifier mpoIdentifier = objectFromFileUtil.getObject("configuration/mpo_identifier.yaml", MpoIdentifier.class);
        log.info(ReflectionToStringBuilder.toString(mpoIdentifier, ToStringStyle.MULTI_LINE_STYLE));
    }

}
