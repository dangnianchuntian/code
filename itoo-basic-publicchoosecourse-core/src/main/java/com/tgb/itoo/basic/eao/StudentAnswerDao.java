package com.tgb.itoo.basic.eao;

import java.util.List;

import com.tgb.itoo.base.eao.BaseEao;
import com.tgb.itoo.basic.entity.ChooseCourseRound;
import com.tgb.itoo.basic.entity.StudentAnswer;
import com.tgb.itoo.basic.entity.StudentResult;

/**
 * 学生提交的作业-刘新阳-2016年6月11日20:31:26-v5.0
 * @author xinyang
 *
 */
public interface StudentAnswerDao extends BaseEao<StudentAnswer> {
	
	/**
	 * 根据学号查询学生所有选修课的作业-刘新阳-2016年6月11日20:31:26-v5.0
	 * @param dataBaseName
	 * @param studentCode
	 * @return
	 */
   public List queryQuestion( String dataBaseName,String studentCode);
   
   /**
    * 学生提交作业-刘新阳-2016年6月11日20:31:26-v5.0
    * @param studentAnswer
    * @return
    */
   public boolean addStudentAnswer(StudentAnswer studentAnswer);

   /**
    * 根据学生id，老师留的作业id,上课班id查询学生作业-刘新阳-2016年6月11日20:31:26-v5.0
    * @param studentId
    * @param questionId
    * @param teachClassIdString
    * @param dataBaseName
    * @return
    */
   public String queryStudentAnswerByHql(String studentId,String questionId,String teachClassId ,String dataBaseName);

   
   /**
	 * 查询学生提交的作业，用于教师判分-刘新阳-2016年6月11日20:31:26-v5.0
	 * @param dataBaseName
	 * @param teacherId
	 * @return
	 */
   public List queryStudentAnswer(String dataBaseName,String teacherId,String schoolcalendarId); 	

   
   /**
    * 更新学生作业-刘新阳-2016年6月11日20:31:26-v5.0
    * @param studentAnswer
    * @return
    */
   public boolean updateStudentAnswer(StudentAnswer studentAnswer);
   
   

}
