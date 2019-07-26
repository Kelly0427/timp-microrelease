package com.timp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timp.domain.TempMessage;
import com.timp.service.TempMessageService;

@RestController
public class TempMessageController {
	@Autowired
	TempMessageService tempMessageService;
	/**
	 * 添加发布信息
	 * @param tempMessage
	 * @return
	 */
	@PostMapping("/tempMsg/addTempMessage")
	public TempMessage addTempMessage(TempMessage tempMessage) {
		return tempMessageService.addTempMessage(tempMessage);
	}
	/**
	 * 分页展示所有的发布信息
	 * @param pageable
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@GetMapping("/tempMsg/searchTempMsg")
	public Page<TempMessage> searchTempMsg(Pageable pageable,String startTime,String endTime){
		return tempMessageService.searchTempMsg(pageable, startTime, endTime);
	} 
	/**
	 * 编辑发布的信息
	 * @param ids
	 * @return
	 */
	@PostMapping("/tempMsg/editTempMessage")
	public int editTempMessage(Long[] ids) {
		return tempMessageService.editTempMessage(ids);
	}
	/**
	 * 删除发布的信息
	 * @param ids
	 * @return
	 */
	@DeleteMapping("/tempMsg/deleteTempMessage")
	public int deleteTempMessage(Long[] ids) {
		return tempMessageService.deleteTempMessage(ids);
	}
}
