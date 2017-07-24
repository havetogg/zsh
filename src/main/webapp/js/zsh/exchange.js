//初始化页面数据
$.ajax({
	url:"/zsh/customer/query.do",
	type:"get",
	success:function(data){
		if(data.status==0){
			var customer = data.data;
			$("#integer").text(customer[0].customerIntegral);
		}
		if(data.status==2){
			window.location.href="/zsh/wc/oauth.do?redUrl=/zsh/exchange.html";
		}
		if(data.status==3){
			window.location.href="/zsh/exchange.html";
		}
	}
});

$(function(){
	$(".change").click(function(){
		var name = $(this).parents(".goods_text").find("h2").text();
		var jf = $(this).find("span").text();
		$.ajax({
			url:"/zsh/exchange/add.do",
			type:"post",
			data:{"exchangeName":name,"exchangeInteger":jf},
			dataType:"json",
			success:function(data){
				if(data.status==0){
					location.reload();
				}
				if(data.status==1){
					alert(data.describe);
				}
			}
		});
	});
})