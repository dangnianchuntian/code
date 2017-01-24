package com.tgb.itoo.basic.eao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.tgb.itoo.base.eao.impl.BaseEaoImpl;
import com.tgb.itoo.basic.eao.ResultRuleDao;
import com.tgb.itoo.basic.entity.ResultRule;
import com.tgb.itoo.basic.entity.SchoolCalendar;
import com.tgb.itoo.tool.objectToMap.ObjectToMap;
import com.tgb.itoo.tool.pageModel.PageEntity;

/**
 * 成绩规则
 * 
 * @author xinyang
 *
 */
@Stateless(name = "resultRuleDaoImpl")
@Remote(ResultRuleDao.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ResultRuleDaoImpl extends BaseEaoImpl<ResultRule> implements
		ResultRuleDao {
	/**
	 * 查询所有成绩基础设置比例信息-李立平-2016-3-22 15:46:09-v5.0
	 *
	 * @param pageNum
	 *            分页数量
	 * @param pageSize
	 *            每页大小
	 * @param dataBaseName
	 *            数据库名称
	 * @return 返回ResultRule的list集合
	 */
	@Override
	public List queryAllResultRule(int pageNum, int pageSize,
			String dataBaseName) {
		// 声明定义分页查询实体
		PageEntity pageEntity = new PageEntity();
		StringBuilder sb = new StringBuilder();

		// 定义sql语句，需要关联课程表，教师表，和学校日历表，并isdelete=0
		sb.append("SELECT c.courseName,s.termName,r.daliyResultRate,te.NAME,r.setTime,r.id ,r.finalResultRate FROM ");
		sb.append("" + dataBaseName + ".tb_resultrule r INNER JOIN  ");
		sb.append("" + dataBaseName
				+ ".tb_schoolcalendar s ON r.schoolcalendarId = s.id");
		sb.append(" INNER JOIN " + dataBaseName
				+ ".tb_teacher te ON r.teacherId = te.id");
		sb.append(" INNER JOIN " + dataBaseName
				+ ".tb_courseinfo c ON r.courseId = c.id where r.isDelete=0 ");
		sb.append("order by r.setTime");

		// 设置pageEntity必须几个属性
		pageEntity.setDataBaseName(dataBaseName);
		// 设置分页实体页数，每页显示信息数目
		pageEntity.setPageNum(pageNum);
		pageEntity.setPageSize(pageSize);

		// 分页查询比例设置信息，返回
		return queryPageEntityBySql(sb.toString(), pageEntity);
	}

	/**
	 * 分页查询所有成绩基础设置比例数量-李立平-2016年3月23日15:40:08-v5.0
	 *
	 * @param dataBaseName
	 *            数据库名称
	 * @return 返回int
	 */
	@Override
	public int queryAllResultRuleCount(String dataBaseName) {
		String sql = new String();

		// 定义sql语句，需要关联课程表，教师表，和学校日历表，并isdelete=0
		sql = "SELECT c.courseName,s.termName,r.daliyResultRate,te.NAME,r.setTime,r.id FROM "
				+ ""
				+ dataBaseName
				+ ".tb_resultrule r INNER JOIN  "
				+ ""
				+ dataBaseName
				+ ".tb_schoolcalendar s ON r.schoolcalendarId = s.id "
				+ "INNER JOIN "
				+ dataBaseName
				+ ".tb_teacher te ON r.teacherId = te.id "
				+ "INNER JOIN "
				+ dataBaseName
				+ ".tb_courseinfo c ON r.courseId = c.id where r.isDelete='0'";

		List list = queryBySql(sql);
		return list.size();
	}

	/**
	 * 模糊查询成绩基础设置比例信息-李立平-2016年4月5日09:49:33-v5.0
	 *
	 * @param pageNum
	 *            分页数量
	 * @param pageSize
	 *            每页大小
	 * @param dataBaseName
	 *            数据库名称
	 * @param conditions
	 *            查询条件
	 * @return 返回ResultRule的list集合
	 */
	public List queryResultRuleByCondition(int pageNum, int pageSize,
			String dataBaseName, String conditions) {
		// 声明定义分页查询实体
		PageEntity pageEntity = new PageEntity();
		StringBuilder sb = new StringBuilder();

		// 定义sql语句，需要关联课程表，教师表，和学校日历表，并isdelete=0,最后加上模糊查询条件
		sb.append("SELECT c.courseName,s.termName,r.daliyResultRate,te.NAME,r.setTime,r.id ,r.finalResultRate FROM ");
		sb.append("" + dataBaseName + ".tb_resultrule r INNER JOIN  ");
		sb.append("" + dataBaseName
				+ ".tb_schoolcalendar s ON r.schoolcalendarId = s.id");
		sb.append(" INNER JOIN " + dataBaseName
				+ ".tb_teacher te ON r.teacherId = te.id");
		sb.append(" INNER JOIN "
				+ dataBaseName
				+ ".tb_courseinfo c ON r.courseId = c.id where r.isDelete=0  AND (c.courseName like'%"
				+ conditions + "%' or s.termName like '%");
		sb.append("" + conditions + "%' or te.NAME like '%" + conditions
				+ "%')");
		sb.append("order by r.setTime");

		// 设置pageEntity必须几个属性
		pageEntity.setDataBaseName(dataBaseName);
		// 设置分页实体页数，每页显示信息数目
		pageEntity.setPageNum(pageNum);
		pageEntity.setPageSize(pageSize);

		// 分页查询比例设置信息，返回

		return queryPageEntityBySql(sb.toString(), pageEntity);

	}

	/**
	 * 模糊查询成绩基础设置比例数量-李立平-2016年4月5日09:51:10-v5.0
	 *
	 * @param dataBaseName
	 *            数据库名称
	 * @param conditions
	 *            查询条件
	 * @return 返回int
	 */
	public int queryResultRuleByConditionCount(String conditions,
			String dataBaseName) {
		String sql = new String();

		// 定义sql语句，需要关联课程表，教师表，和学校日历表，并isdelete=0,最后加上模糊查询条件
		sql = "SELECT c.courseName,s.termName,r.daliyResultRate,te.NAME,r.setTime,r.id FROM "
				+ ""
				+ dataBaseName
				+ ".tb_resultrule r INNER JOIN  "
				+ ""
				+ dataBaseName
				+ ".tb_schoolcalendar s ON r.schoolcalendarId = s.id "
				+ "INNER JOIN "
				+ dataBaseName
				+ ".tb_teacher te ON r.teacherId = te.id "
				+ "INNER JOIN "
				+ dataBaseName
				+ ".tb_courseinfo c ON r.courseId = c.id where r.isDelete='0' AND "
				+ "(c.courseName like'%"
				+ conditions
				+ "%' or s.termName like '%"
				+ " + conditions"
				+ "%' or te.NAME like '%" + conditions + "%')";

		List list = queryBySql(sql);
		return list.size();
	}

	/**
	 * 查询所有学年信息（添加时使用，包括学年，教师，课程）-李立平-2016年3月25日16:05:13-v5.0
	 * 
	 * @param map
	 *            存放参数信息
	 * @return 返回建筑的list集合
	 */
	public List<SchoolCalendar> querySchoolCalendarByHql(
			Map<Serializable, Serializable> map) {
		String hql = "From SchoolCalendar h where h.isDelete=0";
		String DataBaseName = map.get("DataBaseName").toString();
		return super.queryByHql(hql, null, DataBaseName);
	}

	/**
	 * 查询所有教师信息（添加时使用，包括学年，教师，课程）-李立平-2016年3月25日16:05:13-v5.0
	 * 
	 * @param map
	 *            存放参数信息
	 * @return 返回建筑的list集合
	 */
	public List queryTeacherByHql(String courseID, String dataBaseName) {
		String sql = new String();
		// 定义使用的基础数据库
		// 定义sql语句，需要根据课程id关联培养计划—根据培养计划id在课程表里拿到教师id
		sql = "SELECT t.id,t.NAME FROM "
				+ " "
				+ dataBaseName
				+ " .tb_teacher t WHERE t.id IN ("
				+ "SELECT c.teacherid FROM  "
				+ dataBaseName
				+ " .tb_curriculumschedule_now c WHERE c.trainingProgramsID IN ("
				+ "SELECT t.id FROM  " + dataBaseName
				+ " .tb_trainingprograms_now t WHERE t.courseID ='" + courseID
				+ "' ))";

		// 要查询的字段名称集合
		List fields = new ArrayList<>();
		fields.add("id");
		fields.add("name");
		// 实例化object转map的类
		ObjectToMap objectToMap = new ObjectToMap();

		List teacherlist = this.queryObjectBySql(sql, dataBaseName);
		return objectToMap.convertToMap(fields, teacherlist);

	}

	/**
	 * 查询课程信息（添加时使用，根据学年和课程类型查询课程）-李立平-2016年3月25日16:05:13-v5.0
	 * 
	 * @param map
	 *            存放参数信息
	 * @return 返回建筑的list集合
	 */
	public List queryCourseBycourseTypeId(String dataBaseName,
			String SchoolCalanderId) {
		/**
		 * 根据课程性质
		 */
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c.id,c.courseName from " + dataBaseName
				+ ".tb_curriculumschedule_now cn ");
		sql.append("LEFT JOIN "
				+ dataBaseName
				+ ".tb_trainingprograms_now tn ON cn.trainingProgramsID =tn.id ");
		sql.append("LEFT JOIN "
				+ dataBaseName
				+ ".tb_courseinfo c on tn.courseID=c.id where c.courseTypeId='JbUEA7vDEeV6gyQjriPvoM' ");
		sql.append("and c.id not in(SELECT r.courseId from " + dataBaseName
				+ ".tb_resultrule r where r.schoolcalendarId='"
				+ SchoolCalanderId + "' and r.isDelete=0)");
		return this.queryObjectBySql(sql.toString(), dataBaseName);
	}

	/**
	 * 保存比例设置信息-李立平-2016年3月28日11:39:46-v5.0
	 * 
	 * @param ResultRule
	 *            比例规则实体
	 * @return true 或 false
	 */
	public boolean addResultRule(ResultRule resultRule) {
		// 调用底层保存实体方法
		return super.saveEntity(resultRule);
	}

	/**
	 * 修改比例设置-李立平-2016年3月28日20:31:01-v5.0
	 *
	 * @param resultRule
	 *            比例实体
	 * @return 返回boolean
	 */
	public boolean updateResultRule(ResultRule resultRule) {
		Boolean flag = false;
		/* 更新的实体　ResultRule */
		ResultRule resultrule = new ResultRule();

		/* 根据ID查找相应的实体记录 */
		resultrule = this.findEntityByIdGeneric(resultRule.getId(),
				resultRule.getDataBaseName());

		resultrule.setDaliyResultRate(resultRule.getDaliyResultRate());
		resultrule.setDataBaseName(resultRule.getDataBaseName());
		resultrule.setFinalResultRate(resultRule.getFinalResultRate());
		resultrule.setSetTime(resultRule.getSetTime());

		return this.updateEntityGeneric(resultrule);// 保存成功返回true
	}

	@Override
	public String queryTermIdByName(String termName, String dataBaseName) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT id from " + dataBaseName
				+ ".tb_schoolcalendar s where s.termName='" + termName + "'");
		List list = this.queryBySql(sb.toString());
		String termId = "";
		if (list != null && list.size() > 0) {
			return termId = list.get(0).toString();
		} else {
			return null;
		}
	}
}
