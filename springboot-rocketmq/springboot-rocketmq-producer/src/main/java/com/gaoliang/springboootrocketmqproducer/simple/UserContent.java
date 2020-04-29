package com.gaoliang.springboootrocketmqproducer.simple;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author GaoLiang
 * @date 2020/4/29 15:04
 */
@Data
@AllArgsConstructor
public class UserContent {
    private String username;
    private String pwd;
}
