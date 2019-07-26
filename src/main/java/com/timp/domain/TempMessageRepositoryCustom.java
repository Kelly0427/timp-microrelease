package com.timp.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TempMessageRepositoryCustom {
	
	/**
	 * 展示发布信息
	 * @param pageable
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Page<TempMessage> searchTempMsg(Pageable pageable,String startTime,String endTime);
}
