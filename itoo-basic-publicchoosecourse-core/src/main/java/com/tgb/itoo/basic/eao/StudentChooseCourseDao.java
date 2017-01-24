package com.tgb.itoo.basic.eao;

import java.util.List;

import com.tgb.itoo.base.eao.BaseEao;
import com.tgb.itoo.basic.entity.CourseRound;

public interface StudentChooseCourseDao extends BaseEao<CourseRound> {

	/**
	 * 根据课程表ID查看所有可选课程-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 * 
	 * 作者：小王丹
	 * 
	 * 时间：2016年1月4日10:47:04 分页查询课程信息
	 * 
	 * @param pageNum
	 *            页码
	 * @param pageSize
	 *            每页显示的数据
	 * @return 返回课程信息实体
	 */
	public List queryAllChooseCourse(int pageNum, int pageSize,
			String dataBaseName, String studentCode, String SortName,
			String SortValue);

	/*
	 * 根据学生ID和上课班ID查询所有课程 张晗 2017年1月20日08:30:03-v5.0
	 */
	public List queryChooseCourseBySIdAndTId(String studentId,
			String teachclassId, String dataBaseName);

	/**
	 * 根据选课时间段查询所有学生
	 * 
	 * @param studentCode
	 * @param dataBaseName
	 * @return
	 */
	public List queryStudentCodeByTime(String beginTime, String dataBaseName);

	/**
	 * 无条件分页查询，查询记录数量-维护人员：王孟梅--2016年6月11日14:55:38--v5.0 小王丹 2016-2-29 16:01:39
	 * */
	public int queryAllChooseCourseCount(String dataBaseName, String studentCode);

	/**
	 * 根据学生查询学生所有选课-维护人员：王孟梅--2016年6月11日14:55:38--v5.0 作者：小王丹
	 * 
	 * */
	public List queryStudentChooseCourse(String dataBaseName, String studentCode);

	/**
	 * 学生提交选课-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 * 小王丹-改CommonDao注释-张晗-2017年1月20日21:42:15
	 * */
	/*
	 * public boolean submitChooseCourse(String dataBaseName, StudentTeachClass
	 * studentTeachClass);
	 */

	/**
	 * 模糊查询课程-维护人员：王孟梅--2016年6月11日14:55:38--v5.0 作者小王丹 2016年1月20日15:18:29
	 * 
	 * */
	public List queryChooseCourseByCondition(int pageNum, int pageSize,
			String conditions, String dataBaseName, String studentCode,
			String SortName, String SortValue);

	/**
	 * 模糊查询--有条件查询--查询记录数量-维护人员：王孟梅--2016年6月11日14:55:38--v5.0 小王丹 2016-2-29
	 * 16:08:54
	 * */
	public int queryChooseCourseByConditionCount(String conditions,
			String dataBaseName, String studentCode);

	/**
	 * 学生选课更新剩余容量-维护人员：王孟梅--2016年6月11日14:55:38--v5.0 作者：小王丹 2016年1月22日09:28:01
	 * 
	 * */
	public boolean updateRemainCourse(CourseRound courseRound);

	/**
	 * 学生退课更新剩余容量-维护人员：王孟梅--2016年6月11日14:55:38--v5.0 作者：小王丹 2016年1月22日09:28:01
	 * 
	 * */
	public boolean updateReturnRemainCourse(CourseRound courseround);

	/**
	 * 退课删除上课班中的学生-维护人员：王孟梅--2016年6月11日14:55:38--v5.0 小王丹
	 * 2016年1月22日14:06:50--去除对CommonDao依赖注释该方法-张晗-2017年1月20日17:03:42
	 * 
	 * */
	/*
	 * public boolean deleteStudentChooseCourse(String studentId, String
	 * teachclassId, String dataBaseName);
	 */

	/**
	 * 根据学生ID查询公选课ID-维护人员：王孟梅--2016年6月11日14:55:38--v5.0 小王丹 2016年1月23日16:21:55
	 * 
	 * */

	public List queryByCourseRoundId(String courseroundId, String dataBaseName);

	/**
	 * 根据学生登录时间判断是不是可以现在这个时间段进行选课-维护人员：王孟梅--2016年6月11日14:55:38--v5.0 小王丹
	 * 2016年1月31日15:55:48
	 * */
	public List queryTimeByStudentCode(String studentCode, String dataBaseName);

	/**
	 * 柳亮亮-通过学生的id，查询学生大学期间所选的所有课程！公共选修课 ---该方法主要给移动端提供接口--2016年3月30日15:14:362
	 * 
	 * @param StudentId
	 *            学生id
	 * @param dataBaseName
	 *            数据库的名称
	 * @return list 返回所有的课程信息集合
	 */
	public List getStudentSelectCourseDaoYD(String StudentId,
			String dataBaseName);

	/**
	 * 柳亮亮-通过学生的id，查询学生本学期期间所选的所有课程！公共选修课 ---该方法主要给移动端提供接口--2016年3月30日15:14:362
	 * 
	 * @param StudentId
	 *            学生id
	 * @param dataBaseName
	 *            数据库的名称
	 * @return list 返回所有的课程信息集合
	 */
	public List getStudentSelectCourseByTimeDaoYD(String StudentId,
			String dataBaseName);

	/**
	 * 查询该轮次下所有课程，学生没有选择的课程-移动端开发-柳亮亮-2016年3月30日15:38:48-v5.0
	 * 
	 * @param StudentId
	 * @param natureID
	 * @param dataBaseName
	 * @return
	 */
	public List getAllChooseCourseDaoYD(String StudentId, String natureID,
			String dataBaseName);

	/**
	 * 查询该轮次下所有课程，学生没有选择的课程-模糊查询-移动端开发-柳亮亮-2016年3月30日15:38:48-v5.0
	 * 
	 * @param StudentId
	 * @param conditions
	 * @param dataBaseName
	 * @return
	 */
	public List queryChooseCourseDaoYD(String StudentId, String conditions,
			String dataBaseName);
}
