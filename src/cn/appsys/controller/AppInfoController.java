package cn.appsys.controller;


import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

import com.alibaba.fastjson.JSONArray;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.pojo.PageSupport;
import cn.appsys.service.AppCategoryService;
import cn.appsys.service.AppInfoService;
import cn.appsys.service.AppVersionService;
import cn.appsys.service.DataDictionaryService;

@Controller
@RequestMapping(value="/appInfo")
public class AppInfoController {
	private Logger logger=Logger.getLogger(AppInfoController.class);
	@Resource
    private AppInfoService appinfoService;
	@Resource
	private DataDictionaryService dds;
	@Resource
	private AppCategoryService acs;
	@Resource
	private AppVersionService avs;
	//进入APP开发者平台--APP查询列表
	@RequestMapping(value="/flatform/app/list")
	public String appList(Model model,HttpSession session,
			@RequestParam(value="querySoftwareName",required=false)String querySoftwareName,
			@RequestParam(value="queryStatus",required=false)String _queryStatus,
			@RequestParam(value="queryFlatformId",required=false)String _queryFlatformId,
			@RequestParam(value="queryCategoryLevel1",required=false)String _queryCategoryLevel1,
			@RequestParam(value="queryCategoryLevel2",required=false)String _queryCategoryLevel2,
			@RequestParam(value="queryCategoryLevel3",required=false)String _queryCategoryLevel3,
			@RequestParam(value="pageIndex",required=false)String pageIndex) {
	logger.info("querySoftwareName===========>"+querySoftwareName);
	logger.info("queryStatus===========>"+_queryStatus);
	logger.info("queryFlatformId===========>"+_queryFlatformId);
	logger.info("queryCategoryLevel1===========>"+_queryCategoryLevel1);
	logger.info("queryCategoryLevel2===========>"+_queryCategoryLevel2);
	logger.info("queryCategoryLevel3===========>"+_queryCategoryLevel3);
	logger.info("pageIndex===========>"+pageIndex);
	//当前页
    Integer currentPageNo=1;
    //设置页面大小
    Integer pageSize=5;
    List<AppInfo> appInfoList=null;
    List<DataDictionary> flatFormList=null;
    List<DataDictionary> statusList=null;
    List<AppCategory> categoryLevel1List=null;
    List<AppCategory> categoryLevel2List=null;
    List<AppCategory> categoryLevel3List=null;
    if(pageIndex!=null) {
    	try {
    		currentPageNo=Integer.valueOf(pageIndex);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
    }
    Integer queryStatus=null;
    if(_queryStatus!=null && !_queryStatus.equals("")) {
    	queryStatus=Integer.parseInt(_queryStatus);
    }
    Integer queryFlatformId=null;
    if(_queryFlatformId!=null && !_queryFlatformId.equals("")) {
    	queryFlatformId=Integer.parseInt(_queryFlatformId);
    }
    Integer queryCategoryLevel1=null;
    if(_queryCategoryLevel1!=null && !_queryCategoryLevel1.equals("")) {
    	queryCategoryLevel1=Integer.parseInt(_queryCategoryLevel1);
    }
    Integer queryCategoryLevel2=null;
    if(_queryCategoryLevel2!=null && !_queryCategoryLevel2.equals("")) {
    	queryCategoryLevel2=Integer.parseInt(_queryCategoryLevel2);
    }
    Integer queryCategoryLevel3=null;
    if(_queryCategoryLevel3!=null && !_queryCategoryLevel3.equals("")) {
    	queryCategoryLevel3=Integer.parseInt(_queryCategoryLevel3);
    }
    //总记录数
    int totalCount=0;
    try {
		totalCount=appinfoService.getSelectAllCounts(querySoftwareName, queryStatus, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3);
	} catch (Exception e) {
		e.printStackTrace();
	}
    appInfoList=appinfoService.selectLists(querySoftwareName, queryStatus, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, (currentPageNo-1)*pageSize, pageSize);
    flatFormList=dds.selectList("APP_FLATFORM");
    statusList=dds.selectList("APP_STATUS");
    categoryLevel1List=acs.selectList(null);
    PageSupport pages=new PageSupport();
    pages.setCurrentPageNo(currentPageNo);
    pages.setPageSize(pageSize);
    pages.setTotalCount(totalCount);
    //总页数
    int totalPageCount=pages.getTotalPageCount();
    //控制首页和尾页
    if(currentPageNo<1) {
    	currentPageNo=1;
    }else if(currentPageNo>totalPageCount){
    	currentPageNo=totalPageCount;
    }
    model.addAttribute("appInfoList",appInfoList);
    model.addAttribute("flatFormList",flatFormList);
    model.addAttribute("statusList", statusList);
    model.addAttribute("categoryLevel1List", categoryLevel1List);
    model.addAttribute("pages", pages);
    model.addAttribute("queryStatus", queryStatus);
    model.addAttribute("queryCategoryLevel1", queryCategoryLevel1);
    model.addAttribute("queryCategoryLevel2", queryCategoryLevel2);
    model.addAttribute("queryCategoryLevel3", queryCategoryLevel3);
    model.addAttribute("queryFlatformId", queryFlatformId);
	    return "developer/appinfolist";	   
	}
	@RequestMapping(value="/dataDictionary.json")
	@ResponseBody
	public List<DataDictionary> selectList(String tcode){
		List<DataDictionary> dataDictionarylist=null;
		try {
			dataDictionarylist=dds.selectList(tcode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataDictionarylist;		
	}
	@RequestMapping(value="/categorylevellist.json",method=RequestMethod.GET )
	@ResponseBody
	public List<AppCategory> selectAppCategoryList(String pid){
		if(("").equals(pid)) {
			pid=null;
		}
		return appCategoryList(pid);		
	}
	public List<AppCategory> appCategoryList(String pid) {
		List<AppCategory> list=acs.selectList(pid==null?null:Integer.parseInt(pid));
		return list;	
	}
	/**
	 * 跳转到添加页面
	 */
	@RequestMapping(value="/flatform/app/appinfoadd")
	public String addAppInfo() {
		return "developer/appinfoadd";		
	}
	/**
	 * 保存添加信息
	 * @return
	 */
	@RequestMapping(value="/appinfoAddSave")
	public String addAppInfoSave(Model model,AppInfo appInfo,HttpSession session,HttpServletRequest request,
			@RequestParam(value="a_logoPicPath",required = false) MultipartFile attach) {
		String logoPicPath = null;		//要保存图片的路径	
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("statics"+java.io.File.separator+"uploadfiles");
			logger.info("uploadFile path: " + path);
			String oldFileName = attach.getOriginalFilename();//原文件名
			String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
			int filesize = 500000;
			if(attach.getSize() > filesize){//上传大小不得超过 50k
				request.setAttribute("fileUploadError","错误");
				return "developer/appinfoadd";
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
			   ||prefix.equalsIgnoreCase("jepg") || prefix.equalsIgnoreCase("pneg")){//上传图片格式
				 String fileName = appInfo.getAPKName() + ".jpg";//上传LOGO图片命名:apk名称.apk
				 File targetFile = new File(path,fileName);
				 if(!targetFile.exists()){
					 targetFile.mkdirs();
				 }
				 try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError","错误");
					return "developer/appinfoadd";
				} 
				 logoPicPath = request.getContextPath()+"/statics/uploadfiles/"+fileName;
			}else{
				request.setAttribute("fileUploadError", "错误");
				return "developer/appinfoadd";
			}
		}
		appInfo.setCreatedBy(((DevUser)session.getAttribute("devUserSession")).getId());
		appInfo.setCreationDate(new Date());
		appInfo.setLogoPicPath(logoPicPath);
		appInfo.setDevId(((DevUser)session.getAttribute("devUserSession")).getId());
		appInfo.setStatus(1);
		int count=0;
		count=appinfoService.addAppInfos(appInfo);
		try {
			if(count>0){				
				return "redirect:/appInfo/flatform/app/list";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "developer/appinfoadd";		
	}
	/**
	 * 进入修改页面
	 * @return
	 */
	@RequestMapping(value="/appinfomodify.json")
	public String modifyAppInfo(Model model,Integer id) {
		System.out.println("id====================>"+id);
		AppInfo appInfo=null;
		appInfo=appinfoService.selectAppInfoId(id);
		if(appInfo!=null) {
			model.addAttribute("appInfo", appInfo);
			return "developer/appinfomodify";
		}
		return "redirect:/appInfo/flatform/app/list";		
	}
	/**
	 * 保存修改页面
	 * @param model
	 * @param appinfo
	 * @return
	 */
	@RequestMapping(value="/modifyAppInfoSave.json")
	public String modifyAppInfoSave(Model model,AppInfo appInfo,HttpSession session,HttpServletRequest request,
			@RequestParam(value="attach",required = false) MultipartFile attach) {
		String logoPicPath = null;		//要保存图片的路径
		String logoLocPath = null;			//第二张图片的路径
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("statics"+java.io.File.separator+"uploadfiles");
			logger.info("uploadFile path: " + path);
			String oldFileName = attach.getOriginalFilename();//原文件名
			String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
			int filesize = 500000;
			if(attach.getSize() > filesize){//上传大小不得超过 50k
				request.setAttribute("fileUploadError","错误");
				return "developer/appinfoadd";
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
     			   ||prefix.equalsIgnoreCase("jepg") || prefix.equalsIgnoreCase("pneg")){//上传图片格式
				 String fileName = appInfo.getAPKName() + ".jpg";//上传LOGO图片命名:apk名称.apk
				 File targetFile = new File(path,fileName);
				 if(!targetFile.exists()){
					 targetFile.mkdirs();
				 }
				 try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError","错误");
					return "developer/appinfoadd";
				} 
				 logoPicPath = request.getContextPath()+"/statics/uploadfiles/"+fileName;
				 logoLocPath = path+File.separator+fileName;
			}else{
				request.setAttribute("fileUploadError", "错误");
				return "developer/appinfoadd";
			}
		}		
		System.out.println("versionid="+((DevUser)session.getAttribute("devUserSession")).getId());
		appInfo.setCreatedBy(((DevUser)session.getAttribute("devUserSession")).getId());		
		appInfo.setCreationDate(new Date());
		appInfo.setLogoPicPath(logoPicPath);
		appInfo.setLogoLocPath(logoLocPath);
		appInfo.setDevId(((DevUser)session.getAttribute("devUserSession")).getId());
		appInfo.setStatus(1);
		int count=0;
		count=appinfoService.updateInfos(appInfo);
		try {
			if(count>0) {
				return "redirect:/appInfo/flatform/app/list";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return "developer/appinfomodify";		
	}
	/**
	 * 异步app平台方法
	 * @return
	 */
	@RequestMapping(value="/datadictionarylist.json",method=RequestMethod.GET )
	@ResponseBody
	public List<DataDictionary> getData() {		
		return dds.selectList("APP_FLATFORM");		
	}
	/**
	 * 查看APP详细信息
	 * @return
	 */
	@RequestMapping(value="/flatform/app/appinfoview")
	public String appinfoView(Model model,@RequestParam(value="id")Integer appId) {
		System.out.println("appId-=-=-=-=-="+appId);
		AppInfo appInfo=null;
		List<AppVersion> appVersionList=null;
		appInfo=appinfoService.selectAppInfoId(appId);
		appVersionList=avs.selectAppVersionIds(appId);
		System.out.println("name"+appVersionList.get(0).getAppName());
		model.addAttribute("appInfo", appInfo);
		model.addAttribute("appVersionList", appVersionList);
		return "developer/appinfoview";		
	}
	/**
	 * 根据Id删除信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/flatform/app/delapp.json")
	@ResponseBody     //异步返回
	public Object deleteappinfo(@RequestParam(value="id")Integer id) {
		logger.debug("idDel:========>"+id);
		HashMap<String, String> resultMap=new HashMap<String, String>();
		int count=0;
		List<AppVersion> appversion=null;
		appversion=avs.selectAppVersionIds(id);   //根据appId查询版本信息
		if(appversion!=null) {                     //判断版本信息是否存在，如果存在就删除
			int cc=avs.deleteAppVersions(id);      //删除版本信息
			if(cc>0) {                             //判断版本是否已删除，如果已删除，就删除基本信息
				count=appinfoService.deleteAppInfoIds(id);  //删除基本信息
				if(count>0) {                      //判断基本信息是否已删除，若已删除，就返回ture
					resultMap.put("delResult","true");
				}else if(count<0) {
					resultMap.put("delResult","false");
				}
			}else {
				resultMap.put("delResult","false");
			}
		}else {                                    //若不存在版本信息，就直接删除基本信息
			count=appinfoService.deleteAppInfoIds(id);
			if(count>0) {
				resultMap.put("delResult","true");
			}else if(count<0) {
				resultMap.put("delResult","false");
			}
		}
		return JSONArray.toJSONString(resultMap);		
	}
	/**
	 * 上架 、下架
	 * @param appid
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/flatform/app/{appid}/sale.json",method=RequestMethod.PUT)
	@ResponseBody
	public Object sale(@PathVariable String appid,HttpSession session){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Integer appIdInteger = 0;
		try{
			appIdInteger = Integer.parseInt(appid);
		}catch(Exception e){
			appIdInteger = 0;
		}
		resultMap.put("errorCode", "0");
		resultMap.put("appId", appid);
		if(appIdInteger>0){
			try {
				DevUser devUser = (DevUser)session.getAttribute("devUserSession");
				AppInfo appInfo = new AppInfo();
				appInfo.setId(appIdInteger);
				appInfo.setModifyBy(devUser.getId());
				if(appinfoService.appsysUpdateSaleStausByAppId(appInfo)){
					resultMap.put("resultMsg", "success");
				}else{
					resultMap.put("resultMsg", "failed");
				}		
			} catch (Exception e) {
				e.printStackTrace();
				resultMap.put("errorCode", "exception000001");
			}
		}else{
			
			//errorCode:0为正常
			resultMap.put("errorCode", "param000001");
		}
		return resultMap;
	}
}
