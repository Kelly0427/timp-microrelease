package com.timp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="headlines")
public class Headlines {
	@Id @GeneratedValue
	@Column
	private Long id;
	//文章标题，限定32个中英文字符以内--必传
	@Column
	private String title;
	//正文内容，限制90000个中英文字符内，需要urlencode--必传
	@Column(columnDefinition="mediumtext")
	private String content;
	//文章封面图片地址url(图片需要上传到服务器)--必传
	@Column
	private String cover;
	//文章导语--不必传
	@Column
	private String summary;
	//与其绑定短微博内容，限制1900个中英文字符内--必传
	@Column
	private String text;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
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
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
}
