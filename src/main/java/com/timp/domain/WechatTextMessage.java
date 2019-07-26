package com.timp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 文本消息表
 * @author Maria
 *
 */
@Entity
@Table(name="wechattextmessage")
public class WechatTextMessage {
	@Id @GeneratedValue
	@Column
	private Long id;
	@Column(columnDefinition="mediumtext")
	private String content;//文本内容
	@Column
	private String addOperator;//添加人员
	@Column
	private String sendOperator;//审阅(发布)人员
	@Column
	private String sendTime;//发布时间
	@Column
	private String status;//是否发布到微信公众号--待审核=未发布&已审核=发布
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getAddOperator() {
		return addOperator;
	}
	public void setAddOperator(String addOperator) {
		this.addOperator = addOperator;
	}
	public String getSendOperator() {
		return sendOperator;
	}
	public void setSendOperator(String sendOperator) {
		this.sendOperator = sendOperator;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}	
}
