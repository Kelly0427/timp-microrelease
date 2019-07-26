package com.timp.tool;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class HelperUtils {
	public static Page<Object> getPageList(Pageable pageable, List<Object> list) {
		List<Object> listPage = new ArrayList<>();
		// 返回分页对象
		int size = pageable.getPageSize();
		int number = pageable.getPageNumber();
		if (number == 0) {
			number = 1;
		} else if (number >= 1) {
			number = number + 1;
		}
		// 当前页小于最大数组，说明看的不是第一页
		if (size * number <= list.size()) {
			for (int i = size * (number - 1); i < size * number; i++) {
				listPage.add(list.get(i));
			}
		} else if (size * number > list.size()) {
			for (int i = size * (number - 1); i < list.size(); i++) {
				listPage.add(list.get(i));
			}
		}
		Page<Object> page = new PageImpl<Object>(listPage, pageable, list.size());
		return page;
	}
}
