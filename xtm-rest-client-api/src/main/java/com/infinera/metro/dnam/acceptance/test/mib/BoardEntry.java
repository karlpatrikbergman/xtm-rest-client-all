package com.infinera.metro.dnam.acceptance.test.mib;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import static com.infinera.metro.dnam.acceptance.test.mib.util.MibPathUtil.MIB_PATH_UTIL;

@AllArgsConstructor(access = AccessLevel.PUBLIC) //Needed by Orika
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //Needed by Hibernate and Jackson
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Value //Needs jackson > 2.8
@Builder
public class BoardEntry implements MibEntry {
    @NonNull private final Module module = Module.EQUIPMENT;
    @NonNull private final GroupOrTable groupOrTable = GroupOrTable.BOARD;
    @NonNull private final Board board;
    @NonNull private final Integer subrack;
    @NonNull private final Integer slot;

    @Override
    public String getMibEntryString() {
        return MIB_PATH_UTIL.getMibEntryString (board.getName(), getSubrack() ,getSlot());
    }

    @Override
    public String getMibEntryPath() {
        return MIB_PATH_UTIL.getMibEntryPath(module, groupOrTable, this);
    }
}
