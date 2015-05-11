<?
//error_reporting(0);
//@session_start();	 
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
$table_room="zixishi_room";

$db  = new DB;

$html_id = $_REQUEST["html_id"];
if (!$html_id) echo "No html_id revieved!";
//else echo "html_id=".$html_id;
$html_data = $_REQUEST["html_data"];

switch ($html_id)
{
	//例子：从zixishi_compus表中选出$html_data数据为任意的所有字段，并将id字段数据作为 option 标签的value输出，将compus_name字段作为 option的text输出
	case "#campuse":	
	{
		$db->query("select * from ".$table_compus." order by `id` asc;");
		$out="";
		$n=0;
		while($nut=$db->fetchArray())
		{	
			$out.="<option value=\"".$nut['id']."\">".$nut["compus_name"]."</option>\n";
		}
		echo iconv("gbk", "UTF-8", $out);
		break;
	}
	
	//从zixishi_weeks表中选出$is_this_year为1的所有数据，返回weeks字段数值
	case "#weeks":	
	{
		$db->query("select * from ".$table_weeks." where is_this_year = 1");
		$nut=$db->fetchArray();
		$out=$nut['weeks'];
		echo iconv("gbk", "UTF-8", $out);	
		break;		
	}
	
	//从zixishi_weeks表中选出$is_this_year为1的first_monday，并以此为基准返回当前是第几周
	case "#week_now":	
	{
		$db->query("select * from ".$table_weeks." where is_this_year = 1");
		$nut=$db->fetchArray();
		$first_day=$nut['first_monday'];
		$date_1 = date('Y-m-d',time()-8*60*60);
 		$date_2= $first_day;
    	$date1_arr = explode("-",$date_1);
    	$date2_arr = explode("-",$date_2);
    	$day1 = mktime(0,0,0,$date1_arr[1],$date1_arr[2],$date1_arr[0]);
    	$day2 = mktime(0,0,0,$date2_arr[1],$date2_arr[2],$date2_arr[0]);
		$first_day_weekday=date("w", $day2); 
		if($first_day_weekday!='0'){
			$first_week_left=7-$first_day_weekday;
		}else{
			$first_week_left=0;
		}
    	$days = ceil(($day1-$day2)/3600/24-$first_week_left);								
		$week_now=ceil($days/7)+1;
		if($week_now>$nut['weeks']){$week_now=$nut['weeks'];}//判断当前周数与weeks的大小，大于当前学期周次的话统一设为最后一周
		if($week_now <1) $week_now=1;
		$out=$week_now;
	
		echo iconv("gbk", "UTF-8", $out);	
		break;		
	}
	
	//从zixishi_weeks表中选出$is_this_year为1的所有数据，返回从第一周到本学期的最后一周的option，其中第n周，数字n作为option的value，“第n周”作为option的text，week_now作为默认的option
	case "#week_list":	
	{
 		$db->query("select * from ".$table_weeks." where is_this_year = 1");
		$out="";
		$n=1;
		$nut=$db->fetchArray();		
		
		$first_day=$nut['first_monday'];
		date_default_timezone_set('PRC');
		$date_1 = date('Y-m-d',time()-8*60*60);
		$week_day = date('w',time()-8*60*60);	//当前星期
		$date_time = date('H:i',time()-8*60*60);	//当前时刻
 		$date_2 = $first_day;
    	$date1_arr = explode("-",$date_1);
    	$date2_arr = explode("-",$date_2);
    	$day1 = mktime(0,0,0,$date1_arr[1],$date1_arr[2],$date1_arr[0]);
    	$day2 = mktime(0,0,0,$date2_arr[1],$date2_arr[2],$date2_arr[0]);
    	$first_day_weekday = date("w", $day2); 
		if($first_day_weekday!='0'){
			$first_week_left = 7-$first_day_weekday;
		}else{
			$first_week_left = 0;
		}
    	$days = ceil(($day1-$day2)/3600/24-$first_week_left);								
		$week_now = ceil($days/7)+1;
		if(($date_time>'22:00' and $week_day==6) or $week_day==0)			//如果当前是周六或者周日，并且时间大于22:00，默认选择下一周
		{
			if($week_now>$nut['weeks']){$week_now = $nut['weeks'];}//判断当前周数与weeks的大小，大于当前学期周次的话统一设为最后一周
			while($n<=$nut['weeks'])
			{	
				if($week_now<$nut['weeks'])			//当前不是最后一周，则默认选择下一周
				{
					if ($n!=$week_now+1)
					{
						$out.="<option value=\"".$n."\" >"."第".$n."周"."</option>\n";
					}else{ 
						$out.="<option value=\"".$n."\" selected=\"".$n."\" >"."第".$n."周"."</option>\n";
					}
				}else{								//如果当前是最后一周，则默认选择最后一周
					if ($n!=$week_now)
					{
						$out.="<option value=\"".$n."\" >"."第".$n."周"."</option>\n";
					}else{ 
						$out.="<option value=\"".$n."\" selected=\"".$week_now."\" >"."第".$week_now."周"."</option>\n";
					}
				}
				$n++;
			}
		}else{				//当前不是周六和时间大于22：00且不是周日，则默认选择当前周次
			if($week_now>$nut['weeks']){$week_now=$nut['weeks'];}//判断当前周数与weeks的大小，大于当前学期周次的话统一设为最后一周
			while($n<=$nut['weeks'])
			{	
				if ($n!=$week_now)
				{
					$out.="<option value=\"".$n."\" >"."第".$n."周"."</option>\n";
				}else{ 
					$out.="<option value=\"".$n."\" selected=\"".$week_now."\" >"."第".$week_now."周"."</option>\n";
				}
			$n++;
			}
		}
 		echo $out;
		break;	
	}
	
	//获取兴隆山全部教室，且第一个option为-1全部
	case "#room_init":	
	{
		$db->query("select * from ".$table_room." where building_id<6 order by `room_name` asc;");
		$out="";
		$n=0;
		$out.="<option value=\""."-1"."\" selected=\""."-1"."\" >"."全部"."</option>\n";
		while($nut=$db->fetchArray())
		{	if($nut['room_name']!="" and $nut['room_name']!='0'){$out.="<option value=\"".$nut['id']."\">".iconv("gbk", "UTF-8", $nut['room_name'])."</option>\n";}
		}
 		echo $out;
		break;											
	}
	//列出周一到周六，select当前周几
	case "#weekday":	
	{
		$out="";
		$n=1;
//		date_default_timezone_set('PRC');
		$week_day = date('w',time()-8*60*60);
		$date_time=date('H:i',time()-8*60*60);	//当前时刻
//		if($week_day==0){$week_day=1;}				//若为周日，显示周一
		$week_day_ar=array("日","一","二","三","四","五","六"); 
		if($date_time<='22:00'){
			if($week_day==0){$week_day=1;}				//若为周日，显示周一
			while($n<=6)
			{	
				if ($n!=$week_day)
				{
					$out.="<option value=\"".$n."\" >"."星期".$week_day_ar[$n]."</option>\n";
				}else{
					$out.="<option value=\"".$n."\" selected=\"".$week_now."\" >"."星期".$week_day_ar[$n]."</option>\n";
				}
				$n++;
			}
		}else{
			if($week_day!=6 and $week_day!=0 )
			{
				while($n<=6)
				{	
					if ($n!=$week_day)
					{
						$out.="<option value=\"".$n."\" >"."星期".$week_day_ar[$n]."</option>\n";
					}else{
						$out.="<option value=\"".$n."\" selected=\"".$week_now."\" >"."星期".$week_day_ar[$n+1]."</option>\n";
					}
					$n++;		
				}
			}else{
				while($n<=6)
				{	
					if ($n!=1)
					{
						$out.="<option value=\"".$n."\" >"."星期".$week_day_ar[$n]."</option>\n";
					}else{
						$out.="<option value=\"".'1'."\" selected=\"".$week_now."\" >"."星期".$week_day_ar[1]."</option>\n";
					}
					$n++;		
				}
			}
		}
		echo $out;
		break;					
	}
	//列出各时段，select当前时段的下一个时段
	case "#interval":	
	{
		$out="";
		$n=0;
		date_default_timezone_set('PRC');
		$date_today = date('m-d');		//当前月份
		$date_time=date('H:i',time()-8*60*60);	//当前时刻
//		$date_time=date('H:i');		
		$date_summer='05-01';			//夏季作息开始时间
		$date_winter='10-01';			//夏季作息结束时间
		$time_table_arr=array("全天","上午","第一节","第二节","下午","第三节","第四节","第五节"); 
//		echo $date_time."<p>";
		if($date_summer<=$date_today and $date_today<$date_winter)
		{
//			echo "夏作息"."<p>";//输出夏季作息
			switch($date_time){
				case  $date_time<'10:00';
//				echo "第一节"."<p>";
				$date_time_id ='2';
				break;
				case $date_time>='10:10' and $date_time<='12:00';
//				echo "第二节"."<p>";
				$date_time_id ='3';
				break;
				case $date_time>'12:00' and $date_time <'14:00';
//				echo "中午"."<p>";
				$date_time_id ='4';
				break;
				case $date_time>='14:00' and $date_time<'16:00';
//				echo "第三节"."<p>";
				$date_time_id ='5';
				break;
				case $date_time>='16:00' and $date_time<'17:50';
//				echo "第四节"."<p>";
				$date_time_id ='6';
				break;
				case $date_time>='17:50' and $date_time<'22:00';
//				echo "第五节"."<p>";
				$date_time_id ='7';
				break;
				case $date_time>='22:00';
				$date_time_id ='8';
				break;
				}		
		}else{
//			echo "冬季作息"."<p>";//输出冬季作息
			switch($date_time){
				case $date_time<'10:00';
//				echo "第一节"."<p>";
				$date_time_id ='2';
				break;
				case $date_time>='10:00'and $date_time<='12:00' ;//
//				echo "第二节"."<p>";
				$date_time_id ='3';
				break;
				case $date_time>'12:00' and $date_time <'13:30';
//				echo "中午"."<p>";
				$date_time_id ='4';
				break;
				case $date_time>='13:30' and $date_time<'15:30';
//				echo "第三节"."<p>";
				$date_time_id ='5';
				break;
				case $date_time>='15:30' and $date_time<'17:20';
//				echo "第四节"."<p>";
				$date_time_id ='6';
				break;
				case $date_time>='17:20'and $date_time<'22:00';
//				echo "第五节"."<p>";
				$date_time_id ='7';
				break;
				case $date_time>='22:00';
				$date_time_id ='8';
				break;
				}	
		}
		if($date_time_id <'7')				//当前节次为一至四节，默认选择下一个节次
		{
			while($n<=7)
			{
				if($n == 7)
				$out.="<option value=\"7\" >晚上</option>\n";				
				if ($n!=$date_time_id+1)
				{
					if($n==0)
					{
						$out.="<option value=\""."-1"."\" >".$time_table_arr[$n]."</option>\n";
					}
					else
					{
						$out.="<option value=\"".$n."\" >".$time_table_arr[$n]."</option>\n";
					}
				}
				else
				{
					$out.="<option value=\"".$n."\" selected=\"selected\" >".$time_table_arr[$n]."</option>\n";
				}
				$n++;
			}
		}
		if($date_time_id =='7')			//当前时段为第五节，并且时间小于22:00，默认选中当前节次
		{
			while($n<=7)
			{
				if($n == 7)
				$out.="<option value=\"7\" >晚上</option>\n";				
				if ($n!=$date_time_id)
				{
					if($n==0)
					{
						$out.="<option value=\""."-1"."\" >".$time_table_arr[$n]."</option>\n";
					}
					else
					{
						$out.="<option value=\"".$n."\" >".$time_table_arr[$n]."</option>\n";
					}
				}
				else
				{
					$out.="<option value=\"".$n."\" selected=\"selected\" >".$time_table_arr[$n]."</option>\n";
				}
				$n++;
			}
		}
		if($date_time_id =='8')			//当前时段为第五节，并且时间小于22:00，默认选中当前节次
		{
			while($n<=7)
			{
				if($n == 7)
				$out.="<option value=\"7\" >晚上</option>\n";				
				if ($n!=1)
				{
					if($n==0)
					{
						$out.="<option value=\""."-1"."\" >".$time_table_arr[$n]."</option>\n";
					}
					else
					{
						$out.="<option value=\"".$n."\" >".$time_table_arr[$n]."</option>\n";
					}
				}
				else
				{
					$out.="<option value=\"".$n."\" selected=\"selected\" >".$time_table_arr[$n]."</option>\n";
				}
				$n++;
			}
		}
		echo $out;
		break;						
	}

	//从zixishi_building表中选出compus_id=$html_data的所有字段，并将id字段数据作为 option 标签的value输出，将building_name字段作为 option的text输出
	case "#building":	
	{
        $db->query("select * from ".$table_building." where compus_id=$html_data order by `id` asc;");
		$out="";
		$n=0;
		if($html_data==1){$out.="<option value=\"-1\">兴隆山教学楼群</option>\n";}
		while($nut=$db->fetchArray())
		{	
			$out.="<option value=\"".$nut['id']."\">".iconv("gbk", "UTF-8", $nut['building_name'])."</option>\n";
		}	
		echo $out;
		break;						
	}
	
	//从zixishi_room表中选出building_id=$html_data的所有字段，并将id字段数据作为 option 标签的value输出，将room_name字段作为 option的text输出
	case "#room":	
	{
		if($html_data == -1)
			$db->query("select * from ".$table_room." where building_id<6 order by `room_name` asc;");
        else $db->query("select * from ".$table_room." where building_id=$html_data order by `room_name` asc;");
		$out="<option value=\"-1\">全部</option>\n";
		$n=0;
		while($nut=$db->fetchArray())
		{	
			if($nut['room_name']!="" and $nut['room_name']!='0')
			{$out.="<option value=\"".$nut['id']."\">".iconv("gbk", "UTF-8", $nut['room_name'])."</option>\n";}
		}
		echo $out;
		break;					
	}
}
@$db->closeDB();
?>