package com.timp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timp.domain.WBTokenState;
import com.timp.service.WBTokenStateService;

@RestController
public class WBTokenStateController {
	@Autowired
	WBTokenStateService wBTokenStateService;
	/**
	 * 添加获取的AccessToken
	 * @param wBTokenState
	 * @return
	 */
	@PostMapping("/wBTokenState/addWBTokenState")
	public WBTokenState addWBTokenState(WBTokenState wBTokenState) {
		return wBTokenStateService.addWBTokenState(wBTokenState);
	}
	/**
	 * 读取用户授权的唯一票据信息
	 * @param code
	 * @return
	 */
	@GetMapping("/wBTokenState/getCode")
	public String getAccessToken(String code){		
		return wBTokenStateService.getAccessToken(code);
	}
	/**
	 * 读取code
	 * @return
	 */
	@GetMapping("/getCode")
	public String getCode() {
		return wBTokenStateService.getCode();
	}
}
