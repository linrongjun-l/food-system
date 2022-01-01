package cn.ecut.sms.service;

import java.util.Map;

public interface SmsService {
    void sendSms(String mobile,String templateCode,String[] code);

    void send(String mobile, String templateCode, Map<String, Object> param);
}
