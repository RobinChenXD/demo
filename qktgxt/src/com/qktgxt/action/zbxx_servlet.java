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
import com.qktgxt.orm.Zbxx;

public class zbxx_servlet extends HttpServlet{
	public void service(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException 
	{
		String type=req.getParameter("type");
		
		if(type.endsWith("zbxxMana")){
			zbxxMana(req, res);
		}
		if(type.endsWith("zbxxAdd")){
			zbxxAdd(req, res);
		}
		if(type.endsWith("zbxxEdit")){
			zbxxEdit(req, res);
		}
		if(type.endsWith("zbxxDel")){
			zbxxDel(req, res);
		}
	}
	
	public void zbxxMana(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		List zbxxList = new ArrayList();
		String sql="select * from t_zbxx where del=? and id !=1";
		Object[] params={0};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				Zbxx zbxx=new Zbxx();
				zbxx.setId(rs.getInt("id"));
				zbxx.setXingming(rs.getString("xingming"));
				zbxx.setSex(rs.getString("sex"));
				zbxx.setCjgzsj(rs.getString("cjgzsj"));
				zbxx.setLoginName(rs.getString("loginName"));
				zbxxList.add(zbxx);
			}
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		
		req.setAttribute("zbxxList", zbxxList);
		req.getRequestDispatcher("zbxx/zbxxMana.jsp").forward(req, res);
	}
	public void zbxxAdd(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		String xingming = req.getParameter("xingming");
		String sex = req.getParameter("sex");
		String cjgzsj = req.getParameter("cjgzsj");
		String loginName = req.getParameter("loginName");
		String loginPass = req.getParameter("loginPass");
		String sql = "insert into t_zbxx values (?,?,?,?,?,?)";

		try{			
			Object[] params={xingming,sex,cjgzsj,loginName,loginPass,0};
			DB mydb=new DB();
			mydb.doUpdate(sql, params);
			mydb.closed();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "zbxx?type=zbxxMana");
		
        String targetURL = "/success.jsp";
		dispatch(targetURL, req, res);
	}
	public void zbxxEdit(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		int id = Integer.parseInt(req.getParameter("id"));
		String xingming = req.getParameter("xingming");
		String sex = req.getParameter("sex");
		String cjgzsj = req.getParameter("cjgzsj");
		
		String sql = "update t_zbxx set xingming=?,sex=?,cjgzsj=? where id=?";
		try{			
			Object[] params={xingming,sex,cjgzsj,id};
			DB mydb=new DB();
			mydb.doUpdate(sql, params);
			mydb.closed();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "zbxx?type=zbxxMana");
		
        String targetURL = "/success.jsp";
		dispatch(targetURL, req, res);

	}
	public void zbxxDel(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		int id = Integer.parseInt(req.getParameter("id"));
		String sql = "update t_zbxx set del=? where id=?";
		try{			
			Object[] params={1,id};
			DB mydb=new DB();
			mydb.doUpdate(sql, params);
			mydb.closed();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "zbxx?type=zbxxMana");
		
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
