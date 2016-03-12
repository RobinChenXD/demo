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
           function tgxxDel(id)
           {
               if(confirm('您确定删除吗？'))
               {
                   window.location.href="<%=path %>/tgxx?type=tgxxDel&tgxxid="+id;
               }
           }
           
           function tgxxTijiao(id)
           {
               if(confirm('提交后信息将无法修改,确认提交？'))
               {
                   window.location.href="<%=path %>/tgxx?type=tgxxTijiao&tgxxid="+id;
               }
           }
           
           function tgxxAdd()
           {
                 var url="<%=path %>/tgxx/tgxxAdd.jsp";
				 window.location.href=url;
           }
           
       </script>
	</head>

	<body leftmargin="2" topmargin="2" background='<%=path %>/img/allbg.gif'>
			<table width='98%'  border='0'style="margin-top:8px;margin-left: 5px;">
			  <tr>
			    <td>
			        <form action="<%=path %>/tgxx?type=tgxxSearch" name="formAdd" method="post">
			          标题： <input type="text" name="biaoti" value="${requestScope.biaoti }"/>
			           &nbsp;
			           <input type="submit" value="查询"/>
			        </form>
			    </td>
			  </tr>
		    </table>
			
			<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
				<tr bgcolor="#E7E7E7">
					<td height="14" colspan="6" background="<%=path %>/img/tbg.gif">&nbsp;投稿管理&nbsp;</td>
				</tr>
				<tr align="center" bgcolor="#FAFAF1" height="22">
					<td width="*">标题</td>
					<td width="10%">摘引</td>
					<td width="10%">关键字</td>
					<td width="10%">投稿日期</td>
					<td width="10%">当前状态</td>
					<td width="18%">操作</td>
		        </tr>	
				<c:forEach items="${requestScope.tgxxList}" var="tgxx">
				<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
					<td bgcolor="#FFFFFF" align="center">
						${tgxx.biaoti}
					</td>
					<td bgcolor="#FFFFFF" align="center">
						${tgxx.zy}
					</td>
					<td bgcolor="#FFFFFF" align="center">
					    ${tgxx.gjz}
					</td>
					<td bgcolor="#FFFFFF" align="center">
						${tgxx.tgrq}
					</td>
					<td bgcolor="#FFFFFF" align="center">
						${tgxx.strZt}
						
					</td>
					<c:if test="${tgxx.zt==0}">
					<td bgcolor="#FFFFFF" align="center">
					    <form action="<%=path %>/tgxx/tgxxEditPre.jsp" name="" method="post">
						    <input type="button" onclick="tgxxDel(${tgxx.id})" value="删除"/>
						    <input type="hidden" name="tgxxid" value="${tgxx.id}"/>
						    <input type="hidden" name="biaoti" value="${tgxx.biaoti}"/>
						    <input type="hidden" name="zy" value="${tgxx.zy}"/>
						    <input type="hidden" name="gjz" value="${tgxx.gjz}"/>
						    <input type="hidden" name="tgfjid" value="${tgxx.tgfj.id}"/>
						    <input type="hidden" name="fjmc" value="${tgxx.tgfj.fjmc}"/>
						    <input type="hidden" name="fjlj" value="${tgxx.tgfj.fjlj}"/>
						    <input type="submit" value="编辑"/>
						    <input type="button" onclick="tgxxTijiao(${tgxx.id})" value="提交"/>
						</form>
					</td>
					</c:if>
					<c:if test="${tgxx.zt!=0}">
						<td bgcolor="#FFFFFF" align="center">
							&nbsp;
						</td>
					</c:if>
				</tr>
				</c:forEach>
			</table>
			
			<table width='98%'  border='0'style="margin-top:8px;margin-left: 5px;">
			  <tr>
			    <td>
			      <input type="button" value="添加" style="width: 80px;" onclick="tgxxAdd()" />
			    </td>
			  </tr>
		    </table>
	</body>
</html>
