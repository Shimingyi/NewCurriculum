<?

//echo "<br><br><br>_POST";
//print_r ($_POST);
//echo "<br><br><br>_REQUEST";
//print_r ($_REQUEST);
//echo "<br><br><br>_SERVER";
//print_r ($_SERVER);
//echo "<br><br><br>_SESSION";
//print_r ($_SESSION);
//echo "<br><br><br>_ENV";
//print_r ($_ENV);
//echo "<br><br><br>_COOKIE";
//print_r ($_COOKIE);
require_once("../cms/class/error.class.php");
require_once("../cms/class/datalink.class.php");
require_once("../cms/class/database.class.php");
require_once("../cms/class/config.inc.php");
require_once("../cms/class/function.inc.php");
require_once("../cms/class/session.class.php");

$table_class="zixishi_class";
$table_building="zixishi_building";
$table_compus="zixishi_compus";
$table_weeks="zixishi_weeks";

$db  = new DB;
$db1  = new DB;
$db2  = new DB;
$db3  = new DB;
$db4  = new DB;
$db5  = new DB;


//[week_list] => 12 [campuse] => 1 [building] => -1 [room] => -1 [weekday] => 4 [interval] => 4 ) 

$week_list = $_REQUEST["week_list"];
$campuse = $_REQUEST["campuse"];
$building = $_REQUEST["building"];
$room = $_REQUEST["room"];
$weekday = $_REQUEST["weekday"];
$interval = $_REQUEST["interval"];
$by_methord = $_REQUEST["by_methord"];



switch ($_REQUEST["interval"])
{
case "-1":
  $interval_case = "zixishi_class.class_time=".$weekday."1 or zixishi_class.class_time=".$weekday."2 or zixishi_class.class_time=".$weekday."3 or zixishi_class.class_time=".$weekday."4 or zixishi_class.class_time=".$weekday."5";
  $tips_hasclass = " 全天 部分时间或全部时间有课! =_=";
  $tips_noclass = " 全天 完全空闲！^_^";
  break;  
case "1":
  $interval_case = "zixishi_class.class_time=".$weekday."1 or zixishi_class.class_time=".$weekday."2";
  $tips_hasclass = " 上午 部分时间或全部时间有课! =_=";
  $tips_noclass = " 上午 完全空闲！^_^";
  break;
case "2":
  $interval_case = "zixishi_class.class_time=".$weekday."1";
  $tips_hasclass = " 在该时段（第一大节）有课! =_=";
  $tips_noclass = " 在该时段（第一大节）空闲！^_^";
  break;  
case "3":
  $interval_case = "zixishi_class.class_time=".$weekday."2";
  $tips_hasclass = " 在该时段（第二大节）有课! =_=";
  $tips_noclass = " 在该时段（第二大节）空闲！^_^";
  break;  
case "4":
  $interval_case = "zixishi_class.class_time=".$weekday."3 or zixishi_class.class_time=".$weekday."4";
  $tips_hasclass = " 下午 部分时间或全部时间有课! =_=";
  $tips_noclass = " 下午 完全空闲！^_^";
  break;
case "5":
  $interval_case = "zixishi_class.class_time=".$weekday."3";
  $tips_hasclass = " 在该时段（第三大节）有课! =_=";
  $tips_noclass = " 在该时段（第三大节）空闲！^_^";
  break;    
case "6":
  $interval_case ="zixishi_class.class_time=".$weekday."4";
  $tips_hasclass = " 在该时段（第四大节）有课! =_=";
  $tips_noclass = " 在该时段（第四大节）空闲！^_^";
  break; 
case "7":
  $interval_case = "zixishi_class.class_time=".$weekday."5";
  $tips_hasclass = " 在该时段（第五大节 晚上）有课! =_=";
  $tips_noclass = " 在该时段（第五大节 晚上）空闲！^_^";
  break; 

}
//print_r ($_REQUEST);
//echo "<br>interval_case=$interval_case<br>";

if (!($week_list&&$campuse&&$building&&$room&&$weekday&&$interval&&$by_methord))echo "some data missing!";

