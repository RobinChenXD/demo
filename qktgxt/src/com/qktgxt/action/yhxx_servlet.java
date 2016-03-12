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
import com.qktgxt.orm.Yonghu;

public class yhxx_servlet extends HttpServlet{
	public void service(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException 
	{
		String type=req.getParameter("type");
		if(type.endsWith("yhxxMana")){
			yonghuMana(req, res);
		}
		if(type.endsWith("yhxxDel")){
			yhxxDel(req, res);
		}
	}
	
	public void yhxxDel(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		int id = Integer.parseInt(req.getParameter("id"));
		int del = Integer.parseInt(req.getParameter("del"));
		
		String sql = "update t_yonghu set del=? where id=?";
		try{			
			Object[] params={del,id};
			DB mydb=new DB();
			mydb.doUpdate(sql, params);
			mydb.closed();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		req.setAttribute("message", "²Ù×÷³É¹¦");
		req.setAttribute("path", "yhxx?type=yhxxMana");
		
        String targetURL = "/success.jsp";
		dispatch(targetURL, req, res);
	}
	public void yonghuMana(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		List yonghuList=new ArrayList();
		String sql = "select id,name,sex,pid,lxdh,dz,yb,loginName,del from t_yonghu";
		Object[] params={};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				Yonghu yonghu=new Yonghu();
				yonghu.setId(rs.getInt("id"));
				yonghu.setName(rs.getString("name"));
				yonghu.setSex(rs.getString("sex"));
				yonghu.setPid(rs.getString("pid"));
				yonghu.setLxdh(rs.getString("lxdh"));
				yonghu.setDz(rs.getString("dz"));
				yonghu.setYb(rs.getString("yb"));
				yonghu.setLoginName(rs.getString("loginName"));
				yonghu.setDel(rs.getInt("del"));
				yonghuList.add(yonghu);
			}
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		
		req.setAttribute("yhxxList", yonghuList);
		req.getRequestDispatcher("/yhxx/yhxxMana.jsp").forward(req, res);
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
