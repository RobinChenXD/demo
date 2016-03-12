package com.qktgxt.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import com.jspsmart.upload.SmartUpload;
import com.qktgxt.dao.DB;
import com.qktgxt.orm.Tgfj;
import com.qktgxt.orm.Tgxx;
import com.qktgxt.orm.Yonghu;
import com.qktgxt.util.DateUtils;
import com.qktgxt.util.Tgzt;

public class tgxx_servlet extends HttpServlet{
	public void service(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException 
	{
		String type=req.getParameter("type");
		if(type.endsWith("tgxxMana")){
			tgxxManage(req, res);
		}
		if(type.endsWith("tgxxSearch")){
			tgxxSearch(req, res);
		}
		if(type.endsWith("tgxxShow")){
			tgxxShow(req, res);
		}
		if(type.endsWith("tgxxShowSearch")){
			tgxxShowSearch(req, res);
		}
		if(type.endsWith("tgxxAdd")){
			tgxxAdd(req, res);
		}
		if(type.endsWith("tgxxEdit")){
			tgxxEdit(req, res);
		}
		if(type.endsWith("tgxxDel")){
			tgxxDel(req, res);
		}
		if(type.endsWith("tgxxTijiao")){
			tgxxTijiao(req, res);
		}
		if(type.endsWith("down")){
			down(req, res);
		}
	}
	
	public void tgxxShowSearch(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		String biaoti = req.getParameter("biaoti");
		List tgxxList=new ArrayList();
		String sql = "select id,(select name from t_yonghu tb where tb.id=yonghu)strYonghu," +
					 "biaoti,zy,gjz,tgrq,zt from t_tgxx where zt=? and biaoti like '%"+biaoti+"%'";
		Object[] params={Tgzt.ZBTY};
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
				int zt = rs.getInt("zt");
				tgxx.setZt(zt);
				tgxx.setStrZt(Tgzt.showZt(zt));
				
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
		
		req.setAttribute("biaoti", biaoti);
		req.setAttribute("tgxxList", tgxxList);
		req.getRequestDispatcher("/tgxx/tgxxShow.jsp").forward(req, res);
	}
	public void tgxxShow(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		List tgxxList=new ArrayList();
		String sql = "select id,(select name from t_yonghu tb where tb.id=yonghu)strYonghu,biaoti,zy,gjz,tgrq,zt from t_tgxx where zt=?";
		Object[] params={Tgzt.ZBTY};
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
				int zt = rs.getInt("zt");
				tgxx.setZt(zt);
				tgxx.setStrZt(Tgzt.showZt(zt));
				
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
		req.getRequestDispatcher("/tgxx/tgxxShow.jsp").forward(req, res);
	}
	public void down(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		int fjid = Integer.parseInt(req.getParameter("fjid"));
		String sql = "select * from t_tgfj where id=?";
		Object[] params={fjid};
		DB mydb=new DB();
		try{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			if(rs.next()){
		        String fujianPath=rs.getString("fjlj");
		        String fujianYuashiMing=rs.getString("fjmc");
		        fujianYuashiMing=java.net.URLDecoder.decode(fujianYuashiMing,"UTF-8");
		        SmartUpload su = new SmartUpload(); // 新建一个SmartUpload对象 
		        PageContext pageContext = JspFactory.getDefaultFactory().
		        									 getPageContext(this, req,res,null,true,8192,true);
		        su.initialize(pageContext); // 初始化 

		        su.setContentDisposition(null); 
		        
		        su.downloadFile(fujianPath, null, new String(fujianYuashiMing.getBytes(), "ISO8859-1")); // 下载中文文件
		        JspWriter out = pageContext.getOut();
		        out.clear();
		        out=pageContext.pushBody(); 
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			req.getRequestDispatcher("/err/err.jsp").forward(req, res);
		}
	}
	public void tgxxSearch(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		String biaoti = req.getParameter("biaoti");
		Yonghu yonghu = (Yonghu) req.getSession().getAttribute("userinfo");
		List tgxxList=new ArrayList();
		String sql = "select id,biaoti,zy,gjz,tgrq,zt from t_tgxx where del=? and yonghu=? and biaoti like '%"+biaoti+"%'";
		Object[] params={0,yonghu.getId()};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				Tgxx tgxx=new Tgxx();
				tgxx.setId(rs.getInt("id"));
				tgxx.setBiaoti(rs.getString("biaoti"));
				tgxx.setZy(rs.getString("zy"));
				tgxx.setGjz(rs.getString("gjz"));
				tgxx.setTgrq(rs.getString("tgrq"));
				int zt = rs.getInt("zt");
				tgxx.setZt(zt);
				tgxx.setStrZt(Tgzt.showZt(zt));
				
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
		
		req.setAttribute("biaoti", biaoti);
		req.setAttribute("tgxxList", tgxxList);
		req.getRequestDispatcher("/tgxx/tgxxMana.jsp").forward(req, res);
	}
	public void tgxxManage(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		Yonghu yonghu = (Yonghu) req.getSession().getAttribute("userinfo");
		List tgxxList=new ArrayList();
		String sql = "select id,biaoti,zy,gjz,tgrq,zt from t_tgxx where del=? and yonghu=?";
		Object[] params={0,yonghu.getId()};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				Tgxx tgxx=new Tgxx();
				tgxx.setId(rs.getInt("id"));
				tgxx.setBiaoti(rs.getString("biaoti"));
				tgxx.setZy(rs.getString("zy"));
				tgxx.setGjz(rs.getString("gjz"));
				tgxx.setTgrq(rs.getString("tgrq"));
				int zt = rs.getInt("zt");
				tgxx.setZt(zt);
				tgxx.setStrZt(Tgzt.showZt(zt));
				
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
		req.getRequestDispatcher("/tgxx/tgxxMana.jsp").forward(req, res);
	}
	public void tgxxAdd(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		Yonghu yonghu = (Yonghu) req.getSession().getAttribute("userinfo");
		
		String biaoti = req.getParameter("biaoti");
		String zy = req.getParameter("zy");
		String gjz = req.getParameter("gjz");
		String tgrq = DateUtils.getShortStrDate();
		int zt = Integer.parseInt(req.getParameter("zt"));
		String fName = req.getParameter("fj");
		String path = req.getParameter("fullpath");

		String sql = "insert into t_tgxx values (?,?,?,?,?,?,?)";
		
		try{			
			Object[] params={yonghu.getId(),biaoti,zy,gjz,tgrq,zt,0};
			DB mydb=new DB();
			int tgid = mydb.doPstmGetId(sql, params);
			
			sql = "insert into t_tgfj values(?,?,?)";
			Object[] params1={tgid,fName,path};
			mydb.doUpdate(sql, params1);
			mydb.closed();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "tgxx?type=tgxxMana");
		
        String targetURL = "/success.jsp";
		dispatch(targetURL, req, res);
	}
	public void tgxxEdit(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		int tgxxid = Integer.parseInt(req.getParameter("tgxxid"));
		String biaoti = req.getParameter("biaoti");
		String zy = req.getParameter("zy");
		String gjz = req.getParameter("gjz");
		int zt = Integer.parseInt(req.getParameter("zt"));
		
		String strTgfjId = req.getParameter("tgfjid");
		int tgfjid=0;
		String fName = req.getParameter("fj");
		String path = req.getParameter("fullpath");

		String sql = "update t_tgxx set biaoti=?,zy=?,gjz=?,zt=? where id=?";
		
		try{			
			Object[] params={biaoti,zy,gjz,zt,tgxxid};
			DB mydb=new DB();
			mydb.doUpdate(sql, params);
			if("0".equals(strTgfjId)){
				sql = "insert into t_tgfj values(?,?,?)";
				Object[] params1={tgxxid,fName,path};
				mydb.doUpdate(sql, params1);
			}else{
				tgfjid = Integer.parseInt(strTgfjId);
				sql = "update t_tgfj set fjmc=?,fjlj=? where id=?";
				Object[] params1={fName,path,tgfjid};
				mydb.doUpdate(sql, params1);
			}
			mydb.closed();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "tgxx?type=tgxxMana");
		
        String targetURL = "/success.jsp";
		dispatch(targetURL, req, res);
	}
	public void tgxxDel(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		int tgxxid = Integer.parseInt(req.getParameter("tgxxid"));
		String sql = "update t_tgxx set del=? where id=?";
		try{			
			Object[] params={1,tgxxid};
			DB mydb=new DB();
			mydb.doUpdate(sql, params);
			mydb.closed();
		}catch(Exception e){
			e.printStackTrace();
		}
		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "tgxx?type=tgxxMana");
		
        String targetURL = "/success.jsp";
		dispatch(targetURL, req, res);
	}
	public void tgxxTijiao(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		int tgxxid = Integer.parseInt(req.getParameter("tgxxid"));
		String sql = "update t_tgxx set zt=? where id=?";
		try{			
			Object[] params={Tgzt.ZJSP,tgxxid};
			DB mydb=new DB();
			mydb.doUpdate(sql, params);
			mydb.closed();
		}catch(Exception e){
			e.printStackTrace();
		}
		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "tgxx?type=tgxxMana");
		
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
