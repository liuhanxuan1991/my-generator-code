$(function () {
 $('#search_statusPay').val("");
     layui.use('laydate', function () {
            var laydate = layui.laydate;
             var layer = layui.layer;


        layui.use('table', function(){
            var table = layui.table;

            //方法级渲染
            table.render({
                elem: '#test1'
                ,url: '/back/auth/config/configList'
                ,page: true
                ,cols: [[
                    {field:'id', title: '序号',width: 100}
                    ,{field:'type', title: '类型',templet:'#typeTpl'}
                    ,{field:'sKey', title: 'sKey'}
                    ,{field:'val', title: 'val'}
                    ,{field:'createTimeShow', title: '创建时间'}
                    ,{field:'operation', title:'操作', templet:'#operationTpl'}
                ]]
                ,id: 'testReload'

            });

            var $ = layui.$, active = {
                reload: function(){//status userID orderNo startTime endTime
                    var key = $('#key').val();
                    var type = $('#type').val();

                    //执行重载
                    table.reload('testReload', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        ,where: {
                            key: key,
                            type: type
                        }
                    });
                }
            };

            $('#search').on('click', function(){
                var type = $(this).data('type');
               // console.log(type,'tyoe njnjn')
                active[type] ? active[type].call(this) : '';
            });

        });

         $("#reset").click(function(){
             $('#key').val("");
             $('#type').val("");

        });

    });
});



function cancelOrder() {
    window.location.href = '/back/auth/order/cancel';
}


