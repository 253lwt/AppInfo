package cn.appsys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppVersion;

public interface AppVersionMapper {
	//添加版本信息
    public int addAppVersion(AppVersion appVersion);
    //根据id和appId查询版本信息
    public AppVersion selectAppVersionId(@Param("id")Integer id,@Param("appId")Integer appId);
    //修改版本信息
    public int updateAppVersion(AppVersion appVersion);
    //根据id查询版本信息
    public List<AppVersion> appVersionIds(@Param("appId")Integer appId);
    //根据Id删除版本信息
    public int deleteAppVersion(@Param("appId")Integer appId);
    //根据appId和versionNo查询id
    public AppVersion selectAppIdAndVersionId(@Param("appId")Integer appId,@Param("versionNo")String versionNo);       
}
