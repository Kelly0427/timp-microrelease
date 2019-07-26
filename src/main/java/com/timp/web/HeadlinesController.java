package com.timp.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timp.domain.Headlines;
import com.timp.service.HeadlinesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Headlines/db", tags = "微博头条文章", description = "用于对微博头条文章的增删改查操作")
public class HeadlinesController {
	@Autowired
	HeadlinesService headlinesService;
	/**
	 * 发布头条文章
	 * @param title：文章标题
	 * @param content：正文内容
	 * @param cover：文章封面图片地址
	 * @param summary：文章导语
	 * @param text：与其绑定短微博内容
	 * @return
	 */
	 @ApiOperation(value="发布头条文章",notes = "发布头条文章",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 @PostMapping("/headlines/sendHeadlines")
	 public Headlines sendHeadlines(Long id,String sendOperator) {
		 return headlinesService.sendHeadlines(id,sendOperator);
	 }
	 /**
	  * 微博驳回接口
	  * @param id
	  * @return
	  */
	 @PostMapping("/headlines/rejectHeadlines")
	 public Headlines rejectHeadlines(Long id,String rejectOperator) {
		 return headlinesService.rejectHeadlines(id, rejectOperator);
	 }
	 /**
	  * 查询所有内容
	  * @param pageable
	  * @param startTime
	  * @param endTime
	  * @return
	  */
	 @ApiOperation(value="展示所有的信息(分页)",notes = "向数据库查询所有",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 @GetMapping("/headlines/searchHeadlines")
	 public Page<Headlines> searchHeadlines(Pageable pageable,String startTime,String endTime){
		 return headlinesService.searchHeadlines(pageable, startTime, endTime);
	 }
	 /**
	  * 添加到数据库，未审阅
	  * @param headlines
	  * @return
	  */
	 @ApiOperation(value="添加/修改未审阅数据",notes = "向数据库添加/修改一条未审阅信息",produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response =Headlines.class)
	 @PostMapping("/headlines/addHeadlines")
	 public Headlines addHeadlines(Headlines headlines) {
		 return headlinesService.addHeadlines(headlines);
	 }
	 /**
	  * 查询发布的记录
	  * @param pageable
	  * @param startTime
	  * @param endTime
	  * @return
	  */
	 @ApiOperation(value="展示发布的信息",notes = "向数据库查询发布的信息",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 @GetMapping("/headlines/queryCheckInfo")
	 Page<Object> queryCheckInfo(Pageable pageable,String startTime,String endTime){
		 return headlinesService.queryCheckInfo(pageable, startTime, endTime);
	 }
	 /**
	  * 查询未发布的记录
	  * @param pageable
	  * @param startTime
	  * @param endTime
	  * @return
	  */
	 @ApiOperation(value="展示未发布的信息",notes = "向数据库查询未发布的信息",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 @GetMapping("/headlines/queryUnCheckInfo")
	 Page<Object> queryUnCheckInfo(Pageable pageable,String startTime,String endTime){
		 return headlinesService.queryUnCheckInfo(pageable, startTime, endTime);
	 }
	 /**
	  * 展示所有的信息
	  * @return
	  */
	 @ApiOperation(value="展示所有的信息",notes = "向数据库查询所有的信息",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 @GetMapping("/headlines/queryHeadlinesList")
	 public List<Headlines> queryHeadlinesList(){
		 return headlinesService.queryHeadlinesList();
	 }
	 /**
	  * 删除头条文章
	  * @param ids
	  * @return
	  */
	 @ApiOperation(value="删除头条文章",notes = "删除一条或多条未发布的头条文章",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 @DeleteMapping("/headlines/deleteHeadlines")
	 int deleteHeadlines(Long[] ids) {
		 return headlinesService.deleteHeadlines(ids);
	 }
	 /**
	  * 保存并发布
	  * @param headlines
	  * @return
	  */
	 @GetMapping("/headlines/sendAndSave")
	 public Headlines sendAndSave(Headlines headlines) {
		 return headlinesService.sendAndSave(headlines);
	 }
}
