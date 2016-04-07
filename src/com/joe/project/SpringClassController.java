package com.joe.project;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.joe.project.LogExcel;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.joe.project.MySessionBean;
import com.joe.project.StoreDAO;

@Controller
public class SpringClassController {
	// COMENTARIO BASE EVE
	 /** Define a host server */
    String host;
    
    /** Define a port */
    int port;
    
    public SpringClassController() {
   	 /** Define a host server */
        host = "localhost";
        
        /** Define a port */
        port = 19999;
	}

	private StoreDAO dao;
	
	@Autowired
	private HttpSession httpSession;
	private LogExcel logExcel;
	
	
	@Autowired
	private MySessionBean sessionBean;
	
	@Autowired
	public void setDao(StoreDAO dao) {
		this.dao = dao;
	}
	
	@RequestMapping("/home")
	public ModelAndView renderHome(){
		if (this.sessionBean.isLogged) {
			return new ModelAndView("home");
		}
		
		return new ModelAndView("redirect:/login");
	}
	
	@RequestMapping("/serverResponse")
	public ModelAndView serverResponse(){
		System.out.println(httpSession.getAttribute("data"));
		if (this.sessionBean.isLogged) {
			return new ModelAndView("serverResponse");
		}

		
		return new ModelAndView("redirect:/login");
	}
	
	@RequestMapping("/profregister")
	public ModelAndView renderProfRegister(){
		if (this.sessionBean.isLogged) {
			return new ModelAndView("profregister");
		}
		
		return new ModelAndView("redirect:/login");
	}
	
	@RequestMapping("/profregister/post")
	public ModelAndView postProfessorRegister(@RequestParam("sn")String sn, @RequestParam("name")String name, @RequestParam("address")String address, @RequestParam("salary")String salary, @RequestParam("birth")String birth, @RequestParam("department")String department, @RequestParam("gender")String gender, @RequestParam("bAction")String bAction){
		System.out.println("Action Triggered");
		String object = bAction + "_" + sn + "_" + name  + "_" + address + "_" + salary + "_" + birth + "_" + gender + "_" + department;
		
		String resultSet = transferData(object);
		
		/* Socket generation */
		if(resultSet.equals(""))
			return new ModelAndView("redirect:/profregister");
		
		/* Set attribute at the session level */
		httpSession.setAttribute("data", resultSet);
		
		return new ModelAndView("serverResponse");
		
	    //return new ModelAndView("serverResponse", "datos", ""+resultSet);
		//return new ModelAndView("redirect:/serverResponse", "datos", resultSet);    
	}
	
	@RequestMapping(value="/studregister", method=RequestMethod.GET)
	public ModelAndView renderStudRegister(){
		System.out.println("INDEX");
		
		if (this.sessionBean.isLogged) {
			return new ModelAndView("studregister");
		}
		
		return new ModelAndView("redirect:/login");
	}
	
	@RequestMapping(value="/studregister" , params="RegisterStudent",method=RequestMethod.POST )
	public ModelAndView postStudRegister(@RequestParam("mat")String mat, @RequestParam("name")String name, @RequestParam("address")String address, @RequestParam("phone")String phone, @RequestParam("career")String career, @RequestParam("plan")String plan, @RequestParam("bAction")String bAction){
		System.out.println("Action Triggered:student reg");
		String object = bAction + "_" + mat + "_" + name  + "_" + address + "_" + phone + "_" + career + "_" + plan ;
		System.out.println(object);
		
		String resultSet = transferData(object);
		
		/* Socket generation */
		if(resultSet.equals(""))
			return new ModelAndView("redirect:/studregister");
		
		/* Set attribute at the session level */
		httpSession.setAttribute("data", resultSet);
		
		return new ModelAndView("serverResponse");
		 
	}
	
	@RequestMapping(value="/studRegister", params="leerExcelLog", method=RequestMethod.POST)
	public ModelAndView leerExcel()
	{
		System.out.println("Action Triggered:leerExcel");
		String message="";
		List<String> excelList= logExcel.readExcel();
		for (String reg : excelList) 
		{
			 System.out.println("voy a mandar a BD :"+reg);
		}
		return new ModelAndView("insertar","msg",message);
	}
	

	
	@RequestMapping("/login")
	public ModelAndView renderLogin(){
		return new ModelAndView("login");
	}
	
	@RequestMapping("/login/user")
	public ModelAndView authenticate(@RequestParam("username")String username, @RequestParam("password")String password, @RequestParam("bLogin")Object button){

		int success = dao.doAuth(username, password);
		
		if (success == 0) {
			return new ModelAndView("redirect:/login");
		}
		
		this.sessionBean.isLogged = true;
		this.sessionBean.username = username;
		return new ModelAndView("redirect:/home");
	}
	
	@RequestMapping("/signup")
	public ModelAndView renderSignup(){
		return new ModelAndView("signup");
	}
	
	@RequestMapping("/signup/user")
	public ModelAndView addRow(@RequestParam("username")String username, @RequestParam("password")String password, @RequestParam("bSignup")String button){
		Object[] params = new Object[]{username, password};
		dao.doSignup(params);
		
		this.sessionBean.isLogged = true;
		this.sessionBean.username = username;
		return new ModelAndView("redirect:/home");
	}
	
	private String transferData(String jObject){
	    System.out.println("SocketClient initialized");
	    Object[] params;
	    String resultSet = "";
	    try {
	        /** Obtain an address object of the server */
	        InetAddress address = InetAddress.getByName(host);
	        /** Establish a socket connection */
	        Socket connection = new Socket(address, port);
	        
	        /** Instantiate a BufferedOutputStream object */
	        BufferedOutputStream bos = new BufferedOutputStream(connection.
	            getOutputStream());

	        /** Instantiate an OutputStreamWriter object with the optional character
	         * encoding.
	         */
	        OutputStreamWriter osw = new OutputStreamWriter(bos);
	        
	        jObject = jObject + (char) 13;
	        /** Write across the socket connection and flush the buffer */
	        osw.write(jObject);
	        osw.flush();
	        
	        /** Instantiate a BufferedInputStream object for reading
	        /** Instantiate a BufferedInputStream object for reading
	         * incoming socket streams.
	         */

	        BufferedInputStream bis = new BufferedInputStream(connection.
	            getInputStream());
	        /**Instantiate an InputStreamReader with the optional
	         * character encoding.
	         */

	        InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");

	        /**Read the socket's InputStream and append to a StringBuffer */
	        int c;
	        StringBuffer instr = new StringBuffer();
	        while ( (c = isr.read()) != 13)
	          instr.append( (char) c);
	        
	        /** Close the socket connection. */
	        connection.close();
	        
	        /** Log to the database **/
	        String[] action = jObject.split("_", 2);

	        /** Saves the Log Operation when the operation is a success **/
	        String nuevo=instr.toString();
	        String[] nuevoArr = nuevo.split(" ");
	        
	        if(nuevoArr[0].equals("ERROR"))
	        {
	        	System.out.println("NOT INSERTED."+nuevo);
	        }
	        else
	        {
	        	if(action[0].equals("Register") || action[0].equals("RegisterStudent") ){
			        params = new Object[]{sessionBean.username, instr};
			        dao.doLog(params);
			        System.out.println("SUCCESS:"+instr);
		        }
		         else{
		        	 resultSet = instr.toString();
		        }
	        }
	       }
	      catch (IOException f) {
	        System.out.println("IOException: " + f);
	      }
	      catch (Exception g) {
	        System.out.println("Exception: " + g);
	      }
	    
	    return resultSet;
	}

}
