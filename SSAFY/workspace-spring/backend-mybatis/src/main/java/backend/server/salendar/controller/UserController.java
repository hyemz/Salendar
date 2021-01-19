package backend.server.salendar.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import backend.server.salendar.dto.User;
import backend.server.salendar.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@RequestMapping("/regist")
	public String regist(User user) {
		try {
			service.regist(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
	
	@RequestMapping("/login")
	public String login(User user, HttpSession session) {
		try {
			if(service.login(user) == 1) {
				session.setAttribute("userinfo",service.search(user.getUsr_email()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
	
//	@RequestMapping("/logout")
//	public String logout(HttpSession session) {
//		session.invalidate();
//		return "redirect:/";
//	}

	
}
