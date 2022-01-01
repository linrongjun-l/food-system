package cn.ecut.sms.controller;




import cn.ecut.lrj.common.api.CommonResult;
import cn.ecut.lrj.common.api.ResultCode;
import cn.ecut.lrj.common.exception.Asserts;
import cn.ecut.lrj.common.util.RandomUtils;
import cn.ecut.lrj.common.util.RegexValidateUtils;
import cn.ecut.sms.service.SmsService;
import cn.ecut.sms.util.SmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/sms")
@Slf4j
public class ApiSmsController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SmsService smsService;

    @GetMapping("/send/{mobile}")
    public CommonResult send(@PathVariable("mobile") String mobile){
        //校验手机号吗不能为空
        Asserts.notEmpty(mobile, ResultCode.MOBILE_NULL_ERROR);
        //是否是合法的手机号码
        Asserts.isTrue(RegexValidateUtils.checkCellphone(mobile), ResultCode.MOBILE_ERROR);

        //判断手机号是否已经注册
//        boolean result = coreUserInfoClient.checkMobile(mobile);
//        log.info("result = " + result);
//        Assert.isTrue(result == false, ResponseEnum.MOBILE_EXIST_ERROR);


        String code = RandomUtils.getFourBitRandom();
        /*HashMap<String, Object> map = new HashMap<>();
        map.put("code", code);*/
        smsService.sendSms(mobile, SmsProperties.TEMPLATE_CODE, new String[]{code,SmsProperties.TIMEOUT});

        //将验证码存入redis
        redisTemplate.opsForValue().set("food:system:sms:code:" + mobile, code, Integer.parseInt(SmsProperties.TIMEOUT), TimeUnit.MINUTES);

        return CommonResult.success("短信发送成功");
    }

    @GetMapping("/send2/{mobile}")
    public CommonResult send2(@PathVariable("mobile") String mobile){
        return CommonResult.success("数据返回成功");
    }

}
