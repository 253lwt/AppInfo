package cn.appsys.dao;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DevUser;

public interface DevUserMapper {
	//验证用户名和密码是否正确
    public DevUser selectLogin(@Param("devCode")String devCode,
    		@Param("devPassword")String devPassword);
}
