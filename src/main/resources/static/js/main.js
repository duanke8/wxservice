// index列表页面模块跳转
function Jump_module(obj) {
    var iframes = $("#oiframes");
    console.log(obj);
    if ($(obj).html().indexOf("视频上传") != -1) {
        iframes.attr("src", "../html/videoUpload.html");
    } else if ($(obj).html().indexOf("图片上传") != -1) {
        iframes.attr("src", "../html/pictureUpload.html");
    } else if ($(obj).html().indexOf("上传记录") != -1) {
        iframes.attr("src", "../html/uploadRecord.html");
    } else if ($(obj).html().indexOf("图片查看") != -1) {
        iframes.attr("src", "../html/tpck.html");
    } else if ($(obj).html().indexOf("[退出]") != -1) {
        Ewin.confirm({
            message: "确认要退出当前系统吗？"
        }).on(function (e) {
            if (!e) {
                return;
            } else {
                loginData({
                    type: "TUICHU",
                    callback: "fanhui"
                }, false, "/index_json.jsp");
                window.location.href = "../html/login.html";
            }
        })
    }
}

// iframes自适应高度
function changeFrameHeight() {
    var ifm = document.getElementById("oiframes");
    ifm.height = document.documentElement.clientHeight - 56;
}

window.onresize = function () {
    changeFrameHeight();
}
$(function () {
    changeFrameHeight();
});



