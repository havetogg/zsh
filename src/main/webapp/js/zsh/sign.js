
upImg();
//图片上传
function upImg(){
$(function(){
		 $('input[name="file"]').on('change', function () {
        if (typeof this.files == 'undefined') {
            return;
        }
        var img = this.files[0];//获取图片信息
        var type = img.type;//获取图片类型，判断使用
        var url = getObjectURL(this.files[0]);//使用自定义函数，获取图片本地url
        var fd = new FormData();//实例化表单，提交数据使用
        fd.append('file', img);//将img追加进去
        var date = new Date();
        $('.pic1').attr('src', url).show();//展示图片
        if (type.substr(0, 5) != 'image') {//无效的类型过滤
            //alert('非图片类型，无法上传！');
            return;
        }
		
    					
           	 //开始ajax请求，后台用的tp
        $.ajax({
            type: 'post',
            url: getRootPath() + '/savePic.do',
            data: fd,
            processData: false,  // tell jQuery not to process the data  ，这个是必须的，否则会报错
            contentType: false,   // tell jQuery not to set contentType
            dataType: 'text',
            xhr: function () {//这个是重点，获取到原始的xhr对象，进而绑定upload.onprogress
                var xhr = jQuery.ajaxSettings.xhr();
                xhr.upload.onprogress = function (ev) {
                    //这边开始计算百分比
                    var parcent = 0;
                    if (ev.lengthComputable) {
                        percent = 100 * ev.loaded / ev.total;
                        percent = parseFloat(percent).toFixed(2);
                        $('.bgpro').css('width', percent + '%').html(percent + '%');
                    }
                };
                return xhr;
            },
            success: function (data) {
                console.log(data);
                var res = JSON.parse(data);
                if (res.success) {
                   console.log(res);
		   $('#filePath').val(res.filePath);
console.log('p='+$('#filePath').val());

                }
            }
        });
       

    });
	});
	//自定义获取图片路径函数
    function getObjectURL(file) {
        var url = null;
        if (window.createObjectURL != undefined) { // basic
            url = window.createObjectURL(file);
        } else if (window.URL != undefined) { // mozilla(firefox)
            url = window.URL.createObjectURL(file);
        } else if (window.webkitURL != undefined) { // webkit or chrome
            url = window.webkitURL.createObjectURL(file);
        }
        return url;
    }
}