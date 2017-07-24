$(function () {
	checkLogin();
})

function checkLogin(){
    $.ajax({
        url:"/zsh/wc/checkWxLoginInfo.do",
        type:"get",
        success:function(data){
            if(data.status == 0&&data.data == 1||data.status == 0&&data.data == "1"){
                $.ajax({
                    url: "/zsh/customer/query.do",
                    type: "get",
                    success: function (data) {
                        if (data.status == 0) {
                            var customer = data.data[0];
                            if (customer.hobby == "" || customer.birthday == "" || customer.hobby == null || customer.birthday == null) {
                                alert("请完善个人信息");
                                window.location.href = "/zsh/supplement.html";
                            }
                            if(customer.oilPush == "0"||customer.oilPush == 0){
                                init();
							}else{
                                window.location.href="/zsh/fail.html";
							}
                        }
                    }
                });
            }else{
                window.location.href="/zsh/fail.html";
            }
        }
	})
}

//初始化页面数据
function init(){
	$.ajax({
		url:"/zsh/oil/query.do",
		type:"get",
		success:function(data){
			if(data.status==0){
				var customerOilsArr = data.data.customerPrice;
                var internationalOilsArr = data.data.internationalPrice;
                var benchmarkOilArr = data.data.benchmarkList;
                var customerHtml = "<tr>";
                $.each(customerOilsArr,function (i,item) {
                    if(i==customerOilsArr.length-1){

                    } else {
                        customerHtml+="<td>"+(item.oilPrice+benchmarkOilArr[i].oilPrice)+"</td>";
					}
                })
                customerHtml+="</tr>";
                var internationalHtml = "<tr>";
                $.each(internationalOilsArr,function (i,item) {
                	if(i==internationalOilsArr.length-1){

					} else if(i==internationalOilsArr.length-2){
                        internationalHtml+="<td>"+item.oilPrice+"%</td>";
                    }else{
						if(i==0){
							$("#remarks").append(item.remarks);
						}else if(i == 1){
							if(item.remarks == ""||item.remarks == "null"||item.remarks == undefined||item.remarks == null){

							}else{
								$("#notes").html("注:"+item.remarks);
							}
						}
                        internationalHtml+="<td>"+item.oilPrice+"</td>";
					}
                })
                internationalHtml+="</tr>";
				$("#tbody1").append(customerHtml);
				$("#tbody2").append(internationalHtml);
			}
			if(data.status==1){
				alert(data.describe);
			}
			if(data.status==2){
				window.location.href="/zsh/wc/oauth.do?redUrl=/zsh/todayPrice.html";
			}
			if(data.status==3){
				window.location.href="/zsh/fail.html";
			}
		}
	});
}