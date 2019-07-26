var code=getQueryString("code");
console.log(code);
//获取地址看的参数(name为传入的参数)
function getQueryString(name) {                                       
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
    //截取地址栏参数值
    var r = window.location.search.substr(1).match(reg);
    //判断获取到的参数值是否为空
    if (r != null) {
    	return unescape(r[2]); 
    }else{
    	return null;
    }						
}
//获取授权微博唯一凭证
function getAccesstoken(){
	$.ajax({
		url:"/wBTokenState/getCode",
	    type:'get',
	    data:{
	    	//将地址栏获取到的code值传给后台
	    	"code":code
	    },
	    success:function(res){
	    	console.log(res);
	    },
	    error:function(res){
	    	console.log(res);
	    }	
	});
}
if(code!=null){
	getAccesstoken();
	break;
}
headline();
function headline(){
	$.ajax({  
		url:"/headlines/sendHeadlines",
	    type:'get',
	    data:{
	    	"title":"这里是标题",
	    	"content":"这里是文章内容",
	    	"cover":"https://www.baidu.com/img/bd_logo1.png",
	    	"summary":"",
	    	"text":"这里是text"
	    },
	    success:function(res){
	    	console.log(res);
	    },
	    error:function(res){
	    	window.location.href="https://api.weibo.com/oauth2/authorize?client_id=2865329630&redirect_uri=https://www.hbsyzn.com"
	    }	
	})
}