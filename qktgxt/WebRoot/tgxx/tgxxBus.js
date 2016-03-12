function subVai(zt)
{
	var vBiaoti = document.formAdd.biaoti.value;
	if(vBiaoti.Trim() == "")
	{
		alert("标题不能为空!");
		return false;
	}
	document.getElementById("zt").value=zt;
	if(zt==1)
		return window.confirm("提交后信息将无法修改,确认提交?");
	else
		return true;
}

function chooseVai()
{
	var arrGsid = document.getElementsByName("gsid");
	var bolChoose = false;
	var hidName = "hid_gsid";
	var gsid = "";
	var strGsname = "";
	
	for(var i=0;i<arrGsid.length;i++)
	{
		if(arrGsid[i].checked)
		{
			bolChoose = true;
			
			gsid = arrGsid[i].value;
			strGsname = document.getElementById(hidName+gsid).value;
			
			break;
		}
	}
	
	if(bolChoose == false)
	{
		alert("请选择出租公司!")
	}else{
		var arrRet = new Array();
		
		arrRet[0] = gsid;
		arrRet[1] = strGsname;
		
		window.returnValue = arrRet;
		window.close();
	}
}