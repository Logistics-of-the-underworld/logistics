package com.tiandi.logistics.utils;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;

/**
 * 短信发送工具类
 *
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/30 14:08
 */
@Slf4j
public final class SMSSendingUtil {

    private static final String ACCESS_KEY_ID = "~~L~~T~~A~~I~~4~~G~~H~~F~~C~~Y~~dx~~q~~ok~~~~kS~~7~~syF~~zwX~~";
    private static final String ACCESS_KEY_SECRET = "0~~nn~~~~~~5k~~r~~M1Yko~~~~M2ni~~CivTc~~VuAT0Hg~~mKb~~";

    public static CommonResponse sendALiSms(String telephone, String code) throws Exception {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID.replace("~",""), ACCESS_KEY_SECRET.replace("~",""));
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", telephone);
        request.putQueryParameter("SignName", "TP物流");
        request.putQueryParameter("TemplateCode", "SMS_205887767");
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            log.info(response.toString());
            return response;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static CommonResponse sendOrderCode(String orderCode, String distribution, String phone) throws Exception {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID.replace("~",""), ACCESS_KEY_SECRET.replace("~",""));
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "TP物流");
        request.putQueryParameter("TemplateCode", "SMS_206554280");
        request.putQueryParameter("TemplateParam", "{ \"code\":\"" + orderCode + "\", \"distribution\": \"" + distribution +"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            log.info(JSON.toJSONString(response));
            return response;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }
}
