//初始化页面数据

	//加载服务之星排名
	$.ajax({
		url:"/zsh/top/query.do",
		type:"get",
		success:function(data){
			var tops = data.data;
			
			var ul1 ="<ul id='top1'>";
			var ul2 ="<ul id='top2'>";
			var ul3 ="<ul id='top3'>";
			var ul4 ="<ul id='top4'>";
			if(data.status==0){
				
			$.each(tops,function(i,field){
				if(field.topType==1){
					ul1 += "<li class='flex'>";
					if(field.employeeTop==1){
						ul1 +="<div class='flex-1 jmt_center'> " +
			  						"<img src='img/top1.png' > " +
			  					"</div>";
					}else
					if(field.employeeTop==2){
						ul1 +="<div class='flex-1 jmt_center'> " +
			  						"<img src='img/top2.png' > " +
			  					"</div>";
					}else
					if(field.employeeTop==3){
						ul1 +="<div class='flex-1 jmt_center'> " +
			  						"<img src='img/top3.png' > " +
			  					"</div>";
					}else{ul1 +="<div class='flex-1 jmt_center'> " +
						"<span class='top_number'>"+field.employeeTop+"</span> " +
						"</div> ";}
					
							ul1 += " <div class='flex-1 jmt_center'>"+
							          	"<img src='"+field.employeeHead+"' class='zsh_top_icon'>"+
							            "</div> " +
							  " <div class='flex-4'>"+field.employeeName+"</div></li>";
							
				}
				if(field.topType==2){
					ul2 += "<li class='flex'>";
					if(field.employeeTop==1){
						ul2 +="<div class='flex-1 jmt_center'> " +
			  						"<img src='img/top1.png' > " +
			  					"</div>";
					}else
					if(field.employeeTop==2){
						ul2 +="<div class='flex-1 jmt_center'> " +
			  						"<img src='img/top2.png' > " +
			  					"</div>";
					}else
					if(field.employeeTop==3){
						ul2 +="<div class='flex-1 jmt_center'> " +
			  						"<img src='img/top3.png' > " +
			  					"</div>";
					}else{ul2 +="<div class='flex-1 jmt_center'> " +
						"<span class='top_number'>"+field.employeeTop+"</span> " +
						"</div> ";}
					
					ul2 += " <div class='flex-1 jmt_center'>"+
							          	"<img src='"+field.employeeHead+"' class='zsh_top_icon'>"+
							            "</div> " +
							  " <div class='flex-4'>"+field.employeeName+"</div></li>";	
				}
				if(field.topType==3){
					ul3 += "<li class='flex'>";
					if(field.employeeTop==1){
						ul3 +="<div class='flex-1 jmt_center'> " +
			  						"<img src='img/top1.png' > " +
			  					"</div>";
					}else
					if(field.employeeTop==2){
						ul3 +="<div class='flex-1 jmt_center'> " +
			  						"<img src='img/top2.png' > " +
			  					"</div>";
					}else
					if(field.employeeTop==3){
						ul3 +="<div class='flex-1 jmt_center'> " +
			  						"<img src='img/top3.png' > " +
			  					"</div>";
					}else{ul3 +="<div class='flex-1 jmt_center'> " +
						"<span class='top_number'>"+field.employeeTop+"</span> " +
						"</div> ";}
					
					ul3 += " <div class='flex-1 jmt_center'>"+
							          	"<img src='"+field.employeeHead+"' class='zsh_top_icon'>"+
							            "</div> " +
							  " <div class='flex-4'>"+field.employeeName+"</div></li>";	
				}
				if(field.topType==4){
					ul4 += "<li class='flex'>";
					if(field.employeeTop==1){
						ul4 +="<div class='flex-1 jmt_center'> " +
			  						"<img src='img/top1.png' > " +
			  					"</div>";
					}else
					if(field.employeeTop==2){
						ul4 +="<div class='flex-1 jmt_center'> " +
			  						"<img src='img/top2.png' > " +
			  					"</div>";
					}else
					if(field.employeeTop==3){
						ul4 +="<div class='flex-1 jmt_center'> " +
			  						"<img src='img/top3.png' > " +
			  					"</div>";
					}else{ul4 +="<div class='flex-1 jmt_center'> " +
						"<span class='top_number'>"+field.employeeTop+"</span> " +
						"</div> ";}
					
					ul4 += " <div class='flex-1 jmt_center'>"+
							          	"<img src='"+field.employeeHead+"' class='zsh_top_icon'>"+
							            "</div> " +
							  " <div class='flex-4'>"+field.employeeName+"</div></li>";	
				}
			});
			ul1 +="</ul>";
			ul2 +="</ul>";
			ul3 +="</ul>";
			ul4 +="</ul>";
			$("#d1").append(ul1);
			$("#d1").append(ul2);
			$("#d1").append(ul3);
			$("#d1").append(ul4);
			$("#top1").show();
        	$("#top3").hide();
			$("#top2").hide();
			$("#top4").hide();
			}
			if(data.status==1){
				alert(data.describe);
			}
			if(data.status==2){
				window.location.href="/zsh/wc/oauth.do?redUrl=/zsh/top.html";
			}
			if(data.status==3){
				window.location.href="/zsh/fail.html"
			}
		} 
	});
//点击事件选择排名类型
	function selectTab(self){
		        $('.zsh_menu').find('li').attr('class','flex-1');
		        $(self).attr('class','flex-1 menu_selected');
		        var title = $(self).text();
		        if(title=="服务之星"){
		        	$("#top2").hide();
		        	$("#top1").show();
					$("#top3").hide();
					$("#top4").hide();
		        }
		        if(title=="销售之星"){
		        	$("#top1").hide();
		        	$("#top2").show();
					$("#top3").hide();
					$("#top4").hide();
		        }
		        if(title=="明日之星"){
		        	$("#top1").hide();
		        	$("#top3").show();
					$("#top2").hide();
					$("#top4").hide();
		        }
		        if(title=="其他"){
		        	$("#top1").hide();
					$("#top2").hide();
					$("#top3").hide();
					$("#top4").show();
		        }        	
	}
		
