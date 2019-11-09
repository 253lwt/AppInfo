package cn.appsys.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.parser.SymbolTable;

import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.AppInfoService;
import cn.appsys.service.AppVersionService;

@Controller
@RequestMapping(value="version")
public class AppVersionController {
	private Logger logger=Logger.getLogger(AppVersionController.class);
	@Resource
	private AppVersionService appVersionService;
	@Resource
	private AppInfoService appinfoservice;
	/**
	 * 进入版本添加页面
	 * @return
	 */
	@RequestMapping(value="/appversionadd")
    public String addVersion(Model model,@RequestParam(value="id")Integer id,AppVersion appversion) {
		System.out.println("addId------------"+id);
		appversion.setAppId(id);
		System.out.println(appversion.getAppId());
		model.addAttribute("appVersion", appversion);
		return "developer/appversionadd";   	
    }
	/**
	 * 保存添加版本信息
	 * @param model
	 * @param appVersion
	 * @return
	 */
	@RequestMapping(value="/addversionsave")
	public String addversionSave(AppVersion appVersion,HttpSession session,HttpServletRequest request,
			@RequestParam(value="a_downloadLink",required= false) MultipartFile attach) {
		String downloadLink =  null;
		String apkLocPath = null;
		String apkFileName = null;
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			logger.info("uploadFile path: " + path);
			String oldFileName = attach.getOriginalFilename();//原文件名
			String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
			if(prefix.equalsIgnoreCase("jpg")
					|| prefix.equalsIgnoreCase("png")
					|| prefix.equalsIgnoreCase("jpeg")
					|| prefix.equalsIgnoreCase("pneg")){//apk文件命名：apk名称+版本号+.apk
				 String apkName = null;
				 try {
					apkName = appinfoservice.getAppInfo(appVersion.getAppId(),null).getAPKName();
				 } catch (Exception e1) {
					e1.printStackTrace();
				 }
				 if(apkName == null || "".equals(apkName)){
					 return "redirect:/version/appversionadd?id="+appVersion.getAppId()
							 +"&error=error1";
				 }
				 apkFileName = apkName + "-" +appVersion.getVersionNo() + ".apk";
				 File targetFile = new File(path,apkFileName);
				 if(!targetFile.exists()){
					 targetFile.mkdirs();
				 }
				 try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					return "redirect:/version/appversionadd?id="+appVersion.getAppId()
							 +"&error=error2";
				} 
				downloadLink = request.getContextPath()+"/statics/uploadfiles/"+apkFileName;
				apkLocPath = path+File.separator+apkFileName;
			}else{
				return "redirect:/version/appversionadd?id="+appVersion.getAppId()
						 +"&error=error3";
			}
		}
		appVersion.setCreatedBy(((DevUser)session.getAttribute("devUserSession")).getId());
		appVersion.setCreationDate(new Date());
		appVersion.setDownloadLink(downloadLink);
		appVersion.setApkLocPath(apkLocPath);
		appVersion.setApkFileName(apkFileName);
		int count=0;
		count=appVersionService.addAppVersion(appVersion);
		AppVersion app=appVersionService.selectAppIdAndVersionIds(appVersion.getAppId(),appVersion.getVersionNo());
		System.out.println("appversion2===="+appVersion.getAppId());
		try {
			if(count>0){
				if(app!=null){
					appinfoservice.updateVersionIds(app.getId(),appVersion.getAppId());
				}
				return "redirect:/appInfo/flatform/app/list";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "developer/appversionadd";		
	}
	/**
	 * 进入版本修改页面
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/appversionmodify")
	public String appversionModify(Model model,
			@RequestParam(value="vid",required=false)Integer id,
			@RequestParam(value="aid")Integer appId) {				
		AppVersion count=null;
		count=appVersionService.selectAppVersionId(id,appId);
		if(count!=null) {
			model.addAttribute("appVersion", count);
			return "developer/appversionmodify";
		}
		return "redirect:/appInfo/flatform/app/list";		
	}
	/**
	 * 保存版本修改页面
	 * @param model
	 * @param appVersion
	 * @return
	 */
	@RequestMapping(value="/appversionmodifysave")
	public String appversionModifySave(AppVersion appVersion,HttpSession session,HttpServletRequest request,
			@RequestParam(value="attach",required= false) MultipartFile attach) {
		String downloadLink =  null;
		String apkLocPath = null;
		String apkFileName = null;
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			logger.info("uploadFile path: " + path);
			String oldFileName = attach.getOriginalFilename();//原文件名
			String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
			if(prefix.equalsIgnoreCase("jpg")
					|| prefix.equalsIgnoreCase("png")
					|| prefix.equalsIgnoreCase("jpeg")
					|| prefix.equalsIgnoreCase("pneg")){//apk文件命名：apk名称+版本号+.apk
				 String apkName = null;
				 try {
					apkName = appinfoservice.getAppInfo(appVersion.getAppId(),null).getAPKName();
				 } catch (Exception e1) {
					e1.printStackTrace();
				 }
				 if(apkName == null || "".equals(apkName)){
					 return "redirect:/version/appversionmodify?vid="+appVersion.getId()
							 +"&aid="+appVersion.getAppId()
							 +"&error=error1";
				 }
				 apkFileName = apkName + "-" +appVersion.getVersionNo() + ".apk";
				 File targetFile = new File(path,apkFileName);
				 if(!targetFile.exists()){
					 targetFile.mkdirs();
				 }
				 try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					return "redirect:/version/appversionmodify?vid="+appVersion.getId()
							 +"&aid="+appVersion.getAppId()
							 +"&error=error2";
				} 
				downloadLink = request.getContextPath()+"/statics/uploadfiles/"+apkFileName;
				apkLocPath = path+File.separator+apkFileName;
			}else{
				return "redirect:/version/appversionmodify?vid="+appVersion.getId()
						 +"&aid="+appVersion.getAppId()
						 +"&error=error3";
			}
		}
		appVersion.setCreatedBy(((DevUser)session.getAttribute("devUserSession")).getId());
		appVersion.setModifyDate(new Date());
		appVersion.setDownloadLink(downloadLink);
		appVersion.setApkLocPath(apkLocPath);
		appVersion.setApkFileName(apkFileName);
		int count=0;
		count=appVersionService.updateAppVersion(appVersion);
		try {
			if(count>0){
				return "redirect:/appInfo/flatform/app/list";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		System.out.println("ID========"+appVersion.getId());
		System.out.println("appID========"+appVersion.getAppId());
		System.out.println("大小========"+appVersion.getVersionSize());
		System.out.println("发送到========"+appVersion.getVersionInfo());
		return "developer/appversionmodify";		
	}
}
