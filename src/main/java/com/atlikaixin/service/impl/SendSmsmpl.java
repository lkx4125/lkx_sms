package com.atlikaixin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atlikaixin.service.SendSms;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author : LiKaixin
 * @number : phone 15179204125
 * @create : 2022-09-16 18:26
 * @Description : 描述
 */
@Service
public class SendSmsmpl implements SendSms {
    @Override
    public boolean send(String phoneNum, String templateCode, Map<String, Object> code) {

        // 连接阿里云
        DefaultProfile profile = DefaultProfile.getProfile("regionId", "accessKeyId", "sercet");
        IAcsClient client = new DefaultAcsClient(profile);

        // 构建请求
        CommonRequest request = new CommonRequest();

        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com"); // 不可变
        request.setSysVersion("2017-05-25"); // 不可变
        request.setSysAction("SendSms");

        // 自定义的参数（手机号、验证码、签名、模板！）
        request.putQueryParameter("PhoneNumbers", phoneNum);
        request.putQueryParameter("SignName","signName");
        request.putQueryParameter("TemplateCode", templateCode);

        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(code));

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return  response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }


        return false;
    }
}
