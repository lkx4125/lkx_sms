package com.atlikaixin;

import com.atlikaixin.service.SendSms;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class LkxSmsApplicationTests {


    @Autowired
    private SendSms sendSms;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void contextLoads() {
        String phone = "phoneNum";

        // 调用发送方法（模拟真实业务 redis）
        String code = redisTemplate.opsForValue().get(phone);
        System.out.println(code);

        // 生成验证码并存储到redis中
        code = UUID.randomUUID().toString().substring(0, 4);
        HashMap<String, Object> param = new HashMap<>();
        param.put("code", code);

        boolean isSend = sendSms.send(phone, "templateCode", param);
        System.out.println(isSend);
    }

}
