package com.timp.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WechatTextMessageRepositoryCustom {

	/**
	 * 展示发布的内容
	 * @param pageable
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Page<Object> queryCheckText(Pageable pageable, String startTime,String endTime);
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
}
