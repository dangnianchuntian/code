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

import com.tgb.itoo.base.eao.BaseEao;
import com.tgb.itoo.base.service.impl.BaseBeanImpl;
import com.tgb.itoo.basic.eao.QuestionDao;
import com.tgb.itoo.basic.entity.Question;
import com.tgb.itoo.basic.service.QuestionBean;
import com.tgb.itoo.tool.objectToMap.ObjectToMap;
import com.tgb.itoo.tool.pageModel.PageEntity;



@Stateless(name = "questionBeanImpl")
@Remote(QuestionBean.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class QuestionBeanImpl extends BaseBeanImpl<Question> implements QuestionBean{

	@EJB(name = "questionDaoImpl")
	private QuestionDao questionDao;

	public QuestionDao getQuestionDao() {
		return questionDao;
	}

	public void setQuestionDao(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	@Override
	public BaseEao getBaseEao() {
		return questionDao;
	}

	/**
	 * 查询教师布置的作业-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public  PageEntity queryQuestion(int pageNum, int pageSize,String conditions, String teacherId,String dataBaseName) {
		return questionDao.queryQuestion(pageNum,pageSize,conditions, teacherId,dataBaseName);
	}
	/**
	 * 查询教所有学年-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List<Map<Serializable, Serializable>> queryTermName(
			String dataBaseName) {
		List list=new ArrayList<>();
		List field = new ArrayList();
		ObjectToMap o2m=new ObjectToMap();

			list= questionDao.queryTermName(dataBaseName);
			field.add("id");
			field.add("termName");
	
		return o2m.convertToMap(field, list);
	}
	/**
	 * 查询所有课程-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 * @param dataBaseName
	 * @return
	 */
	@Override
	public List<Map<Serializable, Serializable>> queryCourseName(
			String dataBaseName, String teacherId) {
		List list=new ArrayList<>();
		List field = new ArrayList();
		ObjectToMap o2m=new ObjectToMap();

			list= questionDao.queryCourseName(dataBaseName, teacherId);
			field.add("id");
			field.add("courseCode");
			field.add("courseName");
		return o2m.convertToMap(field, list);
	}
	/**
	 * 查询该教师所教的班级-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 * @param dataBaseName
	 * @return
	 */
	@Override
	public List<Map<Serializable, Serializable>> queryClassName(
			String dataBaseName, String teacherId) {
		List list=new ArrayList<>();
		List field = new ArrayList();
		ObjectToMap o2m=new ObjectToMap();

			list= questionDao.queryTeachClassName(dataBaseName, teacherId);
			field.add("id");
			field.add("teachClassCode");
			field.add("teachClassName");
		
		return o2m.convertToMap(field, list);
	}
	/**
	 * 根据教师id和课程id查询teachclassid-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List<Map<Serializable, Serializable>> queryTeachClassBycat(String dataBaseName,String teacherId,String courseId) {
		
		List list=new ArrayList<>();
		List field = new ArrayList();
		ObjectToMap o2m=new ObjectToMap();

			list= questionDao.queryTeachClassBycat(dataBaseName, teacherId, courseId);
			field.add("teachClassId");
		return o2m.convertToMap(field, list);
	}

	/**
	 * 保存作业-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public boolean addQuestion(Question question) {
		return questionDao.addQuestionInfo(question);
	}
	
	/**
	 * 删除作业-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public boolean deleteQuestions(String dataBaseName, String[] ids) {
		return questionDao.deleteQuestions(dataBaseName, ids);
	}

	/**
	 * 查询教师id-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public String queryTeachId(String dataBaseName, String teachCode) {

		return questionDao.queryTeachId(dataBaseName, teachCode);
	}

	/**
	 * 根据id查询答案内容-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public String queryContextById(String dataBaseName, String id) {

		List list= questionDao.queryContextById(dataBaseName, id);

		return list.get(0).toString();
	}

	/**
	 * 根据id更新答案-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public boolean updateContextById(String dataBaseName, String id,
			String content) {
		return questionDao.updateContextById(dataBaseName, id, content);
	}
	
	/**
	 * 根据学期Name查询学期id
	 * @param dataBaseName
	 * @param termName
	 * @return
	 */
	public String queryschoolcalendarIdByName(String dataBaseName,
			String termName){
		return questionDao.queryschoolcalendarIdByName(dataBaseName,termName);
	}

}
