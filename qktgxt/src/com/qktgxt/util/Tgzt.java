package com.qktgxt.util;

//投稿状态静态类
public class Tgzt {
	public static final int WTJ=0;	//未提交
	public static final int ZJSP=1;	//审批中
	public static final int ZJTY=2;	//专家审批通过
	public static final int ZJTH=3;	//专家审批不通过
	public static final int ZBTY=4;	//主编审批同意
	public static final int ZBTH=5;	//主编审批不通过
	
	
	public static String showZt(int type){
		String[] arrZt={"未提交","审批中","专家审批通过","专家审批不通过","主编审批通过","主编审批不通过"};
		return arrZt[type];
	}
}
