package com.dzig;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class AuthServlet extends HttpServlet {
	
	 private static final Logger log = Logger.getLogger(HttpServlet.class.getName());
	
	 public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	        UserService userService = UserServiceFactory.getUserService();
	        User user = userService.getCurrentUser();
	        if (user != null) {
	            req.setAttribute("user", user);
	            	resp.sendRedirect("index.html?email=" + user.getEmail() + "&fedaratedId=" + user.getFederatedIdentity() + "&nickname=" + user.getNickname() + "&userId="+ user.getUserId());
	           
	        } else {
	         
	        	resp.sendRedirect("index.html?fail=true");
	           
	        }
	    }
}
