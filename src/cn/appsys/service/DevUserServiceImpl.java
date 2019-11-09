package cn.appsys.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.DevUserMapper;
import cn.appsys.pojo.DevUser;
@Service
public class DevUserServiceImpl implements DevUserService{
	@Resource
    private DevUserMapper dev;
    /**
     * 验证用户名和密码是否正确
     */

	@Override
	public DevUser selectDevLogin(String devCode, String devPassword) {
		DevUser devUser=null;
		try {
			System.out.println("密码"+devPassword +"用户名"+devCode);
			devUser=dev.selectLogin(devCode, devPassword);
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return devUser;
	}
	

}
