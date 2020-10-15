
var imageCount=0;
$(function () {


     layui.use(['form', 'layedit', 'laydate'], function(){
       var form = layui.form
       ,layer = layui.layer
       ,layedit = layui.layedit
       ,laydate = layui.laydate;
        //监听提交
      $("#type").next().remove();
         form.on('submit(demo1)', function(data){
          var arr = {
                  "type":$("#type").val(),
                  "key":$("#key").val(),
                  "sort":$("#sort").val(),
                  "val": $("#val").val()
              };
              console.log("参数："+JSON.stringify(arr));
          layer.confirm('确认提交吗？', {
             btn: ['确定', '取消'] //可以无限个按钮
           }, function(index, layero){
                var index = parent.layer.load(2);
               $.ajax({
                          url :"/back/auth/config/insertConfig",
                          type : "POST",
                          data:arr,
                          success : function(data) {
                               console.log("添加config："+JSON.stringify(data));
                               if (data.code == 0) {
                                    layer.msg("添加成功",{icon: 1,time:3000},function(){
                                        window.history.go(-1);
                                        window.location.reload();
                                    });

                                }else if(data.code == 900){
                                   layer.msg("该类型下的key已存在",{icon:2,time:3000},function(){

                                    });

                                }else{
                                    layer.msg("添加失败",{icon:2,time:3000},function(){

                                    });
                                }
                          },complete:function(XMLHttpRequest,textStatus){
                                parent.layer.close(index);
                            },error:function(XMLHttpRequest,textStatus,errorThrown){
                               // alert('error...状态文本值：'+textStatus+" 异常信息："+errorThrown);
                              parent.layer.close(index);
                               layer.msg("请稍后再重试",{icon: 2,time:3000},function(){

                               });
                           }
                     });
              }, function(index){
                   layer.close(index);
             });
             return false;
         });

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