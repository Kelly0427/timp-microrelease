package com.timp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.timp.domain.ArticleMessage;
import com.timp.domain.ArticleMessageRepository;
import com.timp.domain.Headlines;

@Service
public class ArticleMessageServiceImpl implements ArticleMessageService{
	@Autowired
	ArticleMessageRepository articleMessageRepository;
	/**
	 * 将图片上传到微信服务器获取url地址
	 */
	@Override
	public String upload(String filePath, String fileType) {
		return articleMessageRepository.upload(filePath, fileType);
	}
	/**
	 * 上传图文消息到微信服务器获取图文消息id
	 */
	@Override
	public String sendArticle(ArticleMessage article,String meidaId) {
		return articleMessageRepository.sendArticle(article, meidaId);
	}
	/**
	 * 群发图文消息
	 */
	@Override
	public String sendNews(String filePath, String fileType, ArticleMessage article, int send_ignore_reprint) {
		//图片id
		String imgId=articleMessageRepository.upload(filePath, fileType);
		//素材id
		String mediaId=articleMessageRepository.sendArticle(article,imgId);		
		return articleMessageRepository.sendNews(mediaId, send_ignore_reprint,article);
	}
	/**
	 * 添加信息到数据库
	 */
	@Override
	public ArticleMessage addArticleMessage(ArticleMessage articleMessage) {
		return articleMessageRepository.save(articleMessage);
	}
	//待审核列表
	@Override
	public Page<Object> queryUncheckInfo(Pageable pageable, String startTime, String endTime) {
		return articleMessageRepository.queryUncheckInfo(pageable, startTime, endTime);
	}
	//发布列表
	@Override
	public Page<Object> queryCheckInfo(Pageable pageable, String startTime, String endTime) {
		return articleMessageRepository.queryCheckInfo(pageable, startTime, endTime);
	}
	//展示所有内容
	@Override
	public List<ArticleMessage> queryArticleList() {
		return articleMessageRepository.queryArticleList();
	}
	//删除图文消息
	@Override
	public int deleteArticle(Long[] ids) {
		int res=0;
		for (Long id : ids) {
			articleMessageRepository.delete(id);
			res++;
		}
		return res;
	}
}
