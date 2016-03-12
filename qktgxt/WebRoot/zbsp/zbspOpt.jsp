<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.qktgxt.util.Tgzt;"%> 

<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<script type="text/javascript" src="<%=path%>/commutil.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=path %>/css/base.css" />
        <script language="javascript">
           function zbspTijiao(zt)
           {
               if(confirm('提交后信息将无法修改,确认提交？'))
               {
                   //window.location.href="<%=path %>/&tgxx="+id+"&zt="+zt;
                   document.formAdd.zt.value = zt;
                   document.formAdd.submit();
               }
           }
       </script>
	</head>

	<body leftmargin="2" topmargin="9" background='<%=path %>/img/allbg.gif'>
			<form action="<%=path %>/zbsp?type=zbspOpt" name="formAdd" method="post">
				     <table width="98%" align="center" border="0" cellpadding="4" cellspacing="1" bgcolor="#CBD8AC" style="margin-bottom:8px">
						<tr bgcolor="#EEF4EA">
					        <td colspan="3" background="<%=path %>/img/wbg.gif" class='title'><span>投稿信息修改</span></td>
					    </tr>
						<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
						    <td width="25%" bgcolor="#FFFFFF" align="right">
						         标题：
						    </td>
						    <td width="75%" bgcolor="#FFFFFF" align="left">
						        ${requestScope.tgxx.biaoti }
						    </td>
						</tr>
						<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
						    <td width="25%" bgcolor="#FFFFFF" align="right">
						         作者：
						    </td>
						    <td width="75%" bgcolor="#FFFFFF" align="left">
						         ${requestScope.tgxx.strYonghu }
						    </td>
						</tr>
						<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
						    <td width="25%" bgcolor="#FFFFFF" align="right">
						         摘要：
						    </td>
						    <td width="75%" bgcolor="#FFFFFF" align="left">
						         ${requestScope.tgxx.zy }
						    </td>
						</tr>
						<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
						    <td width="25%" bgcolor="#FFFFFF" align="right">
						         关键字：
						    </td>
						    <td width="75%" bgcolor="#FFFFFF" align="left">
						         ${requestScope.tgxx.gjz }
						    </td>
						</tr>
						<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
						    <td width="25%" bgcolor="#FFFFFF" align="right">
						         附件：
						    </td>
						    <td width="75%" bgcolor="#FFFFFF" align="left">
								<c:if test="${tgxx.tgfj.id==0}">
									&nbsp;
								</c:if>
								<c:if test="${tgxx.tgfj.id!=0}">
									<a title="点击下载" href="<%=path %>/tgxx?type=down&fjid=${tgxx.tgfj.id }">${tgxx.tgfj.fjmc }</a>
								</c:if>
						    </td>
						</tr>
						<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
							<td width="25%" bgcolor="#FFFFFF" align="right">
								评审专家：
							</td>
							<td width="75%" bgcolor="#FFFFFF" align="left">
								${requestScope.tgxx.zjps.strZjxx }
							</td>
						</tr>
						<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
							<td width="25%" bgcolor="#FFFFFF" align="right">
								专家审批时间：
							</td>
							<td width="75%" bgcolor="#FFFFFF" align="left">
								${requestScope.tgxx.zjps.zjpssj }
							</td>
						</tr>
						<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
							<td width="25%" bgcolor="#FFFFFF" align="right">
								专家意见：
							</td>
							<td width="75%" bgcolor="#FFFFFF" align="left">
								${requestScope.tgxx.zjps.zjyj }
							</td>
						</tr>
						<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
							<td width="25%" bgcolor="#FFFFFF" align="right">
								主编意见：
							</td>
							<td width="75%" bgcolor="#FFFFFF" align="left">
								<textarea rows="4" cols="16" name="zbyj"></textarea>
							</td>
						</tr>
						<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
						    <td width="25%" bgcolor="#FFFFFF" align="right">
						        &nbsp;
						    </td>
						    <td width="75%" bgcolor="#FFFFFF" align="left">
						    	<input type="hidden" name="tgxx" value="${tgxx.id}"/>
						    	<input type="hidden" name="zt"/>
							  <input type="button" onclick="zbspTijiao(<%=Tgzt.ZBTY %>)" value="同意"/>
							  <input type="button" onclick="zbspTijiao(<%=Tgzt.ZBTH %>)" value="不同意"/>
						      <input type="reset" value="返回" onclick="javascript:history.back();"/>&nbsp;
						    </td>
						</tr>
					 </table>
			</form>
   </body>
</html>
