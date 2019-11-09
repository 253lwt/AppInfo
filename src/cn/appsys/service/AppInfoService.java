package cn.appsys.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;

public interface AppInfoService {
	//APP开发者平台--APP查询列表
    public List<AppInfo> selectLists(String querySoftwareName,
    		       Integer queryStatus,
    		       Integer queryFlatformId,
    		       Integer queryCategoryLevel1,
    		       Integer queryCategoryLevel2,
    		       Integer queryCategoryLevel3,
    		       Integer pageIndex,
    		       Integer pageSize);
    //查询总记录数
    public int getSelectAllCounts(String querySoftwareName,
		       Integer queryStatus,
		       Integer queryFlatformId,
		       Integer queryCategoryLevel1,
		       Integer queryCategoryLevel2,
		       Integer queryCategoryLevel3);
    //APP开发者平台--新增APP基础信息界面
    public int addAppInfos(AppInfo appinfo);
    //APP开发者平台——修改APP基础信息-待审核
    public int updateInfos(AppInfo appinfo);
   //根据Id查询信息
    public AppInfo selectAppInfoId(Integer id);
    //根据Id删除信息
    public int deleteAppInfoIds(@Param("id")Integer id);
  //根据id修改vsersionId
    public int updateVersionIds(@Param("versionId")Integer versionId,@Param("id")Integer id);
    public boolean appsysUpdateSaleStausByAppId(AppInfo appInfoObj);
  //根据id和APK名称查询信息
    public AppInfo getAppInfo(@Param("id") Integer id,@Param("APKName") String APKName);
}
