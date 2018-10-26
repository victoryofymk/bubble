/**
 * Created by yanmingkun on 2017/5/3.
 */
window.ctxPaths = getContextPath();
document.writeln('<link rel="stylesheet" type="text/css" href="static/plugin/css/jq22.css"/>');
document.writeln('<link rel="stylesheet" type="text/css" href="static/plugin/css/font-awesome.min.css"/>');
document.writeln('<script type="text/javascript" src="static/js/plugin/jquery.min.js"><\/script>');
document.writeln('<script type="text/javascript" src="static/js/plugin/jquery.messager.js"><\/script>');
document.writeln('<script type="text/javascript" src="static/js/plugin/jquery.easyui.min.js"><\/script>');

function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0, index + 1);
    return result;
}
