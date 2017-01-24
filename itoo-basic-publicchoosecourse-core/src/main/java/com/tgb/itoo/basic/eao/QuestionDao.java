package com.tgb.itoo.basic.eao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.tgb.itoo.base.eao.BaseEao;
import com.tgb.itoo.basic.entity.Question;
import com.tgb.itoo.tool.pageModel.PageEntity;

/**
 * 教师留的作业，问题
 * 
 * @author xinyang
 *
 */
public interface QuestionDao extends BaseEao<Question> {
	/**
	 * 查询教师布置的作业-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param dateBaseName
	 * @param teacherId
	 * @return
	 */
	public PageEntity  queryQuestion(int pageNum, int pageSize,String conditions, String teacherId,String dataBaseName);

	/**
	 * 查询所有学年-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param dataBaseName
	 * @return
	 */
	public List queryTermName(String dataBaseName);

	/**
	 * 查询该教师所教的所有课程-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param dataBaseName
	 * @param teacherId
	 * @return
	 */
	public List queryCourseName(String dataBaseName, String teacherId);

	/**
	 * 查询该教师所教的所有班级-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param dataBaseName
	 * @param teacherId
	 * @return
	 */
	public List queryTeachClassName(String dataBaseName, String teacherId);

	/**
	 * 查询teachClassId-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @return
	 */
	public List queryTeachClassBycat(String dataBaseName, String teacherId,
			String courseId);

	/**
	 * 保存作业-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param question
	 * @return
	 */
	public boolean addQuestionInfo(Question question);

	/**
	 * 删除选中作业-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param question
	 * @return
	 */
	public boolean deleteQuestions(String dataBaseName, String[] ids);
	/**
	 * 根据教师code查询教师id-刘新阳-2016年6月11日20:31:26-v5.0
	 * @param dataBaseName
	 * @param teachCode
	 * @return
	 */
	public String queryTeachId(String dataBaseName, String teachCode);
	
	/**
	 * 根据id查询作业内容-刘新阳-2016年6月11日20:31:26-v5.0
	 * @param dataBaseName
	 * @param id
	 * @return
	 */
	public List queryContextById(String dataBaseName,String id);
	/**
	 * 根据id修改作业内容-刘新阳-2016年6月11日20:31:26-v5.0
	 * @param dataBaseName
	 * @param id
	 * @return
	 */
	public boolean updateContextById(String dataBaseName,String id,String content);

	/**
	 * 根据学期Name查询学期id--王孟梅-2016年9月25日20:53:49
	 * @param dataBaseName
	 * @param termName
	 * @return
	 */
	public String queryschoolcalendarIdByName(String dataBaseName,
			String termName);
}
