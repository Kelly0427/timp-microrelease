package com.timp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 微信粉丝表
 * @author Maria
 *
 */
@Entity
@Table(name="openid")
public class OpenId {
	@Id @GeneratedValue
	@Column(name="id")
	private Long id;
	@Column(name="openid",columnDefinition="mediumtext" )
	private String openId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
}
