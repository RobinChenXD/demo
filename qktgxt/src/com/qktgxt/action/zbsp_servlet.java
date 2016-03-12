package com.qktgxt.action;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qktgxt.dao.DB;
import com.qktgxt.orm.Tgfj;
import com.qktgxt.orm.Tgxx;
import com.qktgxt.orm.Yonghu;
import com.qktgxt.orm.Zbps;
import com.qktgxt.orm.Zjps;
import com.qktgxt.util.DateUtils;
import com.qktgxt.util.Tgzt;

public class zbsp_servlet extends HttpServlet{
	public void service(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException 
	{
		String type=req.getParameter("type");
		
		if(type.endsWith("zbspMana")){
			zbspMana(req, res);
		}
		if(type.endsWith("zbspToOpt")){
			zbspToOpt(req, res);
		}
		if(type.endsWith("zbspOpt")){
			zbspOpt(req, res);
		}
		if(type.endsWith("zbspHis")){
			zbspHis(req, res);
		}
	}
	public void zbspHis(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		//主编审批历史信息查询
		Yonghu yonghu = (Yonghu) req.getSession().getAttribute("userinfo");
		List tgxxList=new ArrayList();
		String sql = "select id,(select name from t_yonghu tb where tb.id=yonghu)strYonghu," +
					 " biaoti,zy,gjz,tgrq from t_tgxx where id in (select tgid from t_zbps where zbid=?)";
		Object[] params={yonghu.getId()};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				Tgxx tgxx=new Tgxx();
				tgxx.setId(rs.getInt("id"));
				tgxx.setStrYonghu(rs.getString("strYonghu"));
				tgxx.setBiaoti(rs.getString("biaoti"));
				tgxx.setZy(rs.getString("zy"));
				tgxx.setGjz(rs.getString("gjz"));
				tgxx.setTgrq(rs.getString("tgrq"));
				
				sql = "select * from t_tgfj where tgxx="+tgxx.getId();
				mydb.doPstm(sql, null);
				ResultSet fjrs = mydb.getRs();
				Tgfj tgfj = new Tgfj();
				if(fjrs.next()){
					tgfj.setId(fjrs.getInt("id"));
					tgfj.setFjmc(fjrs.getString("fjmc"));
					tgfj.setFjlj(fjrs.getString("fjlj"));
				}
				fjrs.close();
				tgxx.setTgfj(tgfj);
				sql = "select zbpsjg,zbpsrq from t_zbps where tgid="+tgxx.getId();
				
				mydb.doPstm(sql, null);
				ResultSet zbpsrs = mydb.getRs();
				Zbps zbps = new Zbps();
				if(zbpsrs.next()){
					int zbpsjg = zbpsrs.getInt("zbpsjg");
					zbps.setStrZbpsjg(Tgzt.showZt(zbpsjg));
					zbps.setZbpsrq(zbpsrs.getString("zbpsrq"));
				}
				tgxx.setZbps(zbps);
				zbpsrs.close();
				tgxxList.add(tgxx);
			}
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		
		req.setAttribute("tgxxList", tgxxList);
		req.getRequestDispatcher("/zbsp/zbspHis.jsp").forward(req, res);
	}
	public void zbspToOpt(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		int tgxxId = Integer.parseInt(req.getParameter("tgxx"));
		Tgxx tgxx=new Tgxx();
		String sql = "select id,(select name from t_yonghu tb where tb.id=yonghu)strYonghu," +
		 			 "biaoti,zy,gjz,tgrq from t_tgxx where id=?";
		Object[] params={tgxxId};
		DB mydb=new DB();
		try
		{
		mydb.doPstm(sql, params);
		ResultSet rs=mydb.getRs();
		while(rs.next())
		{
			
			tgxx.setId(rs.getInt("id"));
			tgxx.setStrYonghu(rs.getString("strYonghu"));
			tgxx.setBiaoti(rs.getString("biaoti"));
			tgxx.setZy(rs.getString("zy"));
			tgxx.setGjz(rs.getString("gjz"));
			tgxx.setTgrq(rs.getString("tgrq"));
			
			sql = "select * from t_tgfj where tgxx="+tgxx.getId();
			mydb.doPstm(sql, null);
			ResultSet fjrs = mydb.getRs();
			Tgfj tgfj = new Tgfj();
			if(fjrs.next()){
				tgfj.setId(fjrs.getInt("id"));
				tgfj.setFjmc(fjrs.getString("fjmc"));
				tgfj.setFjlj(fjrs.getString("fjlj"));
			}
			fjrs.close();
			sql = "select (select xingming from t_zjxx tb where tb.id=zjid)strZjxx,zjyj,zjpssj from t_zjps where tgid="+tgxx.getId();
			mydb.doPstm(sql, null);
			ResultSet zjpsrs = mydb.getRs();
			Zjps zjps = new Zjps();
			if(zjpsrs.next()){
				zjps.setStrZjxx(zjpsrs.getString("strZjxx"));
				zjps.setZjyj(zjpsrs.getString("zjyj"));
				zjps.setZjpssj(zjpsrs.getString("zjpssj"));
			}
			zjpsrs.close();
			tgxx.setZjps(zjps);
			tgxx.setTgfj(tgfj);
		}
		rs.close();
		}
		catch(Exception e)
		{
		e.printStackTrace();
		}
		mydb.closed();
		
		req.setAttribute("tgxx", tgxx);
		req.getRequestDispatcher("/zbsp/zbspOpt.jsp").forward(req, res);
	}
	public void zbspOpt(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		int tgxx = Integer.parseInt(req.getParameter("tgxx"));
		int zt = Integer.parseInt(req.getParameter("zt"));
		
		String sql = "update t_tgxx set zt=? where id=?";
		try{			
			Object[] params={zt,tgxx};
			DB mydb=new DB();
			mydb.doUpdate(sql, params);
			
			Yonghu yonghu = (Yonghu) req.getSession().getAttribute("userinfo");
			String psDate = DateUtils.getShortStrDate();
			String zbyj = req.getParameter("zbyj");
			sql = "insert into t_zbps values (?,?,?,?,?,?)";
			Object[] params1={tgxx,yonghu.getId(),zt,zbyj,psDate,0};
			mydb.doUpdate(sql, params1);
			mydb.closed();
		}catch(Exception e){
			e.printStackTrace();
		}
		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "zbsp?type=zbspMana");
		
