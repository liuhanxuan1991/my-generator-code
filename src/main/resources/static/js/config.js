/**
 * Created by admin on 2018/1/2.
 */
// var config_contextPath = 'http://localhost:8086';
// var config_contextPath = 'http://1.1.1.1:8086'; //开发
var config_contextPath = 'http://47.96.37.107:8085'; //测试
// var config_contextPath = 'http://192.168.100.174:8085'; //对接人
var API = {

    //品牌管理
    brandPageList:config_contextPath + '/manager/brand/brandPageList',
    addUpdateBrand:config_contextPath + '/manager/brand/addUpdateBrand',
    delBrand:config_contextPath + '/manager/brand/delBrand',

    upload:config_contextPath + '/manager/file/upload',


    //分类管理
    categoryPageList:config_contextPath + '/manager/category/categoryPageList',
    addUpdateCategory:config_contextPath + '/manager/category/addUpdateCategory',
    delCategory:config_contextPath + '/manager/category/delCategory',


    //spu管理
    spuPageList:config_contextPath + '/manager/spu/spuPageList',
    addUpdateSpu:config_contextPath + '/manager/spu/addUpdateSpu',
    delSpu:config_contextPath + '/manager/spu/delSpu',
    //获取SPU列表
    apiSpuList:config_contextPath + '/api/spu/spuList',


    categoryList:config_contextPath + '/api/category/categoryList',
    brandList:config_contextPath + '/api/brand/brandList',
    spuList:config_contextPath + '/api/spu/spuList',


    //账号管理
    roleSearch:config_contextPath + '/manager/role/search',
    adminSearch:config_contextPath + '/manager/admin/search',
    adminAdd:config_contextPath + '/manager/admin/add',
    //运营后台增加角色
    addRole:config_contextPath + '/manager/role/add',
    //运营获取权限配置
    getAllMenuByRoleKey:config_contextPath + '/manager/menu/getAllMenuByRoleKey',



    /**后台参数管理接口**/
    //参数分页查询
    parameterPage:config_contextPath + '/manager/parameter/parameterPage',
    parameterPageList:config_contextPath + '/manager/parameter/parameterPageList',
    //参数查询
    getParameter:config_contextPath + '/manager/parameter/getParameter',
    //添加或修改参数
    addUpdateParameter:config_contextPath + '/manager/parameter/addUpdateParameter',
    //删除参数
    delParameter:config_contextPath + '/manager/parameter/delParameter',

    /**后台属性管理接口**/
    //属性分页查询
    attributePage:config_contextPath + '/manager/attribute/attributePage',
    attributePageList:config_contextPath + '/manager/attribute/attributePageList',
    //属性查询
    getAttribute:config_contextPath + '/manager/attribute/getAttribute',
    //添加或修改属性
    addUpdateAttribute:config_contextPath + '/manager/attribute/addUpdateAttribute',
    //属性删除
    delAttribute:config_contextPath + '/manager/attribute/delAttribute',

    //获取分类列表
    //categoryList:config_contextPath + '/api/category/categoryList',


    /**后台货品管理接口**/
    //货品分页查询
    productPage:config_contextPath + '/manager/product/productPage',
    productPageList:config_contextPath + '/manager/product/productPageList',
    //货品查询
    getProduct:config_contextPath + '/manager/product/getProduct',
    //添加或修改货品
    addUpdateProduct:config_contextPath + '/manager/product/addUpdateProduct',
    //获取货品属性值
    apiProductAttributeList:config_contextPath + '/api/productAttribute/productAttributeList',
    //根据分类查询参数
    listParameterByCategory:config_contextPath + '/manager/parameter/listParameterByCategory',


};


function getajaxdata(url, method, data, datatype, callback) {
    var index = parent.layer.load(2);
    $.ajax({
        url: url,
        method: method,
        data: data,
        dataType: datatype,
    }).done(function (data) {
        parent.layer.close(index);
        callback(data)
    }).fail(function () {
        //alert("请求数据错误，请稍后再试")
        parent.layer.close(index);
    })
}

function getJsonAjaxData(url, method, data, dataType,contentType, callback) {
    var index = parent.layer.load(2);
    $.ajax({
        url: url,
        method: method,
        data: JSON.stringify(data),
        dataType: dataType,
        contentType:contentType
    }).done(function (data) {
        parent.layer.close(index);
        callback(data)
    }).fail(function () {
      parent.layer.close(index);
       // alert("请求数据错误，请稍后再试")
        layer.msg("请求数据错误，请稍后再试",{icon: 2,time:3000},function(){

         });
    })
}

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}

function GetArgsFromHref(sHref, sArgName) {
    var args = sHref.split("?");
    var retval = "";
    /*参数为空*/
    if(args[0] == sHref) {
        return retval; /*无需做任何处理*/
    }
    var str = args[1];
    args = str.split("&");
    for(var i = 0; i < args.length; i ++) {
        str = args[i];
        var arg = str.split("=");
        if(arg.length <= 1) continue;
        if(arg[0] == sArgName) {
            retval = arg[1];
        }
    }
    return retval;
}


function arrayRemoveByValue(arr, val) {
    for(var i=0; i<arr.length; i++) {
        if(arr[i] == val) {
            arr.splice(i, 1);
            break;
        }
    }
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

//读取cookies
function getCookie(name) {
    return '1111';
    // var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    // if(arr=document.cookie.match(reg)){
    //     return unescape(arr[2]);
    // } else {
    //     return null;
    // }
}