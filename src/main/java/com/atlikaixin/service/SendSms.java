package com.atlikaixin.service;

import java.util.Map;

/**
 * @author : LiKaixin
 * @number : phone 15179204125
 * @create : 2022-09-16 18:23
 * @Description : 描述
 */
public interface SendSms {

    public boolean send(String phoneNum, String templateCode, Map<String, Object> code);

}
