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
                error:function (XMLresponse) {
                    alert(XMLresponse.responseText);
                }
            }
        )

    })

});