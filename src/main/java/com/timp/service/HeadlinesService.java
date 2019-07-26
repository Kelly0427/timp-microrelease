package com.timp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.timp.domain.ArticleMessage;
import com.timp.domain.Headlines;


public interface HeadlinesService {
	/**
	 * 发布头条文章
	 * @param title：文章标题
	 * @param content：正文内容
	 * @param cover：文章封面图片地址
	 * @param summary：文章导语
	 * @param text：与其绑定短微博内容
	 * @return
	 */
	 public Headlines sendHeadlines(Long id,String sendOperator);
	 /**
	  * 保存并发布
	  * @param headlines
	  * @return
	  */
	 public Headlines sendAndSave(Headlines headlines);
	 /**
	  * 微博驳回接口
	  * @param id
	  * @return
	  */
	 public Headlines rejectHeadlines(Long id,String rejectOperator);
	 /**
	  * 查询所有的头条文章列表
	  * @param pageable
	  * @param startTime
	  * @param endTime
	  * @return
	  */
	 public Page<Headlines> searchHeadlines(Pageable pageable,String startTime,String endTime);
	 /**
	  * 添加到数据库，未审阅
	  * @param headlines
	  * @return
	  */
	 public Headlines addHeadlines(Headlines headlines);
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
	  * 展示所有的信息
	  * @return
	  */
	 List<Headlines> queryHeadlinesList();
	 /**
	  * 删除头条文章
	  * @param ids
	  * @return
	  */
	 int deleteHeadlines(Long[] ids);
	 
	 
}
