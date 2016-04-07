package com.joe.project;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.joe.project.MySessionBean;
import com.joe.project.StoreDAO;

@Controller
public class SpringClassController {
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
	
	
	
	@Autowired
	private MySessionBean sessionBean;
	
	@Autowired
	public void setDao(StoreDAO dao) {
		this.dao = dao;
	}
	
	@RequestMapping("/api")
	public ModelAndView getApi(@RequestParam("api")String api, @RequestParam("action")String action){
		/* Check if the API key is valid */
		int success = dao.doAuthApi(api);
		
		if (success == 0) {
			return new ModelAndView("api", "data", "INVALID API KEY");
		}
		
		if(action.equals("professorSalaries")){
			String object = "Query" + "_" + "_"  + "_" + "_"  + "_"  + "_"  + "_";
			String resultSet = transferData(object);
			
			return new ModelAndView("api", "data", resultSet);
		}else if (action.equals("studentCourses")) {
			
		}else {
			return new ModelAndView("api", "data", "UNDEFINED ACTION");
		}
		return null;
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
	        
	        /* Log to the database */
	        String[] action = jObject.split("_", 2);
	        
	        if(action[0].equals("Register")){
		        params = new Object[]{sessionBean.username, instr};
		        dao.doLog(params);
	        } else{

	        	resultSet = instr.toString();
	        }
	        System.out.println(instr);
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
