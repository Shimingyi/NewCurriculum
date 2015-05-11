<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>自习室查询前端</title>
<script src="scripts/javascripts/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="scripts/javascripts/main.js" type="text/javascript"></script>
</head>

<body>
<div>
<a id="by_time" href="#">按时段查询</a> <a id="by_room" href="#">按教室查询</a>
<form action="" method="post">
<div id="by_methord_div" title="by_time">
<input name="by_methord" value="by_time" type="hidden">
</div>
本学期共<strong> <span id="weeks" ></span> </strong>周，当前周次第 <strong><span id="week_now" > </span></strong> 周   
<br />

要查询 校区<select name="campuse" id="campuse">
<!--<option value="1">兴隆山</option>
<option value="2">中心</option>
<option value="3">洪家楼</option>
<option value="4">软件园</option>
<option value="5">千佛山</option>
<option value="6">趵突泉</option>-->
</select>

楼宇<select name="building" id="building">
<option value="-1">兴隆山教学楼群</option>
<option value="1">1号楼</option>
<option value="2">2号楼</option>
<option value="3">3号楼</option>
<option value="4">4号楼</option>
<option value="5">5号楼</option>
<option value="6">兴隆山实验楼</option>
<option value="7">兴隆山讲学堂</option>
</select>

教室<select name="room" id="room">
<option value="-1">全部</option>
</select><br /> 
<select name="week_list" id="week_list">
<!--<option value="1">第一周</option>
<option value="2">第二周</option>
<option value="3">第三周</option>
<option value="4">第四周</option>
<option value="5">第五周</option>
<option value="6">第六周</option>-->
</select>
<span id="time_div">
时间<select name="weekday" id="weekday">
<!--<option value="1">周一</option>
<option value="2">周二</option>
<option value="3">周三</option>
<option value="4">周四</option>
<option value="5">周五</option>
<option value="6">周六</option>-->
</select>
时段<select name="interval" id="interval">
<!--<option value="-1">全天</option>
<option value="1">上午</option>
<option value="2">第一大节</option>
<option value="3">第二大节</option>
<option value="4">下午</option>
<option value="5">第三大节</option>
<option value="6">第四大节</option>
<option value="7">晚上</option>
<option value="7">第五大节</option>-->
</select>
</span>
无课教室
</form>
<button id="query">查询</button>
</div>
<div id="result"></div>
</body>
</html>