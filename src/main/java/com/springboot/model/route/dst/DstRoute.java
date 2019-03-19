package com.springboot.model.route.dst;

import lombok.Data;

/**
 * 目的主机
 */
@Data
public class DstRoute {

    /**
     * 目的主机名称
     */
    String dst;
    /**
     * 目的主机IP
     */
    String ip;
    /**
     * 目的主机端口
     */
    int port;

}
