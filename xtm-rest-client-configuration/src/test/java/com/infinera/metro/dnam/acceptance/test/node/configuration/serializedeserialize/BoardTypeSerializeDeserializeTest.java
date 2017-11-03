package com.infinera.metro.dnam.acceptance.test.node.configuration.serializedeserialize;

import com.infinera.metro.dnam.acceptance.test.node.mib.type.BoardType;
import org.junit.Ignore;

/**
 * Serialization fails for unclear reason
 * Expected:
 * ---
 * tpd10gbe
 *
 * Actual:
 * --- "tpd10gbe"
 *
 * TODO: Figure out how to serialize Enums properly
 */
@Ignore
public class BoardTypeSerializeDeserializeTest extends AbstractYamlSerializeDeserializeTest<BoardType> {

    public BoardTypeSerializeDeserializeTest() {
        super(BoardType.class, BoardType.TPD10GBE, "configuration/boardtype.yaml");
    }

}
