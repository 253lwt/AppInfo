package cn.appsys.service;

import java.util.List;

import cn.appsys.pojo.DataDictionary;

public interface DataDictionaryService {
	//查询字典列表信息
    public List<DataDictionary> selectList(String typeCode);
    
    
}
