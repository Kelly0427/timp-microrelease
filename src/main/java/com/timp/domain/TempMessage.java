package com.timp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tempmessage")
public class TempMessage {
	@Id @GeneratedValue
	@Column(name="id")
	protected Long id;
	//图文消息作者
	@Column
	private String author;
	//图文消息页面的内容
	@Column
	private String content;
	//点击图文消息跳转链接
	@Column
	private String content_source_url;
	//图文小心的描述
	@Column
	private String digest;
	//Uint32 是否打开评论，0不打开，1打开
	@Column
	private int need_open_comment;
	//Uint32 是否粉丝才可评论，0所有人可评论，1粉丝才可评论	
	@Column
	private int only_fans_can_comment;
	//是否显示封面，1为显示，0为不显示	
	@Column
	private int show_cover_pic;
	//图片链接，支持JPG、PNG格式,较好的效果为大图640像素*320像素,小图80像素*80像素
	@Column
	private String thumb_media_id;
	//图文消息标题
	@Column
	private String title;
	//消息类型
	@Column
	private String type;
	//文章封面图片地址url(图片需要上传到服务器)--必传
	@Column
	private String cover;
	//文章导语--不必传
	@Column
	private String summary;
	//与其绑定短微博内容，限制1900个中英文字符内--必传
	@Column
	private String text;
	//发布时间
	@Column
	private Long sendTime;
	//状态
	@Column
	private String status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent_source_url() {
		return content_source_url;
	}
	public void setContent_source_url(String content_source_url) {
		this.content_source_url = content_source_url;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	public int getNeed_open_comment() {
		return need_open_comment;
	}
	public void setNeed_open_comment(int need_open_comment) {
		this.need_open_comment = need_open_comment;
	}
	public int getOnly_fans_can_comment() {
		return only_fans_can_comment;
	}
	public void setOnly_fans_can_comment(int only_fans_can_comment) {
		this.only_fans_can_comment = only_fans_can_comment;
	}
	public int getShow_cover_pic() {
		return show_cover_pic;
	}
	public void setShow_cover_pic(int show_cover_pic) {
		this.show_cover_pic = show_cover_pic;
	}
	public String getThumb_media_id() {
		return thumb_media_id;
	}
	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public Long getSendTime() {
		return sendTime;
	}
	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
