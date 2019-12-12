/**
 * 格式化分配状态
 * @param value
 * @returns {string}
 */
function formatState(value) {
    if(value==0){
        return "未分配";
    }else if(value==1){
        return "已分配";
    }else{
        return "未知";
    }
}

/**
 * 搜索查询
 */
function searchSaleChance() {
    $("#dg").datagrid("load",{
        customerName:$("#s_customerName").val(),
        createMan:$("#s_createMan").val(),
        state:$("#s_state").combobox("getValue")
    })
}

/**
 * 打开添加营销机会会话框
 */
function openSaleChanceAddDialog() {
    openDialog("dlg","营销机会添加");
}

/**
 * 关闭添加营销机会会话框
 */
function closeSaleChanceDialog() {
    closeDialog("dlg")
}

/**
 * 清空会话框的值
 */
$(function () {
    $("#dlg").dialog({
        onClose:function () {
            $("#customerName").val("");
            $("#chanceSource").val("");
            $("#linkMan").val("");
            $("#linkPhone").val("");
            $("#cgjl").val("");
            $("#overview").val("");
            $("#description").val("");
            $("#assignMan").combobox("setValue","");
            //$("#id").val("");
        }
    });
});

/**
 * 添加营销机会数据
 */
function saveOrUpdateSaleChance() {
    //saveOrUpdateRecode("fm",ctx+"/sale_chance/save",ctx+"/sale_chance/update","dlg",searchSaleChance);
    var url=ctx+"/sale_chance/save";
    if(!isEmpty($("#id").val())){
        url=ctx+"/sale_chance/update";
    }
    $("#fm").form("submit",{
        url:url,
        onSubmit:function () {
            return $("#fm").form("validate");
        },
        dataType:"json",
        success:function (data) {
            console.log(data);
            data=JSON.parse(data);
            if(data.code==200){
                closeSaleChanceDialog();

                searchSaleChance();
            }else{
                $.messager.alert("来自crm",data.msg,"error");
            }
        }
    })
}

/**
 * 更新营销机会数据
 */
function openSaleChanceModifyDialog() {
    openModifyDialog("dg","fm","dlg","营销机会更新");
}

/**
 * 删除营销机会数据
 */
function deleteSaleChance() {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择待删除的数据!","warning");
        return;
    }
    var ids="ids=";
    for(var i=0;i<rows.length;i++){
        if(i<rows.length-1){
            ids=ids+rows[i].id+"&ids=";
        }else{
            ids=ids+rows[i].id;
        }
    }
    $.messager.confirm("来自crm","确定删除选中的记录",function (r) {
        if(r){
            $.ajax({
                type:"post",
                url:ctx+"/sale_chance/delete",
                data:ids,
                dataType:"json",
                success:function (data) {
                    if(data.code==200){
                        searchSaleChance();
                    }else {
                        $.messager.alert("来自crm",data.msg,"error");
                    }
                }
            })
        }
    })
}