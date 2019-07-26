package com.timp.domain;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.timp.tool.HelperUtils;
import com.timp.tool.WechatTokenUtil;


public class ArticleMessageRepositoryImpl implements ArticleMessageRepositoryCustom{
	@PersistenceContext
	private EntityManager em;
	@Autowired
	OpenIdRepository openIdRepository;
	@Autowired
	WechatTokenUtil tokenUtil;
	@Autowired
	ArticleMessageRepository articleMessageRepository;
	@Autowired
	ArticleRecordRepository articleRecordRepository;
	private static Logger logger = LoggerFactory.getLogger(ArticleMessageRepositoryImpl.class);	
	public static final String upload_media_url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	public static final String upload_news_url="https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
	public static final String send_news_url="https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
	private static String imgResult=null;
	private static String imgTextResult=null;
	/**
	 * 上传图文消息内的图片获取media_id(类型区分大小写：Image)
	 */
	@Override
	public String upload(String filePath, String fileType) {
		String access_token=tokenUtil.checkAccessToken();
		//图片路径
		File file=new File(filePath);
		if(!file.exists() || !file.isFile()) {
			System.out.println("上传的文件不符合要求");
		}
		String urlString = upload_media_url.replace("ACCESS_TOKEN", access_token).replace("TYPE", fileType);
		URL url;
		HttpURLConnection conn;
		StringBuffer buffer = null;
        BufferedReader reader;
		try {
			url = new URL(urlString);			
			try {
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true);
		        conn.setDoOutput(true);
		        conn.setUseCaches(false);
		        conn.setRequestProperty("Connection", "Keep-Alive");
		        conn.setRequestProperty("Charset", "UTF-8");
		        // 设置边界
		        String BOUNDARY = "----------" + System.currentTimeMillis();
		        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary="+ BOUNDARY);
		        //请求正文信息
				StringBuilder sb = new StringBuilder();
				sb.append("--"); // ////////必须多两道线
		        sb.append(BOUNDARY);
		        sb.append("\r\n");
		        sb.append("Content-Disposition: form-data;name=\"file\";filename=\""+ file.getName() + "\"\r\n");
		        sb.append("Content-Type:application/octet-stream\r\n\r\n");
		        byte[] head = sb.toString().getBytes("utf-8");
		        // 获得输出流
		        OutputStream out = new DataOutputStream(conn.getOutputStream());
		        out.write(head);
		        // 文件正文部分
		        DataInputStream in = new DataInputStream(new FileInputStream(file));
		        int bytes = 0;
		        byte[] bufferOut = new byte[1024];
		        while ((bytes = in.read(bufferOut)) != -1) {
		            out.write(bufferOut, 0, bytes);
		        }
		        in.close();
		        // 结尾部分
		        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
		        out.write(foot);
		        out.flush();
		        out.close();
		        // 定义BufferedReader输入流来读取URL的响应
	            buffer = new StringBuffer();
	            reader = new BufferedReader(new InputStreamReader(
	                    conn.getInputStream()));
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                buffer.append(line);
	            }
	            reader.close();
	            conn.disconnect();	      
			} catch (IOException e) {
				logger.error("发送POST请求出现异常！" + e);
				e.printStackTrace();
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}	
		JSONObject data = (JSONObject) JSON.parse(buffer.toString());
        return data.getString("media_id");
	}
	/**
	 * 上传图文消息素材获取素材id
	 */
	@Override
	public String sendArticle(ArticleMessage article,String meidaId) {
		article.setThumb_media_id(meidaId);
		String access_token=tokenUtil.checkAccessToken();
		String url= upload_news_url.replace("ACCESS_TOKEN", access_token);
		List list=new ArrayList<>();
		list.add(article);
		JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(list));
		//存放参数的json对象
		JSONObject params = new JSONObject();
		//粉丝
		params.put("articles", jsonArray);
		//异步请求
		AsyncRestTemplate restTemplate = new AsyncRestTemplate();
		//设置请求头
		HttpHeaders headers=new HttpHeaders();
		//设置编码格式
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		//组装
		HttpEntity<String> entity = new HttpEntity<String>(params.toJSONString(), headers);
		//请求开始
	    ListenableFuture<ResponseEntity<String>> forEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	    //回调
	  	forEntity.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
	  		@Override
	  		public void onSuccess(ResponseEntity<String> result) {		
	  			logger.info(String.format("回调Post请求 %s 成功，返回值为%s", url, result.getBody()));
	  			String strBody=result.getBody().toString();
	  			if(strBody != null) {
	  				JSONObject data = (JSONObject) JSON.parse(strBody);
	  				imgResult=data.getString("media_id");
	  				System.out.println(data);
	  			}else {
	  				System.out.println("null");
	  			}	
	  		}
	  		@Override
	  		public void onFailure(Throwable ex) {
	  			logger.error(String.format("回调Post请求 %s 失败，%s", url, ex.getMessage()));
	  		}			
	  	});	
	    
	    return imgResult;
	}
	/**
	 * 群发图文消息
	 */
	@Override
	public String sendNews(String mediaId,int send_ignore_reprint,ArticleMessage article) {
		String access_token=tokenUtil.checkAccessToken();
		if(mediaId != null && mediaId.length()>0) {
			String url= send_news_url.replace("ACCESS_TOKEN", access_token);
			//获取粉丝列表
//			List<OpenId> lists=openIdRepository.findAll();
			//异步请求
			AsyncRestTemplate restTemplate = new AsyncRestTemplate();
			//设置请求头
			HttpHeaders headers=new HttpHeaders();
			//设置编码格式
			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//			String str=null;
//			if(lists.size()>0) {
//				str=lists.get(lists.size()-1).getOpenId();
//			}else {
//			    str=lists.get(0).getOpenId();
//				OpenId opens=openIdRepository.searchOpenIdList();
//				str=opens.getOpenId();
//			}
		    String str="[\"ojdOvuPu-whxzl84Iesdeuox8kZU\",\"ojdOvuG-P3K2yhO3aBwHtJ33_viI\",\"ojdOvuKvm2USciZIVV74QJfcF_pU\",\"ojdOvuJpfpaSi5C-hk4Xj4RZrSBY\",\"ojdOvuEDFza9At8RpO1Fo6vcoZFs\",\"ojdOvuEBO9ORRM43TW3cLvJ0P8kI\",]";
			//去掉开头结尾（[]）后的值
			String touser=str.substring(1, str.length()-1);
			//以逗号分隔取单个值放到数组中
			String[] strArr=touser.split(",");
			//用来存放openid的集合（传参格式需要）
			List list=new ArrayList<>();
			if(strArr.length>0) {
				for(int i=0;i<strArr.length;i++) {
					//去掉开头结尾的双引号
					list.add(strArr[i].substring(1, strArr[i].length()-1));
				}
			}else {
				list.add(strArr[0].substring(1,strArr[0].length()-1 ));
			}
			//将openid集合转换为json数组
			JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(list));
			//存放参数的json对象
			JSONObject params = new JSONObject();
			//粉丝
			params.put("touser", jsonArray);
			//图文素材id
			JSONObject paramsTemp = new JSONObject();
			paramsTemp.put("media_id", mediaId);
			params.put("mpnews", paramsTemp);
			//消息类型
			params.put("msgtype", "mpnews");
			//图文消息被判定为转载时，是否继续群发。 1为继续群发（转载），0为停止群发。 该参数默认为0
			if(send_ignore_reprint != 0) {
				params.put("send_ignore_reprint", 1);
			}else {
				params.put("send_ignore_reprint", 0);
			}
			//组装
			HttpEntity<String> entity = new HttpEntity<String>(params.toJSONString(), headers);
			//请求开始
		    ListenableFuture<ResponseEntity<String>> forEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		    //回调
			forEntity.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
				@Override
				public void onSuccess(ResponseEntity<String> result) {		
					logger.info(String.format("回调Post请求 %s 成功，返回值为%s", url, result.getBody()));
					String strBody=result.getBody().toString();
					if(strBody != null) {
						JSONObject data = (JSONObject) JSON.parse(strBody);
						imgTextResult=data.getString("media_id");//消息id
						String title=article.getTitle();//标题
						ArticleRecord articleRecord=new ArticleRecord();
						articleRecord.setTitle(title);
						articleRecord.setMsgid(imgTextResult);
						articleRecordRepository.save(articleRecord);
						articleMessageRepository.save(article);
						System.out.println(data);
					}else {
						System.out.println("null");
					}	
				}
				@Override
				public void onFailure(Throwable ex) {
					logger.error(String.format("回调Post请求 %s 失败，%s", url, ex.getMessage()));
				}			
			});	
		}
		return "imgTextResult";
	}
	//展示未发布的信息
	@Override
	public Page<Object> queryUncheckInfo(Pageable pageable, String startTime, String endTime) {
		String sql="SELECT * FROM articlemessage WHERE `status`='待审核'";
		if(startTime !=null && startTime.length()>0) {
			sql+=" AND send_time >="+"'"+startTime+"'";
		}
		if(startTime != null && startTime.length()>0) {
			sql+=" AND send_time <="+"'"+endTime+"'";
		}
		sql+=" ORDER BY id DESC";
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Object> query = builder.createQuery(Object.class);
		Query qPage = em.createNativeQuery(sql);
		List<Object> objects=qPage.getResultList();
		Iterator<Object> iterator = objects.iterator();
		List<Object> resultList = new ArrayList<>();
		while(iterator.hasNext()) {
			Object[] row = (Object[]) iterator.next();
			Map<String, Object> map = new HashMap<>();
			map.put("id",  row[0]==null?null:row[0]);
			map.put("author", row[1]==null?null:row[1]);
			map.put("content", row[2]==null?null:row[2]);
			map.put("content_source_url", row[3]==null?null:row[3]);
			map.put("digest", row[4]==null?null:row[4]);
			map.put("needOpenComment", row[5]==null?null:row[5]);
			map.put("onlyFansCanComment", row[6]==null?null:row[6]);
			map.put("showCoverPic", row[7]==null?null:row[7]);
			map.put("thumbMediaId", row[8]==null?null:row[8]);
			map.put("title", row[9]==null?null:row[9]);
			map.put("addOperator", row[10]==null?null:row[10]);
			map.put("sendOperator", row[11]==null?null:row[11]);
			map.put("sendTime", row[12]==null?null:row[12]);
			resultList.add(map);
		}
		return HelperUtils.getPageList(pageable, resultList);
	}
	//展示发布的信息
	@Override
	public Page<Object> queryCheckInfo(Pageable pageable, String startTime, String endTime) {
		String sql="SELECT * FROM articlemessage WHERE `status`='已审核'";
		if(startTime !=null && startTime.length()>0) {
			sql+=" AND send_time >="+"'"+startTime+"'";
		}
		if(startTime != null && startTime.length()>0) {
			sql+=" AND send_time <="+"'"+endTime+"'";
		}
		sql+=" ORDER BY id DESC";
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Object> query = builder.createQuery(Object.class);
		Query qPage = em.createNativeQuery(sql);
		List<Object> objects=qPage.getResultList();
		Iterator<Object> iterator = objects.iterator();
		List<Object> resultList = new ArrayList<>();
		while(iterator.hasNext()) {
			Object[] row = (Object[]) iterator.next();
			Map<String, Object> map = new HashMap<>();
			map.put("id",  row[0]==null?null:row[0]);
			map.put("author", row[1]==null?null:row[1]);
			map.put("content", row[2]==null?null:row[2]);
			map.put("content_source_url", row[3]==null?null:row[3]);
			map.put("digest", row[4]==null?null:row[4]);
			map.put("needOpenComment", row[5]==null?null:row[5]);
			map.put("onlyFansCanComment", row[6]==null?null:row[6]);
			map.put("showCoverPic", row[7]==null?null:row[7]);
			map.put("thumbMediaId", row[8]==null?null:row[8]);
			map.put("title", row[9]==null?null:row[9]);
			map.put("addOperator", row[10]==null?null:row[10]);
			map.put("sendOperator", row[11]==null?null:row[11]);
			map.put("sendTime", row[12]==null?null:row[12]);
			resultList.add(map);
		}
		return HelperUtils.getPageList(pageable, resultList);
	}
	//展示所有的内容
	@Override
	public List<ArticleMessage> queryArticleList() {
		List<Predicate> predicatesList = new ArrayList<Predicate>();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Object> query = builder.createQuery(Object.class);
		Root<ArticleMessage> root = query.from(ArticleMessage.class);
		query.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
		long total = (long) em.createQuery(query.select(builder.count(root))).getSingleResult();
		query.orderBy(builder.desc(root.get("id")));
		Query qPage = em.createQuery(query.select(root));
		return qPage.getResultList();
	}
}
