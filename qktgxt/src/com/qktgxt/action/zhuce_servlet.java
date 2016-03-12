package com.qktgxt.action;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qktgxt.dao.DB;
import com.qktgxt.orm.Tgfj;
import com.qktgxt.orm.Tgxx;
import com.qktgxt.orm.Yonghu;
import com.qktgxt.util.Tgzt;
import com.qktgxt.util.Yhqx;

public class zhuce_servlet extends HttpServlet{
	public void service(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException 
	{
		String type=req.getParameter("type");
		
		if(type.endsWith("zhuce")){
			zhuce(req, res);
		}
		if(type.endsWith("toEdit")){
			toEdit(req, res);
		}
		if(type.endsWith("grxxEdit")){
			grxxEdit(req, res);
		}
	}
	
	public void zhuce(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		String loginName = req.getParameter("loginName");
		String loginPw = req.getParameter("loginPw");
		String realName = req.getParameter("realName");
		int id = 0;
		String sql = "insert into t_yonghu (loginName,loginPw,name,sex,del) values (?,?,?,?,?)";
		try{
			Object[] params={loginName,loginPw,realName,"男",0};
			DB mydb=new DB();
			id = mydb.doPstmGetId(sql, params);
			mydb.closed();
		}catch(Exception e){
		e.printStackTrace();
		}
		
		Yonghu yonghu = new Yonghu();
		yonghu.setId(id);
		yonghu.setName(realName);
		yonghu.setLoginName(loginName);
		yonghu.setLoginPw(loginPw);
		yonghu.setQx(Yhqx.YH);
		
		req.getSession().setAttribute("userinfo", yonghu);
		req.getRequestDispatcher("/index.jsp").forward(req, res);
	}
	
	public void toEdit(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		Yonghu yonghu = (Yonghu) req.getSession().getAttribute("userinfo");
		
		String sql = "select * from t_yonghu where id=?";
		Object[] params={yonghu.getId()};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				yonghu.setName(rs.getString("name"));
				yonghu.setSex(rs.getString("sex"));
				yonghu.setPid(rs.getString("pid"));
				yonghu.setLxdh(rs.getString("lxdh"));
				yonghu.setDz(rs.getString("dz"));
				yonghu.setYb(rs.getString("yb"));
			}
			rs.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		mydb.closed();
		
		req.setAttribute("grxx", yonghu);
		req.getRequestDispatcher("/yonghu/grxxws.jsp").forward(req, res);		
	}
	public void grxxEdit(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		//个人详细信息填写
		int id = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("name");
		String sex = req.getParameter("sex");
		String pid = req.getParameter("pid");
		String lxdh = req.getParameter("lxdh");
		String dz = req.getParameter("dz");
		String yb = req.getParameter("yb");
		
		String sql = "update t_yonghu set name=?,sex=?,pid=?,lxdh=?,dz=?,yb=? where id=?";
		try{			
			Object[] params={name,sex,pid,lxdh,dz,yb,id};
			DB mydb=new DB();
			mydb.doUpdate(sql, params);
			mydb.closed();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "zhuce?type=toEdit");
		
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
