package cn.appsys.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import cn.appsys.dao.AppInfoMapper;
import cn.appsys.dao.AppVersionMapper;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
@Service
public class AppInfoServiceImpl implements AppInfoService{
	@Resource
    private AppInfoMapper appinfoMapper;
	@Resource 
	private AppVersionMapper appVersionMapper;
	/**
	 * APP开发者平台--APP查询列表
	 */
	@Override
	public List<AppInfo> selectLists(String querySoftwareName,
		      Integer queryStatus,
		      Integer queryFlatformId,
		      Integer queryCategoryLevel1,
		       Integer queryCategoryLevel2,
		       Integer queryCategoryLevel3,
		      Integer pageIndex,
		       Integer pageSize) {
		List<AppInfo> list=null;
		System.out.println(""+pageIndex+""+pageSize);
		try {
			list=appinfoMapper.selectList(querySoftwareName, queryStatus, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, pageIndex, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * APP开发者平台--新增APP基础信息界面
	 */
	@Override
	public int addAppInfos(AppInfo appinfo) {
		int count=0;
		try {
			count=appinfoMapper.addAppInfo(appinfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * APP开发者平台——修改APP基础信息-待审核
	 */
	@Override
	public int updateInfos(AppInfo appinfo) {
		int count=0;
		try {
			count=appinfoMapper.updateInfo(appinfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 查询总记录数
	 */
	@Override
	public int getSelectAllCounts(String querySoftwareName,
		       Integer queryStatus,
		       Integer queryFlatformId,
		       Integer queryCategoryLevel1,
		       Integer queryCategoryLevel2,
		       Integer queryCategoryLevel3) {
		return appinfoMapper.getSelectAllCount(querySoftwareName, queryStatus, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3);
	}
	/**
	 * 根据id查询信息
	 */
	@Override
	public AppInfo selectAppInfoId(Integer id) {
		System.out.println("idService=====:"+id);
		return appinfoMapper.selectAppInfoId(id);
	}
	/**
	 * 根据id删除信息
	 */
	@Override
	public int deleteAppInfoIds(Integer id) {
		return appinfoMapper.deleteAppInfoId(id);
	}
	/**
	 * 根据id修改vsersionId
	 */
	@Override
	public int updateVersionIds(Integer versionId,Integer id) {
		return appinfoMapper.updateVersionId(versionId, id);
	}
	@Override
	public boolean appsysUpdateSaleStausByAppId(AppInfo appInfoObj) {
		AppInfo appInfo=appinfoMapper.getAppInfo(appInfoObj.getId(),null);
		Integer operator=appInfoObj.getModifyBy();
		if(operator>0||appInfoObj.getId()<0) {
			if(appInfo==null) {
				System.out.println("Service:++++++++=");
				return false;
			}else {
				switch (appInfo.getStatus()) {
				
				case 2:  //当状态为审核通过,可以进行上架操作
					System.out.println("Service:++++++++=2");
					onSale(appInfo,operator,4,2);
					break;
				case 5:   //当状态为已下架,可以进行上架操作
					System.out.println("Service:++++++++=5");
					onSale(appInfo,operator,4,2);
					break;
				case 4:    //当状态为已上架,可以进行下架操作
					System.out.println("Service:++++++++=4");
					offSale(appInfo,operator,5);
				default:
					System.out.println("Service:++++++++=默认");
					return false;
				}
			}
		}
		return false;
	}
	/**
	 * on Sale
	 * @param appInfo
	 * @param operator
	 * @param appInstatus
	 * @param versionStatus
	 */
    private void onSale(AppInfo appInfo,Integer operator,Integer appInstatus,Integer versionStatus) {
    	offSale(appInfo,operator,appInstatus);
    	setSaleSwitchAppVersion(appInfo,operator,versionStatus);
    }
    /**
     * off Sale
     * @param appInfo
     * @param operator
     * @param appInstatus
     * @return 
     */
    private boolean offSale(AppInfo appInfo,Integer operator,Integer appInstatus) {
    	AppInfo appInfos=new AppInfo();
    	appInfos.setId(appInfo.getId());
    	appInfos.setStatus(appInstatus);
    	appInfos.setModifyBy(operator);
    	appInfos.setOffSaleDate(new Date(System.currentTimeMillis()));
    	appinfoMapper.updateInfo(appInfos);
    	return true;
    }
    private boolean setSaleSwitchAppVersion(AppInfo appInfo,Integer operator,Integer saleStatus) {
		AppVersion appVersion=new AppVersion();
		appVersion.setId(appInfo.getVersionId());
		appVersion.setPublishStatus(saleStatus);
		appVersion.setModifyBy(operator);
		appVersion.setModifyDate(new Date(System.currentTimeMillis()));
		appVersionMapper.updateAppVersion(appVersion);
		return false;    	
    }
	@Override
	public AppInfo getAppInfo(Integer id, String APKName) {
		return appinfoMapper.getAppInfo(id, APKName);
	}
}
