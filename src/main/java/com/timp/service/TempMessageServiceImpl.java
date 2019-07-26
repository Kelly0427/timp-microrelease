package com.timp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.timp.domain.TempMessage;
import com.timp.domain.TempMessageRepository;

@Service
public class TempMessageServiceImpl implements TempMessageService{
	@Autowired
	TempMessageRepository tempMessageRepository;
	//添加发布信息
	@Override
	public TempMessage addTempMessage(TempMessage tempMessage) {
		return tempMessageRepository.save(tempMessage);
	}
    //分页展示所有的发布信息
	@Override
	public Page<TempMessage> searchTempMsg(Pageable pageable, String startTime, String endTime) {
		return tempMessageRepository.searchTempMsg(pageable, startTime, endTime);
	}
	//编辑发布的信息
	@Override
	public int editTempMessage(Long[] ids) {		
		int result=0;
		List<TempMessage> lists=null;
		for (Long id : ids) {
			lists=tempMessageRepository.findById(id);
			for (TempMessage tempMessage : lists) {
				tempMessageRepository.save(tempMessage);
			}
		}
		return result;
	}
	//删除发布的信息
	@Override
	public int deleteTempMessage(Long[] ids) {
		int result=0;
		for (Long id : ids) {
			tempMessageRepository.delete(id);
			result++;
		}
		return result;
	}	
}
