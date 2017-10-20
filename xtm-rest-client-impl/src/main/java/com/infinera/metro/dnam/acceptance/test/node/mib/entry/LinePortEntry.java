package com.infinera.metro.dnam.acceptance.test.node.mib.entry;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.LinePortType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

//TODO: Make sure we still need @JsonProperty. I don't remember why it is there

@EqualsAndHashCode(callSuper = true)
@Value
public class LinePortEntry extends AbstractPortEntry implements MibEntry {

    @JsonCreator
    @Builder
    private LinePortEntry(@JsonProperty("moduleType") ModuleType moduleType,
                          @JsonProperty("groupOrTableType") GroupOrTableType groupOrTableType,
                          @JsonProperty("linePortType") LinePortType linePortType,
                          @JsonProperty("subrack") Integer subrack,
                          @JsonProperty("slot") Integer slot,
                          @JsonProperty("transmitPort") Integer transmitPort,
                          @JsonProperty("receivePort") Integer receivePort) {
        super(moduleType, groupOrTableType, linePortType, subrack, slot, transmitPort, receivePort);
    }
}