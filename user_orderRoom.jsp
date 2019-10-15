<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
  <% String loginUserName=(String)session.getAttribute("user_name"); %>

  <%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta charset="utf-8">
<meta name="description" content="">
<meta name="HandheldFriendly" content="True">
<meta name="MobileOptimized" content="320">
<meta name="viewport" content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>江畔农庄-orderRoom</title>
<script type="text/javascript">
    function check() {
 	   if(document.forms.myform.timein.value==""){
 		   alert("请入住日期！");
 		  document.forms.myform.timein.focus();
 		   return false;
 	   }
 	  if(document.forms.myform.timeout.value==""){
		   alert("请离开日期！");
		  document.forms.myform.timeout.focus();
		   return false;
	   }
 	  if(!isNumber(document.forms.myform.phone.value)){
		   alert("请输入正确的电话格式");
		  document.forms.myform.phone.focus();
		   return false;
	   }
 	
	function isNumber(number){
	 var re = new RegExp(/^\d{3,}$/);
	  return re.test(number);
   }
	
    }
</script>
<link rel="alternate" type="application/rss+xml" title="egrappler.com" href="feed/index.html">
<link href="http://fonts.googleapis.com/css?family=Raleway:700,300" rel="stylesheet"
        type="text/css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/prettify.css">
</head>
<body>
<nav>
  <div class="container">
    <h1>GUET</h1>
    <div id="menu">
      <ul class="toplinks">
        <li><a href="User_returnToIndex2.action">首页</a></li>
      	<li><a href="User_viewCart.action" class=" ">餐饮查询</a></li>
       <li><a href="User_viewRoom.action" class=" ">客房查询</a></li>
        <li><a href="User_viewvegetables.action" class=" ">特色农产品</a></li>
         <li><a href="User_logout.action"><%= loginUserName %>退出</a></li>
      </ul>
    </div>
    <a id="menu-toggle" href="#" class=" ">&#9776;</a> </div>
</nav>
<header>
  <div class="container">
    <h2 class="docs-header"> 客房预约 - 欢迎光临</h2>
  </div>
</header>
<section>
  <div class="container">
    <ul class="docs-nav" id="menu-left">
      <li><strong>客房服务</strong></li>
      <li><a href="User_orderRoom.action" class=" ">预定房间</a></li>
      <li><a href="User_queryOrder.action" class=" ">查询预订信息</a></li>
      <li><a href="User_historyRoom.action" class=" ">历史住房记录</a></li>
      <li class="separator"></li>
      <li><strong>餐饮服务</strong></li>
      <li><a href="User_viewTable.action" class=" ">餐饮预定</a></li>
      <li><a href="User_historyCart.action" class=" ">餐饮历史消费信息</a></li>
			<li class="separator"></li>
			<li><strong>特色农产品</strong></li>
			<li><a href="User_viewvegetables.action" class=" ">农产品购买</a></li>
			<li><a href="User_CountVeg.action" class=" ">农产品购物车</a></li>
			<li><a href="User_historyVeg.action" class=" ">农产品历史消费信息</a></li>
			 <li class="separator"></li>
			<li><strong>个人信息</strong></li>
			
			<li><a href="User_updateUser.action" class=" ">修改个人信息</a></li>
    </ul>
   
    <div class="docs-content">
			
      <h2> 客房服务</h2>
      <h3 id="welcome"> 欢迎使用</h3>
       <ul class="sys-info-list">
          <li>
              <label class="res-lab">1、</label><span class="res-info">本店有多种房型可供选择；</span>
          </li>
          <li>
              <label class="res-lab">2、</label><span class="res-info">点击预定按钮预定客房;</span>
          </li>
      </ul>
			      <h3 id="benefits"> 客房信息：</h3>
			
			<form action="User_queryOneTypeHomeAfter.action" method="post">
                    <table class="search-tab">
                       
                        <tr>
                                <th>入住日期：</th>
                                <td><input class="common-text" name="timein" size="50"  type="date" id="timein" min="2019-06-04" max="2019-06-20"></td>
                            </tr>
                                <tr>
                                <th>离开日期：</th>
                                <td><input class="common-text" name="timeout" size="50"  type="date" id="timeout" min="2019-06-05" max="2019-06-30"></td>
                            </tr>
                             <tr>
                                    
                            <td><input class="btn btn-primary btn2" name="sub" value="查询" type="submit" class="btn" onclick="return check(this)"></td>
                        </tr>
                    </table>
                </form>
			<div class="result-wrap">
             <form action="#" method="post" id="myform" name="myform">
                <div class="config-items">                   
                    <div class="result-content">
                        <table width="100%" class="insert-tab">
                         <tr style="background-color:#ccc">
									<td>房间号</td>
									<td>床位数</td>	
									
									<td>房间价格（元/晚）</td>	
									<td>房间状态</td>
									<td>操作</td>							
						 </tr>
						 <!-- 遍历开始 -->
						 <s:iterator value="#request.AllHome_list" var="AllHome">
								<tr class="list">
									<td><s:property value="#AllHome.roomNum" /></td>
                                    <td><s:property value="#AllHome.bedNum" /></td>
                                    <td><s:property value="#AllHome.roomprice" /></td>
									<td><s:property value="#AllHome.roomState" /></td>
									<td><a href="User_selectOrderRoom.action?roomNum=<s:property value="#AllHome.roomNum"/>" class="btn btn-primary btn2"  >预定</a></td>
								</tr>
						 </s:iterator>
						 <!-- 遍历结束 -->
                         </table>
                    </div>
                </div>              
            </form>
            <h3 id="license"> License</h3>
			<p> This farm housekeeper software is developed independently by Rong Yu. It takes two months to develop. 
			For detailed development instructions,
			please refer to the graduation design instructions of Guilin University of Electronic Science and Technology. 
			Contact telephone 18172645547.</p>
         </div>
    </div>
  </div>
</section>
<script src="js/jquery.min.js"></script> 
<script type="text/javascript" src="js/prettify/prettify.js"></script> 
<script src="https://google-code-prettify.googlecode.com/svn/loader/run_prettify.js?lang=css&skin=sunburst"></script>
<script src="js/layout.js"></script>
 <script src="js/jquery.localscroll-1.2.7.js" type="text/javascript"></script>
 <script src="js/jquery.scrollTo-1.4.3.1.js" type="text/javascript"></script>
</body>
</html>
