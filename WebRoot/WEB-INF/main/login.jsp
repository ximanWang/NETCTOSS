<%@page pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>达内－NetCTOSS</title>
        <link type="text/css" rel="stylesheet" media="all" href="../styles/global.css" />
        <link type="text/css" rel="stylesheet" media="all" href="../styles/global_color.css" /> 
    	<script type="text/javascript" language="javascript">
    		function change(imageObj){
    			imageObj.src = "createValidateCode?date="+ new Date().getTime();
    		}
    	</script>
    </head>
    <body class="index">
        <div class="login_box">
        	<form action="login" method="post">
            <table>
                <tr>
                    <td class="login_info">账号：</td>
                    <td colspan="2"><input name="adminCode" type="text" class="width150" value='<s:property value="adminCode"/>'/></td>
                    <td class="login_error_info"><span class="required">30长度的字母、数字和下划线</span></td>
                </tr>
                <tr>
                    <td class="login_info">密码：</td>
                    <td colspan="2"><input name="password" type="password" class="width150" value='<s:property value="password"/>'/></td>
                    <td><span class="required">30长度的字母、数字和下划线</span></td>
                </tr>
                <tr>
                    <td class="login_info">验证码：</td>
                    <td class="width70"><input name="userImageCode" type="text" class="width70" /></td>
                    <td><img src="createValidateCode" alt="验证码" title="点击更换" onclick="change(this);"/></td>  
                    <td><span class="required"></span></td>              
                </tr>            
                <tr>
                    <td></td>
                    <td class="login_button" colspan="2">
                        <a href="javascript:document.forms[0].submit();"><img src="../images/login_btn.png" /></a>
                    </td>    
                    <td><span class="required"><s:property value="errMsg"/></span></td>                
                </tr>
            </table>
            </form>
        </div>
    </body>
</html>