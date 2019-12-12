/**
 * 营销机会数据条件搜索
 */
function searcherSaleChance() {
    $("#dg").datagrid("load",{
        customerName:$("#s_customerName").val(),
        creatMan:$("#s_createMan").val()
    })
}

/**
 *定义开发状态值对应的状态
 * @param value
 * @returns {string}
 */
function formatterDevResult(value) {
    if(value==0){
        return "未开发";
    }else if(value==1){
        return "开发中";
    }else if(value==2){
        return "开发成功";
    }else if(value==3){
        return "开发失败";
    }else{
        return "未知";
    }
}

/**
 * 定义开发状态
 * 根据不同状态设置不同的颜色
 * @returns {string}
 */
function stylerDevResult(value) {
    if(value==0){
        return "background-color: orange;"
    }else if(value==1){
        return "background-color: blue;"
    }else if(value ==2){
        return "background-color: green;"
    }else if(value==3){
        return "background-color: red;"
    }else{
        return "background-color: #8E44AD;"
    }
}

/**
 * 操作链接
 * 根据客户开发状态不同，控制操作打开方式不同格式化控制实现
 * @param value
 * @param rowData
 * @returns {string}
 */
function formatterOp(value,rowData) {
    var href='javascript:openCusDevPlanTab("客户开发计划项_",'+rowData.id+')';
    console.log(href);
    if(rowData.devResult==1){
        return "<a href='"+href+"'>开发</a>";
    }else{
        return "<a href='"+href+"'>查看详情</a>";
    }
}

/**
 * 查看开发计划详情
 */
function openCusDevPlanTab(title,id) {
    window.parent.openTab(title+id,ctx+"/cus_dev_plan/toCusDevPlanManger?sid="+id);
}