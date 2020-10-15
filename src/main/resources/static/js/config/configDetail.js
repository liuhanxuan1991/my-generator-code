var id=getUrlParms("id");
$(function () {
    layui.use(['form', 'layedit', 'laydate'], function(){
           var form = layui.form
           ,layer = layui.layer
           ,layedit = layui.layedit
           ,laydate = layui.laydate;

            //监听提交
           });

});

function back(){
    window.history.go(-1);
}
//获取地址栏参数
function getUrlParms(name){
   var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
   var r = window.location.search.substr(1).match(reg);
   if(r!=null)
   return unescape(r[2]);
   return null;
   }