$(function () {
    //可编辑表格
    $("#dg").edatagrid({
        url:ctx+"/cus_dev_plan/list?sid="+$("#saleChanceId").val(),//获取 edatagrid 显示数据
        saveUrl:ctx+"/cus_dev_plan/save?saleChanceId="+$("#saleChanceId").val(),//定义添加记录URL
        updateUrl:ctx+"/cus_dev_plan/update?saleChanceId="+$("#saleChanceId").val()//定义更新记录URL
    });
    // 页面加载完毕 控制edatagrid 工具栏是否显示  开发中-显示   开发完毕-隐藏
    var devResult=$("#devResult").val();
    if(devResult==2||devResult==3){
        $("#toolbar").hide();
        $("#dg").edatagrid("disableEditing");
    }
});

/**
 * 添加或更新机会数据
 */
function saveCusDevPlan() {
    $("#dg").edatagrid("saveRow");
    $("#dg").edatagrid("load");
}
function deleteCusDevPlan() {
    var rows=$("#dg").edatagrid("getSelections");//获取选中的所有行
    //判断是否选中记录
    if(rows.length==0){
        $.messager.alert("来自CRM","请选择待删除的数据","warning");
    }
    var ids="ids=";
    //遍历多行的数据
    for(var i=0;i<rows.length;i++){
        if(i<rows.length-1){
            ids=ids+rows[i].id+"&ids=";
        }else{
            ids=ids+rows[i].id;
        }
    }
    $.messager.confirm("来自CRM","确定删除选中的记录？",function (r) {
        if(r){
            $.ajax({
                type:"post",
                url:ctx+"/cus_dev_plan/delete",
                data:ids,
                dataType:"json",
                success:function (data) {
                    if(data.code==200){
                        $("#dg").edatagrid("load");
                    }else{
                        $.messager.alert("来自crm",data.msg,"error");
                    }
                }
            })
        }
    })
}
function updateSaleChanceDevResult(result){
    $.ajax({
        type:"post",
        url:ctx+"/sale_chance/updateSaleChanceDevResult",
        data:{
            devResult:result,
            sid:$("#saleChanceId").val()
        },
        dataType:"json",
        success:function (data) {
            if(data.code==200){
                // 隐藏工具栏
                $("#toolbar").hide();
                // 禁用表格编辑功能
                $("#dg").edatagrid("disableEditing");
            }else{
                $.messager.alert("来自crm",data.msg,"error");
            }
        }
    })
}