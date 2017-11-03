package com.infinera.metro.dnam.acceptance.test.node;

import lombok.*;

//@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //Needed by Hibernate
//@AllArgsConstructor(access = AccessLevel.PUBLIC) //Needed by Orika
@Value
@Builder
public class NodeAccessData {
    @NonNull private final String ipAddress;
    @NonNull private final Integer port;
    @NonNull private final String userName;
    @NonNull private final String password;

    public NodeAccessData copyObjectAndChangeIpAddress(String newIpAddress) {
        return NodeAccessData.builder()
                .ipAddress(newIpAddress)
                .port(port)
                .password(password)
                .userName(userName)
                .build();
    }

    public static NodeAccessData createDefault(String ipAddress) {
        return NodeAccessData.builder()
                .ipAddress(ipAddress)
                .port(80)
                .userName("root")
                .password("root")
                .build();
    }
}
