<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<form enctype="multipart/form-data" "action="" method="POST">
选择选课EXCEL文件<input name="xls" type="file" /><br /> 
<input name="OK" type="submit" value="提交" /><br />
<?php

if (isset($_POST['OK']))
    {
	$file=$_FILES['xls']['tmp_name'];
    getfile($file);
	}
function getfile($file1)
   {$conn=mysql_connect("202.194.15.142","root","SQL_0nlin3/192");//连接
	   $file1=$_FILES['xls']['tmp_name'];
   if($file1=="")
        {
	    $filetrue="请选择选课EXCEL文件";
	    echo "$filetrue";
	    return $filetrue;
	    }   
    $uploaddir= 'xls/';//上传文件
    $uploadefile=$uploaddir.basename($_FILES['xls']['name']);

    move_uploaded_file($_FILES['xls']['tmp_name'], $uploadefile);
    $uploaddir= 'xls/';
    $uploadefile=$uploaddir.basename($_FILES['xls']['name']);

    require_once("./Excel/reader.php");//加载所需类
    $data = new Spreadsheet_Excel_Reader();// 实例化
    $data->setOutputEncoding('UTF-8');//设置编码
    $data->read("$uploadefile");//read函数读取所需EXCEL表，支持中文
    mysql_query("set names 'UTF-8'");//设置编码输出
    mysql_select_db('ol7_cms'); //选择数据库
    for ($i = 2; $i <= $data->sheets[0]['numRows']; $i++) {
	//从EXCEL中读取数据
    $time=$data->sheets[0]['cells'][$i][11];
    $class_time=$data->sheets[0]['cells'][$i][10];
    $class_capacity=$data->sheets[0]['cells'][$i][9];
	//分离校区 楼 与 教室
    $j=$data->sheets[0]['cells'][$i][12];
    if($j){
          $compus_name=substr("$j",0,6);
switch($compus_name){
    case "兴隆";
	$campus_id=1;
	switch (substr($j,9,3)){
	case "群";
	$B=substr($j,-6,1);
	$room_id = substr($j,-6,1).substr($j,-4,3);
	break;
	case "实";
	$B=6;
	$room_id=substr($j,18,3);
	break;
	case "讲";
	$B=7;
	$room_id=substr($j,-4,3);
	break;
	default;
	continue;  
}
	break;
	case "中心";
	$campus_id=2;
	switch (substr($j,6,3)){
	
	case "理";
	$B=11;
	$room_id=substr($j,15,3);
	break;
	case "化";
	$B=12;
	$room_id=substr($j,-4,3);
	break;
	case "公";
	$B=13;
	$room_id=substr($j,-4,3);
	break;
	case "电";
	$B=14;
	$room_id=substr($j,-4,3);
	break;
	case "董";
	$B=15;
	$room_id=substr($j,-4,3);
	break;
	case "生";
	$B=16;
	$room_id=substr($j,15,3);
	break;
	case "微";
	  switch(substr($j,15,3)){
	  case "实";
	  $B=17;
	  $room_id=substr($j,-3);
	  break;
	  case "楼";
	  $B=20;
	  $room_id=substr($j,18,3);
	  break;}
	break;
	case "知";
	$B=18;
	$room_id=substr($j,15,1)."-".substr($j,19,3);
	break;
	case "数";
	$B=19;
	$room_id=substr($j,15,3);
	break;
	case "体";
	continue; 
	case "校";
	continue; 
}
	break;
	case "洪楼";
	$campus_id=3;
	switch (substr($j,6,3)){
	case "国";
	$B=21;
	$room_id=substr($j,-3);
	break;
	case "艺";
	$B=22;
	$room_id=substr($j,-3);
	break;	
	case "法";
	$B=23;
	$room_id=substr($j,-4,3);
	break;
	case "物";
	$B=24;
	$room_id=substr($j,-4,3);
	break;
	case "公";
	$B=27;
	$room_id=substr($j,-4,3);
	break;
	case "外";
	continue; 
	case "体";
	continue; 
	default;
	switch (substr($j,4,1)){
	case "3";
	$B=25;
	$room_id=substr($j,9,3);
	break;
	case "6";
	$B=26;
	$room_id=substr($j,-4,3);
	break;
	}
}
	break;
	case "软件";
	$campus_id=4;
	switch(substr($j,9,1)){
	case "1";
	$B=35;
	$room_id=substr($j,13,3);
	break;
	case "4";
	$B=36;
	$room_id=substr($j,13,3);
	break;
	case "5";
	$B=37;
	$room_id=substr($j,13,3);
	break;
}
	break;
	case "千佛";
	$campus_id=5;
	switch (substr($j,-1)){
	case "d";
	switch (substr($j,9,1)){
	    case"1";
		$B=43;
		$room_id=substr($j,-4,3);
		break;
		case"9";
		$B=41;
		$room_id=substr($j,-4,3);
		break;}
	break;
	default;
	$B=42;
	$room_id=substr($j,-3);
}
	break;
	case "趵突";
	$campus_id=6;
	switch (substr($j,12,3)){
	case"东";
	$B=45;
	$room_id=substr($j,-4,3);
	break;
	case"西";
	$B=46;
	$room_id=substr($j,-4,3);
	break;
	case"理";
	$B=49;
	$room_id=substr($j,-4,3);
	break;
	case"腔";
	  switch (substr($j,15,3))
	  {case "新";
	  $B=50;
	  $room_id=substr($j,-5,4);
	  break;
	  case "楼";
	  ontinue;} 
	break;
	case "体";
	continue; 
	case "口";
	continue;}
	switch (substr($j,9,1)){
	    case"2";
		$B=51;
		$room_id=substr($j,-4,3);
		break;
		case"8";
		$B=47;
		$room_id=substr($j,-4,3);
		break;
		case"9";
		$B=48;
		$room_id=substr($j,16,3);
		break;
	    default;
	    continue; 
	}
	break;
	default;
	continue; 
}
}
else{
	continue;
	}
	if(preg_match('/.*[-].*?[-].*/',$time))//分析课程时间
    {
    preg_match('/(.*?)[-].*?[-](.*?)\x{5468}/u',$time,$otime);
    $is_ord=0;}
    else{
    if(ereg("-",$time))
    {
    preg_match('/(.*?)[-](.*?)\x{5468}/u',$time,$otime);
    $is_ord=0;}
    else{
	    if(ereg(",",$time)){
        preg_match('/^(\d)[,].*?[,](\d\d)\x{5468}/u',$time,$otime);
        $is_ord=2-$otime[1]%2;}
        else{$ttime=substr("$time",0,6);
		switch($ttime){
			case "全周";
			$otime[1]=1;
			$otime[2]=17;
			$is_ord=0;
			break;
			case "单周";
			$otime[1]=1;
			$otime[2]=17;
			$is_ord=1;
			break;
			case "周上";
			continue;
			default;
	        preg_match('/(.*?)\x{5468}/u',$time,$otime);	
	        $otime[2]=$otime[1];
			break;}
	    }
	    }
	}
    //判断是否有重复的教室信息，没有则插入
    $sql="select * from zixishi_room where building_id='$B' and room_name='$room_id'";
    $res = mysql_query($sql);
    $rows=mysql_num_rows($res);
    if(!$rows)
    {
    $sql = "INSERT INTO zixishi_room(building_id,room_name) VALUES('$B','$room_id')";
    $insert = mysql_query($sql);
    }
	//读取教室的ID
    $queryl="select * from zixishi_room where building_id='$B' and room_name='$room_id'";
    $resultl=mysql_query($queryl);
    $row = mysql_fetch_array($resultl);
    $id=$row['id'];
	//判断是否有重复的课程信息，没有则插入
    $sql="select * from zixishi_class where room_id='$id' and class_time='$class_time' and week_period_start='$otime[1]'";
    $res = mysql_query($sql);
    $rows=mysql_num_rows($res);
    if(!$rows)
        {
        $sql = "INSERT INTO zixishi_class(room_id,class_time,class_capacity,week_period_start,week_period_end,is_ord,building_id) VALUES('$id','$class_time','$class_capacity','$otime[1]','$otime[2]','$is_ord','$B')";
        $insert = mysql_query($sql);
        }
    }
	if (unlink($uploadefile)) {
        echo "insert success";
         }
mysql_close();
}
?>