package cn.appsys.service;


import cn.appsys.pojo.DevUser;

public interface DevUserService {
	//验证用户名和密码是否正确
    public DevUser selectDevLogin(String devCode,String devPassword);
}
