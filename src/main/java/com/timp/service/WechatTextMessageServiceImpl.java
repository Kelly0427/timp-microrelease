package com.timp.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.fabric.xmlrpc.base.Data;
import com.timp.domain.OpenId;
import com.timp.domain.OpenIdRepository;
import com.timp.domain.WechatTextMessage;
import com.timp.domain.WechatTextMessageRepository;
import com.timp.tool.WechatTokenUtil;

@Service
public class WechatTextMessageServiceImpl implements WechatTextMessageService{
	@Autowired
	WechatTextMessageRepository textMsgRepository;
	@Autowired
	OpenIdRepository openIdRepository;
	@Autowired
	OpenIdService openIdService;
	@Autowired
	WechatTokenUtil tokenUtil;
	@Autowired
	WechatTextMessageRepository wechatTextMessageRepository; 
	private static Logger logger = LoggerFactory.getLogger(WechatTextMessageServiceImpl.class);
	private static final String message_send_url="https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
	//将数据添加到数据库
	@Override
	public WechatTextMessage addTextMsg(WechatTextMessage textMsg) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		textMsg.setSendTime(formatter.format(new Date()));
		return textMsgRepository.save(textMsg);
	}
	//发布文本消息到微信公众号平台
	@Override
	public String sendTextMsg(Long id,String sendOperator) {
		WechatTextMessage message=textMsgRepository.findOne(id);
		//获取access_token
		String access_token=tokenUtil.checkAccessToken();
		//讲微信公众平台的粉丝存储到本地数据库
		OpenId all=openIdService.addOpenId();
		//获取粉丝列表
		List<OpenId> lists=openIdRepository.findAll();
		//请求
		RestTemplate restTemplate = new RestTemplate();
		//设置请求头
		HttpHeaders headers=new HttpHeaders();
		//设置编码格式
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		//请求地址
		String url=message_send_url.replace("ACCESS_TOKEN", access_token);					
		/**
		 * 参数--开始
		 */		
		//获取openid
		String str=null;
		if(lists.size()>0) {
			str=lists.get(lists.size()-1).getOpenId();
		}else {
		    str=lists.get(0).getOpenId();
		}
		
	    //去掉开头结尾（[]）后的值
		String touser=str.substring(1, str.length()-1);
		//以逗号分隔取单个值放到数组中
		String[] strArr=touser.split(",");
		//用来存放openid的集合（传参格式需要）
		List list=new ArrayList<>();
		if(strArr.length>0) {
			for(int i=0;i<strArr.length;i++) {
				//去掉开头结尾的双引号
				list.add(strArr[i].substring(1, strArr[i].length()-1));
			}
		}else {
			list.add(strArr[0].substring(1,strArr[0].length()-1 ));
		}
		//将openid集合转换为json数组
		JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(list));
		//存放参数的json对象
		JSONObject params = new JSONObject();
		//粉丝
		params.put("touser", jsonArray);
		//消息类型
		params.put("msgtype", "text");
		//发送内容
		JSONObject paramsTemp = new JSONObject();
		paramsTemp.put("content", message.getContent());
		params.put("text", paramsTemp);
		/**
		 * 参数--结束
		 */								
		//组装
		HttpEntity<String> entity = new HttpEntity<String>(params.toJSONString(), headers);
		//请求开始
		String strbody = restTemplate.exchange(url, HttpMethod.POST, entity,String.class).getBody();  
		JSONObject jsonObject = JSONObject.parseObject(strbody);
		String errcode=jsonObject.getString("errcode");
		if(errcode.equals("0")) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			message.setSendTime(df.format(new Date()));	
			message.setStatus("已审核");
			textMsgRepository.save(message);//这里主要是传审阅人
		}
		return strbody;
	}
	//修改文本消息
	@Override
	public int editTextMsg(Long[] ids) {
		int res=0;
		List<WechatTextMessage> lists=null;
		for (Long id : ids) {
			lists=textMsgRepository.findById(id);
			for (WechatTextMessage wechatTextMessage : lists) {
				textMsgRepository.save(wechatTextMessage);
			}
			res++;
		}
		return res;
	}
	//删除文本消息
	@Override
	public int deleteTextMsg(Long[] ids) {
		int res=0;
		for (Long id : ids) {
			textMsgRepository.delete(id);
			res++;
		}
		return res;
	}
	//发布信息列表
	@Override
	public Page<Object> queryCheckText(Pageable pageable, String startTime, String endTime) {
		return wechatTextMessageRepository.queryCheckText(pageable, startTime, endTime);
	}
	//待审核信息列表
	@Override
	public Page<Object> queryUncheckText(Pageable pageable, String startTime, String endTime) {
		return wechatTextMessageRepository.queryUncheckText(pageable, startTime, endTime);
	}
	//展示所有的内容
	@Override
	public List<WechatTextMessage> queryTextList() {
		return wechatTextMessageRepository.queryTextList();
	}
	//微信文本消息驳回接口
	@Override
	public WechatTextMessage rejectMessage(Long id, String sendOperator) {
		WechatTextMessage message=wechatTextMessageRepository.findOne(id);
		message.setId(id);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		message.setSendTime(df.format(new Date()));	
		message.setStatus("待审核");
		return wechatTextMessageRepository.save(message);
	}
	//保存并发布
	@Override
	public String saveAndSend(WechatTextMessage message) {
		//获取access_token
		String access_token=tokenUtil.checkAccessToken();
		//讲微信公众平台的粉丝存储到本地数据库
		OpenId all=openIdService.addOpenId();
		//获取粉丝列表
		List<OpenId> lists=openIdRepository.findAll();
		//请求
		RestTemplate restTemplate = new RestTemplate();
		//设置请求头
		HttpHeaders headers=new HttpHeaders();
		//设置编码格式
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		//请求地址
		String url=message_send_url.replace("ACCESS_TOKEN", access_token);					
		/**
		  * 参数--开始
		*/		
		//获取openid
		String str=null;
		if(lists.size()>0) {
			str=lists.get(lists.size()-1).getOpenId();
		}else {
			str=lists.get(0).getOpenId();
		}
				
		//去掉开头结尾（[]）后的值
		String touser=str.substring(1, str.length()-1);
		//以逗号分隔取单个值放到数组中
		String[] strArr=touser.split(",");
		//用来存放openid的集合（传参格式需要）
		List list=new ArrayList<>();
		if(strArr.length>0) {
			for(int i=0;i<strArr.length;i++) {
				//去掉开头结尾的双引号
				list.add(strArr[i].substring(1, strArr[i].length()-1));
			}
		}else {
			list.add(strArr[0].substring(1,strArr[0].length()-1 ));
		}
		//将openid集合转换为json数组
		JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(list));
		//存放参数的json对象
		JSONObject params = new JSONObject();
		//粉丝
		params.put("touser", jsonArray);
		//消息类型
		params.put("msgtype", "text");
		//发送内容
		JSONObject paramsTemp = new JSONObject();
		paramsTemp.put("content", message.getContent());
		params.put("text", paramsTemp);
		/**
		 * 参数--结束
		 */								
		//组装
		HttpEntity<String> entity = new HttpEntity<String>(params.toJSONString(), headers);
		//请求开始
		String strbody = restTemplate.exchange(url, HttpMethod.POST, entity,String.class).getBody();  
		JSONObject jsonObject = JSONObject.parseObject(strbody);
		String errcode=jsonObject.getString("errcode");
		if(errcode.equals("0")) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			message.setSendTime(df.format(new Date()));	
			message.setStatus("已审核");
			textMsgRepository.save(message);//这里主要是传审阅人
		}
		return strbody;
	}
}
