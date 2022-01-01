package cn.ecut.lrj.aliyun.oss.controller;

import cn.ecut.lrj.aliyun.oss.OssProperties;
import cn.ecut.lrj.common.api.CommonResult;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.baomidou.mybatisplus.extension.api.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/aliyun")
public class OSSController {
    @CrossOrigin //解决跨域
    @RequestMapping("/oss/policy")
    public CommonResult policy(@RequestParam("fileName") String fileName){

        String accessId = OssProperties.KEY_ID; // 请填写您的AccessKeyId。
        String accessKey = OssProperties.KEY_SECRET; // 请填写您的AccessKeySecret。
        String endpoint = OssProperties.ENDPOINT; // 请填写您的 endpoint。
        String bucket = OssProperties.BUCKET_NAME; // 请填写您的 bucketname 。
        String host = "https://" + bucket + "." + endpoint; // host的格式为 bucketname.endpoint



        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());

        String dir = OssProperties.DIR_PREFIX + date+"/"; // 用户上传文件时指定的前缀。


        //文件名生成
        fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
        String key = dir + fileName;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessId, accessKey);
        try {

            long expireEndTime = System.currentTimeMillis() + new Long(OssProperties.POLICY_EXPIRE) * 1000;
            Date expiration = new Date(expireEndTime);
            // PostObject请求最大可支持的文件大小为5 GB，即CONTENT_LENGTH_RANGE为5*1024*1024*1024。
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            Map<String, String> respMap = new LinkedHashMap<String, String>();
            respMap.put("accessKeyId", accessId);//您的AccessKeyId。
            respMap.put("policy", encodedPolicy);//Policy规定了请求表单域的合法性。
            respMap.put("signature", postSignature);//根据AccessKey Secret和Policy计算的签名信息，OSS验证该签名信息从而验证该Post请求的合法性。
            respMap.put("dir", dir); //前缀
            respMap.put("host", host); // "https://" + bucketname + '.' + endpoint;  (前端请求oss服务路径)
            respMap.put("key", key); //dir + fileName (上传Object的名称。)
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            // respMap.put("expire", formatISO8601Date(expiration));
            return CommonResult.success(respMap);
        } catch (Exception e) {
            log.error(e.getMessage());
            Assert.fail(e.getMessage());
        } finally {
            ossClient.shutdown();
        }
        return null;
    }
}
