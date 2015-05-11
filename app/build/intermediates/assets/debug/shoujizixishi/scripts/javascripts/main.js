// JavaScript Document by Lyndon 2013-2-6 20:42:31
$(document).ready(function() {
	
	//$("#weeks").append(result);
	//获取本学期总周数
	$.post("http://202.194.15.193/shoujizixishi/getmenu.php",{html_id:"#weeks",html_data:''},function(result)
	{
		//alert (result);
		$("#weeks").empty();
		$("#weeks").append(result);
	});
	//获取当前周次(数字)
	$.post("http://202.194.15.193/shoujizixishi/getmenu.php",{html_id:"#week_now",html_data:''},function(result)
	{
		//alert (result);
		$("#week_now").empty();
		$("#week_now").append(result);
	});
	//获取本学期各周次
	$.post("http://202.194.15.193/shoujizixishi/getmenu.php",{html_id:"#week_list",html_data:''},function(result)
	{
		//alert (result);
		$("#week_list").empty();
		$("#week_list").append(result);
	});
	//获取校区信息
	$.post("http://202.194.15.193/shoujizixishi/getmenu.php",{html_id:"#campuse",html_data:''},function(result)
	{
		//alert (result);
		$("#campuse").empty();
		$("#campuse").append(result);
	});
	//获取兴隆山全部教室，且第一个option为-1全部
	$.post("http://202.194.15.193/shoujizixishi/getmenu.php",{html_id:"#room_init",html_data:''},function(result)
	{
		//alert (result);
		$("#room").empty();
		$("#room").append(result);
	});
	//获取周几列表
	$.post("http://202.194.15.193/shoujizixishi/getmenu.php",{html_id:"#weekday",html_data:''},function(result)
	{
		//alert (result);
		$("#weekday").empty();
		$("#weekday").append(result);
	});
	//获取当前时段的下一个时段
	$.post("http://202.194.15.193/shoujizixishi/getmenu.php",{html_id:"#interval",html_data:''},function(result)
	{
		//alert (result);
		$("#interval").empty();
		$("#interval").append(result);
	});

	
	//获取楼宇信息
	$('#campuse').live('change', function() {
		//alert ($('#campuse').val());
		$.post("http://202.194.15.193/shoujizixishi/getmenu.php",{html_id:"#building",html_data:$('#campuse').val()},function(result)
		{
			//alert (result);
			$("#building").empty();
			$("#building").append(result);
			$('#building').attr("selected","selected");
			$('#building').trigger("change");
		});
	});
	//获取教室信息
	$('#building').live('change', function() {
		$.post("http://202.194.15.193/shoujizixishi/getmenu.php",{html_id:"#room",html_data:$('#building').val()},function(result)
		{
			//alert (result);
			$("#room").empty();
			$("#room").append(result);
			if($('#by_methord_div').attr("title") == 'by_room')
			{
				$("#room option[value='-1']").attr("selected", false);
				$("#room").get(0).selectedIndex=1;	
			}
			if($('#room').val() == -1)
			{
				 $.post("http://202.194.15.193/shoujizixishi/getmenu.php",{html_id:"#interval",html_data:''},function(result)
				{
					//alert (result);
					$("#interval").empty();
					$("#interval").append(result);
				});
			}
		});
	});
	//查询教室还是查询楼层
	$('#room').live('change', function() {
		
		if($('#room').val() != -1)
		{
			 $("#interval option[value='-1']").attr("selected", true);
			// alert($('#by_methord').val());
		}
		else
		{
			if($('#by_methord_div').attr("title") == 'by_room')
			{
				$("#room option[value='-1']").attr("selected", false);
				$("#room").get(0).selectedIndex=1;	
			}
			
			$.post("http://202.194.15.193/shoujizixishi/getmenu.php",{html_id:"#interval",html_data:''},function(result)
			{
				//alert (result);
				$("#interval").empty();
				$("#interval").append(result);
			});
			
		}
		
	});
	//查询整栋楼不得查询一整天时间
	$('#interval').live('change', function() {
		if($('#room').val() == -1 && $('#interval').val() == -1)
		{
			alert('不选择特定教室（查询整栋楼），不能选择查询一整天的空课时间！');	
			$.post("http://202.194.15.193/shoujizixishi/getmenu.php",{html_id:"#interval",html_data:''},function(result)
			{
				//alert (result);
				$("#interval").empty();
				$("#interval").append(result);
			});
		} 
		
	});
	//查询
	$('#query').live('click', function() {
		$.post("http://202.194.15.193/shoujizixishi/getresult.php",$("form").serialize(),function(result)
		{
			//alert (result);
			$("#result").empty();
			$("#result").append(result);
		});
	});
	//查询方式  打开结果面板
	$('#query').live('click', function() {
		$('#result_out').fadeIn('slow');
		$(".button_outer").animate({marginTop:'10px'});
		$.post("http://202.194.15.193/shoujizixishi/getresult.php",$("form").serialize(),function(result)
		{
			//alert (result);
			$("#result").empty();
			$("#result").append(result);
		});
	});
	//按教室查询
	$('#by_room').live('click', function() {
		$("#result").empty();
		$('#by_room').css("text-decoration","underline");
		$('#by_time').css("text-decoration","none");
		$('#by_room').css("color","#010101");
		$('#by_time').css("color","#4d4e4e");
		$('#result').css("text-align","center");
		$('table').css("align","center");  //table居中
		$('.sub_menu_right').hide();
		$('.sub_menu_left').hide();
		$('#result_out').fadeOut('slow');
		$('.sub_menu').animate({height:'140px'});
		$("#by_methord_div").empty();
		$("#by_methord_div").append('<input name="by_methord" value="by_room" type="hidden" >');
		$('#by_methord_div').attr('title','by_room');
		$("#room ").get(0).selectedIndex=1;	
	});
	//按时间查询（默认）
	$('#by_time').live('click', function() {
		$("#result").empty();
		$('#by_room').css("text-decoration","none");
		$('#by_time').css("text-decoration","underline");
		$('#by_room').css("color","#4d4e4e");
		$('#by_time').css("color","#010101");
		$('#result_out').fadeOut('slow');
		$('.sub_menu').animate({height:'270px'});
		$('.sub_menu_right').show();
		$('.sub_menu_left').show();
		$("#by_methord_div").empty();
		$('#by_methord_div').attr('title','by_time');
		$("#by_methord_div").append('<input name="by_methord" value="by_time" type="hidden" >');	
	});
	//返回，退出结果
	$('#back_button').click(function(){
		$('#result_out').fadeOut('slow');
		$(".button_outer").animate({marginTop:'30px'});
	});
});