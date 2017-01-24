/**
 * 
 * 
 * 作者：小王丹
 * 
 * 上午11:18:42
 * */
package com.tgb.itoo.basic.eao.impl;

/**
 * 作者：小王丹
 * 时间：2016年1月4日11:09:02
 * 
 * */
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.tgb.itoo.base.eao.impl.BaseEaoImpl;
import com.tgb.itoo.basic.eao.StudentChooseCourseDao;
import com.tgb.itoo.basic.entity.CourseRound;
import com.tgb.itoo.tool.pageModel.PageEntity;

@Stateless(name = "StudenttChooseCourseDaoImpl")
@Remote(StudentChooseCourseDao.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudentChooseCourseDaoImpl extends BaseEaoImpl<CourseRound>
		implements StudentChooseCourseDao {

	/**
	 * x学生选课--有条件分页查询 小王丹 2016年1月29日10:20:36-v5.0
	 */
	@Override
	public List queryChooseCourseByCondition(int pageNum, int pageSize,
			String conditions, String dataBaseName, String studentCode,
			String SortName, String SortValue) {
		/* 创建分页实体 */
		PageEntity pageEntity = new PageEntity();

		StringBuilder queryAllCourse = new StringBuilder();
		/* 查询语句--走的线路：学生-班级-机构-选课轮次关系表-选课轮次-公选课-课表-培养计划--老师--课程--机构--字典--节次-字典 */
		queryAllCourse
				.append("select cor.courseName,tea.name,i2.InstitutionName,dd.content as dictionarycontent,d.content,pp.periodTimesName,cor.classHour,cor.classPoint,");
		queryAllCourse
				.append("crs.remainCapacity,crs.choosedCapacity,crs.id as courseroundid,stu.id as studentid,crr.teachClassId from ");
		queryAllCourse.append("" + dataBaseName + ".tb_student stu LEFT JOIN "
				+ dataBaseName
				+ ".tb_classes cls on stu.classesId=cls.id LEFT JOIN ");
		queryAllCourse.append("" + dataBaseName
				+ ".tb_institution i on i.id=cls.institutionId LEFT JOIN ");
		queryAllCourse
				.append(""
						+ dataBaseName
						+ ".tb_choosecourseroundgradecollege cg on cg.insititutionID=i.pid and cg.levelId=i.levelId AND cg.gradeID= cls.gradeId LEFT JOIN ");
		queryAllCourse
				.append(""
						+ dataBaseName
						+ ".tb_courseround crs on crs.roundId=cg.courseRoundId LEFT JOIN ");
		queryAllCourse
				.append(""
						+ dataBaseName
						+ ".tb_publicchoosecourse pch on crs.publicchoosecourseId=pch.id LEFT JOIN ");
		queryAllCourse
				.append(""
						+ dataBaseName
						+ ".tb_curriculumschedule_now crr on pch.CurriculumsheduleId=crr.id LEFT JOIN ");
		queryAllCourse
				.append(""
						+ dataBaseName
						+ ".tb_trainingprograms_now tp on tp.id=crr.trainingProgramsID LEFT JOIN ");
		queryAllCourse.append("" + dataBaseName
				+ ".tb_teacher tea on tea.id=crr.teacherID LEFT JOIN ");
		queryAllCourse.append("" + dataBaseName
				+ ".tb_courseinfo cor on cor.id=tp.courseID left join ");
		queryAllCourse.append("" + dataBaseName
				+ ".tb_institution i2 on i2.id=tea.institutionId LEFT JOIN ");
		queryAllCourse.append("" + dataBaseName
				+ ".tb_dictionary_now d on crr.weekID = d.id LEFT JOIN ");
		queryAllCourse.append("" + dataBaseName
				+ ".tb_periodtimes pp on crr.cellTimeID= pp.id LEFT JOIN ");
		queryAllCourse
				.append(""
						+ dataBaseName
						+ ".tb_dictionary_now dd ON cor.courseNatureId=dd.id where stu.code='");
		queryAllCourse.append("" + studentCode
				+ "' and cg.isDelete=0 AND (cor.courseName like'%" + conditions
				+ "%' or tea.`name` like '%");
		queryAllCourse.append("" + conditions
				+ "%' or i.InstitutionName like '%" + conditions
				+ "%' or dd.content like '%");
		queryAllCourse.append("" + conditions + "%' or d.content like '%"
				+ conditions + "%' or pp.periodTimesName like '%");
		queryAllCourse.append("" + conditions + "%' or cor.classHour like '%"
				+ conditions + "%')");
		queryAllCourse.append(" ORDER BY " + SortName + " " + SortValue + "");

		// 设置pageentity必须的几个属性
		pageEntity.setDataBaseName(dataBaseName);

		// 为分页实体设置查询语句，设置页数，和每页显示信息数目
		pageEntity.setPageNum(pageNum);
		pageEntity.setPageSize(pageSize);

		// 分页查询课程信息并返回
		return queryPageEntityBySql(queryAllCourse.toString(), pageEntity);
	}

	/*
	 * 根据学生ID和上课班ID查询所有课程 张晗 2017年1月20日08:30:03-v5.0
	 */
	@Override
	public List queryChooseCourseBySIdAndTId(String studentId,
			String teachclassId, String dataBaseName) {
		/* 查询轮次ID */
		StringBuffer queryId = new StringBuffer();
		/* 根据学生ID和上课班ＩＤ查找学生上课班关系的ID */
		queryId.append("SELECT id FROM " + dataBaseName
				+ ".tb_studentteachclass WHERE studentId='" + studentId
				+ "' AND teachClassId='" + teachclassId + "' AND isDelete='0'");
		return this.queryObjectBySql(queryId.toString(), dataBaseName);
	}

	/**
	 * 模糊查询--有条件查询所有课程 小王丹 2016-2-29 16:07:38-v5.0
	 * */
	@Override
	public int queryChooseCourseByConditionCount(String conditions,
			String dataBaseName, String studentCode) {

		String queryAllCourse = new String();
		/* 查询语句--走的线路：学生-班级-机构-选课轮次关系表-选课轮次-公选课-课表-培养计划--老师--课程--机构--字典--节次-字典 */
		queryAllCourse = "select cor.courseName,tea.name,i2.InstitutionName,dd.content,d.content,pp.periodTimesName,cor.classHour,cor.classPoint,"
				+ "crs.remainCapacity,crs.choosedCapacity,crs.id,stu.id,crr.teachClassId from "
				+ dataBaseName
				+ ".tb_student stu LEFT JOIN "
				+ dataBaseName
				+ ".tb_classes cls on stu.classesId=cls.id LEFT JOIN "
				+ dataBaseName
				+ ".tb_institution i on i.id=cls.institutionId LEFT JOIN "
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege cg on cg.insititutionID=i.pid and cg.levelId=i.levelId AND cg.gradeID= cls.gradeId LEFT JOIN "
				+ dataBaseName
				+ ".tb_courseround crs on crs.roundId=cg.courseRoundId LEFT JOIN "
				+ dataBaseName
				+ ".tb_publicchoosecourse pch on crs.publicchoosecourseId=pch.id LEFT JOIN "
				+ dataBaseName
				+ ".tb_curriculumschedule_now crr on pch.CurriculumsheduleId=crr.id LEFT JOIN "
				+ dataBaseName
				+ ".tb_trainingprograms_now tp on tp.id=crr.trainingProgramsID LEFT JOIN "
				+ dataBaseName
				+ ".tb_teacher tea on tea.id=crr.teacherID LEFT JOIN "
				+ dataBaseName
				+ ".tb_courseinfo cor on cor.id=tp.courseID left join "
				+ dataBaseName
				+ ".tb_institution i2 on i2.id=tea.institutionId LEFT JOIN "
				+ dataBaseName
				+ ".tb_dictionary_now d on crr.weekID = d.id LEFT JOIN "
				+ dataBaseName
				+ ".tb_periodtimes pp on crr.cellTimeID= pp.id LEFT JOIN "
				+ dataBaseName
				+ ".tb_dictionary_now dd ON cor.courseNatureId=dd.id where stu.code='"
				+ studentCode
				+ "' and cg.isDelete=0 AND "
				+ "(cor.courseName like'%"
				+ conditions
				+ "%' or tea.`name` like '%"
				+ conditions
				+ "%' or i.InstitutionName like '%"
				+ conditions
				+ "%' or dd.content like '%"
				+ conditions
				+ "%' or d.content like '%"
				+ conditions
				+ "%' or pp.periodTimesName like '%"
				+ conditions
				+ "%' or cor.classHour like '%" + conditions + "%')";

		List list = queryBySql(queryAllCourse);
		return list.size();
	}

	/**
	 * 查询所有可选课程名称和值班老师-刘新阳-2016年6月11日20:31:26-v5.0
	 * */
	@Override
	public List queryAllChooseCourse(int pageNum, int pageSize,
			String dataBaseName, String studentCode, String SortName,
			String SortValue) {
		// 声明一个分页查询实体
		PageEntity pageEntity = new PageEntity();

		StringBuilder queryAllCourse = new StringBuilder();
		/* p.id公选课程ID */
		/* 查询语句--走的线路：学生-班级-机构-选课轮次关系表-选课轮次-公选课-课表-培养计划--老师--课程--机构--字典--节次-字典 */
		queryAllCourse
				.append("select cor.courseName,tea.name,i2.InstitutionName,dd.content,d.content,pp.periodTimesName,cor.classHour,cor.classPoint,");
		queryAllCourse
				.append("crs.remainCapacity,crs.choosedCapacity,crs.id,stu.id,crr.teachClassId from ");
		queryAllCourse.append("" + dataBaseName + ".tb_student stu LEFT JOIN ");
		queryAllCourse.append("" + dataBaseName
				+ ".tb_classes cls on stu.classesId=cls.id LEFT JOIN ");
		queryAllCourse.append("" + dataBaseName
				+ ".tb_institution i on i.id=cls.institutionId LEFT JOIN ");
		queryAllCourse
				.append(""
						+ dataBaseName
						+ ".tb_choosecourseroundgradecollege cg on cg.insititutionID=i.pid and cg.levelId=i.levelId AND cg.gradeID= cls.gradeId LEFT JOIN ");
		queryAllCourse
				.append(""
						+ dataBaseName
						+ ".tb_courseround crs on crs.roundId=cg.courseRoundId LEFT JOIN ");
		queryAllCourse
				.append(""
						+ dataBaseName
						+ ".tb_publicchoosecourse pch on crs.publicchoosecourseId=pch.id LEFT JOIN ");
		queryAllCourse
				.append(""
						+ dataBaseName
						+ ".tb_curriculumschedule_now crr on pch.CurriculumsheduleId=crr.id LEFT JOIN ");
		queryAllCourse
				.append(""
						+ dataBaseName
						+ ".tb_trainingprograms_now tp on tp.id=crr.trainingProgramsID LEFT JOIN ");
		queryAllCourse.append("" + dataBaseName
				+ ".tb_teacher tea on tea.id=crr.teacherID LEFT JOIN ");
		queryAllCourse.append("" + dataBaseName
				+ ".tb_courseinfo cor on cor.id=tp.courseID left join ");
		queryAllCourse.append("" + dataBaseName
				+ ".tb_institution i2 on i2.id=tea.institutionId LEFT JOIN ");
		queryAllCourse.append("" + dataBaseName
				+ ".tb_dictionary_now d on crr.weekID = d.id LEFT JOIN ");
		queryAllCourse.append("" + dataBaseName
				+ ".tb_periodtimes pp on crr.cellTimeID= pp.id LEFT JOIN ");
		queryAllCourse
				.append(""
						+ dataBaseName
						+ ".tb_dictionary_now dd ON cor.courseNatureId=dd.id where stu.code='"
						+ studentCode + "' and cg.isDelete=0");
		queryAllCourse.append(" ORDER BY " + SortName + " " + SortValue + "");
		// 设置pageentity必须的几个属性
		pageEntity.setDataBaseName(dataBaseName);

		// 为分页实体设置查询语句，设置页数，和每页显示信息数目
		pageEntity.setPageNum(pageNum);
		pageEntity.setPageSize(pageSize);

		// 分页查询课程信息并返回

		return queryPageEntityBySql(queryAllCourse.toString(), pageEntity);
	}

	/**
	 * 查询所有选修课的数量-刘新阳-2016年6月11日20:31:26-v5.0
	 */
	@Override
	public int queryAllChooseCourseCount(String dataBaseName, String studentCode) {

		String queryAllCourse = new String();
		/* p.id公选课程ID */
		/* 查询语句--走的线路：学生-班级-机构-选课轮次关系表-选课轮次-公选课-课表-培养计划--老师--课程--机构--字典--节次-字典 */
		queryAllCourse = "select cor.courseName,tea.name,i2.InstitutionName,dd.content,d.content,pp.periodTimesName,cor.classHour,cor.classPoint,"
				+ "crs.remainCapacity,crs.choosedCapacity,crs.id,stu.id,crr.teachClassId from "
				+ dataBaseName
				+ ".tb_student stu LEFT JOIN "
				+ dataBaseName
				+ ".tb_classes cls on stu.classesId=cls.id LEFT JOIN "
				+ dataBaseName
				+ ".tb_institution i on i.id=cls.institutionId LEFT JOIN "
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege cg on cg.insititutionID=i.pid and cg.levelId=i.levelId AND cg.gradeID= cls.gradeId LEFT JOIN "
				+ dataBaseName
				+ ".tb_courseround crs on crs.roundId=cg.courseRoundId LEFT JOIN "
				+ dataBaseName
				+ ".tb_publicchoosecourse pch on crs.publicchoosecourseId=pch.id LEFT JOIN "
				+ dataBaseName
				+ ".tb_curriculumschedule_now crr on pch.CurriculumsheduleId=crr.id LEFT JOIN "
				+ dataBaseName
				+ ".tb_trainingprograms_now tp on tp.id=crr.trainingProgramsID LEFT JOIN "
				+ dataBaseName
				+ ".tb_teacher tea on tea.id=crr.teacherID LEFT JOIN "
				+ dataBaseName
				+ ".tb_courseinfo cor on cor.id=tp.courseID left join "
				+ dataBaseName
				+ ".tb_institution i2 on i2.id=tea.institutionId LEFT JOIN "
				+ dataBaseName
				+ ".tb_dictionary_now d on crr.weekID = d.id LEFT JOIN "
				+ dataBaseName
				+ ".tb_periodtimes pp on crr.cellTimeID= pp.id LEFT JOIN "
				+ dataBaseName
				+ ".tb_dictionary_now dd ON cor.courseNatureId=dd.id where stu.code='"
				+ studentCode + "' and cg.isDelete=0";

		List list = queryBySql(queryAllCourse);
		return list.size();
	}

	/**
	 * 查询学生已选课程-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 * 
	 * 作者：小王丹
	 * 
	 * 时间：2016年1月8日14:51:06
	 * 
	 * */
	@Override
	public List queryStudentChooseCourse(String dataBaseName, String studentCode) {

		StringBuffer queryAllCourse = new StringBuffer();
		/* 需要加最后的是公选课的条件 */
		/* 查询语句--走的线路：学生-班级-机构-选课轮次关系表-选课轮次-公选课-课表-培养计划--老师--课程--机构--字典--节次- */
		/* 字典 和学生可选课程走的路线一致，需要符合的条件是上课班ID和课表里面公选课的上课班ＩＤ是一致的 */
		queryAllCourse
				.append("SELECT cor.courseName,cor.classHour,cor.classPoint,tea.`name`,i2.InstitutionName,dd.content,d.content,pp.periodTimesName,ddd.content,crr.teachClassId,crs.id,stu.id from "
						+ dataBaseName
						+ ".tb_student stu LEFT JOIN "
						+ dataBaseName
						+ ".tb_classes cls on stu.classesId=cls.id LEFT JOIN "
						+ dataBaseName
						+ ".tb_institution i on i.id=cls.institutionId LEFT JOIN "
						+ dataBaseName
						+ ".tb_choosecourseroundgradecollege cg on cg.insititutionID=i.pid and cg.levelId=i.levelId AND cg.gradeID= cls.gradeId LEFT JOIN "
						+ dataBaseName
						+ ".tb_courseround crs on crs.roundId=cg.courseRoundId LEFT JOIN "
						+ dataBaseName
						+ ".tb_publicchoosecourse pch on crs.publicchoosecourseId=pch.id LEFT JOIN "
						+ dataBaseName
						+ ".tb_curriculumschedule_now crr on pch.CurriculumsheduleId=crr.id LEFT JOIN "
						+ dataBaseName
						+ ".tb_studentteachclass t1 ON stu.id= t1.studentId  AND crr.teachClassId= t1.teachClassId LEFT JOIN "
						+ dataBaseName
						+ ".tb_trainingprograms_now t3 ON crr.trainingProgramsID= t3.id LEFT JOIN "
						+ dataBaseName
						+ ".tb_teacher tea on tea.id=crr.teacherID LEFT JOIN "
						+ dataBaseName
						+ ".tb_courseinfo cor on cor.id=t3.courseID left join "
						+ dataBaseName
						+ ".tb_institution i2 on i2.id=tea.institutionId LEFT JOIN "
						+ dataBaseName
						+ ".tb_dictionary_now d on crr.weekID = d.id LEFT JOIN "
						+ dataBaseName
						+ ".tb_periodtimes pp on crr.cellTimeID= pp.id LEFT JOIN "
						+ dataBaseName
						+ ".tb_dictionary_now dd ON cor.courseNatureId=dd.id LEFT JOIN "
						+ dataBaseName
						+ ".tb_dictionary_now ddd ON crr.semesterID = ddd.id where stu.code='"
						+ studentCode
						+ "' AND t1.isDelete='0' and cg.isDelete=0");

		return this.queryObjectBySql(queryAllCourse.toString(), dataBaseName);

	}

	/**
	 * 提交选课－－需要更新的记录是把学生填入到相应的上课班中 小王丹
	 * 2016年1月22日14:10:07-v5.0-改CommonDao注释-张晗-2017年1月20日21:42:15
	 * */
	/*
	 * @Override public boolean submitChooseCourse(String dataBaseName,
	 * StudentTeachClass studentTeachClass) { boolean flag = false;
	 * 
	 * flag = this.saveEntity(studentTeachClass);
	 * 
	 * return flag;// 保存成功返回true
	 * 
	 * }
	 */

	/**
	 * 学生选课－－提交选课－－更新剩余容量 小王丹 2016年1月22日14:10:41-v5.0
	 * */
	@Override
	public boolean updateRemainCourse(CourseRound courseround) {

		Boolean flag = false;
		/* 更新的实体　CourseRound */
		CourseRound courseRound = new CourseRound();

		/* 根据ID查找相应的实体记录 */
		courseRound = this.findEntityByIdGeneric(courseround.getId(),
				courseround.getDataBaseName());
		int remainCapacity = Integer.parseInt(courseRound.getRemainCapacity()) - 1;

		int chooseCapacity = Integer.parseInt(courseRound.getChoosedCapacity()) + 1;
		/* 更新实体的可选容量和剩余容量 */
		courseRound.setRemainCapacity(String.valueOf(remainCapacity));
		courseRound.setChoosedCapacity(String.valueOf(chooseCapacity));

		return this.updateEntityGeneric(courseRound);// 保存成功返回true

	}

	/**
	 * 学生退课时更新剩余容量 小王丹 2016年2月3日16:08:57-v5.0
	 * 
	 * */
	@Override
	public boolean updateReturnRemainCourse(CourseRound courseround) {
		Boolean flag = false;
		/* 更新的实体　CourseRound */
		CourseRound courseRound = new CourseRound();

		/* 根据ID查找相应的实体记录 */
		courseRound = this.findEntityByIdGeneric(courseround.getId(),
				courseround.getDataBaseName());
		int remainCapacity = Integer.parseInt(courseRound.getRemainCapacity()) + 1;
		int chooseCapacity = Integer.parseInt(courseRound.getChoosedCapacity()) - 1;

		/* 更新实体的可选容量和剩余容量 */
		courseRound.setRemainCapacity(String.valueOf(remainCapacity));
		courseRound.setChoosedCapacity(String.valueOf(chooseCapacity));

		return this.updateEntity(courseRound);// 保存成功返回true

	}

	/**
	 * 删除学生选课 小王丹
	 * 2016年1月22日14:11:19-v5.0--去除对CommonDao依赖注释该方法-张晗-2017年1月20日17:03:42
	 * 
	 * */
	/*
	 * @Override public boolean deleteStudentChooseCourse(String studentId,
	 * String teachclassId, String dataBaseName) { 查询轮次ID，需要修改的容量 StringBuffer
	 * queryId = new StringBuffer(); 根据学生ID和上课班ＩＤ查找学生上课班关系的ID
	 * queryId.append("SELECT id FROM " + dataBaseName +
	 * ".tb_studentteachclass WHERE studentId='" + studentId +
	 * "' AND teachClassId='" + teachclassId + "' AND isDelete='0'"); List list
	 * = this.queryObjectBySql(queryId.toString(), dataBaseName);
	 * 
	 * 获取查到的数据并转换成string类型 String id = list.get(0).toString(); //
	 * System.out.println("--------------------->" + id); StudentTeachClass
	 * studentTeachClass = new StudentTeachClass(); 更新前需要先查找这条记录
	 * studentTeachClass = this.findEntityById(StudentTeachClass.class, id,
	 * dataBaseName); // 需要模块间
	 * 
	 * int i = 1; studentTeachClass.setIsDelete(i);
	 * studentTeachClass.setDataBaseName(dataBaseName); 删除退选课程这条记录，软删除
	 * 
	 * boolean flag= this.deleteEntityById(StudentTeachClass.class, id,
	 * dataBaseName);
	 * 
	 * return this.updateEntity(studentTeachClass); // 需要模块间调用-张晗-2017年1月19日
	 * 
	 * }
	 */

	/**
	 * 退选--根据courseroundId，需要修改的容量 小王丹 2016年1月27日20:22:10-v5.0
	 * 
	 * */
	@Override
	public List queryByCourseRoundId(String courseroundId, String dataBaseName) {
		StringBuffer queryId = new StringBuffer();
		/* 在退选课程过程中需要通过学生ID查询选课轮次，当学生ID确定，学院、层次、年级也就确定了，学生的选课轮次也就确定了 */
		queryId.append("select remainCapacity,choosedCapacity from "
				+ dataBaseName + ".tb_courseround WHERE id='" + courseroundId
				+ "'");
		return this.queryObjectBySql(queryId.toString(), dataBaseName);
	}

	/**
	 * 判断该时间点儿学生是不是可以选课 小王丹 2016年1月31日15:47:31-v5.0
	 * */

	public List queryTimeByStudentCode(String studentCode, String dataBaseName) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT crs.beginTime,crs.endTime from "
				+ dataBaseName
				+ ".tb_choosecourseround crs LEFT JOIN "
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege cg on crs.id=cg.courseRoundId LEFT JOIN "
				+ dataBaseName
				+ ".tb_institution i on cg.levelId=i.levelId and cg.insititutionID=i.pId LEFT JOIN "
				+ dataBaseName
				+ ".tb_classes c on i.id=c.institutionId and cg.gradeID=c.gradeId LEFT JOIN "
				+ dataBaseName
				+ ".tb_student s on c.id=s.classesId where crs.isDelete=0 and s.`code`='"
				+ studentCode + "'");

		return this.queryObjectBySql(sql.toString(), dataBaseName);

	}

	/**
	 * 根据时间查询所有该时间段的所有学号存入redis
	 */

	/**
	 * 柳亮亮-通过学生的id，查询学生大学期间所选的所有课程！公共选修课 ---该方法主要给移动端提供接口--2016年3月30日15:14:362
	 * 
	 * @param StudentId
	 *            学生id
	 * @param dataBaseName
	 *            数据库的名称
	 * @return list 返回所有的课程信息集合
	 */
	@Override
	public List getStudentSelectCourseDaoYD(String StudentId,
			String dataBaseName) {

		StringBuffer queryAllCourse = new StringBuffer();

		queryAllCourse
				.append("SELECT cif.courseName,drn.content,drna.content,cif.classPoint,tea.`name`,drnb.content from  "
						+ dataBaseName
						+ ".tb_studentteachclass stc LEFT JOIN  "
						+ dataBaseName
						+ ".tb_curriculumschedule_now csn ON stc.teachClassId = csn.teachClassId LEFT JOIN  "
						+ dataBaseName
						+ ".tb_teacher tea on tea.id=csn.teacherID LEFT JOIN  "
						+ dataBaseName
						+ ".tb_dictionary_now drn ON csn.weekID = drn.id  LEFT JOIN  "
						+ dataBaseName
						+ ".tb_dictionary_now drna on csn.semesterID = drna.id LEFT JOIN  "
						+ dataBaseName
						+ ".tb_trainingprograms_now tgn ON csn.trainingProgramsID = tgn.id LEFT JOIN "
						+ dataBaseName
						+ ".tb_courseinfo cif on tgn.courseID = cif.id LEFT join  "
						+ dataBaseName
						+ ".tb_dictionary_now drnb ON cif.courseNatureId = drnb.id LEFT join "
						+ dataBaseName
						+ ".tb_coursetype cty on cif.courseTypeId = cty.id where stc.studentId ='"
						+ StudentId
						+ "' and cty.courseTypeName='公共选修课' and cif.isDelete ='0' ");

		return this.queryObjectBySql(queryAllCourse.toString(), dataBaseName);
	}

	/**
	 * 查询本学期的学生所选择的所有课程---2016年3月30日20:03:02--柳亮亮-v5.0
	 * 相比于上一个方法，添加了轮次，和上课班isdelete 为0
	 */
	@Override
	public List getStudentSelectCourseByTimeDaoYD(String StudentId,
			String dataBaseName) {
		StringBuffer queryAllCourse = new StringBuffer();
		List listCourse = new ArrayList();

		queryAllCourse
				.append("SELECT cif.courseName,drn.content,cif.classPoint,stc.teachClassId,cg.courseRoundId,drnb.content,tea.`name` from "
						+ dataBaseName
						+ ".tb_studentteachclass stc LEFT JOIN "
						+ dataBaseName
						+ ".tb_curriculumschedule_now csn ON stc.teachClassId = csn.teachClassId LEFT JOIN "
						+ dataBaseName
						+ ".tb_student stut on stc.studentId = stut.id LEFT JOIN "
						+ dataBaseName
						+ ".tb_classes clss on stut.classesId = clss.id left JOIN "
						+ dataBaseName
						+ ".tb_institution i on i.id=clss.institutionId LEFT JOIN "
						+ dataBaseName
						+ ".tb_choosecourseroundgradecollege cg on cg.insititutionID=i.pid and cg.levelId=i.levelId AND cg.gradeID= clss.gradeId LEFT JOIN "
						+ dataBaseName
						+ ".tb_teacher tea on tea.id=csn.teacherID LEFT JOIN "
						+ dataBaseName
						+ ".tb_dictionary_now drn ON csn.weekID = drn.id  LEFT JOIN "
						+ dataBaseName
						+ ".tb_dictionary_now drna on csn.semesterID = drna.id LEFT JOIN "
						+ dataBaseName
						+ ".tb_trainingprograms_now tgn ON csn.trainingProgramsID = tgn.id LEFT JOIN "
						+ dataBaseName
						+ ".tb_courseinfo cif on tgn.courseID = cif.id LEFT join "
						+ dataBaseName
						+ ".tb_dictionary_now drnb ON cif.courseNatureId = drnb.id LEFT join "
						+ dataBaseName
						+ ".tb_coursetype cty on cif.courseTypeId = cty.id "
						+ "where stc.studentId ='"
						+ StudentId
						+ "' and stc.isDelete=0 and cty.courseTypeName='公共选修课' and cif.isDelete=0 ");

		return this.queryObjectBySql(queryAllCourse.toString(), dataBaseName);
	}

	// 本轮次，所有没有选择的课程--维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	@Override
	public List getAllChooseCourseDaoYD(String StudentId, String natureID,
			String dataBaseName) {
		StringBuffer queryAllCourse = new StringBuffer();
		List listCourse = new ArrayList();

		queryAllCourse
				.append("SELECT crrn.teachClassId, cg.courseRoundId, crsd.remainCapacity,tea.`name`,drn.content,cif.courseName,cif.classPoint from "
						+ dataBaseName
						+ ".tb_student stut LEFT JOIN "
						+ dataBaseName
						+ ".tb_classes clss on stut.classesId = clss.id left JOIN "
						+ dataBaseName
						+ ".tb_institution i on i.id=clss.institutionId LEFT JOIN "
						+ dataBaseName
						+ ".tb_choosecourseroundgradecollege cg on cg.insititutionID=i.pid and cg.levelId=i.levelId AND cg.gradeID= clss.gradeId LEFT JOIN "
						+ dataBaseName
						+ ".tb_courseround crsd on crsd.roundId=cg.courseRoundId LEFT JOIN "
						+ dataBaseName
						+ ".tb_publicchoosecourse pch on crsd.publicchoosecourseId=pch.id LEFT JOIN "
						+ dataBaseName
						+ ".tb_curriculumschedule_now crrn on pch.CurriculumsheduleId=crrn.id LEFT JOIN "
						+ dataBaseName
						+ ".tb_teacher tea on tea.id=crrn.teacherID LEFT JOIN "
						+ dataBaseName
						+ ".tb_dictionary_now drn ON crrn.weekID = drn.id  LEFT JOIN "
						+ dataBaseName
						+ ".tb_trainingprograms_now tgn ON crrn.trainingProgramsID = tgn.id LEFT JOIN "
						+ dataBaseName
						+ ".tb_courseinfo cif on tgn.courseID = cif.id LEFT join "
						+ dataBaseName
						+ ".tb_dictionary_now drnb ON cif.courseNatureId = drnb.id LEFT join "
						+ dataBaseName
						+ ".tb_coursetype cty on cif.courseTypeId = cty.id where stut.id ='"
						+ StudentId
						+ "' and cty.courseTypeName='公共选修课' and cif.isDelete=0 and cg.isDelete=0 and drnb.id='"
						+ natureID
						+ "' and crrn.teachClassId not IN "
						+ "(SELECT stc.teachClassId from  "
						+ dataBaseName
						+ ".tb_studentteachclass stc LEFT JOIN "
						+ dataBaseName
						+ ".tb_curriculumschedule_now csn ON stc.teachClassId = csn.teachClassId LEFT JOIN "
						+ dataBaseName
						+ ".tb_student stut on stc.studentId = stut.id LEFT JOIN "
						+ dataBaseName
						+ ".tb_classes clss on stut.classesId = clss.id left JOIN "
						+ dataBaseName
						+ ".tb_institution i on i.id=clss.institutionId LEFT JOIN "
						+ dataBaseName
						+ ".tb_choosecourseroundgradecollege cg on cg.insititutionID=i.pid and cg.levelId=i.levelId AND cg.gradeID= clss.gradeId LEFT JOIN "
						+ dataBaseName
						+ ".tb_teacher tea on tea.id=csn.teacherID LEFT JOIN "
						+ dataBaseName
						+ ".tb_dictionary_now drn ON csn.weekID = drn.id  LEFT JOIN "
						+ dataBaseName
						+ ".tb_dictionary_now drna on csn.semesterID = drna.id LEFT JOIN "
						+ dataBaseName
						+ ".tb_trainingprograms_now tgn ON csn.trainingProgramsID = tgn.id LEFT JOIN "
						+ dataBaseName
						+ ".tb_courseinfo cif on tgn.courseID = cif.id LEFT join "
						+ dataBaseName
						+ ".tb_dictionary_now drnb ON cif.courseNatureId = drnb.id LEFT join  "
						+ dataBaseName
						+ ".tb_coursetype cty on cif.courseTypeId = cty.id where stc.studentId ='"
						+ StudentId
						+ "' and stc.isDelete=0 and cty.courseTypeName='公共选修课' and cif.isDelete=0 and drnb.id='"
						+ natureID + "') ");

		return this.queryObjectBySql(queryAllCourse.toString(), dataBaseName);
	}

	// 本轮次，所有没有选择的课程-模糊查询--维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	@Override
	public List queryChooseCourseDaoYD(String StudentId, String conditions,
			String dataBaseName) {
		StringBuffer queryAllCourse = new StringBuffer();

		queryAllCourse
				.append("SELECT crrn.teachClassId, cg.courseRoundId, crsd.remainCapacity,tea.`name`,drn.content,cif.courseName,cif.classPoint from "
						+ dataBaseName
						+ ".tb_student stut LEFT JOIN "
						+ dataBaseName
						+ ".tb_classes clss on stut.classesId = clss.id left JOIN "
						+ dataBaseName
						+ ".tb_institution i on i.id=clss.institutionId LEFT JOIN "
						+ dataBaseName
						+ ".tb_choosecourseroundgradecollege cg on cg.insititutionID=i.pid and cg.levelId=i.levelId AND cg.gradeID= clss.gradeId LEFT JOIN "
						+ dataBaseName
						+ ".tb_courseround crsd on crsd.roundId=cg.courseRoundId LEFT JOIN "
						+ dataBaseName
						+ ".tb_publicchoosecourse pch on crsd.publicchoosecourseId=pch.id LEFT JOIN "
						+ dataBaseName
						+ ".tb_curriculumschedule_now crrn on pch.CurriculumsheduleId=crrn.id LEFT JOIN "
						+ dataBaseName
						+ ".tb_teacher tea on tea.id=crrn.teacherID LEFT JOIN "
						+ dataBaseName
						+ ".tb_dictionary_now drn ON crrn.weekID = drn.id  LEFT JOIN "
						+ dataBaseName
						+ ".tb_trainingprograms_now tgn ON crrn.trainingProgramsID = tgn.id LEFT JOIN "
						+ dataBaseName
						+ ".tb_courseinfo cif on tgn.courseID = cif.id LEFT join "
						+ dataBaseName
						+ ".tb_dictionary_now drnb ON cif.courseNatureId = drnb.id LEFT join "
						+ dataBaseName
						+ ".tb_coursetype cty on cif.courseTypeId = cty.id where stut.id ='12CxmvvEbM2FtRDXGfGVnr' and(cif.courseName like '%"
						+ conditions
						+ "%' or drn.content like '%"
						+ conditions
						+ "%' or tea.`name` like '%"
						+ conditions
						+ "%') and cty.courseTypeName='公共选修课' and cif.isDelete=0 and cg.isDelete=0 and crrn.teachClassId not IN "
						+ "(SELECT stc.teachClassId from  "
						+ dataBaseName
						+ ".tb_studentteachclass stc LEFT JOIN "
						+ dataBaseName
						+ ".tb_curriculumschedule_now csn ON stc.teachClassId = csn.teachClassId LEFT JOIN "
						+ dataBaseName
						+ ".tb_student stut on stc.studentId = stut.id LEFT JOIN "
						+ dataBaseName
						+ ".tb_classes clss on stut.classesId = clss.id left JOIN "
						+ dataBaseName
						+ ".tb_institution i on i.id=clss.institutionId LEFT JOIN "
						+ dataBaseName
						+ ".tb_choosecourseroundgradecollege cg on cg.insititutionID=i.pid and cg.levelId=i.levelId AND cg.gradeID= clss.gradeId LEFT JOIN "
						+ dataBaseName
						+ ".tb_teacher tea on tea.id=csn.teacherID LEFT JOIN "
						+ dataBaseName
						+ ".tb_dictionary_now drn ON csn.weekID = drn.id  LEFT JOIN "
						+ dataBaseName
						+ ".tb_dictionary_now drna on csn.semesterID = drna.id LEFT JOIN "
						+ dataBaseName
						+ ".tb_trainingprograms_now tgn ON csn.trainingProgramsID = tgn.id LEFT JOIN "
						+ dataBaseName
						+ ".tb_courseinfo cif on tgn.courseID = cif.id LEFT join "
						+ dataBaseName
						+ ".tb_dictionary_now drnb ON cif.courseNatureId = drnb.id LEFT join  "
						+ dataBaseName
						+ ".tb_coursetype cty on cif.courseTypeId = cty.id where stc.studentId ='"
						+ StudentId
						+ "' and stc.isDelete=0 and cty.courseTypeName='公共选修课' and cif.isDelete=0 ) ");

		return this.queryObjectBySql(queryAllCourse.toString(), dataBaseName);
	}

	@Override
	public List queryStudentCodeByTime(String beginTime, String dataBaseName) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT s.`code` from "
				+ dataBaseName
				+ ".tb_choosecourseround crs LEFT JOIN "
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege cg on crs.id=cg.courseRoundId LEFT JOIN "
				+ dataBaseName
				+ ".tb_institution i on cg.levelId=i.levelId and cg.insititutionID=i.pId LEFT JOIN "
				+ dataBaseName
				+ ".tb_classes c on i.id=c.institutionId and cg.gradeID=c.gradeId LEFT JOIN "
				+ dataBaseName
				+ ".tb_student s on c.id=s.classesId where crs.isDelete=0 and crs.beginTime ='"
				+ beginTime + "' AND NOT ISNULL(s.`code`)");

		return this.queryObjectBySql(sql.toString(), dataBaseName);
	}

}
