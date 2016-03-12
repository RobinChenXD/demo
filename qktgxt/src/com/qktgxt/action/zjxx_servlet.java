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
import com.qktgxt.orm.Zjxx;

public class zjxx_servlet extends HttpServlet{
	public void service(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException 
	{
		String type=req.getParameter("type");
		
		if(type.endsWith("zjxxMana")){
			zjxxMana(req, res);
		}
		if(type.endsWith("zjxxAdd")){
			zjxxAdd(req, res);
		}
		if(type.endsWith("zjxxEdit")){
			zjxxEdit(req, res);
		}
		if(type.endsWith("zjxxDel")){
			zjxxDel(req, res);
		}
	}
	public void zjxxMana(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		List zjxxList = new ArrayList();
		String sql="select * from t_zjxx where del=?";
		Object[] params={0};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				Zjxx zjxx=new Zjxx();
				zjxx.setId(rs.getInt("id"));
				zjxx.setXingming(rs.getString("xingming"));
				zjxx.setSex(rs.getString("sex"));
				zjxx.setPid(rs.getString("pid"));
				zjxx.setLxdh(rs.getString("lxdh"));
				zjxx.setYb(rs.getString("yb"));
				zjxx.setDz(rs.getString("dz"));
				zjxx.setYjfx(rs.getString("yjfx"));
				zjxx.setLoginName(rs.getString("loginName"));
				zjxx.setLoginPass(rs.getString("loginPass"));
				zjxxList.add(zjxx);
			}
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		
		req.setAttribute("zjxxList", zjxxList);
		req.getRequestDispatcher("zjxx/zjxxMana.jsp").forward(req, res);
	}
	public void zjxxAdd(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		String xingming = req.getParameter("xingming");
		String sex = req.getParameter("sex");
		String pid = req.getParameter("pid");
		String lxdh = req.getParameter("lxdh");
		String dz = req.getParameter("dz");
		String yb = req.getParameter("yb");
		String yjfx = req.getParameter("yjfx");
		String loginName = req.getParameter("loginName");
		String loginPass = req.getParameter("loginPass");
		
		String sql = "insert into t_zjxx values (?,?,?,?,?,?,?,?,?,?)";
		try{			
			Object[] params={xingming,sex,pid,lxdh,dz,yb,yjfx,loginName,loginPass,0};
			DB mydb=new DB();
			mydb.doUpdate(sql, params);
			mydb.closed();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "zjxx?type=zjxxMana");
		
        String targetURL = "/success.jsp";
		dispatch(targetURL, req, res);
	}
	public void zjxxEdit(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		int id = Integer.parseInt(req.getParameter("id"));
		String xingming = req.getParameter("xingming");
		String sex = req.getParameter("sex");
		String pid = req.getParameter("pid");
		String lxdh = req.getParameter("lxdh");
		String dz = req.getParameter("dz");
		String yb = req.getParameter("yb");
		String yjfx = req.getParameter("yjfx");
		
		String sql = "update t_zjxx set xingming=?,sex=?,pid=?,lxdh=?,dz=?,yb=?,yjfx=? where id=?";
		try{			
			Object[] params={xingming,sex,pid,lxdh,dz,yb,yjfx,id};
			DB mydb=new DB();
			mydb.doUpdate(sql, params);
			mydb.closed();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "zjxx?type=zjxxMana");
		
        String targetURL = "/success.jsp";
		dispatch(targetURL, req, res);
	}
	public void zjxxDel(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		int id = Integer.parseInt(req.getParameter("id"));
		String sql = "update t_zjxx set del=? where id=?";
		try{			
			Object[] params={1,id};
			DB mydb=new DB();
			mydb.doUpdate(sql, params);
			mydb.closed();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "zjxx?type=zjxxMana");
		
        String targetURL = "/success.jsp";
		dispatch(targetURL, req, res);
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
