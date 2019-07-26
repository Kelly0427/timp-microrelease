package com.timp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timp.domain.OpenId;
import com.timp.service.OpenIdService;

@RestController
public class OpenIdController {
	@Autowired
	OpenIdService openIdService;
	/**
	 * 获取粉丝列表添加到本地数据库
	 * @return
	 */
	@GetMapping("/addOpenId")
	public OpenId addOpenId() {
		return openIdService.addOpenId();
	}
}
