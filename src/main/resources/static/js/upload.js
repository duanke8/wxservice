function fileCountCheck(filesObj, minFileNum, maxFileNum) {
    if (window.File && window.FileList) {
        var fileCount = filesObj.files.length;
        if (fileCount < minFileNum || fileCount > maxFileNum) {
            // 不符合数量的处理
            alert('文件数不能小于' + minFileNum + '个，也不能超过' + maxFileNum + '个，你选择了' + fileCount + '个');
            var file = document.getElementById('file');
            file.value = ''; //虽然file的value值不能设为有内容的字符，但是可以设置为空字符
            return false;
        } else {
            // 符合数量的处理
            return true;
        }
    } else {
        window.alert('抱歉，你的浏览器不支持FileAPI，请升级浏览器！');
        return false;
    }
}

