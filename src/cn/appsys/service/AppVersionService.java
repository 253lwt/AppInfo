package cn.appsys.service;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppVersion;
public interface AppVersionService {
	//添加版本信息
    public int addAppVersion(AppVersion appVersion);
    //根据id查询信息
    public AppVersion selectAppVersionId(Integer id,Integer appId);
    //修改版本信息
    public int updateAppVersion(AppVersion appVersion);
    //根据id查询版本信息
    public List<AppVersion> selectAppVersionIds(Integer appId);
    //根据Id删除版本信息
    public int deleteAppVersions(@Param("appId")Integer appId);
  //根据appId和versionNO查询id
    public AppVersion selectAppIdAndVersionIds(@Param("appId")Integer appId,String versionNo);       
}
