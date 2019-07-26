package com.timp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.timp.domain.TempMessage;

public interface TempMessageService {
	/**
	 * 添加发布信息
	 * @param tempMessage
	 * @return
	 */
	public TempMessage addTempMessage(TempMessage tempMessage);
	/**
	 * 分页展示所有的发布信息
	 * @param pageable
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Page<TempMessage> searchTempMsg(Pageable pageable,String startTime,String endTime);
	/**
	 * 编辑发布的信息
	 * @param ids
	 * @return
	 */
	public int editTempMessage(Long[] ids);
	/**
	 * 删除发布的信息
	 * @param ids
	 * @return
	 */
	public int deleteTempMessage(Long[] ids);
}
