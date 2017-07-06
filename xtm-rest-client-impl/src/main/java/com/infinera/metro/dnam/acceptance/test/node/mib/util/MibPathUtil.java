package com.infinera.metro.dnam.acceptance.test.node.mib.util;

import com.infinera.metro.dnam.acceptance.test.node.mib.*;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.MibEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.GroupOrTableType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.MibType;
import com.infinera.metro.dnam.acceptance.test.node.mib.type.ModuleType;
import org.stringtemplate.v4.ST;

/**
 * TODO: This class is a mess, not very readably and maybe not filling a purpose.
 * - Methods that are only used by one class should reside in that class and not here
 */

public enum MibPathUtil {
    MIB_PATH_UTIL;

    public String getMibEntryString(MibType entry, int subrack, int slot, int port, MpoIdentifier mpoIdentifier) {
        ST result = new ST("<entry>:<subrack>:<slot><mtoIdentifier>:<port>");
        result.add("entry", entry.getValue());
        result.add("subrack", subrack);
        result.add("slot", slot);
        result.add("port", port);
        result.add("mtoIdentifier", mpoIdentifier);
        return result.render();
    }

    //TODO: Change type of entry to MibType, and use MibType.getValue()
    public String getMibEntryString(String entry, int subrack, int slot, int transmitPort, MpoIdentifier mpoIdentifier) {
        ST result = new ST("<entry>:<subrack>:<slot><mtoIdentifier>:<transmitPort>");
        result.add("entry", entry);
        result.add("subrack", subrack);
        result.add("slot", slot);
        result.add("transmitPort", transmitPort);
        result.add("mtoIdentifier", mpoIdentifier);
        return result.render();
    }

    //TODO: Change type of entry to MibType, and use MibType.getValue()
    public String getMibEntryString(String entry, int subrack, int slot, int transmitPort, int receivePort) {
        ST result = new ST("<entry>:<subrack>:<slot>:<transmitPort>-<receivePort>");
        result.add("entry", entry);
        result.add("subrack", subrack);
        result.add("slot", slot);
        result.add("transmitPort", transmitPort);
        result.add("receivePort", receivePort);
        return result.render();
    }

    //TODO: Change type of entry to MibType and use MibType.getValue()
    public String getMibEntryString(String entry, int subrack, int slot) {
        ST result = new ST("<entry>:<subrack>:<slot>");
        result.add("entry", entry);
        result.add("subrack", subrack);
        result.add("slot", slot);
        return result.render();
    }

    public String getMibEntryPath(ModuleType moduleType, GroupOrTableType group, MibEntry entry) {
        ST url = new ST("/mib/<module>/<group>/<entry>");
        url.add("module", moduleType.getValue());
        url.add("group", group.getValue());

        url.add("entry", entry.getMibEntryString());
        return url.render();
    }

    public String getMibPathAndCommand(MibEntry entry, CommandType commandType) {
        ST url = new ST("<entry>/<commandType>");
        url.add("entry", entry.getMibEntryPath());
        url.add("commandType", commandType.getValue());
        return url.render();
    }
}
