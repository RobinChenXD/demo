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
           function zbxxDel(id)
           {
               if(confirm('您确定删除吗？'))
               {
                   window.location.href="<%=path %>/zbxx?type=zbxxDel&id="+id;
               }
           }
           
           function zbxxAdd()
           {
                 var url="<%=path %>/zbxx/zbxxAdd.jsp";
				 window.location.href=url;
           }
           
       </script>
	</head>

	<body leftmargin="2" topmargin="2" background='<%=path %>/img/allbg.gif'>
			
			<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
				<tr bgcolor="#E7E7E7">
					<td height="14" colspan="5" background="<%=path %>/img/tbg.gif">&nbsp;主编管理&nbsp;</td>
				</tr>
				<tr align="center" bgcolor="#FAFAF1" height="22">
					<td width="10%">姓名</td>
					<td width="8%">性别</td>
					<td width="12%">参加工作时间</td>
					<td width="12%">登录名</td>
					<td width="10%">操作</td>
		        </tr>	
				<c:forEach items="${requestScope.zbxxList}" var="zbxx">
				<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
					<td bgcolor="#FFFFFF" align="center">
						${zbxx.xingming}
					</td>
					<td bgcolor="#FFFFFF" align="center">
						${zbxx.sex}
					</td>
					<td bgcolor="#FFFFFF" align="center">
					    ${zbxx.cjgzsj}
					</td>
					<td bgcolor="#FFFFFF" align="center">
						${zbxx.loginName}
					</td>
					<td bgcolor="#FFFFFF" align="center">
					    <form action="<%=path %>/zbxx/zbxxEditPre.jsp" name="" method="post">
						    <input type="button" onclick="zbxxDel(${zbxx.id})" value="删除"/>
						        <input type="hidden" name="id" value="${zbxx.id}"/>
						        <input type="hidden" name="xingming" value="${zbxx.xingming}"/>
						        <input type="hidden" name="sex" value="${zbxx.sex}"/>
						        <input type="hidden" name="cjgzsj" value="${zbxx.cjgzsj}"/>
						    <input type="submit" value="编辑"/>
						</form>
					</td>
				</tr>
				</c:forEach>
			</table>
			
			<table width='98%'  border='0'style="margin-top:8px;margin-left: 5px;">
			  <tr>
			    <td>
			      <input type="button" value="添加" style="width: 80px;" onclick="zbxxAdd()" />
			    </td>
			  </tr>
		    </table>
	</body>
</html>
