package cn.appsys.service;

import java.util.List;

import cn.appsys.pojo.AppCategory;

public interface AppCategoryService {
	//根据父级查询信息
    public List<AppCategory> selectList(Integer parentId);
}
