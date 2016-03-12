package com.qktgxt.orm;

//投稿信息
public class Tgxx {
	private int id;
	private int yonghu; 	//用户
	private String biaoti;	//标题
	private String zy;		//摘引
	private String gjz;		//关键字
	private String tgrq;	//投稿日期
	private int zt;			//当前状态
	private int del;		//删除标识
	
	private String strYonghu;
	private String strZt;
	
	private Tgfj tgfj;
	private Zjps zjps;
	private Zbps zbps;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getYonghu() {
		return yonghu;
	}

	public void setYonghu(int yonghu) {
		this.yonghu = yonghu;
	}

	public String getBiaoti() {
		return biaoti;
	}

	public void setBiaoti(String biaoti) {
		this.biaoti = biaoti;
	}

	public String getZy() {
		return zy;
	}

	public void setZy(String zy) {
		this.zy = zy;
	}

	public String getGjz() {
		return gjz;
	}

	public void setGjz(String gjz) {
		this.gjz = gjz;
	}

	public String getTgrq() {
		return tgrq;
	}

	public void setTgrq(String tgrq) {
		this.tgrq = tgrq;
	}

	public int getZt() {
		return zt;
	}

	public void setZt(int zt) {
		this.zt = zt;
	}

	public int getDel() {
		return del;
	}

	public void setDel(int del) {
		this.del = del;
	}

	public String getStrYonghu() {
		return strYonghu;
	}

	public void setStrYonghu(String strYonghu) {
		this.strYonghu = strYonghu;
	}

	public String getStrZt() {
		return strZt;
	}

	public void setStrZt(String strZt) {
		this.strZt = strZt;
	}

	public Tgfj getTgfj() {
		return tgfj;
	}

	public void setTgfj(Tgfj tgfj) {
		this.tgfj = tgfj;
	}

	public Zjps getZjps() {
		return zjps;
	}

	public void setZjps(Zjps zjps) {
		this.zjps = zjps;
	}

	public Zbps getZbps() {
		return zbps;
	}

	public void setZbps(Zbps zbps) {
		this.zbps = zbps;
	}
	
}
