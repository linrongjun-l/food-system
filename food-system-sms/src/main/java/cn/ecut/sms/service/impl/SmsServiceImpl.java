package cn.ecut.sms.service.impl;

import cn.ecut.lrj.common.api.ResultCode;
import cn.ecut.lrj.common.exception.ApiException;
import cn.ecut.lrj.common.exception.Asserts;
import cn.ecut.sms.service.SmsService;
import cn.ecut.sms.util.SmsProperties;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    /**
     * 荣联云短信服务平台
     * @param mobile
     * @param templateCode
     * @param code
     */
    @Override
    public void sendSms(String mobile, String templateCode, String[] code) {
        HashMap<String, Object> result = null;

        //初始化SDK
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();

        //******************************注释*********************************************
        //*初始化服务器地址和端口                                                       *
        //*沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
        //*生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883");       *
        //*******************************************************************************
        restAPI.init("app.cloopen.com", "8883");

        //******************************注释*********************************************
        //*初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN     *
        //*ACOUNT SID和AUTH TOKEN在登陆官网后，在“应用-管理控制台”中查看开发者主账号获取*
        //*参数顺序：第一个参数是ACOUNT SID，第二个参数是AUTH TOKEN。                   *
        //*******************************************************************************
        restAPI.setAccount("8a216da87ba59937017be1ad38ca0caf", "b3c232296f9c46b498680aed823bae8e");


        //******************************注释*********************************************
        //*初始化应用ID                                                                 *
        //*测试开发可使用“测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID     *
        //*应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID*
        //*******************************************************************************
        restAPI.setAppId("8a216da87ba59937017be1ad39cf0cb6");


        //******************************注释****************************************************************
        //*调用发送模板短信的接口发送短信                                                                  *
        //*参数顺序说明：                                                                                  *
        //*第一个参数:是要发送的手机号码，可以用逗号分隔，一次最多支持100个手机号                          *
        //*第二个参数:是模板ID，在平台上创建的短信模板的ID值；测试的时候可以使用系统的默认模板，id为1。    *
        //*系统默认模板的内容为“【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入”*
        //*第三个参数是要替换的内容数组。																														       *
        //**************************************************************************************************

        //**************************************举例说明***********************************************************************
        //*假设您用测试Demo的APP ID，则需使用默认模板ID 1，发送手机号是13800000000，传入参数为6532和5，则调用方式为           *
        //*result = restAPI.sendTemplateSMS("13800000000","1" ,new String[]{"6532","5"});																		  *
        //*则13800000000手机号收到的短信内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是6532，请于5分钟内正确输入     *
        //*********************************************************************************************************************
        result = restAPI.sendTemplateSMS(mobile,templateCode ,code);
        System.out.println("SDKTestGetSubAccounts result=" + result);
        if("000000".equals(result.get("statusCode"))){
            //正常返回输出data包体信息（map）
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for(String key:keySet){
                Object object = data.get(key);
                System.out.println(key +" = "+object);
            }
        }else{
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
        }
    }


    /**
     * 阿里云短信服务平台
     * @param mobile
     * @param templateCode
     * @param param
     */
    @Override
    public void send(String mobile, String templateCode, Map<String, Object> param) {

        //创建远程连接客户端对象
        DefaultProfile profile = DefaultProfile.getProfile(
                SmsProperties.REGION_Id,
                SmsProperties.KEY_ID,
                SmsProperties.KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        //创建远程连接的请求参数
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", SmsProperties.REGION_Id);
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", SmsProperties.SIGN_NAME);
        request.putQueryParameter("TemplateCode", templateCode);

        Gson gson = new Gson();
        String jsonParam = gson.toJson(param);
        request.putQueryParameter("TemplateParam", jsonParam);
        try {
            //使用客户端对象携带请求参数向远程阿里云服务器发起远程调用，并得到响应结果
            CommonResponse response = client.getCommonResponse(request);
            System.out.println("response.getData()：" + response.getData());

            //通信失败的处理
            boolean success = response.getHttpResponse().isSuccess();
            Asserts.isTrue(success, ResultCode.ALIYUN_RESPONSE_ERROR);

            //获取响应结果
            String data = response.getData();
            HashMap<String, String> resultMap = gson.fromJson(data, HashMap.class);
            String code = resultMap.get("Code");
            String message = resultMap.get("Message");
            log.info("code：" + code + "，message：" + message);

            //业务失败的处理
            Asserts.notEquals("isv.BUSINESS_LIMIT_CONTROL", code, ResultCode.ALIYUN_SMS_LIMIT_CONTROL_ERROR);
            Asserts.equals("OK", code, ResultCode.ALIYUN_SMS_ERROR);

        } catch (ServerException e) {
            log.error("阿里云短信发送sdk调用失败:" + e.getErrCode() + ", " + e.getErrMsg());
            throw new ApiException(ResultCode.ALIYUN_SMS_ERROR, e);
//            e.printStackTrace();
        } catch (ClientException e) {
            log.error("阿里云短信发送sdk调用失败:" + e.getErrCode() + ", " + e.getErrMsg());
            throw new ApiException(ResultCode.ALIYUN_SMS_ERROR, e);
//            e.printStackTrace();
        }
    }
}
