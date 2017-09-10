
function checkMessage(form) {

    if(form.name.value.length < 5){
        alert("请输入完整的名字!");
        console.log(form.name.value);
        return false;
    }

    return true;

}
function startTime() {
    var today = new Date();
    var h = today.getHours();
    var m = today.getMinutes();
    var s = today.getSeconds();
    m = checkTime(m);
    s = checkTime(s);
    document.getElementById("time").innerHTML="time:"+h+":"+m+":"+s;
    t=setTimeout('startTime()',500);
}

function checkTime(i) {
    if(i<10) {
        i = "0" + i;
    }
    return i;

}
//AJAX 请求message
$(document).ready(function () {
    $("#MessageButton").click(function () {
         $.ajax(
            {
                url:"/message",
                type:"GET",
                dataType:"json",
                // async:false,
                success:function (data) {
                    console.log(data.length);
                    $("#allMessage").children().remove();
                    for(var i = 0;i<data.length;i++) {
                        $("#allMessage").append("<textarea class=\"form-control\" rows=\"4\"  readonly=\"readonly\">" + data[i].message + "</textarea>");
                        // $("#showMessage").html(data[0].message);
                    }
                },
                error:function (errorData) {
                    alert(errorData.responseText);
                }
            }
        )

    });
//AJAX 无刷新上传文件
    $("#file").change(function () {
        /*var formData = new FormData($("#fileUpload")[0]);
        var file = $("#file")[0];*/
        let fileUpload = $("#fileUpload")[0];
        let fileName = $("#file").val();

        if(fileName.length>0){
            fileUpLoad();
        }
        else {
            alert("请选择文件！");
        }
        console.log(fileName);
        // console.log(formData);


        function fileUpLoad() {
            $.ajax(
                {
                    url:"/fileLoad",
                    type: "POST",
                    cache: false,
                    data: new FormData(fileUpload),
                    processData: false,
                    contentType: false,
                    success: function (response) {
                        var fileCtx = $('#uploadCtx');
                        var data = eval(response);
                        console.log(data);

                        fileCtx.empty();
                        //判定是否为图片文件
                        if (data.length===1){
                         let fileUpLoadDiv = $('#fileUpLoadDiv');
                         let pic = fileUpLoadDiv.find('> img');
                         pic.remove();
                         fileUpLoadDiv.append(`<img src="/images/upLoadFile/${data[0]}"/>`);
                        }else {
                            for (let i = 0; i < data.length; i++) {

                                fileCtx.append(`<textarea class="form-control" rows=2  readonly="readonly"> ${data[i]} </textarea>`);
                            }
                        }
                    },
                    error: function (response) {
                        alert(response);
                    }
                }
            );
        }
    });

});

