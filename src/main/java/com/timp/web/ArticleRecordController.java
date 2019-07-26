package com.timp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timp.service.ArticleRecordService;

@RestController
public class ArticleRecordController {
	@Autowired
	ArticleRecordService articleRecordService;
	/**
	 * 删除群发消息
	 * @param title
	 * @return
	 */
	@GetMapping("/record/deleteArticleRecord")
	int deleteArticleRecord(String title) {
		return articleRecordService.deleteArticleRecord(title);
	}
}
