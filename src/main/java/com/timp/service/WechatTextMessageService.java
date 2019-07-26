package com.timp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.timp.domain.WechatTextMessage;

public interface WechatTextMessageService {
	/**
	 * 添加文本消息到数据库
	 * @param textMsg：内容
	 * @param type：text
	 * @return
	 */
	public WechatTextMessage addTextMsg(WechatTextMessage textMsg);
	/**
	 * 修改文本消息
	 * @param ids
	 * @return
	 */
	public int editTextMsg(Long[] ids);
	/**
	 * 删除文本消息
	 * @param ids
	 * @return
	 */
	public int deleteTextMsg(Long[] ids);
	/**
	 * 文本消息发布到微信公众号
	 * @param id
	 * @param sendOperator
	 * @return
	 */
	public String sendTextMsg(Long id,String sendOperator);
	/**
	 * 微信文本消息驳回接口
	 * @param id
	 * @param sendOperator
	 * @return
	 */
	public WechatTextMessage rejectMessage(Long id,String sendOperator);
	/**
	 * 展示发布的内容
	 * @param pageable
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Page<Object> queryCheckText(Pageable pageable,String startTime,String endTime);
	/**
	 * 展示审核的内容
	 * @param pageable
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Page<Object> queryUncheckText(Pageable pageable,String startTime,String endTime);
	/**
	 * 展示所有的内容
	 * @return
	 */
	List<WechatTextMessage> queryTextList();
	/**
	 * 保存并发布
	 * @param wechatTextMessage
	 * @return
	 */
	public String saveAndSend(WechatTextMessage message);
}
