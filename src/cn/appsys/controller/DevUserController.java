package cn.appsys.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.dao.DevUserMapper;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.DevUserService;
@Controller
@RequestMapping(value="/dev")
public class DevUserController {
	private Logger logger=Logger.getLogger(DevUserController.class);
	@Resource
	private DevUserService dev;
	//系统入口
   @RequestMapping(value="/index.html")
   public String indexs() {
	   return "index";	   
   }
   //进入APP开发者平台--登录页面
   @RequestMapping(value="/devlogin.html")
   public String devLogin() {
	   return "devlogin";
   }
   //验证登录是否成功
   @RequestMapping(value="/dologin.html")
   public String LoginSuccess(@RequestParam String devCode,@RequestParam String devPassword
		   ,HttpSession session,HttpServletRequest request) {
	   logger.info("login=================");
	   logger.info("devCode"+devCode+"devPassword:"+devPassword);
   	   DevUser devUser=null;
   	devUser=dev.selectDevLogin(devCode,devPassword);
   	   if(devUser != null) {
   		   logger.debug("登入成功====="+devUser.getDevName());
   		session.setAttribute("devUserSession", devUser);
   		return "developer/main";
   	   }
	   return "devlogin";	   //否则，还是进入登录页面
   }
}
