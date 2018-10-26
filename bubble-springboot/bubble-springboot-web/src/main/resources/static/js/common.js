/**
 * 扩展jquery
 * Created by yanmingkun on 2017/5/19.
 */
$(function () {

    /**
     * 扩展
     */
    $.extend({
        remind: function (options) {
            var _msg = '<div style="width:100%;">';
            if (options.icon != undefined) {
                _msg += '<div class="messager-icon messager-' + options.icon + '"></div>';
            }
            if (options.msg != undefined) {
                _msg += '<div style="word-break : break-all;">' + options.msg + '</div>';
            }
            _msg += '</div>';
            options.msg = _msg;
            $.messager.show(options);
        }
    });

    /**
     * 拓展panel、window、dialog onMove 拖动效果
     * @type {easyuiPanelOnMove}
     */
    $.fn.panel.defaults.onMove = easyuiPanelOnMove;
    $.fn.window.defaults.onMove = easyuiPanelOnMove;
    $.fn.dialog.defaults.onMove = easyuiPanelOnMove;

    /**
     * 只可以输入数字
     */
    $(".number").keydown(function () {
        var e = $(this).event || window.event;
        if (e == undefined) {
            return false;
        }
        // console.debug(e);
        var code = Number(e.keyCode);
        if ((code >= 48 && code <= 57) || (code >= 96 && code <= 105) || code == 8) {
            return true;
        } else {
            return false;
        }
    });

    /**
     * 只可以输入字母
     */
    $(".alphabet").keydown(function () {
        var e = $(this).event || window.event;
        if (e == undefined) {
            return false;
        }
        // console.debug(e);
        var code = Number(e.keyCode);
        if (!(code >= 65 && code <= 90) || code == 8) {
            return false;
        }
    });
});

/**
 * 解决easyui window,pannel,dialog拖动到body外面无法拉回的问题
 *
 * @param left 到左边的距离
 * @param top 到上面的距离
 */
function easyuiPanelOnMove(left, top) {
    var parentObj = $(this).panel('panel').parent();
    if (left < 0) {
        $(this).window('move', {
            left: 1
        });
    }
    if (top < 0) {
        $(this).window('move', {
            top: 1
        });
    }
    var width = $(this).panel('options').width;
    var height = $(this).panel('options').height;
    var parentWidth = parentObj.width();
    var parentHeight = parentObj.height();
    if (parentObj.css("overflow") == "hidden") {
        if (left > parentWidth - width) {
            $(this).window('move', {
                "left": parentWidth - width
            });
        }
        if (top > parentHeight - height) {
            $(this).window('move', {
                "top": parentHeight - height
            });
        }
    }
};

/**
 * 方法的作用为对于提醒类的信息 展示几秒后 提示框自动消失
 * title:窗体的头 icon:提示内容的图标 info 成功 warning 警告 error 错误 msg: 提示内容 type:窗体的消失方式
 * fade 和slide time:
 */
var toastr = function (title, icon, msg, type, time) {
    $.remind({
        title: title,
        icon: icon,
        msg: msg,
        showType: type,
        timeout: time,
        style: {
            right: '',
            bottom: ''
        }
    });
};

/**
 * 判断字符串是否为空 true为空 false反之 此方法去掉了前后中间空格
 *
 * @param str
 * @returns {boolean}
 */
function isStrNull(str) {
    if (str.replace(/(^s*)|(s*$)/g, "").length == 0) {
        return true;
    } else {
        return false;
    }
};

/**
 * 时间对象的格式化
 *
 * @param format
 * @returns {*}
 */
Date.prototype.format = function (format) {
    /*
     * eg:format="yyyy-MM-dd hh:mm:ss";
     */
    var o = {
        "M+": this.getMonth() + 1, // month
        "d+": this.getDate(), // day
        "h+": this.getHours(), // hour
        "m+": this.getMinutes(), // minute
        "s+": this.getSeconds(), // second
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds()
        // millisecond
    }

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
            - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};


/**
 * 根据pattern格式化时间，yyyy-MM-dd hh:mm:ss
 *
 * @param timeStr
 * @param pattern
 * @returns {*}
 */
function forTimeByPattern(timeStr, pattern) {
    if (value) {
        var date = new Date(value);
        return date.format(pattern);
    }
    return "";
};

/**
 * 获取当前日期，默认格式："yyyy-MM-dd hh:mm:ss"
 *
 * @param pattern
 * @returns {*}
 */
function currentDate(pattern) {
    if (pattern) {
        return new Date().format(pattern);
    }
    return new Date().format("yyyy-MM-dd hh:mm:ss");
};

/**
 * 获取服务器时间，格式：yyyy-MM-dd
 *
 * @returns {*}
 */
