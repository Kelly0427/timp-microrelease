package com.timp.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleMessageRepositoryCustom {
	/**
	 * 获取图片路径
	 * @param filePath：图片地址--C:\\test.jpg
	 * @param fileType：类型--Image
	 * @return
	 */
	public String upload(String filePath,String fileType);
	/**
	 * 上传图文消息素材获取素材id
	 * @param article
	 * @return
	 */
	public String sendArticle(ArticleMessage article,String meidaId);
	/**
	 * 按粉丝群发图文消息
	 * @param mediaId：上传到素材库的图文消息id
	 * @param send_ignore_reprint：是否转载-0文章被判定为转载时，将停止群发操作
	 * @return
	 */
	public String sendNews(String mediaId,int send_ignore_reprint,ArticleMessage article);
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
	 * 展示所有的信息
	 * @return
	 */
	public List<ArticleMessage> queryArticleList();
}
