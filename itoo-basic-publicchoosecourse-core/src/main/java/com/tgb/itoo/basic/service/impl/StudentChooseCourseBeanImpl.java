/**
 * 学生选课：查询所有可选课程
 * 
 * 作者：小王丹
 * 
 * 2016年1月4日16:08:50
 * */
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
import com.tgb.itoo.basic.eao.StudentChooseCourseDao;
import com.tgb.itoo.basic.entity.CourseRound;
import com.tgb.itoo.basic.entity.StudentTeachClass;
import com.tgb.itoo.basic.service.StudentChooseCourseBean;
import com.tgb.itoo.basic.service.StudentTeachClassBean;
import com.tgb.itoo.tool.objectToMap.ObjectToMap;

/**
 * @author 小王丹 查询所有课程
 */

@Stateless(name = "studentChooseCourseBeanImpl")
@Remote(StudentChooseCourseBean.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
/*
 * CourseBean cb; try{ cb = (CourseBean)this.lookupRemoteBean(
 * "itoo-basic-course-ear/itoo-basic-course-core/CourseBeanImpl!com.tgb.itoo.basic.service.CourseBean"
 * ); flag1 = cb.addCourseList(courseinfolist); }catch(NamingException ex){
 * ex.printStackTrace(); }
 */
public class StudentChooseCourseBeanImpl extends BaseBeanImpl<CourseRound>
		implements StudentChooseCourseBean {

	@EJB(name = "studentChooseCourseDaoImpl")
	private StudentChooseCourseDao studentChooseCourseDao;

	public StudentChooseCourseDao getStudentChooseCourseDao() {

		return studentChooseCourseDao;
	}

	public void setStudentChooseCourseDao(
			StudentChooseCourseDao studentChooseCourseDao) {
		this.studentChooseCourseDao = studentChooseCourseDao;

	}

	@Override
	public BaseEao getBaseEao() {

		return studentChooseCourseDao;
	}

	/**
	 * 查询所有选修课-维护人员：王孟梅--2016年6月11日14:55:38--v5.0 作者：小王丹 时间：2016年1月8日15:05:20
	 * 
	 * 
	 * */
	@Override
	public List queryAllChooseCourse(int pageNum, int pageSize,
			String dataBaseName, String studentCode, String SortName,
			String SortValue) {

		// 调用方法，进行有条件分页查询
		List pages = new ArrayList();
		List field = new ArrayList();
		/* 查询学生可选课程 */
		pages = studentChooseCourseDao.queryAllChooseCourse(pageNum, pageSize,
				dataBaseName, studentCode, SortName, SortValue);
		// 实例化对象转map
		ObjectToMap o2m = new ObjectToMap();

		field.add("courseName");
		field.add("name");
		field.add("InstitutionName");
		field.add("CourseNature");
		field.add("weekDay");
		field.add("periodTimesName");
		field.add("classHour");
		field.add("classpoint");
		field.add("remainCapacity");
		field.add("chooseCapacity");
		field.add("courseroundid");
		field.add("studentId");
		field.add("teachclassId");

		return o2m.convertToMap(field, pages);
	}

	/**
	 * 查询所有选修课的数量 -维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public int queryAllChooseCourseCount(String dataBaseName, String studentCode) {
		return studentChooseCourseDao.queryAllChooseCourseCount(dataBaseName,
				studentCode);
	}

	/**
	 * 查询学生已选课程-维护人员：王孟梅--2016年6月11日14:55:38--v5.0 作者：小王丹 时间：2016年1月8日15:06:09
	 * 
	 * */
	@Override
	public List<Map<Serializable, Serializable>> queryStudentChooseCourse(
			String dataBaseName, String studentCode) {

		// 查询返回值
		List list = new ArrayList();
		List field = new ArrayList();
		/* 查询学生已选课程 */
		list = studentChooseCourseDao.queryStudentChooseCourse(dataBaseName,
				studentCode);

		// 实例化对象转map
		ObjectToMap o2m = new ObjectToMap();

		field.add("courseName");
		field.add("classHour");
		field.add("classpoint");
		field.add("name");
		field.add("InstitutionName");
		field.add("CourseNature");
		field.add("weekDay");

		field.add("periodTimesName");
		field.add("semester");
		field.add("teachClassId");
		field.add("courseroundId");
		field.add("studentId");

		return o2m.convertToMap(field, list);
	}

	/**
	 * 提交选修课-维护人员：王孟梅--2016年6月11日14:55:38--v5.0 作者：小王丹 2016年1月11日14:20:15
	 * */
	@Override
	public boolean submitChooseCourse(String dataBaseName,
			StudentTeachClass studentTeachClass) {
		/* 学生提交选课--更新学生上课班关系实体 */

		// return studentChooseCourseDao.submitChooseCourse(dataBaseName,
		// studentTeachClass);
		// 改为从其他模块调用实现-张晗-2017年1月19日
		boolean flag = false;
		StudentTeachClassBean stcb;
		try {
			stcb = (StudentTeachClassBean) this
					.lookupRemoteBean("itoo-basic-studentteach-ear/itoo-basic-studentteach-core/StudentTeachBeanImpl!com.tgb.itoo.basic.service.StudentTeachClassBean");
			flag = stcb.saveStudentTeachClass(dataBaseName, studentTeachClass);
		} catch (NamingException ex) {
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * 学生选课-模糊查询-维护人员：王孟梅--2016年6月11日14:55:38--v5.0 小王丹 2016年1月20日15:37:03
	 * */
	@Override
	public List queryChooseCourseByCondition(int pageNum, int pageSize,
			String conditions, String dataBaseName, String studentCode,
			String SortName, String SortValue) {

		// 如果没有给出第几页，则默认显示第1页
		if (pageNum == 0) {
			pageNum = 1;
		}
		// 调用方法，进行有条件分页查询
		// 查询返回值
		List pages = new ArrayList();
		List field = new ArrayList();
		/* 学生可选课程有条件分页查询 */
		pages = studentChooseCourseDao.queryChooseCourseByCondition(pageNum,
				pageSize, conditions, dataBaseName, studentCode, SortName,
				SortValue);
		// 实例化对象转map
		ObjectToMap o2m = new ObjectToMap();
		/* 添加字段 */
		field.add("courseName");
		field.add("name");
		field.add("InstitutionName");
		field.add("CourseNature");
		field.add("weekDay");
		field.add("periodTimesName");
		field.add("classHour");
		field.add("classpoint");
		field.add("remainCapacity");
		field.add("courseroundid");
		field.add("studentId");
		field.add("teachclassId");

		return o2m.convertToMap(field, pages);

	}

	/**
	 * 根据条件内容查询选修课-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public int queryChooseCourseByConditionCount(String conditions,
			String dataBaseName, String studentCode) {

		return studentChooseCourseDao.queryChooseCourseByConditionCount(
				conditions, dataBaseName, studentCode);
	}

	/**
	 * 更新剩余容量和可选容量-维护人员：王孟梅--2016年6月11日14:55:38--v5.0 小王丹 2016年1月23日16:30:14
	 * */
	@Override
	public boolean updateRemainCourse(CourseRound courseRound) {
		/* 更新剩余容量和可选容量 */
		return studentChooseCourseDao.updateRemainCourse(courseRound);
	}

	/**
	 * 学生退课时更新剩余容量-维护人员：王孟梅--2016年6月11日14:55:38--v5.0 小王丹 2016年2月3日16:13:17
	 * 
	 * */
	@Override
	public boolean updateReturnRemainCourse(CourseRound courseround) {
		return studentChooseCourseDao.updateReturnRemainCourse(courseround);

	}

	/**
	 * 学生选课退选课程-删除学生上课班关系表的字段-维护人员：王孟梅--2016年6月11日14:55:38--v5.0 小王丹
	 * 2016年1月29日11:16:19
	 * 
	 * */

	@Override
	public boolean deleteStudentChooseCourse(String studentId,
			String teachClassId, String dataBaseName) {
		// return studentChooseCourseDao.deleteStudentChooseCourse(studentId,
		// teachclassId, dataBaseName);
		boolean flag = false;
		StudentTeachClassBean stcb;
		try {
			stcb = (StudentTeachClassBean) this
					.lookupRemoteBean("itoo-basic-studentteach-ear/itoo-basic-studentteach-core/StudentTeachBeanImpl!com.tgb.itoo.basic.service.StudentTeachClassBean");
			flag = stcb.deleteStudentTeachClass(studentId, teachClassId,
					dataBaseName);
		} catch (NamingException ex) {
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * 根据学生ID查询学生选课轮次-维护人员：王孟梅--2016年6月11日14:55:38--v5.0 小王丹 2016年1月29日11:17:04
	 * 
	 * */
	@Override
	public List queryByCourseRoundId(String courseroundId, String dataBaseName) {
		return studentChooseCourseDao.queryByCourseRoundId(courseroundId,
				dataBaseName);

	}

	/**
	 * 根据学生登录时间判断是不是可以现在这个时间段进行选课-维护人员：王孟梅--2016年6月11日14:55:38--v5.0 小王丹
	 * 2016年1月31日15:55:48
	 * */
	@Override
	public List queryTimeByStudentCode(String studentCode, String dataBaseName) {
		return studentChooseCourseDao.queryTimeByStudentCode(studentCode,
				dataBaseName);

	}

	/**
	 * 提交学生选课--维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public boolean submitStudentCourse(String dataBaseName,
			StudentTeachClass studentTeachClass, CourseRound courseRound) {
		/* 学生提交选课--更新学生上课班关系实体 */
		/*
		 * 去除对CommonDao的依赖-注释-张晗-2017年1月20日17:08:40 boolean flagStudent =
		 * studentChooseCourseDao.submitChooseCourse( dataBaseName,
		 * studentTeachClass);
		 */
		boolean flagStudent = false;
		StudentTeachClassBean stcb;
		try {
			stcb = (StudentTeachClassBean) this
					.lookupRemoteBean("itoo-basic-studentteach-ear/itoo-basic-studentteach-core/StudentTeachBeanImpl!com.tgb.itoo.basic.service.StudentTeachClassBean");
			flagStudent = stcb.saveStudentTeachClass(dataBaseName,
					studentTeachClass);
		} catch (NamingException ex) {
			ex.printStackTrace();
		}

		/* 更新剩余容量和可选容量 */
		boolean flagCapcity = studentChooseCourseDao
				.updateRemainCourse(courseRound);
		boolean flag = false;
		if (flagStudent && flagCapcity) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 查询学生大学期间所选的所有课程，移动端开发-柳亮亮-2016年3月30日15:40:44v5.0
	 */
	@Override
	public List<Map<Serializable, Serializable>> getStudentSelectCourseBeanYD(
			String StudentId, String dataBaseName) {
		// 查询返回值
		List list = new ArrayList();
		List field = new ArrayList();
		// 查询学生所选的课程
		list = studentChooseCourseDao.getStudentSelectCourseDaoYD(StudentId,
				dataBaseName);

		ObjectToMap o2m = new ObjectToMap();
		field.add("courseName");
		field.add("week");
		field.add("semester");
		field.add("classPoint");
		field.add("teacherName");
		field.add("natureName");
		// 现在还没有课程图片的链接，等后面数据库修改之后再说！-柳亮亮
		// field.add("imgUrl");

		return o2m.convertToMap(field, list);
	}

	/**
	 * 查询本学期所选的所有课程-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List<Map<Serializable, Serializable>> getStudentSelectedCourseByTimeBeanYD(
			String StudentId, String dataBaseName) {
		// 查询返回值
		List list = new ArrayList();
		List field = new ArrayList();

		// 查询学生所选的课程
		list = studentChooseCourseDao.getStudentSelectCourseByTimeDaoYD(
				StudentId, dataBaseName);

		ObjectToMap o2m = new ObjectToMap();
		field.add("courseName");
		field.add("week");
		field.add("classPoint");
		field.add("teachClassID");
		field.add("roundID");
		field.add("natureName");
		field.add("teacherName");
		// 现在还没有课程图片的链接，等后面数据库修改之后再说！-柳亮亮
		// field.add("imgUrl");

		return o2m.convertToMap(field, list);

	}

	/**
	 * 通过id查询所有选修课-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List<Map<Serializable, Serializable>> getAllChooseCourseBeanYD(
			String StudentId, String natureID, String dataBaseName) {
		// 查询返回值
		List list = new ArrayList();
		List field = new ArrayList();

		// 查询学生所选的课程
		list = studentChooseCourseDao.getAllChooseCourseDaoYD(StudentId,
				natureID, dataBaseName);

		ObjectToMap o2m = new ObjectToMap();
		field.add("teachClassId");
		field.add("roundId");
		field.add("remainCapacity");
		field.add("teacherName");
		field.add("week");
		field.add("courseName");
		field.add("classPoint");
		// 现在还没有课程图片的链接，等后面数据库修改之后再说！-柳亮亮
		// field.add("imgUrl");

		return o2m.convertToMap(field, list);
	}

	/**
	 * 通过id查询所有选修课-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List<Map<Serializable, Serializable>> queryChooseCourseeBeanYD(
			String StudentId, String conditions, String dataBaseName) {
		// 查询返回值
		List list = new ArrayList();
		List field = new ArrayList();

		// 查询学生所选的课程
		list = studentChooseCourseDao.queryChooseCourseDaoYD(StudentId,
				conditions, dataBaseName);

		ObjectToMap o2m = new ObjectToMap();
		field.add("teachClassId");
		field.add("roundId");
		field.add("remainCapacity");
		field.add("teacherName");
		field.add("week");
		field.add("courseName");
		field.add("classPoint");
		// 现在还没有课程图片的链接，等后面数据库修改之后再说！-柳亮亮
		// field.add("imgUrl");

		return o2m.convertToMap(field, list);

	}

	/**
	 * 根据选课时间段查询该时间段下的所有学生
	 */
	@Override
	public List queryStudentCodeByTime(String beginTime, String dataBaseName) {

		return studentChooseCourseDao.queryStudentCodeByTime(beginTime,
				dataBaseName);
	}
}
