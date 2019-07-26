package com.timp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 图文消息记录表
 * @author Maria
 *
 */
@Entity
@Table(name="articlerecord")
public class ArticleRecord {
	@Id @GeneratedValue
	@Column
	private Long id;
	@Column
	private String title;
	@Column
	private String msgid;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	
}
