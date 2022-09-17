package com.atlikaixin.control;

import com.atlikaixin.service.SendSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author : LiKaixin
 * @number : phone 15179204125
 * @create : 2022-09-16 18:37
 * @Description : 描述
 */
@Controller
@CrossOrigin // 跨越支持
public class SmsapiController {

    @Autowired
    private SendSms sendSms;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/send/{phone}")
    @ResponseBody
    public String code(@PathVariable("phone") String phone) {
        System.out.println(phone);
        // 调用发送方法（模拟真实业务 redis）
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return phone + ":" + code + "已存在，还没有过期";
        }

        // 生成验证码并存储到redis中
        code = UUID.randomUUID().toString().substring(0, 4);
        HashMap<String, Object> param = new HashMap<>();
        param.put("code", code);

        boolean isSend = sendSms.send(phone, "templateCode", param);
        if (isSend) {
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return phone + ":" + code + "发送成功！";
        } else {
            return "发送失败！";
        }
    }
}
