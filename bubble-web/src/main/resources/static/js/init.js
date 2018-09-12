/**
 * 1.全局配置:避免每个页面加入相同配置，使用主数据main.js加载，例：
 require.config({
    paths : {
        "jquery" : ["http://libs.baidu.com/jquery/2.0.3/jquery", "js/jquery"],
        "a" : "js/a"
    }
   })
 后续只要引入<script data-main="js/main" src="js/require.js"></script>
 2.第三方模块
 require.config({
    shim: {
        "underscore" : {
            exports : "_";
        }
    }
   })
 3.jquery.form插件,不符合AMD
 require.config({
        shim: {
            "underscore" : {
                exports : "_";
            },
            "jquery.form" : {
                deps : ["jquery"]
            }
        }
     })
 也可以简写为：

 require.config({
        shim: {
            "underscore" : {
                exports : "_";
            },
            "jquery.form" : ["jquery"]
        }
     })
 */
require.config(
    {
        shim: {
            'jquery.easyui': ['jquery']
        },
        //添加依赖模块
        paths: {
            // 'jquery': 'plugin/jquery-1.10.2',
            'jquery': 'plugin/jquery.min',
            'extension': 'component/extension',
            'domready': 'plugin/domready',
            'jquery.easyui': 'plugin/jquery.easyui.min'
        }
    }
);
require(['jquery', 'extension', 'jquery.easyui'], function ($, extension) {

    // called once the DOM is ready
    /**
     * 时间对象的格式化
     *
     * @param format
     * @returns {*}
     */
    Date.prototype.format = extension.formatTime;
    /**
     *
     */
    $.extend({remind: extension.remind});
    /**
     * 扩展panel 移动
     * @type {easyuiPanelOnMove}
     */
    $.fn.panel.defaults.onMove = extension.easyuiPanelOnMove;
    /**
     * 扩展panel 移动
     * @type {easyuiPanelOnMove}
     */
    $.fn.window.defaults.onMove = extension.easyuiPanelOnMove;
    /**
     * 扩展panel 移动
     * @type {easyuiPanelOnMove}
     */
    $.fn.dialog.defaults.onMove = extension.easyuiPanelOnMove;
    /**
     * 将表单转为json对象
     */
    $.fn.serializeObject = extension.serializeObject
    /**
     * 将josn对象赋值给form
     * */
    $.fn.setForm = extension.setForm
    /**
     *
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
     *
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