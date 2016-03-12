<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false" %> 
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />

		<link rel="stylesheet" type="text/css" href="<%=path %>/css/base.css" />
		
        <script language="javascript">
           function yhxxDel(id,del)
           {
               if(confirm('您确定当前操作吗？'))
               {
                   window.location.href="<%=path %>/yhxx?type=yhxxDel&id="+id+"&del="+del;
               }
           }
       </script>
	</head>

	<body leftmargin="2" topmargin="2" background='<%=path %>/img/allbg.gif'>
			
			<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
				<tr bgcolor="#E7E7E7">
					<td height="14" colspan="9" background="<%=path %>/img/tbg.gif">&nbsp;用户管理&nbsp;</td>
				</tr>
				<tr align="center" bgcolor="#FAFAF1" height="22">
					<td width="10%">姓名</td>
					<td width="8%">性别</td>
					<td width="10%">身份证号码</td>
					<td width="12%">联系电话</td>
					<td width="10%">地址</td>
					<td width="8%">邮编</td>
					<td width="*">登录名</td>
					<td width="8%">状态</td>
					<td width="12%">操作</td>
		        </tr>	
				<c:forEach items="${requestScope.yhxxList}" var="yhxx">
				<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
					<td bgcolor="#FFFFFF" align="center">
						${yhxx.name}
					</td>
					<td bgcolor="#FFFFFF" align="center">
						${yhxx.sex}
					</td>
					<td bgcolor="#FFFFFF" align="center">
						${yhxx.pid}
					</td>
					<td bgcolor="#FFFFFF" align="center">
						${yhxx.lxdh}
					</td>
					<td bgcolor="#FFFFFF" align="center">
						${yhxx.dz}
					</td>
					<td bgcolor="#FFFFFF" align="center">
						${yhxx.yb}
					</td>
					<td bgcolor="#FFFFFF" align="center">
						${yhxx.loginName}
					</td>
						<c:if test="${yhxx.del==0}">
							<td bgcolor="#FFFFFF" align="center">
								正常
							</td>
							<td bgcolor="#FFFFFF" align="center">
							    <form action="<%=path %>/zjxx/zjxxEditPre.jsp" name="" method="post">
								    <input type="button" onclick="yhxxDel(${yhxx.id},1)" value="停用"/>
								</form>
							</td>							
						</c:if>
						<c:if test="${yhxx.del==1}">
							<td bgcolor="#FFFFFF" align="center">
								已停用
							</td>
							<td bgcolor="#FFFFFF" align="center">
							    <form action="<%=path %>/zjxx/zjxxEditPre.jsp" name="" method="post">
								    <input type="button" onclick="yhxxDel(${yhxx.id},0)" value="启用"/>
								</form>
							</td>	
						</c:if>
				</tr>
				</c:forEach>
			</table>
	</body>
</html>
