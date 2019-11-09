package cn.appsys.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.DataDictionaryMapper;
import cn.appsys.pojo.DataDictionary;
@Service
public class DataDictionaryServiceImpl implements DataDictionaryService{
    @Resource
	private DataDictionaryMapper ddm;
    /**
     * 查询字典列表 
     */
	@Override
	public List<DataDictionary> selectList(String typeCode) {
		return ddm.selectList(typeCode);
	}

}
