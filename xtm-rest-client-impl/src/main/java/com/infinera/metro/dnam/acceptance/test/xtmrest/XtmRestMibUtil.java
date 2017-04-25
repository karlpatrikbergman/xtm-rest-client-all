package com.infinera.metro.dnam.acceptance.test.xtmrest;

import com.infinera.metro.dnam.acceptance.test.mib.Command;
import com.infinera.metro.dnam.acceptance.test.mib.GroupOrTable;
import com.infinera.metro.dnam.acceptance.test.mib.MibEntry;
import com.infinera.metro.dnam.acceptance.test.mib.Module;
import org.stringtemplate.v4.ST;

public enum XtmRestMibUtil {
    INSTANCE;

    public String mibRestUrl(Module module, GroupOrTable group, MibEntry entry, Command command) {
        ST url = new ST("/mib/<module>/<group>/<entry>/<command>");
        url.add("module", module.getValue());
        url.add("group", group.getValue());
        url.add("entry", entry.getMibString());
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
