package cn.appsys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;

public interface AppCategoryMapper {
	//根据父级查询信息
    public List<AppCategory> selectList(@Param("parentId")Integer parentId);
}
