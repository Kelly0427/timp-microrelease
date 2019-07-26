package com.timp.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timp.domain.WechatTextMessage;
import com.timp.service.WechatTextMessageService;
import org.springframework.web.servlet.ModelAndView;


@RestController
//@Api(value = "WechatTextMessage/db", tags = "微信公众号文本消息", description = "用于对微信公众号文本消息的增删改查操作")
public class WechatTextMessageController {
	@Autowired
	WechatTextMessageService wechatTextMessageService;
	/**
	 * 添加文本消息到数据库
	 * @param textMsg：内容
	 * @return
	 */
//	@ApiOperation(value="添加文本消息到数据库",notes = "添加文本消息到数据库(待审核消息)",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,response =WechatTextMessage.class)	
	@GetMapping("/textMsg/addTextMsg")
	public WechatTextMessage addTextMsg(WechatTextMessage textMsg){
		return wechatTextMessageService.addTextMsg(textMsg);
	}
//	@ApiOperation(value="发布一条文本消息",notes = "发布一条文本消息",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@GetMapping("/textMsg/sendTextMsg")
	public String sendTextMsg(Long id,String sendOperator) {
		return wechatTextMessageService.sendTextMsg(id,sendOperator);
	}
	/**
	 * 保存并发布
	 * @param message:文本消息內容
	 * @return
	 */
	@GetMapping("/textMsg/saveAndSend")
	public String saveAndSend(WechatTextMessage message) {
		return wechatTextMessageService.saveAndSend(message);
	}
	/**
	 * 微信文本消息驳回接口
	 * @param id
	 * @param sendOperator
	 * @return
	 */
	@PostMapping("/text/rejectMessage")
	public WechatTextMessage rejectMessage(Long id,String sendOperator) {
		return wechatTextMessageService.rejectMessage(id, sendOperator);
	}
	/**
	 * 删除文本消息
	 * @param ids
	 * @return
	 */
//	@ApiOperation(value="删除文本消息",notes = "删除一条或多条未发布的文本消息",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@DeleteMapping("/textMsg/deleteTextMsg")
	public int deleteTextMsg(Long[] ids) {
		return wechatTextMessageService.deleteTextMsg(ids);
	}
	/**
	 * 展示发布的内容
	 * @param pageable
	 * @param startTime
	 * @param endTime
	 * @return
	 */
//	@ApiOperation(value="展示发布的内容(分页)",notes = "展示发布的内容",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@GetMapping("/text/queryCheckText")
	Page<Object> queryCheckText(Pageable pageable,String startTime,String endTime){
		return wechatTextMessageService.queryCheckText(pageable, startTime, endTime);
	}
	/**
	 * 展示审核的内容
	 * @param pageable
	 * @param startTime
	 * @param endTime
	 * @return
	 */
//	@ApiOperation(value="展示未发布的内容(分页)",notes = "展示未发布的内容",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@GetMapping("/text/queryUncheckText")
	Page<Object> queryUncheckText(@PageableDefault(size=13,page=0)Pageable pageable,
			String startTime,String endTime){
		return wechatTextMessageService.queryUncheckText(pageable, startTime, endTime);
	}
	/**
	 * 展示所有的内容
	 * @return
	 */
//	@ApiOperation(value="展示所有的内容",notes = "展示所有的内容",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@GetMapping("/text/queryTextList")
	List<WechatTextMessage> queryTextList(){
		return wechatTextMessageService.queryTextList();
	}
//	//登录页
    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView) {
		 modelAndView.setViewName("login");
		return modelAndView;
	}
}
