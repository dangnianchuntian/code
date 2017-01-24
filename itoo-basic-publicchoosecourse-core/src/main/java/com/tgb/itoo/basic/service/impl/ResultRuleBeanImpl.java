package com.tgb.itoo.basic.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import com.tgb.itoo.base.eao.BaseEao;
import com.tgb.itoo.base.service.impl.BaseBeanImpl;
import com.tgb.itoo.basic.eao.ResultRuleDao;
import com.tgb.itoo.basic.entity.CourseInfo;
import com.tgb.itoo.basic.entity.ResultRule;
import com.tgb.itoo.basic.entity.SchoolCalendar;
import com.tgb.itoo.basic.entity.Teacher;
import com.tgb.itoo.basic.service.ResultRuleBean;
import com.tgb.itoo.tool.objectToMap.ObjectToMap;


@Stateless(name = "resultRuleBeanImpl")
@Remote(ResultRuleBean.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ResultRuleBeanImpl extends BaseBeanImpl<ResultRule> implements ResultRuleBean{

	@EJB(name="resultRuleDaoImpl")
	private ResultRuleDao resultRuleDao;
	@Override
	public BaseEao getBaseEao() {
		return resultRuleDao;
	}
	public ResultRuleDao getResultRuleDao() {
		return resultRuleDao;
	}
	public void setResultRuleDao(ResultRuleDao resultRuleDao) {
		this.resultRuleDao = resultRuleDao;
	}
	/**
	 * 查询所有成绩基础设置比例信息-李立平-2016-3-22 15:46:09-v5.0
	 *
	 * @param pageNum 分页数量 
	 * @param pageSize 每页大小
	 * @param dataBaseName 数据库名称        
	 * @return 返回ResultRule的list集合
	 */
	@Override
	public List queryAllResultRule(int pageNum,
			int pageSize, String dataBaseName )
			{
		//页码默认显示1，如果没有给出
		if(pageNum==0){
			pageNum=1;
		}

			//查询所有的成绩基础设置比例信息
			List result = new ArrayList();
			List fields =new ArrayList();
			result =resultRuleDao.queryAllResultRule(pageNum, pageSize, dataBaseName);
			//实例化对象转换map
			ObjectToMap o2m =new ObjectToMap();
			
			fields.add("courseName");
			fields.add("termName");
			fields.add("daliyResultRate");
			fields.add("NAME");
			fields.add("setTime");
			fields.add("id");
			fields.add("finalResultRate");
			
		return o2m.convertToMap(fields, result);
		}
	
	/**
	 * 分页查询所有成绩基础设置比例数量-李立平-2016年3月23日15:40:08--v5.0
	 *
	 * @param dataBaseName 数据库名称 
	 * @return 返回int	 */
	@Override
	public int queryAllResultRuleCount(String dataBaseName){
		return resultRuleDao.queryAllResultRuleCount(dataBaseName);
	}
	/**
	 * 模糊查询成绩基础设置比例信息-李立平-2016年4月5日09:49:33-v5.0
	 *
	 * @param pageNum 分页数量 
	 * @param pageSize 每页大小
	 * @param dataBaseName 数据库名称    
	 * @param conditions 查询条件      
	 * @return 返回ResultRule的list集合
	 */
	public List queryResultRuleByCondition(int pageNum,
			int pageSize, String dataBaseName,String conditions){
		//页码默认显示1，如果没有给出
				if(pageNum==0){
					pageNum=1;
				}

					//查询所有的成绩基础设置比例信息
					List result = new ArrayList();
					List fields =new ArrayList();
					result =resultRuleDao.queryResultRuleByCondition(pageNum, pageSize, dataBaseName, conditions);
					//实例化对象转换map
					ObjectToMap o2m =new ObjectToMap();
					
					fields.add("courseName");
					fields.add("termName");
					fields.add("daliyResultRate");
					fields.add("NAME");
					fields.add("setTime");
					fields.add("id");
					fields.add("finalResultRate");

				return o2m.convertToMap(fields, result);
	}
	
	/**
	 * 模糊查询成绩基础设置比例数量-李立平-2016年4月5日09:51:10-v5.0
	 *
	 * @param dataBaseName 数据库名称 
	 * @param  conditions 查询条件
	 * @return 返回int
	 */
	public int queryResultRuleByConditionCount(String conditions,String dataBaseName){

		return resultRuleDao.queryResultRuleByConditionCount(conditions, dataBaseName);
	}
	/**
	 * 删除成绩基础设置比例数量-李立平-2016年3月25日11:02:48--v5.0
	 * @ids 选中行的数据id
	 * @param dataBaseName 数据库名称 
	 * @return 返回布尔
	 */
	public boolean deleteResultRuleByIds(String[] ids,String dataBaseName){
		//直接调用底层根据ids删除数据方法（更新isdelete字段为1）
		return resultRuleDao.deleteByIdsGeneric(ids, dataBaseName);
	}
	
	/**
	 * 查询所有学年信息（添加时使用，包括学年，教师，课程）-李立平-2016年3月25日16:05:13-v5.0
	 * @param map 存放参数信息
	 * @return 返回建筑的list集合
	 */
	public List<SchoolCalendar> querySchoolCalendarByHql(Map<Serializable, Serializable> map){
		return resultRuleDao.querySchoolCalendarByHql(map);
	}
	
	/**
	 * 查询所有教师信息（添加时使用，包括学年，教师，课程）-李立平-2016年3月25日16:05:13-v5.0
	 * @param map 存放参数信息
	 * @return 返回建筑的list集合
	 */
	public List<Map<Serializable, Serializable>> queryTeacherByHql(String courseID,String dataBaseName){
		//声明返回集合远程调用bean
		List<Map<Serializable,Serializable>> listMap=new ArrayList<Map<Serializable,Serializable>>();
		return resultRuleDao.queryTeacherByHql(courseID,dataBaseName);

	}
	/**
	 * 查询课程信息（添加时使用，根据学年和课程类型查询课程）-李立平-2016年3月25日16:05:13-v5.0
	 * @param map 存放参数信息
	 * @return 返回建筑的list集合
	 */
	public List <Map<Serializable, Serializable>> queryCourseBycourseTypeId(String dataBaseName,String SchoolCalanderId){
		// 转换map后的返回值
				List field = new ArrayList();
				// 实例化Object转Map的类
				ObjectToMap o2m = new ObjectToMap();
				List list = new ArrayList();
				list = resultRuleDao.queryCourseBycourseTypeId(dataBaseName, SchoolCalanderId);

				field.add("id");
				field.add("courseName");

				return  o2m.convertToMap(field, list);
	}

	/**
	 * 保存比例设置信息-李立平-2016年3月28日11:39:46-v5.0
	 * @param ResultRule 比例规则实体
	 * @return true 或 false
	 */
	public boolean addResultRule(ResultRule resultRule){
		return resultRuleDao.addResultRule(resultRule);
	}
	
	/**
	 * 修改比例设置-李立平-2016年3月28日20:31:01-v5.0
	 *
	 * @param resultRule
	 *            比例实体
	 * @return 返回boolean
	 */
	public boolean updateResultRule(ResultRule resultRule){
		return resultRuleDao.updateResultRule(resultRule);
	}
	/**
	 * 根据学期名称查询学期id
	 */
	@Override
	public String queryTermIdByName(String termName, String dataBaseName) {
		
		return resultRuleDao.queryTermIdByName(termName, dataBaseName);
	}
}