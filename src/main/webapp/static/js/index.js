//用户登录
function login() {
    //获取用户名和密码
    var userName=$("input[name='username']").val();
    var password=$("input[name='password']").val();
    //非空校验
    if(isEmpty(userName)){
        alert("请输入用户名")
        return;
    }
    if(isEmpty(password)){
        alert("请输入密码")
        return;
    }
    //通过Ajax提交表单
    $.ajax({
        type:"post",
        url:ctx+"/user/login",
        data:{
            userName:userName,
            password:password
        },
        dataType:"json",
        success:function (data) {
            if(data.code==200){//判断是否登录成功
                //成功存入cookie信息
                $.cookie("userIdStr",data.result.userIdStr);
                $.cookie("userName",data.result.userName);
                $.cookie("trueName",data.result.trueName);
               //跳转到主页面
                window.location.href=ctx+"/main";
            }else{//否则登录失败
                alert(data.msg);//弹出提示信息
            }
        }
    })

}