//查询某个教室，建议时段为-1，也可是别的
if ($room != -1 && $by_methord == "by_time")
{
	$out="";
	$query=$db1->query("select * from zixishi_class where room_id=".$room." and ($interval_case) and week_period_start<=$week_list and week_period_end>=$week_list order by `id` asc;");
	//若没有上述课程条目，则该教室为空教室        ============还差获取教室名字getone==================
	if(!$db1->numRows($query)) 
	$out.=" ".$query['room_name'].$tips_noclass;
	else $out.=" ".$query['room_name'].$tips_hasclass;
	echo $out;
}
//查询整栋楼的特定时间
else if ($room == -1 && $interval>0 )
{
	if($interval<1) 
	{echo iconv("gbk", "UTF-8", "不选择特定教室（查询整栋楼），不能选择查询一整天的空课时间！");exit();}
	
	$style = "<h style=\"color:#39858E;font-weight:bold\">";
	
	if($building == -1)
	{
	$out1.=$style;
	$out2.=$style;
	$out1.="兴隆山教学楼群</h> <h style=\"color:#39858E;\">在此时段的空教室有：<br></h>\n";
	$out2.="兴隆山教学楼群</h> <h style=\"color:#39858E;\">在此时段没有空教室 =_=</h>";
	$out="";
	}
	else
	{
		$db->query("select * from zixishi_building where id=$building limit 1");
		$out1="";
		$out2="";
		$out="";
		while($nut=$db->fetchArray())
		{	
			$out1.=$style;
			$out2.=$style;
			$out1.=iconv("gbk", "UTF-8", $nut['building_name'])."</h> <h style=\"color:#39858E;\">在此时段的空教室有：<br></h>\n";
			$out2.=iconv("gbk", "UTF-8", $nut['building_name'])."</h> <h style=\"color:#39858E;\">在此时段没有空教室 =_=</h>";
		}
	}	
	
	//选出该楼层所有自习室		
	if($building == -1)
	$db->query("select * from zixishi_room where building_id<6 order by `room_name` asc;");
	else
	$db->query("select * from zixishi_room where building_id=$building order by `room_name` asc;");
	$n=0;
	$thousand = 2000;
	while($nut=$db->fetchArray())
	{	//选出符合教室号的，任意符合时间的，又在课程有效期内的
		$query=$db1->query("select * from zixishi_class where room_id=".$nut['id']." and ($interval_case) and week_period_start<=$week_list and week_period_end>=$week_list order by `id` asc;");
		//若没有上述课程条目，则该教室为空教室
		if(!$db1->numRows($query) &&$nut['room_name']!="" &&$nut['room_name']!=0 &&$nut['room_name']!="0" &&!empty($nut['room_name'])) 
		{
			//兴隆山楼群分楼显示
			if($nut['room_name']>$thousand && $building == -1 )
			{
			$out.="<br>";
			$thousand=$thousand+1000;
			}
			$out.=" ".$nut['room_name']." ";	
			$n++;		
		}
	}
	if($n) 
	{$out1.=$out;echo $out1;}
	else echo $out2;
	
		
}
else if ($by_methord == "by_room")
{	
	$out="";
	$week_array=array(
		"1"=>array("1"=>"0","2"=>"0","3"=>"0","4"=>"0","5"=>"0","6"=>"0"),
		"2"=>array("1"=>"0","2"=>"0","3"=>"0","4"=>"0","5"=>"0","6"=>"0"),
		"3"=>array("1"=>"0","2"=>"0","3"=>"0","4"=>"0","5"=>"0","6"=>"0"),
		"4"=>array("1"=>"0","2"=>"0","3"=>"0","4"=>"0","5"=>"0","6"=>"0"),
		"5"=>array("1"=>"0","2"=>"0","3"=>"0","4"=>"0","5"=>"0","6"=>"0"),
			 
	);
	//选出该楼层所有自习室
	$db->query("select * from zixishi_class where room_id=$room order by `class_time` asc;");
	$n=0;
	while($nut=$db->fetchArray())
	{
		//若class_time存在，则该时段为有课教室
		$time_value=$nut["class_time"];
		$week_array[$time_value%10][($time_value-$time_value%10)/10] = 1;
		
	}
	$out.="<table>";
	
	
	for($k =1;$k<6;$k++)
	{
		$out.="<TR>";
		//if($k =1) $out.="<td bgcolor=\"\"><p align=\"center\"><strong>第".$k."节</strong></p></td>";
		//显示周次
		if($k == 1)
			for($j =1;$j<8;$j++)
			{
				if($j==2)
				$out.="<td bgcolor=\"\"><p align=\"center\" style=\"color: #39858E;\">周一</p></td>";
				else if($j==3)
				$out.="<td bgcolor=\"\"><p align=\"center\" style=\"color: #39858E;\">周二</p></td>";
				else if($j==4)
				$out.="<td bgcolor=\"\"><p align=\"center\" style=\"color: #39858E;\">周三</p></td>";
				else if($j==5)
				$out.="<td bgcolor=\"\"><p align=\"center\" style=\"color: #39858E;\">周四</p></td>";
				else if($j==6)
				$out.="<td bgcolor=\"\"><p align=\"center\" style=\"color: #39858E;\">周五</p></td>";
				else if($j==7)
				$out.="<td bgcolor=\"\"><p align=\"center\" style=\"color: #39858E;\">周六</p></td>";
				else $out.="<td bgcolor=\"\"><p align=\"center\" style=\"color: #39858E;\">周次</p></td>";
			}
		$out.="</TR>";
		$out.="<td bgcolor=\"\"><p align=\"center\" style=\"color: #39858E;\">第".$k."大节</p></td>";
		for($j =1;$j<7;$j++)
		{
			
			if($week_array[$k][$j] ==1)
			$out.="<td bgcolor=\"\"><p align=\"center\" style=\"color: #5F5E61;\">---</p></td>";
			else
			$out.="<td bgcolor=\"\"><p align=\"center\" style=\"color: #5F5E61;\">无课</p></td>";
		}
		$out.="</TR>";	
	}
	$out.="</table>";
	echo $out;

	//print_r($week_array);
	//echo "by_methord == by_room";
	
}

@$db->closeDB();
@$db1->closeDB();
@$db2->closeDB();
@$db3->closeDB();
@$db4->closeDB();
@$db5->closeDB();
?>