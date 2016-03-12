package com.qktgxt.action;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qktgxt.dao.DB;
import com.qktgxt.orm.Yonghu;
import com.qktgxt.util.Yhqx;

public class login_servlet extends HttpServlet{
	public void service(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException 
	{
		String type=req.getParameter("type");
		
		if(type.endsWith("login"))
		{
			int loginType = Integer.parseInt(req.getParameter("loginType"));
			switch(loginType){
				case Yhqx.ZB:	//主编登录
					zbLogin(req, res);
					break;
				case Yhqx.ZJ:	//专家登录
					zjLogin(req, res);
					break;
				case Yhqx.YH:	//普通用户登录
					yhLogin(req, res);
					break;
				default:break;
			}
		}
		if(type.endsWith("userpw"))
		{
			userpw(req, res);
		}
		
	}
	
	public void userpw(HttpServletRequest req,HttpServletResponse res){
		String oldPass = req.getParameter("userPw");
		String newpass = req.getParameter("userPw1");
		
		Yonghu yonghu = (Yonghu) req.getSession().getAttribute("userinfo");
		
		if(oldPass.equals(yonghu.getLoginPw())){
			int id = yonghu.getId();
			int qx = yonghu.getQx();
			String sql="";
			if(Yhqx.YH==qx){
				//普通用户密码修改
				sql = "update t_yonghu set loginPw='"+newpass+"' where id="+id;
			}else if(Yhqx.ZJ==qx){
				//家用密码修改
				sql = "update t_zjxx set loginPass='"+newpass+"' where id="+id;
			}else if(Yhqx.ZB==qx){
				//主编密码修改
				sql = "update t_zbxx set loginPass='"+newpass+"' where id="+id;
			}
			
			Object[] params={};
			DB mydb=new DB();
			mydb.doUpdate(sql, params);
			mydb.closed();
			
			req.setAttribute("message", "操作成功!");
			req.setAttribute("path", "userPw.jsp");
			yonghu.setLoginPw(newpass);
			req.getSession().setAttribute("userinfo", yonghu);
	        String targetURL = "/success.jsp";
			dispatch(targetURL, req, res);
		}else{
			req.setAttribute("message", "旧密码错误!");
			req.setAttribute("path", "userPw.jsp");
			
	        String targetURL = "/success.jsp";
			dispatch(targetURL, req, res);
		}
	}
	public void zbLogin(HttpServletRequest req,HttpServletResponse res){
		String loginName = req.getParameter("loginName");
		String loginPw = req.getParameter("loginPw");
		String sql = "select id,xingming from t_zbxx where loginName=? and loginPass=? and del=?";
		
		Object[] params={loginName,loginPw,0};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			if(rs.next())
			{
				Yonghu yonghu=new Yonghu();
				yonghu.setId(rs.getInt("id"));
				yonghu.setName(rs.getString("xingming"));
				yonghu.setLoginName(loginName);
				yonghu.setLoginPw(loginPw);
				yonghu.setQx(Yhqx.ZB);
				
				rs.close();
				mydb.closed();
				
				req.getSession().setAttribute("userinfo", yonghu);
				req.getRequestDispatcher("/index.jsp").forward(req, res);
			}else{
				req.setAttribute("message", "用户名或密码错误!");
				req.setAttribute("path", "login.jsp");
				
		        String targetURL = "/success.jsp";
				dispatch(targetURL, req, res);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void zjLogin(HttpServletRequest req,HttpServletResponse res){
		String loginName = req.getParameter("loginName");
		String loginPw = req.getParameter("loginPw");
		
		String sql = "select id,xingming from t_zjxx where loginName=? and loginPass=? and del=?";
		
		Object[] params={loginName,loginPw,0};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			if(rs.next())
			{
				Yonghu yonghu=new Yonghu();
				yonghu.setId(rs.getInt("id"));
				yonghu.setName(rs.getString("xingming"));
				yonghu.setLoginName(loginName);
				yonghu.setLoginPw(loginPw);
				yonghu.setQx(Yhqx.ZJ);
				
				rs.close();
				mydb.closed();
				
				req.getSession().setAttribute("userinfo", yonghu);
				req.getRequestDispatcher("/index.jsp").forward(req, res);
			}else{
				req.setAttribute("message", "用户名或密码错误!");
				req.setAttribute("path", "login.jsp");
				
		        String targetURL = "/success.jsp";
				dispatch(targetURL, req, res);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void yhLogin(HttpServletRequest req,HttpServletResponse res){
		String loginName = req.getParameter("loginName");
		String loginPw = req.getParameter("loginPw");
		
		String sql = "select id,name from t_yonghu where loginName=? and loginPw=? and del=?";
		Object[] params={loginName,loginPw,0};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			if(rs.next())
			{
				Yonghu yonghu=new Yonghu();
				yonghu.setId(rs.getInt("id"));
				yonghu.setName(rs.getString("name"));
				yonghu.setLoginName(loginName);
				yonghu.setLoginPw(loginPw);
				yonghu.setQx(Yhqx.YH);
				
				rs.close();
				mydb.closed();
				
				req.getSession().setAttribute("userinfo", yonghu);
				req.getRequestDispatcher("/index.jsp").forward(req, res);
			}else{
				req.setAttribute("message", "用户名或密码错误!");
				req.setAttribute("path", "login.jsp");
				
		        String targetURL = "/success.jsp";
				dispatch(targetURL, req, res);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
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
