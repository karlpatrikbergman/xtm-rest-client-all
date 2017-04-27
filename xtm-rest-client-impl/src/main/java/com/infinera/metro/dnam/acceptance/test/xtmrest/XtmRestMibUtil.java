package com.infinera.metro.dnam.acceptance.test.xtmrest;

import com.infinera.metro.dnam.acceptance.test.mib.Command;
import com.infinera.metro.dnam.acceptance.test.mib.GroupOrTable;
import com.infinera.metro.dnam.acceptance.test.mib.MibEntry;
import com.infinera.metro.dnam.acceptance.test.mib.Module;
import org.stringtemplate.v4.ST;

//TODO: We've got a similar class in api-module. Keep one remove one?
public enum XtmRestMibUtil {
    INSTANCE;

    public String mibRestUrl(MibEntry entry, Command command) {
        ST url = new ST("<entry>/<command>");
        url.add("entry", entry.getMibEntryPath());
        url.add("command", command.getValue());
        return url.render();
    }

    public String mibRestUrl(Module module, GroupOrTable group, MibEntry entry, Command command) {
        ST url = new ST("/mib/<module>/<group>/<entry>/<command>");
        url.add("module", module.getValue());
        url.add("group", group.getValue());
        url.add("entry", entry.getMibEntryString());
        url.add("command", command.getValue());
        return url.render();
    }

    public String mibRestUrl(Module module, GroupOrTable group, Command command) {
        ST url = new ST("/mib/<module>/<group>/<command>");
        url.add("module", module.getValue());
        url.add("group", group.getValue());
        url.add("command", command.getValue());
        return url.render();
    }
}