function getServDate() {
    var servDate;
    $.ajax({
        type: "OPTIONS",
        url: "/",
        async: false,
        complete: function (x) {
            var Currdate = new Date(x.getResponseHeader("Date"));
            servDate = Currdate.format("yyyy-MM-dd")
        }
    });
    return servDate;
};

/**
 * 将表单转为json对象
 */
$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
/**
 * 将josn对象赋值给form
 * @method setForm
 * */
$.fn.setForm = function (jsonValue) {
    var obj = this;
    $.each(jsonValue, function (name, ival) {
        if (ival !== null) {
            var $oinput = obj.find("[name=" + name + "]");
            if ($oinput.length != 0) {
                if ($oinput[0].tagName == 'INPUT') {
                    if ($oinput.attr("type") == "checkbox") {
                        var checkboxObj = $("[name=" + name + "]");
                        var checkArray = ival.split(";");
                        for (var i = 0; i < checkboxObj.length; i++) {
                            for (var j = 0; j < checkArray.length; j++) {
                                if (checkboxObj[i].value == checkArray[j]) {
                                    checkboxObj[i].click();
                                }
                            }
                        }
                    }
                    else if ($oinput.attr("type") == "radio") {
                        $oinput.each(function () {
                            var radioObj = $("[name=" + name + "]");
                            for (var i = 0; i < radioObj.length; i++) {
                                if (radioObj[i].value == ival) {
                                    radioObj[i].click();
                                }
                            }
                        });
                    }
                    else if ($oinput.attr("type") == "textarea") {
                        obj.find("[name=" + name + "]").html(ival);
                    }
                    else {
                        obj.find("[name=" + name + "]").val(ival);
                    }
                } else if ($oinput[0].tagName == 'SPAN') {
                    obj.find("[name=" + name + "]").text(ival);
                } else if ($oinput[0].tagName == 'SELECT') {
                    obj.find("[name=" + name + "]").val(ival);
                } else if ($oinput[0].tagName == 'H2') {
                    obj.find("[name=" + name + "]").text(ival);
                }
            }
        }
    })
};

/**
 * 返回前一页（或关闭本页面）
 * <li>如果没有前一页历史，则直接关闭当前页面</li>
 */
function goBack() {
    if ((navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0)) { // IE
        if (history.length > 0) {
            window.history.go(-1);
        } else {
            window.opener = null;
            window.close();
        }
    } else { //非IE浏览器
        if (navigator.userAgent.indexOf('Firefox') >= 0 ||
            navigator.userAgent.indexOf('Opera') >= 0 ||
            navigator.userAgent.indexOf('Safari') >= 0 ||
            navigator.userAgent.indexOf('Chrome') >= 0 ||
            navigator.userAgent.indexOf('WebKit') >= 0) {

            if (window.history.length > 1) {
                window.history.go(-1);
            } else {
                window.opener = null;
                window.close();
            }
        } else { //未知的浏览器
            window.history.go(-1);
        }
    }
};


/**
 * js对象复制
 *
 * @param obj
 * @returns {*}
 */
function clone(obj) {
    var o, obj;
    if (obj.constructor == Object) {
        o = new obj.constructor();
    } else {
        o = new obj.constructor(obj.valueOf());
    }
    for (var key in obj) {
        if (o[key] != obj[key]) {
            if (typeof (obj[key]) == 'object') {
                o[key] = clone(obj[key]);
            } else {
                o[key] = obj[key];
            }
        }
    }
    o.toString = obj.toString;
    o.valueOf = obj.valueOf;
    return o;
};

/**
 * 获取字符数（中文处理）
 *
 * @param str 字符串
 * @returns {Number}
 */
function getLength(str) {
    return str.replace(/[^\x00-\xff]/g, "aa").length;
};

/**
 * 获取url参数值。
 * @param n 为参数名
 **/
var getP = function (n) {
    var hrefstr, pos, parastr, para, tempstr;
    hrefstr = window.location.href;
    pos = hrefstr.indexOf("?");
    parastr = hrefstr.substring(pos + 1);
    para = parastr.split("&");
    tempstr = "";
    for (var i = 0; i < para.length; i++) {
        tempstr = para[i];
        pos = tempstr.indexOf("=");
        if (tempstr.substring(0, pos).toLowerCase() == n.toLowerCase()) {
            return tempstr.substring(pos + 1);
        }
    }
    return '';
};


/**
 * 读取cookies
 *
 * @param name
 * @returns {null}
 */
function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
};

/**
 * 删除cookies
 *
 * @param name
 */
function delCookie(name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = getCookie(name);
    if (cval != null)
        document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
};

/**
 * 设置cookie
 *
 * @param name cookie名称
 * @param value  cookie名称 值
 * @returns {boolean} 状态
 * @constructor
 */
function setCookie(name, value) {
    var exp = new Date();
    exp.setTime(exp.getTime() + 6 * 24 * 60 * 60 * 1000); //6天过期
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
    return true;
};