        String targetURL = "/success.jsp";
		dispatch(targetURL, req, res);
	}
	public void zbspMana(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		List tgxxList = new ArrayList();
		String sql = "select id,(select name from t_yonghu tb where tb.id=yonghu)strYonghu," +
					 "biaoti,zy,gjz,tgrq from t_tgxx where zt=?";
		Object[] params={Tgzt.ZJTY};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				Tgxx tgxx=new Tgxx();
				tgxx.setId(rs.getInt("id"));
				tgxx.setStrYonghu(rs.getString("strYonghu"));
				tgxx.setBiaoti(rs.getString("biaoti"));
				tgxx.setZy(rs.getString("zy"));
				tgxx.setGjz(rs.getString("gjz"));
				tgxx.setTgrq(rs.getString("tgrq"));
				
				sql = "select * from t_tgfj where tgxx="+tgxx.getId();
				mydb.doPstm(sql, null);
				ResultSet fjrs = mydb.getRs();
				Tgfj tgfj = new Tgfj();
				if(fjrs.next()){
					tgfj.setId(fjrs.getInt("id"));
					tgfj.setFjmc(fjrs.getString("fjmc"));
					tgfj.setFjlj(fjrs.getString("fjlj"));
				}
				fjrs.close();
				tgxx.setTgfj(tgfj);
				tgxxList.add(tgxx);
			}
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		
		req.setAttribute("tgxxList", tgxxList);
		req.getRequestDispatcher("/zbsp/zbspMana.jsp").forward(req, res);
	}
	
	public void dispatch(String targetURI,HttpServletRequest request,HttpServletResponse response) 
	{
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(targetURI);
		try 
		{
		    dispatch.forward(request, response);
		    return;
		} 
		catch (ServletException e) 
		{
                    e.printStackTrace();
		} 
		catch (IOException e) 
		{
			
		    e.printStackTrace();
		}
	}
}
