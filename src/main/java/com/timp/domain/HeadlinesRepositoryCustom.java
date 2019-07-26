package com.timp.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HeadlinesRepositoryCustom {
	/**
	 * 发布头条文章
	 * @param title：文章标题
	 * @param content：正文内容
	 * @param cover：文章封面图片地址
	 * @param summary：文章导语
	 * @param text：与其绑定短微博内容
	 * @return
	 */
	 Headlines sendHeadlines(Long id,String sendOperator);
	 /**
	  * 查询所有的头条文章列表
	  * @param pageable
	  * @param startTime
	  * @param endTime
	  * @return
	  */
	 Page<Headlines> searchHeadlines(Pageable pageable,String startTime,String endTime);
	 /**
	  * 查询发布的记录
	  * @param pageable
	  * @param startTime
	  * @param endTime
	  * @return
	  */
	 Page<Object> queryCheckInfo(Pageable pageable,String startTime,String endTime);
	 /**
	  * 查询未发布的记录
	  * @param pageable
	  * @param startTime
	  * @param endTime
	  * @return
	  */
	 Page<Object> queryUnCheckInfo(Pageable pageable,String startTime,String endTime);
     /**
      * 展示所有的内容
      * @return
      */
	 List<Headlines> queryHeadlinesList();
}
