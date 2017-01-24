package com.tgb.itoo.basic.service.impl;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.tgb.itoo.basic.eao.CourseRoundDao;
import com.tgb.itoo.basic.eao.ResultRuleDao;
import com.tgb.itoo.basic.eao.StudentAnswerDao;
import com.tgb.itoo.basic.entity.StudentAnswer;
import com.tgb.itoo.basic.service.StudentAnswerBean;
import com.tgb.itoo.tool.objectToMap.ObjectToMap;


/**
 * 学生提交作业，答案
 * @author xinyang
 *
 */
@Stateless(name = "studentAnswerBeanImpl")
@Remote(StudentAnswerBean.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudentAnswerBeanImpl extends BaseBeanImpl<StudentAnswer> implements StudentAnswerBean{

	@EJB(name="studentAnswerDaoImpl")
	private StudentAnswerDao studentAnswerDao ;
	@Override
	public BaseEao getBaseEao() {

		return studentAnswerDao;
	}
	public StudentAnswerDao getStudentAnswerDao() {
		return studentAnswerDao;
	}
	public void setStudentAnswerDao(StudentAnswerDao studentAnswerDao) {
		this.studentAnswerDao = studentAnswerDao;
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
	 * 查询问题--维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List<Map<Serializable, Serializable>> queryQuestion(String dataBaseName, String studentCode) {
		// 转换map后的返回值

					// 查询返回值
					List list = new ArrayList();

					List field = new ArrayList();
					// 实例化Object转Map的类
					ObjectToMap o2m = new ObjectToMap();
					list = studentAnswerDao.queryQuestion(dataBaseName, studentCode);
					field.add("questionid");
					field.add("teachClassName");
					field.add("teachername");
					field.add("courseName");
					field.add("termName");
					field.add("classPoint");
                    field.add("questionName");
                    field.add("studentId");
                    field.add("teachClassId");
                    field.add("answerName");
                    field.add("answerId");

				return o2m.convertToMap(field, list);

	}
	/**
	 * 添加学生的答案-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public boolean addStudentAnswer(StudentAnswer studentAnswer) {
		return studentAnswerDao.addStudentAnswer(studentAnswer);
	}
	   /**
	    * 根据学生id，老师留的作业id,上课班id查询学生作业-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	    * @param studentId
	    * @param questionId
	    * @param teachClassIdString
	    * @param dataBaseName
	    * @return
	    */
	//TODO 修改方法名称，词不达意-王孟梅-2016年6月11日20:42:54
	@Override
	public String queryStudentAnswerByHql(String studentId, String questionId,
			String teachClassId, String dataBaseName) {
		return studentAnswerDao.queryStudentAnswerByHql(studentId, questionId, teachClassId, dataBaseName);
	}
	
	/**
	 * 更新学生的答案--维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public boolean updateStudentAnswer(StudentAnswer studentAnswer) {
		return studentAnswerDao.updateStudentAnswer(studentAnswer);
	}
	/**
	 * 查询学生提交的作业，用于教师判分-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 * @param dataBaseName
	 * @param teacherId
	 * @param schoolcalendarId 学期名称
	 * @return
	 */
	@Override
	public List<Map<Serializable, Serializable>> queryStudentAnswer(
			String dataBaseName, String teacherId,String schoolcalendarId) {
		List list=new ArrayList<>();
		List field=new ArrayList<>();
		ObjectToMap o2m=new ObjectToMap();
		List<Map<Serializable, Serializable>> listmap=new ArrayList<Map<Serializable,Serializable>>();
		
		NumberFormat nf=NumberFormat.getPercentInstance();
		List<Map<Serializable, Serializable>> maps=new ArrayList<>();
	//根据学期名称查询学期id
		String termid= resultRuleDao.queryTermIdByName(schoolcalendarId, dataBaseName);
			list=studentAnswerDao.queryStudentAnswer(dataBaseName, teacherId,termid);
			
			field.add("id");
			field.add("studentId");
			field.add("teachClassId");
			field.add("code");
			field.add("name");
			field.add("teachClassName");
			field.add("questionName");
			field.add("questionTime");
			field.add("answerName");
			field.add("daliyResultRate");
			field.add("schoolcalendarId");
			field.add("resultId");
			field.add("daliyResult");
			field.add("finalResult");
			field.add("totalResult");
			field.add("context");
			listmap= o2m.convertToMap(field, list);
			if (listmap!=null) {
				//根据平时成绩比例算出期末比例，并加入list中
				for (int i = 0; i < listmap.size(); i++) {
					Map<Serializable, Serializable>map=new HashMap<Serializable,Serializable>();
					map.put("id", listmap.get(i).get("id"));
					map.put("studentId", listmap.get(i).get("studentId"));
					map.put("teachClassId", listmap.get(i).get("teachClassId"));
					map.put("code", listmap.get(i).get("code"));
					map.put("name", listmap.get(i).get("name"));
					map.put("teachClassName", listmap.get(i).get("teachClassName"));
					map.put("questionName", listmap.get(i).get("questionName"));
					map.put("questionTime", listmap.get(i).get("questionTime"));
					map.put("answerName", listmap.get(i).get("answerName"));
					map.put("schoolcalendarId", listmap.get(i).get("schoolcalendarId"));
					map.put("resultId", listmap.get(i).get("resultId"));
					map.put("daliyResult", listmap.get(i).get("daliyResult"));
					map.put("finalResult", listmap.get(i).get("finalResult"));
					map.put("totalResult", listmap.get(i).get("totalResult"));
					map.put("context", listmap.get(i).get("context"));
					String daliyResultRate= (String) listmap.get(i).get("daliyResultRate");
					//将百分比转换成小数
					Number finalRate;
					try {
						finalRate = nf.parse(daliyResultRate);
						double fialnum=finalRate.doubleValue();
						
						BigDecimal   b   =   new   BigDecimal(fialnum); 
						BigDecimal   b1   =   new   BigDecimal(1); 
					b=	b.setScale(2, BigDecimal.ROUND_HALF_UP);
						map.put("finalRate", b1.subtract(b).doubleValue());//期末比例
					   map.put("daliyResultRate",finalRate.doubleValue());//平时成绩
					} catch (ParseException e) {
						e.printStackTrace();
					}
					maps.add(map);
				}
			}
		
				
		return maps;
	}
}
