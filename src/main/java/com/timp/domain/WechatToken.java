package com.timp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 授权票据表
 * @author Maria
 *
 */
@Entity
@Table(name="wechattoken")
public class WechatToken {
	@Id 
	@GeneratedValue
	@Column(name="id")
	private Long id;//主键自增
	@Column(name="token")
	private String token;//令牌
	@Column(name="expiresin")
	private String expiresin;//有效期
	@Column(name="createtime")
	private Long createTime;//保存时间
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getExpiresin() {
		return expiresin;
	}
	public void setExpiresin(String expiresin) {
		this.expiresin = expiresin;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	
}
