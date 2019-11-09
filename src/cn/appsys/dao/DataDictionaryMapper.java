package cn.appsys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DataDictionary;

public interface DataDictionaryMapper {
	//查询列表信息
    public List<DataDictionary> selectList(@Param("typeCode")String typeCode);
    
   
}
