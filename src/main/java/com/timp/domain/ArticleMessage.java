package com.timp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 图文消息表
 * @author Maria
 *
 */
@Entity
@Table(name="articlemessage")
public class ArticleMessage {
	@Id @GeneratedValue
	@Column(name="id")
	protected Long id;
	//图文消息作者
	@Column
	private String author;
	//图文消息页面的内容
	@Column(columnDefinition="mediumtext")
	private String content;
	//点击图文消息跳转链接
	@Column
	private String content_source_url;
	//图文消息的描述
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
	@Column
	private String addOperator;//添加人员
	@Column
	private String sendOperator;//审阅(发布)人员
	@Column
	private String sendTime;//操作日期
	@Column
	private String status;//是否发布到微信公众号--待审核=未发布&已审核=发布
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
