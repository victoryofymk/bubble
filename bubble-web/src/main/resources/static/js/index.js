/**
 * main.js  requirejs主模块  'domready!'
 */
require(['init'], function () {
    require(['jquery', 'extension', 'jquery.easyui'], function ($, extension) {
        $(function () {
            extension.init();
        })
    })
});