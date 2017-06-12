package com.infinera.metro.dnam.acceptance.test.node.mib;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

//TODO: If this works out with OaPortEntry, use on other port entry types as well

@AllArgsConstructor
class AbstractPortEntry {
    @Getter @NonNull private final ModuleType moduleType;
    @Getter @NonNull private final GroupOrTableType groupOrTableType;
    @Getter @NonNull private final PortType portType;
    @Getter @NonNull private final Integer subrack;
    @Getter @NonNull private final Integer slot;
    @Getter @NonNull private final Integer transmitPort;
    @Getter @NonNull private final Integer receivePort;
}
