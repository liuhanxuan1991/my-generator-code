function copyHtml(selector) {
    var ele = document.getElementById(selector);
    if (window.getSelection) {
        var selection = window.getSelection();
        var range = document.createRange();
        range.selectNodeContents(ele);
        selection.removeAllRanges();
        selection.addRange(range);
    }
    document.execCommand("copy", false, null);
    selection.removeAllRanges();
}

function isEmpty(str) {
    if(str == null || str.length == 0) {
        return true;
    }
    return false;
}

/**
 *弹出提示框
 * @param msg 要显示的消息
 * @param icon 图标样式 格式为数字 默认是1
 * @param time 提示停留的时间 单位是毫秒 默认为1700（经过测试1700ms感觉不错）
 */
function msg(msg,icon,time) {
    if(time==null){
        time = 1700;
    }
    if (icon==null){
        icon = 1;
    }
    parent.layer.msg(msg, {
        icon: icon,
        time: time //（如果不配置，默认是3秒）
    });
}

/**
 * 时间转换
 *
 * @param date传入的日期
 * @returns {String} 返回字符串格式日期
 */
function formatTime(date) {

    if (date == null || date == "" || date == undefined) {
        return "";
    }

    var now = new Date(date);

    var year = now.getFullYear();

    var month = now.getMonth() + 1;
    if (month < 10) {
        month = "0" + month;
    }

    var day = now.getDate();

    if (day < 10) {
        day = "0" + day;
    }

    var hour = now.getHours();

    if (hour < 10) {
        hour = "0" + hour;
    }

    var minute = now.getMinutes();

    if (minute < 10) {
        minute = "0" + minute;
    }

    var second = now.getSeconds();

    if (second < 10) {
        second = "0" + second;
    }

    return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
}

function getUrlParms(name){
	   var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	   var r = window.location.search.substr(1).match(reg);
	   if(r!=null)
	   return unescape(r[2]);
	   return null;
	   }
