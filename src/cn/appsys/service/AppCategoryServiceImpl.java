package cn.appsys.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.AppCategoryMapper;
import cn.appsys.pojo.AppCategory;
@Service
public class AppCategoryServiceImpl implements AppCategoryService{
	@Resource
    private AppCategoryMapper acm;
    /**
     * 根据父级查询列表
     */
	@Override
	public List<AppCategory> selectList(Integer parentId) {
		return acm.selectList(parentId);
	}

}
