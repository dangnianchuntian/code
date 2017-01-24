package com.tgb.itoo.basic.eao;

import java.util.List;

import com.tgb.itoo.base.eao.BaseEao;
import com.tgb.itoo.basic.entity.StudentResult;

/**
 * 学生成绩
 * @author xinyang
 *
 */
public interface StudentResultDao extends BaseEao<StudentResult>{
	
	/**
	 * 根据学生学号查询历史已修是选修课以及成绩-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 * @param dataBaseName
	 * @param studentCode
	 * @return
	 */
 public List queryStudentResult(String dataBaseName,String studentCode);
 
 /**
  * 
  * 统计学生选课学分以及已修学分-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
  * @param dataBaseName
  * @param studentCode
  * @return
  */
 public List queryStasticResult(String dataBaseName,String studentCode);

 
 /**
  * 更新学生期末成绩和总成绩-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
  * @param dataBaseName
  * @param id
  * @param dailyResult
  * @param finalResult
  * @param totalResult
  * @return
  */
 public boolean updateResult(String dataBaseName,String id,String dailyResult,String finalResult,String totalResult,String remark);
 
 /**
  * 导入---根据学生学号查询学生id-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
  * @param dataBaseName
  * @param studentId
  * @return
  */
public List queryStudentId (String dataBaseName,String studentId) ;

/**
 * 导入-根据上课班名称查询班级id-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
 * @param dataBaseName
 * @param teachClassName
 * @return
 */
public List queryTeachClassId(String dataBaseName,String teachClassName);
/**
 * 导入--根据学生id和上课班id查询学生成绩表中是否有该学生的成绩-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
 * @param dataBaseName
 * @param teachClassName
 * @param studentId
 * @return
 */
public List queryStudentByStudentIdandTeachClassId(String dataBaseName,String  teachClassid,String studentId);

/**
 * 批量保存学生平时成绩-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
 * @param studentResultlist
 * @return
 */
public boolean saveStudenResult(List<StudentResult> studentResultlist);

/**
 * 根据学年id，上课班id，教师id查询成绩规则id-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
 */
public String queryResultId(String dataBaseName,String termId,String teachClassId,String teacherId);


/**
 * 根据上课班id和学生id更新上课班与学生关系实体 --isdelete=1-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
 */

public boolean updateTeachStudentClass(String dataBaseName,String studentId,String teachClassId);
}
