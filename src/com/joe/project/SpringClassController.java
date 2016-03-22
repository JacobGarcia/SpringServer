package com.joe.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.joe.project.MySessionBean;
import com.joe.project.StoreDAO;

@Controller
public class SpringClassController {

	private StoreDAO dao;
	
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
		return new ModelAndView("redirect:/home");
	}
	
	@RequestMapping("/signup")
	public ModelAndView renderSignup(){
		return new ModelAndView("signup");
	}
	
	@RequestMapping("/signup/user")
	public ModelAndView addRow(@RequestParam("username")Object username, @RequestParam("password")Object password, @RequestParam("bSignup")Object button){
		Object[] params = new Object[]{username, password};
		dao.doSignup(params);
		
		this.sessionBean.isLogged = true;
		return new ModelAndView("redirect:/home");
	}

}
