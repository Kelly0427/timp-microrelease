package com.timp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.timp.domain.ArticleMessage;
import com.timp.domain.Headlines;

public interface ArticleMessageService {
	/**
	 * 上传图片
	 * @param filePath：路径
	 * @param fileType：格式
	 * @return
	 */
	public String upload(String filePath, String fileType) ;
	/**
	 * 上传图文消息同时添加到数据库
	 * @param article
	 * @return
	 */
	public String sendArticle(ArticleMessage article,String meidaId);
	/**
	 * 群发图文消息
	 * @param mediaId
	 * @param send_ignore_reprint
	 * @return
	 */
	
	public String sendNews(String filePath, String fileType, ArticleMessage article,  int send_ignore_reprint);
	/**
	 * 将发送的内容添加到数据库
	 * @param articleMessage
	 * @return
	 */
	public ArticleMessage addArticleMessage(ArticleMessage articleMessage);
	/**
	 * 展示未发布的信息
	 * @param pageable
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Page<Object> queryUncheckInfo(Pageable pageable,String startTime,String endTime);
	/**
	 * 展示发布的信息
	 * @param pageable
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Page<Object> queryCheckInfo(Pageable pageable,String startTime,String endTime);
	/**
	 * 展示所有的内容
	 * @return
	 */
	public List<ArticleMessage> queryArticleList();
	/**
	 * 删除图文消息
	 * @param ids
	 * @return
	 */
	public int deleteArticle(Long[] ids);
}
