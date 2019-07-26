package com.timp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 验证签名日志表
 * @author Maria
 *
 */
@Entity
@Table(name="responsemsg")
public class ResponseMsg {
	@Id @GeneratedValue 
	@Column(name="id")
	private Long id;
	@Column(name="signature")
	private String signature;//签名
	@Column(name="timestamp")
	private String timestamp;//时间
	@Column(name="nonce")
	private String nonce;//标志
	@Column(name="echostr")
	private String echostr;//返回内容
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	public String getEchostr() {
		return echostr;
	}
	public void setEchostr(String echostr) {
		this.echostr = echostr;
	}	
}
