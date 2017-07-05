package com.infinera.metro.dnam.acceptance.test.node.configuration.board;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.infinera.metro.dnam.acceptance.test.node.Node;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.BoardEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.BoardType;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Tpd10gbe.class, name = "tpd10gbe"),
        @JsonSubTypes.Type(value = Tpmr2500.class, name = "tpmr2500"),
        @JsonSubTypes.Type(value = Roadm1x2G50.class, name = "roadm1x2G50"),
        @JsonSubTypes.Type(value = Oa2x17.class, name = "oa2x17")
})
public interface Board {
    BoardType getBoardType();
    BoardEntry getBoardEntry();

    void applyTo(Node node);
    void deleteFrom(Node node);
}