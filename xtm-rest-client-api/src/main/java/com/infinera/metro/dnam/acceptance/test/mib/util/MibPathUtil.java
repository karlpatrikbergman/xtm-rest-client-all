package com.infinera.metro.dnam.acceptance.test.mib.util;

import com.infinera.metro.dnam.acceptance.test.mib.Command;
import com.infinera.metro.dnam.acceptance.test.mib.GroupOrTable;
import com.infinera.metro.dnam.acceptance.test.mib.MibEntry;
import com.infinera.metro.dnam.acceptance.test.mib.Module;
import org.stringtemplate.v4.ST;

//TODO: This class does not belong in api. Should be in shared module. It us used by api and impl modules.
//xtm-rest-client-api
public enum MibPathUtil {
    MIB_PATH_UTIL;

    public String getMibEntryString(String entry, int subrack, int slot, int transceiverPort, int receiverPort) {
        ST result = new ST("<entry>:<subrack>:<slot>:<transceiverPort>-<receiverPort>");
        result.add("entry", entry);
        result.add("subrack", subrack);
        result.add("slot", slot);
        result.add("transceiverPort", transceiverPort);
        result.add("receiverPort", receiverPort);
        return result.render();
    }

    public String getMibEntryString(String entry, int subrack, int slot) {
        ST result = new ST("<entry>:<subrack>:<slot>");
        result.add("entry", entry);
        result.add("subrack", subrack);
        result.add("slot", slot);
        return result.render();
    }

    public String getMibEntryPath(Module module, GroupOrTable group, MibEntry entry) {
        ST url = new ST("/mib/<module>/<group>/<entry>");
        url.add("module", module.getValue());
        url.add("group", group.getValue());
        url.add("entry", entry.getMibEntryString());
        return url.render();
    }

    public String getMibPathAndCommand(MibEntry entry, Command command) {
        ST url = new ST("<entry>/<command>");
        url.add("entry", entry.getMibEntryPath());
        url.add("command", command.getValue());
        return url.render();
    }
}
