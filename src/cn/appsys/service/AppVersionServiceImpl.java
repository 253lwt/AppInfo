package cn.appsys.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.AppVersionMapper;
import cn.appsys.pojo.AppVersion;
@Service
public class AppVersionServiceImpl implements AppVersionService{
	@Resource
    private AppVersionMapper appVersoinMapper;
	/**
	 * 添加版本信息
	 */
	@Override
	public int addAppVersion(AppVersion appVersion) {
		return appVersoinMapper.addAppVersion(appVersion);
	}
    /**
     * 根据Id和AppId查询版本信息
     */
	@Override
	public AppVersion selectAppVersionId(Integer id,Integer appId) {
		System.out.println("idService:==================>"+id+"  "+appId);
		return appVersoinMapper.selectAppVersionId(id,appId);
	}
    /**
     * 修改版本信息
     */
	@Override
	public int updateAppVersion(AppVersion appVersion) {
		return appVersoinMapper.updateAppVersion(appVersion);
	}
	/**
	 * 根据appId查询版本信息
	 */
	@Override
	public List<AppVersion> selectAppVersionIds(Integer appId) {	
		System.out.println("idServ:"+appId);
		return appVersoinMapper.appVersionIds(appId);
	}
	/**
	 * 根据Id删除版本信息
	 */
	@Override
	public int deleteAppVersions(Integer appId) {
		return appVersoinMapper.deleteAppVersion(appId);
	}
	/**
	 * 根据appId和versionNO查询id
	 */
	@Override
	public AppVersion selectAppIdAndVersionIds(Integer appId,String versionNo) {
		return appVersoinMapper.selectAppIdAndVersionId(appId,versionNo);
	}

}
