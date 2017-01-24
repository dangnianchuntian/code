package com.tgb.itoo.basic.eao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.inject.New;

import com.tgb.itoo.base.eao.impl.BaseEaoImpl;
import com.tgb.itoo.basic.eao.StudentAnswerDao;
import com.tgb.itoo.basic.entity.CourseRound;
import com.tgb.itoo.basic.entity.StudentAnswer;
 


/**
 * 学生提交作业 答案
 * @author xinyang
 *
 */
@Stateless(name = "studentAnswerDaoImpl")
@Remote(StudentAnswerDao.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudentAnswerDaoImpl extends BaseEaoImpl<StudentAnswer> implements StudentAnswerDao{

	
	/**
	 * 根据学号查询学生选修课的作业-刘新阳-2016年6月11日20:31:26-v5.0
	 */
	@Override
	public List queryQuestion(String dataBaseName, String studentCode) {
		StringBuilder sql= new StringBuilder();
	
				//c.courseTypeId='JbUEA7vDEeV6gyQjriPvoM'是公共选修课
		sql.append("SELECT DISTINCT q.id as questionid,tc.teachClassName,t.`name`,c.courseName ,scd.termName,c.classPoint ,q.questionName ,s.id as studentId,tc.id as teachClassId ,sa.answerName ,sa.id as answerId  ");
		sql.append(" from "+dataBaseName+".tb_student s LEFT JOIN "+dataBaseName+".tb_studentteachclass sc on s.id= sc.studentId ");
		sql.append("LEFT JOIN "+dataBaseName+".tb_teachclass tc on sc.teachClassId=tc.id  LEFT JOIN "+dataBaseName+".tb_question q on tc.id=q.teachClassId ");
		sql.append("LEFT JOIN "+dataBaseName+".tb_teacher t on q.teacherId =t.id LEFT JOIN "+dataBaseName+".tb_curriculumschedule_now cn on q.teachClassId= cn.teachClassId ");
		sql.append("LEFT JOIN "+dataBaseName+".tb_trainingprograms_now tn on cn.trainingProgramsID=tn.id LEFT JOIN "+dataBaseName+".tb_courseinfo c on tn.courseID =c.id ");
		sql.append("LEFT JOIN "+dataBaseName+".tb_schoolcalendar scd on q.schoolcalendarId=scd.id  LEFT JOIN "+dataBaseName+".tb_studentanswer sa on q.id=sa.questionId  where s.`code`='"+studentCode+"' and c.courseTypeId='JbUEA7vDEeV6gyQjriPvoM' and  q.isDelete=0  ORDER BY q.questionTime desc");
		
		return this.queryObjectBySql(sql.toString(),dataBaseName);
	}

	/**
	 * 添加学生答案-刘新阳-2016年6月11日20:31:26-v5.0
	 */
	@Override
	public boolean addStudentAnswer(StudentAnswer studentAnswer) {
		
		 return this.saveEntityGeneric(studentAnswer);
	}

	/**
	 * 查询学生答案-刘新阳-2016年6月11日20:31:26-v5.0
	 */
	@Override
	public String queryStudentAnswerByHql(String studentId,
			String questionId, String teachClassId, String dataBaseName) {
		   StringBuilder sql= new StringBuilder();
        sql.append("select  context  from "+dataBaseName+".tb_studentanswer where studentId='"+studentId+"' and ");
        sql.append("questionId='"+questionId+"' and teachClassId='"+teachClassId+"' and isDelete=0");
	   
	     List list = this.queryObjectBySql(sql.toString(),dataBaseName);
       
        if (list != null  &&  list.size() !=0) {
        
        	if (list.get(0) != null) {
        		String answercontext =list.get(0).toString();
    			return answercontext;
			}else {
				return null;
			}
        	
		} else {
			return null;
		}
        
     }

	/**
	 * 更新学生答案-刘新阳-2016年6月11日20:31:26-v5.0
	 */
	@Override
	public boolean updateStudentAnswer(StudentAnswer studentAnswer) {
		Boolean flag = false;
		/* 更新的实体　CourseRound */
		StudentAnswer answer = new StudentAnswer();

		/* 根据ID查找相应的实体记录 */
		answer = this.findEntityByIdGeneric(studentAnswer.getId(),
				studentAnswer.getDataBaseName());
		
		answer.setAnswerName(studentAnswer.getAnswerName());
		answer.setContext(studentAnswer.getContext());
		return this.updateEntityGeneric(answer);// 保存成功返回true
	}

	
	
	/**
	 * 查询学生提交的作业，用于教师判分-刘新阳-2016年6月11日20:31:26-v5.0
	 * @param dataBaseName
	 * @param teacherId
	 * @return
	 */
	@Override
	public List queryStudentAnswer(String dataBaseName, String teacherId,String schoolcalendarId) {
		StringBuilder sb=new StringBuilder();
		
		sb.append("SELECT ");
		sb.append("sa.id, ");
		sb.append("sa.studentId, sa.teachClassId ,");
		sb.append(" s.`code`, ");
		sb.append(" s.`name`, ");
		sb.append(" tc.teachClassName, ");
		sb.append(" q.questionName, ");
		sb.append(" q.questionTime, ");
		sb.append(" sa.answerName, ");
		sb.append(" rr.daliyResultRate, ");
		sb.append(" q.schoolcalendarId, ");
		sb.append("sr.id as resultId,");
		sb.append(" sr.daliyResult, ");
		sb.append(" sr.finalResult, ");
		sb.append(" sr.totalResult, ");
		sb.append(" sa.context ");
		sb.append(" FROM ");
		sb.append(""+dataBaseName+".tb_studentanswer sa ");
		sb.append(" LEFT JOIN "+dataBaseName+".tb_student s ON sa.studentId = s.id ");
		sb.append(" LEFT JOIN "+dataBaseName+".tb_question q ON sa.questionId = q.id ");
		sb.append(" LEFT JOIN "+dataBaseName+".tb_teachclass tc ON sa.teachClassId = tc.id ");
		sb.append(" LEFT JOIN "+dataBaseName+".tb_curriculumschedule_now cn ON q.teacherId = cn.teacherID ");
        sb.append(" AND q.teachClassId = cn.teachClassId ");
		sb.append(" LEFT JOIN "+dataBaseName+".tb_trainingprograms_now tn ON cn.trainingProgramsID = tn.id ");
		sb.append(" LEFT JOIN "+dataBaseName+".tb_resultrule rr ON tn.courseID = rr.courseID ");
		sb.append(" AND q.schoolcalendarId = rr.schoolcalendarId ");
		sb.append(" LEFT JOIN "+dataBaseName+".tb_studentresult sr ON sa.studentId=sr.studentId "); 
		sb.append(" AND q.teachClassId=sr.teachClassId "); 
		sb.append(" AND rr.id=sr.resultRuleId ");
		sb.append(" WHERE ");
		sb.append("sa.questionId IN ( ");
		sb.append("	SELECT ");
		sb.append("		q.id ");
		sb.append("	FROM ");
		sb.append("		"+dataBaseName+".tb_question q ");
		sb.append("	LEFT JOIN "+dataBaseName+".tb_curriculumschedule_now cn ON q.teacherId = cn.teacherID ");
		sb.append("	AND q.teachClassId = cn.teachClassId ");
		sb.append("	LEFT JOIN "+dataBaseName+".tb_trainingprograms_now tn ON cn.trainingProgramsID = tn.id ");
		sb.append("	LEFT JOIN "+dataBaseName+".tb_courseinfo c ON tn.courseID = c.id ");
		sb.append("	WHERE ");
		sb.append("		q.isDelete = '0' ");
		
		sb.append("	AND q.teacherId = '"+teacherId+"' ");
		sb.append("	AND c.courseTypeId = 'JbUEA7vDEeV6gyQjriPvoM' ");
		sb.append(") ");
		sb.append("AND sa.isDelete = '0' ");
		sb.append("and q.schoolcalendarId='"+schoolcalendarId+"' ");
		
		String sql= sb.toString();
		return this.queryObjectBySql(sql,dataBaseName);
	} 
}
