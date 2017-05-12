package basic;

import lombok.Data;

@Data
public final class Connection {
    private String url;
    private int poolSize;
}
