package com.tgb.itoo.basic.eao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.tgb.itoo.base.eao.impl.BaseEaoImpl;
import com.tgb.itoo.basic.eao.StudentResultDao;
import com.tgb.itoo.basic.entity.StudentResult;
import com.tgb.itoo.basic.entity.StudentTeachClass;

/**
 * 学生成绩
 * 
 * @author xinyang
 *
 */
@Stateless(name = "studentResultDaoImpl")
@Remote(StudentResultDao.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudentResultDaoImpl extends BaseEaoImpl<StudentResult> implements
		StudentResultDao {

	/**
	 * 查询学生成绩-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryStudentResult(String dataBaseName, String studentCode) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c.courseName,t.`name` as teacherName,dn.content,scr.termName ,sr.credit,s.id as studentId ,sr.id as studentResultId ");
		sql.append("from " + dataBaseName + ".tb_student s LEFT JOIN "
				+ dataBaseName + ".tb_studentresult sr on s.id =sr.studentId  ");
		sql.append("LEFT JOIN  "
				+ dataBaseName
				+ ".tb_question q on sr.teachClassId =q.teachClassId  LEFT JOIN "
				+ dataBaseName + ".tb_schoolcalendar scr ");
		sql.append("on q.schoolcalendarId=scr.id  LEFT JOIN "
				+ dataBaseName
				+ ".tb_curriculumschedule_now cn on sr.teachClassId=cn.teachClassId ");
		sql.append("LEFT JOIN " + dataBaseName
				+ ".tb_teacher t on cn.teacherID= t.id LEFT JOIN "
				+ dataBaseName
				+ ".tb_trainingprograms_now tn on cn.trainingProgramsID=tn.id ");
		sql.append("LEFT JOIN " + dataBaseName
				+ ".tb_courseinfo c on tn.courseID= c.id LEFT JOIN "
				+ dataBaseName
				+ ".tb_dictionary_now dn on c.courseNatureId =dn.id   ");
		sql.append("where s.`code`='" + studentCode
				+ "' and sr.isDelete=0 ORDER BY  sr.versionStartTime desc");

		return this.queryObjectBySql(sql.toString(),dataBaseName);
	}

	/**
	 * 统计结果-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryStasticResult(String dataBaseName, String studentCode) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT	tm.content,SUM(tm.classPoint) as classPoint,	SUM(tm.credit) as credit ");
		sql.append("FROM(  SELECT DISTINCT c.classPoint ,dn.content,sr.credit from "+dataBaseName+".tb_studentresult sr ");
		sql.append("LEFT JOIN "+dataBaseName+".tb_teachclass tc on sr.teachClassId=tc.id LEFT JOIN "+dataBaseName+".tb_curriculumschedule_now ");
		sql.append("cn on tc.id=cn.teachClassId LEFT JOIN "+dataBaseName+".tb_trainingprograms_now tn on cn.trainingProgramsID=tn.id ");
		sql.append("LEFT JOIN  "+dataBaseName+".tb_courseinfo c ON tn.courseID = c.id LEFT JOIN "+dataBaseName+".tb_dictionary_now dn ");
		sql.append("ON c.courseNatureId = dn.id	LEFT JOIN  "+dataBaseName+".tb_studentteachclass stc on tc.id=stc.teachClassId LEFT JOIN "+dataBaseName+".tb_student s ");
		sql.append("on stc.studentId=s.id where s.`code`='"+studentCode+"' AND dn.id IN (SELECT	tb_dictionary_now.id FROM "+dataBaseName+".tb_dictionary_now ");
		sql.append("WHERE	type = '课程性质'	AND isDelete = 0 )) as tm  GROUP BY tm.content ORDER BY tm.content");
		return this.queryObjectBySql(sql.toString(),dataBaseName);

	}

	/**
	 * 查询学生id-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryStudentId(String dataBaseName, String studentId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id from " + dataBaseName
				+ ".tb_student where code='" + studentId + "'");
		return this.queryObjectBySql(sql.toString(),dataBaseName);
	}

	/**
	 * 查询教师班级id-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryTeachClassId(String dataBaseName, String teachClassName) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id from " + dataBaseName
				+ ".tb_teachclass where teachClassName='" + teachClassName
				+ "'");
		return this.queryObjectBySql(sql.toString(),dataBaseName);
	}

	/**
	 * 通过班级id和学生id查询学生-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryStudentByStudentIdandTeachClassId(String dataBaseName,
			String teachClassid, String studentId) {
		
				StringBuilder sql = new StringBuilder();
		sql.append("SELECT id from "+dataBaseName+".tb_studentresult where isDelete=0 and studentId='"+studentId+"' and teachClassId='"+teachClassid+"'");
		return this.queryObjectBySql(sql.toString(),dataBaseName);
	}

	/**
	 * 添加学生成绩-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public boolean saveStudenResult(List<StudentResult> studentResultlist) {
		return this.saveEntitysGeneric(studentResultlist);
	}

	/**
	 * 更新学生成绩-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public boolean updateResult(String dataBaseName,String id,String dailyResult,String finalResult,String totalResult,String remark) {
		//如果总分大于60分则学分为1
		String credit="";
		if (Integer.parseInt(totalResult)>60) {
			credit="1";
		}
		else{
			credit="0";
		}
		
		/*StudentResult studentresult=new StudentResult();
		studentresult.getDaliyResult();
		studentresult.getRemark()*/
		String variable="h.daliyResult =:dailyResult,h.finalResult =:finalResult,h.totalResult =:totalResult,h.credit=:credit,h.remark=:remark";
		String condition="where h.isDelete =:isDelete and h.id=:id";
		Map<Serializable,Serializable> map = new HashMap<Serializable,Serializable>();
		map.put("dailyResult", dailyResult);
		map.put("finalResult", finalResult);
		map.put("totalResult", totalResult);
		map.put("credit", credit);
		map.put("isDelete", 0);
		map.put("remark", remark);
		map.put("id", id);
		//return this.updateByConditionGeneric(variable, condition, dataBaseName, map, "h");
		return this.updateByCondition(StudentResult.class, variable, condition, dataBaseName, map, "h");
	}
		

	/**
	 * 查询成绩id-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public String queryResultId(String dataBaseName, String termId,
			String teachClassId, String teacherId) {
					StringBuilder sb = new StringBuilder();
		sb.append("SELECT rt.id from "+dataBaseName+".tb_resultrule rt LEFT JOIN "+dataBaseName+".tb_curriculumschedule_now cn ");
		sb.append("on rt.teacherId=cn.teacherID  where rt.teacherId='"+teacherId+"' and rt.schoolcalendarId='"+termId+"' AND cn.teachClassId='"+teachClassId+"'");
		String sql = sb.toString();
		List list = this.queryObjectBySql(sql,dataBaseName);
		if (list.size()>0) {
			return list.get(0).toString();
		}else {
			return null;
		}
	
	}

	/**
	 * 更新学生班级-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public boolean updateTeachStudentClass(String dataBaseName,
			String studentId, String teachClassId) {
		//根据学生id和上课班id查询学生和上课班关系id
		StringBuilder sBuilder=new StringBuilder();
		sBuilder.append("SELECT id from "+dataBaseName+".tb_studentteachclass where studentId='"+studentId+"' and ");
		sBuilder.append("teachClassId='"+teachClassId+"'");
		StudentTeachClass studentTeachClass= new StudentTeachClass();
		List list=this.queryObjectBySql(sBuilder.toString(),dataBaseName);
		if(list.size()>0){
			String studenTeachId=list.get(0).toString();
			studentTeachClass=this.findEntityById(StudentTeachClass.class, studenTeachId, dataBaseName);
			studentTeachClass.setIsDelete(1);
			boolean flag= this.saveEntity(studentTeachClass);
			return flag;
		}
		else {
			return false;
		}
	}
	
	
}
