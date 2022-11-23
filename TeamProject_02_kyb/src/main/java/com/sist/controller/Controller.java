package com.sist.controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.action.CompanyAction;

//@WebServlet("*.do")
public class Controller extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	HashMap<String, CompanyAction> map;
	
	public Controller() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		
		map = new HashMap<String, CompanyAction>();		
		
		String path = config.getServletContext().getRealPath("WEB-INF");
		try {
			FileReader fr = new FileReader(path + "/company.properties");
			Properties prop = new Properties();
			prop.load(fr);
			Iterator iter = prop.keySet().iterator();
			while(iter.hasNext()) {
				String cmd = (String) iter.next();
				String clsName = (String)prop.get(cmd);
				map.put(cmd, (CompanyAction)Class.forName(clsName).newInstance());
			}
			
		
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
		
	}






	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		//System.out.println("uri:"+uri);
		
		String cmd = uri.substring(uri.lastIndexOf("/")+1);
		//System.out.println(cmd);
		
		CompanyAction action=null;
		String view = "";		
		action = map.get(cmd);		
		view = action.pro(request, response);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}