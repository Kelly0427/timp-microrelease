package com.timp.tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.timp.domain.ResponseMsg;
import com.timp.domain.ResponseMsgRepository;
@RestController
public class WechatCallBackapiTest {
	@Autowired
	ResponseMsgRepository responseMsgRepository;	
	//令牌
	private String TOKEN="weixin";
	/**
	 * 验证签名(微信公众号后台配置的时候会自动调用此方法)
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/wechatDemo",method=RequestMethod.GET)
	@ResponseBody
    public String verifySignature(HttpServletRequest request) {
        //微信端发来的签名
		String signature = request.getParameter("signature");
		//微信端发来的时间戳
        String timestamp = request.getParameter("timestamp");
        //微信端发来的随机字符串
        String nonce = request.getParameter("nonce");
        //微信端发来的验证字符串
        String echostr = request.getParameter("echostr");
		//将token、timestamp、nonce三个参数进行字典序排序
        String sortString = sort(TOKEN, timestamp, nonce);
        //将三个参数字符串拼接成一个字符串进行sha1加密
        String myString = sha1(sortString);
        //开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        if (myString != null && myString != "" && myString.equals(signature)) {
        	ResponseMsg responseMsg=new ResponseMsg();
        	responseMsg.setSignature(signature);
        	responseMsg.setTimestamp(timestamp);
        	responseMsg.setNonce(nonce);
        	responseMsg.setEchostr(echostr);
        	responseMsgRepository.save(responseMsg);
            //如果检验成功原样返回echostr，微信服务器接收到此输出，才会确认检验完成。          
            return echostr;
        } else {
            return "Validation error";
        }
    }
	/**
	 * 排序
	 * @param token：令牌
	 * @param timestamp：时间戳
	 * @param nonce：随机数
	 * @return
	 */
    public String sort(String token, String timestamp, String nonce) {
        String[] strArray = {token, timestamp, nonce};
        Arrays.sort(strArray);
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str);
        } 
        return sb.toString();
    }
 
    /**
     * SHA1加密
     * @param str
     * @return
     */
    public String sha1(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
 
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }    
}
