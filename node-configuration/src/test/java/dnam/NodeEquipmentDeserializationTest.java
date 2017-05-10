package dnam;

import com.fasterxml.jackson.databind.ObjectReader;
import com.infinera.metro.dnam.acceptance.test.mib.Board;
import com.infinera.metro.dnam.acceptance.test.node.config.NodeEquipment;
import com.infinera.metro.dnam.acceptance.test.util.ResourceInputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertTrue;

@Slf4j
public class NodeEquipmentDeserializationTest {
    @Test
    public void test() throws IOException {
        ObjectReader reader = JacksonYamlUtil.INSTANCE.getReader().forType(NodeEquipment.class);
        InputStream in = new ResourceInputStream("dnam/node_equipment.yaml");
        NodeEquipment nodeEquipment = reader.readValue(in);
        assertTrue(nodeEquipment.getBoard().equals(Board.TPD10GBE));
        log.info(ReflectionToStringBuilder.toString(nodeEquipment, ToStringStyle.MULTI_LINE_STYLE));
    }
}
