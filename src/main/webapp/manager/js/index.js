

$(function () {
	//弹框关闭
	$(".close").on("click",function(){
		$(this).parent().parent().fadeOut(200);
	})
	
	//删除提示框打开
	$(".delete").on("click",function(){
		$(".hint").fadeIn(200);
	})
	
	//新增内容弹框
	$(".add").on("click",function(){
		$(".addDiv").fadeIn(200);
	})
	
	//修改内容弹框
	$(".compile").on("click",function(){
		$(".edit").fadeIn(200);
	})
	
	//高级搜索弹框
	$(".advanced").on("click",function(){
		$(".adv_search").fadeIn(200);
	})
	
	//check
	$(".adv_search").on("click","label",function(){
		if ($(this).siblings("input").is(':checked')) {
			$(this).siblings("input").prop("checked",false);
		} else{
			$(this).siblings("input").prop("checked",true);
			
		}
	})
	
	$(".staff").on("click","label",function(){
		if($(this).attr("id") != "checkbox"){
            if ($(this).siblings("input").is(':checked')) {
                $(this).siblings("input").prop("checked",false);
            } else{
                $(this).siblings("input").prop("checked",true);
            }
		}

	})
	
	//表格兼容性偶数行的背景色
	$("table tr:nth-child(odd)").css("background-color","#f4f4f4");
	$("table tr th").css("background-color","#fff");
			
	//分页  API:http://www.jq22.com/jquery-info9832
	/*$('#pageToolbar').Paging({pagesize:10,count:85,toolbar:false});
	$('#pageToolbar2').Paging({pagesize:10,count:85,toolbar:false});
	$('#pageToolbar3').Paging({pagesize:10,count:85,toolbar:false});*/
	
	//左侧导航栏js
	$(".main_left>ul>li>a").on("click",function(){
		$(this).parent().siblings().removeClass("active");
		$(this).parent().toggleClass("active");
		if($(this).parent().hasClass("active"))
		{
			$(".main_left li ul").slideUp();		//只打开一个二级目录
			$(this).parent().find("ul").slideDown();
		}else{
			$(this).parent().find("ul").slideUp();
		}
		
	});
	
	var nav_li = $(".main_left>ul>li");
	for(var i=0; i<nav_li.length; i++){
		if(nav_li.eq(i).children("ul").length>0)
		{
			nav_li.eq(i).addClass("dropdown");
		}
	}
	
	//订单页tab
	$('.tab li').on("click",function () {
		var index = $(this).index();
		$(this).addClass("cur").siblings('li').removeClass("cur");
		$('.page').eq(index).fadeIn().siblings('.page').hide();
	});
	
	//油价管理编辑/保存 input状态
	$('.revise').on("click",function () {
		$(this).parent().siblings().find("input").prop("disabled",false);
		$(this).parent().siblings().find("textarea").prop("disabled",false);
		$(this).hide().siblings(".save").show();
	});
	$('.save').on("click",function () {
		$(this).parent().siblings().find("input").prop("disabled",true);
		$(this).parent().siblings().find("textarea").prop("disabled",true);
		$(this).hide().siblings(".revise").show();
	});
	
	//客户管理-组织架构
	$(".structure>ul>li>a").on("click",function(){
		$(this).toggleClass("slideup");
		$(this).siblings("ul").slideToggle();
	});


});

function logout() {
    $.ajax({
        url: "/zsh/wc/logoutPC.do",
        type: "post",
        dataType: "json",
        success: function (data) {
            window.location.href="/zsh/manager/login.html";
        }
    })
}



