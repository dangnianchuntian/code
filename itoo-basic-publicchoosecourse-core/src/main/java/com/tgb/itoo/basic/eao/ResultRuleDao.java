package com.tgb.itoo.basic.eao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.tgb.itoo.base.eao.BaseEao;
import com.tgb.itoo.basic.entity.Build;
import com.tgb.itoo.basic.entity.CourseInfo;
import com.tgb.itoo.basic.entity.ResultRule;
import com.tgb.itoo.basic.entity.SchoolBranch;
import com.tgb.itoo.basic.entity.SchoolCalendar;
import com.tgb.itoo.basic.entity.Teacher;

/**
 * 成绩规则
 * @author xinyang
 *
 */
public interface ResultRuleDao extends BaseEao<ResultRule> {
	/**
	 * 分页查询所有成绩基础设置比例信息-李立平-2016-3-22 15:46:09-v5.0
	 *
	 * @param pageNum 分页数量 
	 * @param pageSize 每页大小
	 * @param dataBaseName 数据库名称        
	 * @return 返回ResultRule的list集合
	 */
	public List queryAllResultRule(int pageNum,
			int pageSize, String dataBaseName );
	
	/**
	 * 分页查询所有成绩基础设置比例数量-李立平-2016年3月23日15:40:08-v5.0
	 *
	 * @param dataBaseName 数据库名称 
	 * @return 返回int
	 */
	public int queryAllResultRuleCount(String dataBaseName);
	
	/**
	 * 模糊查询成绩基础设置比例信息-李立平-2016年4月5日09:49:33
	 *
	 * @param pageNum 分页数量 
	 * @param pageSize 每页大小
	 * @param dataBaseName 数据库名称    
	 * @param conditions 查询条件      
	 * @return 返回ResultRule的list集合
	 */
	public List queryResultRuleByCondition(int pageNum,
			int pageSize, String dataBaseName,String conditions);
	
	/**
	 * 模糊查询成绩基础设置比例数量-李立平-2016年4月5日09:51:10-v5.0
	 *
	 * @param dataBaseName 数据库名称 
	 * @param  conditions 查询条件
	 * @return 返回int
	 */
	public int queryResultRuleByConditionCount(String conditions,String dataBaseName);
	
	/**
	 * 查询所有学年信息（添加时使用，包括学年，教师，课程）-李立平-2016年3月25日16:05:13-v5.0
	 * @param map 存放参数信息
	 * @return 返回建筑的list集合
	 */
	public List<SchoolCalendar> querySchoolCalendarByHql(Map<Serializable, Serializable> map);
	/**
	 * 查询所有教师信息（添加时使用，包括学年，教师，课程）-李立平-2016年3月25日16:05:13-v5.0
	 * @param map 存放参数信息
	 * @return 返回建筑的list集合
	 */
	public List queryTeacherByHql(String courseID,String dataBaseName);
	/**
	 * 查询课程信息（添加时使用，根据学年和课程类型查询课程）-李立平-2016年3月25日16:05:13-v5.0
	 * @param map 存放参数信息
	 * @return 返回建筑的list集合
	 */
	public List queryCourseBycourseTypeId(String dataBaseName,String SchoolCalanderId);
	/**
	 * 保存比例设置信息-李立平-2016年3月28日11:39:46-v5.0
	 * @param ResultRule 比例规则实体
	 * @return true 或 false
	 */
	public boolean addResultRule(ResultRule resultRule);
	/**
	 * 修改比例设置-李立平-2016年3月28日20:31:01-v5.0
	 *
	 * @param resultRule
	 *            比例实体
	 * @return 返回boolean
	 */
	public boolean updateResultRule(ResultRule resultRule);	
	/**
	 * 根据学期名称查询学期id 
	 * @param termName
	 * @param dataBaseName
	 * @return
	 */
	public String queryTermIdByName(String termName ,String dataBaseName);
}
