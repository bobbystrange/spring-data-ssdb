package org.springframework.data.ssdb.pool;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create by tuke on 2019-05-20
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HostAndPort {
    private String host;
    private int port = 8888;
}
