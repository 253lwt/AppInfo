package cn.appsys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;

public interface AppInfoMapper {
	//APP开发者平台--APP查询列表
    public List<AppInfo> selectList(@Param("querysoftwareName")String querySoftwareName,
    		       @Param("queryStatus")Integer queryStatus,
    		       @Param("queryFlatformId")Integer queryFlatformId,
    		       @Param("queryCategoryLevel1")Integer queryCategoryLevel1,
    		       @Param("queryCategoryLevel2")Integer queryCategoryLevel2,
    		       @Param("queryCategoryLevel3")Integer queryCategoryLevel3,
    		       @Param("pageIndex")Integer pageIndex,
    		       @Param("pageSize")Integer pageSize);
    //查询总记录数
    public int getSelectAllCount(@Param("querysoftwareName")String querySoftwareName,
		       @Param("queryStatus")Integer queryStatus,
		       @Param("queryFlatformId")Integer queryFlatformId,
		       @Param("queryCategoryLevel1")Integer queryCategoryLevel1,
		       @Param("queryCategoryLevel2")Integer queryCategoryLevel2,
		       @Param("queryCategoryLevel3")Integer queryCategoryLevel3);
    //APP开发者平台--新增APP基础信息界面
    public int addAppInfo(AppInfo appinfo);
    //APP开发者平台——修改APP基础信息-待审核
    public int updateInfo(AppInfo appinfo);
    //根据Id查询信息
    public AppInfo selectAppInfoId(@Param("id")Integer id);
    //根据Id删除信息
    public int deleteAppInfoId(@Param("id")Integer id);
    //根据id修改vsersionId
    public int updateVersionId(@Param("versionId")Integer versionId,@Param("id")Integer id);
    //根据id和APK名称查询信息
    public AppInfo getAppInfo(@Param("id") Integer id,@Param("APKName") String APKName);
}
