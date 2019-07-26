package com.timp.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timp.domain.ArticleMessage;
import com.timp.domain.ArticleMessageRepository;
import com.timp.service.ArticleMessageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "ArticleMessage/db", tags = "微信公众号图文消息", description = "用于对微信公众号图文消息的增删改查操作")
public class ArticleMessageController {
	@Autowired
	ArticleMessageRepository articleMessageRepository;
	@Autowired
	ArticleMessageService articleMessageService;
	/**
	 * 将图片上传到微信服务器获取url地址
	 * @param filePath
	 * @param fileType
	 * @return
	 */
//	@ApiOperation(value="获取图片网络url",notes = "将图片上传到微信服务器获取url地址",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PostMapping("/article/upload")
	public String upload(String filePath, String fileType) {
		return articleMessageService.upload(filePath, fileType);
	}
	/**
	 * 上传图文消息到微信服务器获取图文消息id
	 * @param article
	 * @return
	 */
//	@ApiOperation(value="获取图片图文消息id",notes = "上传图文消息到微信服务器获取图文消息id",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PostMapping("/article/sendArticle")
	public String sendArticle(ArticleMessage article,String meidaId) {
		return articleMessageService.sendArticle(article, meidaId);
	}
	/**
	 * 群发图文消息
	 * @param mediaId
	 * @param send_ignore_reprint
	 * @return
	 */
//	@ApiOperation(value="群发图文消息",notes = "点击审核时调用群发图文消息接口",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@GetMapping("/article/sendNews")
	public String sendNews(String filePath,String fileType, ArticleMessage article, int send_ignore_reprint) {
		return articleMessageService.sendNews(filePath,fileType,article,send_ignore_reprint);
	}
	/**
	 * 将图文消息添加到本地数据库
	 * @param article
	 * @return
	 */
//	@ApiOperation(value="添加/修改数据",notes = "向数据库添加/修改一条信息",produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response =ArticleMessage.class)
	@PostMapping("/article/addArticle")
	public ArticleMessage addArticle(ArticleMessage articleMessage) {		
		return articleMessageService.addArticleMessage(articleMessage);
	}
	/**
	 * 展示未发布的信息
	 * @param pageable
	 * @param startTime
	 * @param endTime
	 * @return
	 */
//	@ApiOperation(value="展示未发布的信息",notes = "向数据库查询未发布的信息",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@GetMapping("/article/queryUncheckInfo")
	public Page<Object> queryUncheckInfo(Pageable pageable,String startTime,String endTime){
		return articleMessageService.queryUncheckInfo(pageable, startTime, endTime);
	}
	/**
	 * 展示发布的信息
	 * @param pageable
	 * @param startTime
	 * @param endTime
	 * @return
	 */
//	@ApiOperation(value="展示发布的信息",notes = "向数据库查询发布的信息",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@GetMapping("/article/queryCheckInfo")
	public Page<Object> queryCheckInfo(Pageable pageable,String startTime,String endTime){
		return articleMessageService.queryCheckInfo(pageable, startTime, endTime);
	}
	/**
	 * 展示所有的内容
	 * @return
	 */	
//	@ApiOperation(value="展示所有的信息",notes = "向数据库查询所有",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@GetMapping("/article/queryArticleList")
	public List<ArticleMessage> queryArticleList() {
		return articleMessageService.queryArticleList();
	}
	/**
	 * 删除图文消息
	 * @param ids
	 * @return
	 */
//	@ApiOperation(value="删除图文消息",notes = "删除一条或多条未发布的图文消息",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@DeleteMapping("/article/deleteArticle")
	public int deleteArticle(Long[] ids) {
		return articleMessageService.deleteArticle(ids);
	}
}
