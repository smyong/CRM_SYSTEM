function searchUser() {
    $("#dg").datagrid("load",{
        uname:$("#s_userName").val(),
        email:$("#s_email").val(),
        phone:$("#s_phone").val()
    })
}
$(function () {
    $("#dlg").dialog({
        onClose:function () {            
            // 清空表单数据
            $("#userName").val("");
            $("#email").val("");
            $("#phone").val("");
            $("#trueName").val("");
            $("#id").val("");//清空隐藏域ID值
        }
    })
});

/**
 * 打开添加会话框
 */
function openUserAddDialog() {
    openDialog("dlg","用户添加");
}

/**
 * 关闭会话框
 */
function closeUserDialog() {
    closeDialog("dlg");
}

/**
 * 用户添加数据
 */
function saveOrUpdateUser() {
    saveOrUpdateRecode("fm",ctx+"/user/save",ctx+"/user/update","dlg",searchUser)
}

/**
 * 用户更新数据
 */
function openUserModifyDialog() {
    openModifyDialog("dg","fm","dlg","用户更新");
}

/**
 * 删除用户信息
 */
function deleteUser() {
    deleteRecode("dg",ctx+"/user/delete",searchUser);
}