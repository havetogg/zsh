<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>文件上传</title>
</head>
<body>
	<form id="myForm" method="post" enctype="multipart/form-data" action="/zsh/excel/upload">
		选择文件<input type="file" name="excelFile">
		<input type="button" value="上传" id="submit">
	</form>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-3.1.0.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-form.js"></script>
	<script type="text/javascript">
		$("#submit").click(function(){
			$("#myForm").ajaxSubmit({
				type:"POST",
				url:"/zsh/excel/upload",
				dataType:"json",
				success:function(data){
					if(data.status==0){
						alert(123);
					}
					if(data.status==1){
						alert(data.describe)
					}
				}
			})
		});
	</script>
</body>
</html>