function subVai()
{
	var vXm = document.formAdd.xingming.value;
	if(vXm.Trim() == "")
	{
		alert("主编姓名不能为空!");
		return false;
	}
	return true;
}