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
import javax.naming.NamingException;

import com.tgb.itoo.base.eao.BaseEao;
import com.tgb.itoo.base.service.impl.BaseBeanImpl;
import com.tgb.itoo.basic.eao.ResultRuleDao;
import com.tgb.itoo.basic.eao.StudentResultDao;
import com.tgb.itoo.basic.entity.Classes;
import com.tgb.itoo.basic.entity.Student;
import com.tgb.itoo.basic.entity.StudentResult;
import com.tgb.itoo.basic.entity.Teacher;
import com.tgb.itoo.basic.service.StudentBean;
import com.tgb.itoo.basic.service.StudentResultBean;
import com.tgb.itoo.tool.objectToMap.ObjectToMap;
import com.tgb.itoo.tool.pageModel.PageEntity;

@Stateless(name = "studentResultBeanImpl")
@Remote(StudentResultBean.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudentResultBeanImpl extends BaseBeanImpl<StudentResult>
		implements StudentResultBean {
	@EJB(name = "studentResultDaoImpl")
	private StudentResultDao studentResultDao;

	@Override
	public BaseEao getBaseEao() {
		return studentResultDao;
	}

	public StudentResultDao getStudentResultDao() {
		return studentResultDao;
	}

	public void setStudentResultDao(StudentResultDao studentResultDao) {
		this.studentResultDao = studentResultDao;
	}


	@EJB(name="resultRuleDaoImpl")
	private ResultRuleDao resultRuleDao;	
	
	
	public ResultRuleDao getResultRuleDao() {
		return resultRuleDao;
	}
	public void setResultRuleDao(ResultRuleDao resultRuleDao) {
		this.resultRuleDao = resultRuleDao;
	}
	/**
	 * 查询学生选课结果-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List<Map<Serializable, Serializable>> queryStudentResult(
			String dataBaseName, String studentCode) {
		// 转换map后的返回值

			// 查询返回值
			List list = new ArrayList();

			List field = new ArrayList();
			// 实例化Object转Map的类
			ObjectToMap o2m = new ObjectToMap();
			list = studentResultDao.queryStudentResult(dataBaseName,
					studentCode);

			field.add("courseName");

			field.add("teachername");
			
			field.add("courseNature");
			field.add("termName");
			field.add("credit");
			field.add("studentId");
			field.add("studentResultId");
		return  o2m.convertToMap(field, list);
	}

	/**
	 * 统计结果-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List<Map<Serializable, Serializable>> queryStasticResult(
			String dataBaseName, String studentCode) {
		// 转换map后的返回值

			// 查询返回值
			List list = new ArrayList();

			List field = new ArrayList();
			// 实例化Object转Map的类
			ObjectToMap o2m = new ObjectToMap();
			list = studentResultDao.queryStasticResult(dataBaseName,
					studentCode);

			field.add("courseNature");
			field.add("classPoint");
			field.add("credit");

		return o2m.convertToMap(field, list);
	}

	
/**
 * 更新选课结果-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
 */
	@Override
	public boolean updateResult(String dataBaseName, String id,String dailyResult,
			String finalResult, String totalResult,String studentId,String teachClassId,String remark) {
		boolean flag= studentResultDao.updateResult(dataBaseName, id, dailyResult, finalResult, totalResult,remark);
	
		boolean result=studentResultDao.updateTeachStudentClass(dataBaseName, studentId, teachClassId);
		if(flag== true && result ==true){
			return true;
		}
		else {
			return false;
		}
	}


	/**
	 * 根据学生id查询学生信息-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryStudentId(String dataBaseName, String studentId) {
	  return studentResultDao.queryStudentId(dataBaseName, studentId);
	}

	/**
	 * 根据id查询班级信息-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryTeachClassId(String dataBaseName, String teachClassName) {
		return studentResultDao.queryTeachClassId(dataBaseName, teachClassName);
	}

	/**
	 * 根据学生id和班级id查询学生信息-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryStudentByStudentIdandTeachClassId(String dataBaseName,
			String teachClassid, String studentId) {

		return studentResultDao.queryStudentByStudentIdandTeachClassId(dataBaseName, teachClassid, studentId);
	}

	/**
	 * 添加学生结果-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public boolean saveStudenResult(List<StudentResult> studentResultlist) {

		return studentResultDao.saveStudenResult(studentResultlist);
	}

	/**
	 * termId:学期名称
	 */
	@Override
	public String queryResultId(String dataBaseName, String termId,
			String teachClassId, String teacherId) {
		//根据学期名称查询学期id
	String termNameId=resultRuleDao.queryTermIdByName(termId, dataBaseName);
		return studentResultDao.queryResultId(dataBaseName, termNameId, teachClassId, teacherId);
	}


}
