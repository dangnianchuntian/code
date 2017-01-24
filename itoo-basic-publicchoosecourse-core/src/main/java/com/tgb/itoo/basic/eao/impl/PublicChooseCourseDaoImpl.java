package com.tgb.itoo.basic.eao.impl;

import java.io.Serializable;
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
import com.tgb.itoo.basic.eao.PublicChooseCourseDao;
import com.tgb.itoo.basic.entity.CourseRound;
import com.tgb.itoo.basic.entity.PublicChooseCourse;
import com.tgb.itoo.tool.pageModel.PageEntity;

@Stateless(name = "publicChooseCourseDaoImpl")
@Remote(PublicChooseCourseDao.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PublicChooseCourseDaoImpl extends BaseEaoImpl<PublicChooseCourse>
		implements PublicChooseCourseDao {

	/**
	 * 查询选修课程-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryPublicChooseCourse(String dataBaseName) {
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("select id, tatolCapacity,remainCapacity,choosedCapacity,setCapacity from  "
				+ dataBaseName + ".tb_publicchoosecourse ");
		return this.queryObjectBySql(sbSql.toString(), dataBaseName);
	}

	/**
	 * 查询机构-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryInstitutionList(String dataBaseName) {
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("SELECT id,InstitutionName from "
				+ dataBaseName
				+ ".tb_institution WHERE nodeLevelId='5dBU72huzpY5WHdPyqTMRt' and isDelete=0 ORDER BY InstitutionName desc");
		return this.queryObjectBySql(sbSql.toString(), dataBaseName);

	}

	/**
	 * 查询学校层次-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List querySchoolLeve(String dataBaseName) {
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("SELECT id,levelName  from " + dataBaseName
				+ ".tb_schoollevel where isDelete=0 ORDER BY levelName desc");
		return this.queryObjectBySql(sbSql.toString(), dataBaseName);
	}

	/**
	 * 查询年级-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryGradList(String dataBaseName) {

		StringBuilder sbSql = new StringBuilder();
		sbSql.append("SELECT id ,gradeName from " + dataBaseName
				+ ".tb_grade where isDelete=0  ORDER BY gradeName");
		return this.queryObjectBySql(sbSql.toString(), dataBaseName);

	}

	/*
	 * 模糊查询“公共选修课” eaoimpl zhanghui 2016-1-19 15:14:31 v5.0
	 */
	@Override
	public List<PublicChooseCourse> queryPCCompass(
			PageEntity<PublicChooseCourse> pageEntity, String condition,
			String dataBaseName) {

		Map<Serializable, Serializable> map = new HashMap<Serializable, Serializable>();

		StringBuilder sql = new StringBuilder("select " + dataBaseName
				+ ".t1.id," + dataBaseName + ".t2.content as nature,"
				+ dataBaseName + ".t9.content, " + dataBaseName
				+ ".t4.courseName," + dataBaseName + ".t5.NAME," + dataBaseName
				+ ".t6.InstitutionName," + dataBaseName + ".t7.tatolCapacity,"
				+ dataBaseName + ".t7.remainCapacity from " + dataBaseName
				+ ".tb_curriculumschedule_now t1 ");
		sql.append(" LEFT JOIN "
				+ dataBaseName
				+ ".tb_trainingprograms_now t3 ON t3.id = t1.trainingProgramsID");
		sql.append(" LEFT JOIN " + dataBaseName
				+ ".tb_courseinfo t4 ON t4.id = t3.courseID");
		sql.append(" LEFT JOIN " + dataBaseName
				+ ".tb_dictionary_now t2 ON t2.id = t4.courseNatureId");
		sql.append(" LEFT JOIN " + dataBaseName
				+ ".tb_dictionary_now t9 ON t9.id = t1.semesterID ");
		sql.append(" LEFT JOIN " + dataBaseName
				+ ".tb_teacher t5 ON t5.id = t1.teacherID");
		sql.append(" LEFT JOIN " + dataBaseName
				+ ".tb_institution t6 ON t6.id = t5.institutionId");
		sql.append(" LEFT JOIN " + dataBaseName
				+ ".tb_publicchoosecourse t7 ON t7.CurriculumsheduleId = t1.id");
		sql.append(" LEFT JOIN " + dataBaseName
				+ ".tb_coursetype t8 ON t8.id = t4.courseTypeId");
		sql.append(" WHERE t8.courseTypeName = '公共选修课'");
		sql.append(" AND (t4.courseName LIKE '%" + condition
				+ "%' OR t5.`name` LIKE '%" + condition
				+ "%' or t6.InstitutionName LIKE '%" + condition
				+ "%' or t9.content LIKE '%" + condition + "%')");
		sql.append(" GROUP BY t1.id,t2.content,t4.courseName,t5. NAME,t6.InstitutionName,t7.tatolCapacity,t7.remainCapacity");

		System.out.println(sql);

		// EJB优化:将调用CommonEao修改为调用BaseEao-由于BaseDao没有该方法等待--张晗-2017年1月15日23:32:36
		return this.queryPageEntityBySql(sql.toString(), pageEntity);
	}

	/**
	 * 更新公共选修课-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public boolean updatePublicChooseCourse(
			PublicChooseCourse publicChooseCourse) {
		PublicChooseCourse chooseCourse = new PublicChooseCourse();

		chooseCourse = this.findEntityByIdGeneric(publicChooseCourse.getId(),
				publicChooseCourse.getDataBaseName());
		chooseCourse.setRemainCapacity(String.valueOf(Integer
				.parseInt(chooseCourse.getRemainCapacity())
				+ Integer.parseInt(publicChooseCourse.getRemainCapacity())));
		// EJB优化:将调用CommonEao修改为调用BaseEao-张晗-2017年1月15日23:45:49
		return this.updateEntityGeneric(chooseCourse);

	}

	/**
	 * 添加选课-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public boolean addPubChooseCourse(String dataBaseName,
			String pubChooseCourseid, String remainCapacity) {
		String variable1 = "p.remainCapacity=:remainCapacity,p.operator =:operator";
		String condition1 = "where p.id=:id ";
		Map<Serializable, Serializable> map1 = new HashMap<Serializable, Serializable>();
		map1.put("remainCapacity", remainCapacity);
		map1.put("id", pubChooseCourseid);
		map1.put("operator", "zhanghui");
		// 泛型类中的方法

		return this.updateByConditionGeneric(variable1, condition1,
				dataBaseName, map1, "p");
	}

	/**
	 * 查询所有轮次课程信息+分页 eaoimpl-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List<PageEntity<CourseRound>> queryCourseByround(
			PageEntity<CourseRound> pageEntity, String condition, String sort,
			String order, String dataBaseName) {

		String sql = "SELECT tb_courseround.id,roundNo,beginTime,endTime,lastestQuitTime,tb_grade.gradeName,tb_schoollevel.levelName,tb_institution.InstitutionName,tb_publicchoosecourse.id,tb_publicchoosecourse.CurriculumsheduleId,tb_publicchoosecourse.tatolCapacity,courseName,tb_courseround.tatolCapacity,tb_publicchoosecourse.remainCapacity  "
				+ "from (((((((("
				+ dataBaseName
				+ ". tb_publicchoosecourse LEFT JOIN "
				+ dataBaseName
				+ ".tb_courseround ON "
				+ dataBaseName
				+ ".tb_publicchoosecourse.id="
				+ dataBaseName
				+ ".tb_courseround.publicchoosecourseId) LEFT JOIN "
				+ dataBaseName
				+ ".tb_choosecourseround on "
				+ dataBaseName
				+ ".tb_choosecourseround.id="
				+ dataBaseName
				+ ".tb_courseround.roundId ) LEFT JOIN"
				+ " "
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege on "
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege.courseRoundId="
				+ dataBaseName
				+ ".tb_choosecourseround.id) LEFT JOIN "
				+ dataBaseName
				+ ".tb_schoollevel on "
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege.levelId="
				+ dataBaseName
				+ ".tb_schoollevel.id) LEFT JOIN "
				+ dataBaseName
				+ ".tb_grade on tb_grade.id="
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege.gradeID) LEFT JOIN "
				+ dataBaseName
				+ ".tb_institution"
				+ " on tb_institution.id="
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege.insititutionID) LEFT JOIN "
				+ dataBaseName
				+ ".tb_curriculumschedule_now on "
				+ dataBaseName
				+ ".tb_publicchoosecourse.CurriculumsheduleId="
				+ dataBaseName
				+ ".tb_curriculumschedule_now.id)  LEFT JOIN "
				+ dataBaseName
				+ ".tb_trainingprograms_now on"
				+ " itoo_basic.tb_curriculumschedule_now.trainingProgramsID ="
				+ dataBaseName
				+ ".tb_trainingprograms_now.id) LEFT JOIN "
				+ dataBaseName
				+ ".tb_courseinfo ON "
				+ dataBaseName
				+ ".tb_trainingprograms_now.courseID="
				+ dataBaseName
				+ ".tb_courseinfo.id where "
				+ dataBaseName
				+ ".tb_choosecourseround.isDelete=0"
				+ " ORDER BY "
				+ sort
				+ " " + order + "";

		// EJB优化:将调用CommonEao修改为调用BaseEao-由于BaseDao没有该方法等待--张晗-2017年1月15日23:46:20
		return this.queryPageEntityBySql(sql, pageEntity);
	}

	/*
	 * 根据检索内容模糊查询所有轮次课程信息+分页 eaoimpl-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List<PageEntity<CourseRound>> queryCourseByRoundCompass(
			PageEntity<CourseRound> pageEntity, String condition,
			String dataBaseName) {

		String sql = "SELECT tb_courseround.id,roundNo,beginTime,endTime,lastestQuitTime,tb_grade.gradeName,tb_schoollevel.levelName,tb_institution.InstitutionName,tb_publicchoosecourse.id,tb_publicchoosecourse.CurriculumsheduleId,tb_publicchoosecourse.tatolCapacity,courseName,tb_courseround.tatolCapacity,tb_publicchoosecourse.remainCapacity  "
				+ "from (((((((("
				+ dataBaseName
				+ ".tb_courseround LEFT JOIN "
				+ dataBaseName
				+ ".tb_publicchoosecourse ON "
				+ dataBaseName
				+ ".tb_publicchoosecourse.id="
				+ dataBaseName
				+ ".tb_courseround.publicchoosecourseId) LEFT JOIN "
				+ dataBaseName
				+ ".tb_choosecourseround on "
				+ dataBaseName
				+ ".tb_choosecourseround.id="
				+ dataBaseName
				+ ".tb_courseround.roundId ) LEFT JOIN"
				+ " "
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege on "
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege.courseRoundId="
				+ dataBaseName
				+ ".tb_choosecourseround.id) LEFT JOIN "
				+ dataBaseName
				+ ".tb_schoollevel on "
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege.levelId="
				+ dataBaseName
				+ ".tb_schoollevel.id) LEFT JOIN "
				+ dataBaseName
				+ ".tb_grade on tb_grade.id="
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege.gradeID) "
				+ " LEFT JOIN "
				+ dataBaseName
				+ ".tb_institution on tb_institution.id="
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege.insititutionID) LEFT JOIN "
				+ dataBaseName
				+ ".tb_curriculumschedule_now on "
				+ dataBaseName
				+ ".tb_publicchoosecourse.CurriculumsheduleId="
				+ dataBaseName
				+ ".tb_curriculumschedule_now.id)  "
				+ " LEFT JOIN "
				+ dataBaseName
				+ ".tb_trainingprograms_now on "
				+ dataBaseName
				+ ".tb_curriculumschedule_now.trainingProgramsID ="
				+ dataBaseName
				+ ".tb_trainingprograms_now.id) LEFT JOIN "
				+ dataBaseName
				+ ".tb_courseinfo ON "
				+ dataBaseName
				+ ".tb_trainingprograms_now.courseID="
				+ dataBaseName
				+ ".tb_courseinfo.id"
				+ " WHERE "
				+ dataBaseName
				+ ".tb_choosecourseround.isDelete=0 and ("
				+ dataBaseName
				+ ".tb_institution.InstitutionName LIKE  '%"
				+ condition
				+ "%' OR "
				+ dataBaseName
				+ ".tb_choosecourseround.roundNo LIKE  '%"
				+ condition
				+ "%' or "
				+ dataBaseName
				+ ".tb_schoollevel.levelName LIKE '%"
				+ condition
				+ "%' OR tb_grade.gradeName LIKE '%"
				+ condition
				+ "%' OR "
				+ dataBaseName
				+ ".tb_courseinfo.courseName LIKE '%"
				+ condition
				+ "%') ORDER BY "
				+ dataBaseName
				+ ".tb_courseround.versionStartTime DESC";

		// EJB优化:将调用CommonEao修改为调用BaseEao-由于BaseDao没有该方法等待--张晗-2017年1月15日23:46:32
		return this.queryPageEntityBySql(sql, pageEntity);
	}

	/*
	 * 根据publicchoosecourseid更新设置容量dImpl zhanghui 2016-1-25 17:49:49 v5.0
	 */
	@Override
	public boolean updateCapacityByPid(String dataBaseName, String condition,
			String capacity, String operator) {

		String variable1 = "p.remainCapacity=:remainCapacity,p.operator =:operator";
		String condition1 = "where p.id=:id and p.isDelete =:isDelete";
		Map<Serializable, Serializable> map1 = new HashMap<>();
		map1.put("remainCapacity", capacity);
		map1.put("id", condition);
		map1.put("operator", operator);
		map1.put("isDelete", 0);
		// 泛型类中的方法
		return this.updateByConditionGeneric(variable1, condition1,
				dataBaseName, map1, "p");

	}

	/*
	 * zhanghui 2016-2-28 09:57:17 查询所有轮次课程信息v5.0
	 */
	@Override
	public List<PageEntity<CourseRound>> queryCRMaxCount(
			PageEntity<CourseRound> pageEntity, String string,
			String dataBaseName) {

		String sql = "SELECT tb_courseround.id,tb_choosecourseround.roundNo,beginTime,endTime,lastestQuitTime,tb_grade.gradeName,tb_schoollevel.levelName,tb_institution.InstitutionName,tb_publicchoosecourse.id,tb_publicchoosecourse.CurriculumsheduleId,tb_publicchoosecourse.tatolCapacity,courseName,tb_courseround.tatolCapacity,tb_publicchoosecourse.remainCapacity  "
				+ "from (((((((("
				+ dataBaseName
				+ ".tb_courseround LEFT JOIN "
				+ dataBaseName
				+ ".tb_publicchoosecourse ON "
				+ dataBaseName
				+ ".tb_publicchoosecourse.id="
				+ dataBaseName
				+ ".tb_courseround.publicchoosecourseId) LEFT JOIN "
				+ dataBaseName
				+ ".tb_choosecourseround on "
				+ dataBaseName
				+ ".tb_choosecourseround.id="
				+ dataBaseName
				+ ".tb_courseround.roundId ) LEFT JOIN"
				+ " "
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege on "
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege.courseRoundId="
				+ dataBaseName
				+ ".tb_choosecourseround.id) LEFT JOIN "
				+ dataBaseName
				+ ".tb_schoollevel on "
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege.levelId="
				+ dataBaseName
				+ ".tb_schoollevel.id) LEFT JOIN "
				+ dataBaseName
				+ ".tb_grade on tb_grade.id="
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege.gradeID) LEFT JOIN "
				+ dataBaseName
				+ ".tb_institution"
				+ " on tb_institution.id="
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege.insititutionID) LEFT JOIN "
				+ dataBaseName
				+ ".tb_curriculumschedule_now on "
				+ dataBaseName
				+ ".tb_publicchoosecourse.CurriculumsheduleId="
				+ dataBaseName
				+ ".tb_curriculumschedule_now.id)  LEFT JOIN "
				+ dataBaseName
				+ ".tb_trainingprograms_now on"
				+ " itoo_basic.tb_curriculumschedule_now.trainingProgramsID ="
				+ dataBaseName
				+ ".tb_trainingprograms_now.id) LEFT JOIN "
				+ dataBaseName
				+ ".tb_courseinfo ON "
				+ dataBaseName
				+ ".tb_trainingprograms_now.courseID="
				+ dataBaseName
				+ ".tb_courseinfo.id where "
				+ dataBaseName
				+ ".tb_choosecourseround.isDelete=0"
				+ " ORDER BY "
				+ dataBaseName + ".tb_courseround.versionStartTime";

		return this.queryBySql(sql);
	}

	/*
	 * zhanghui 2016-2-28 09:57:56 根据检索内容模糊查询所有轮次课程信息 eaoimpl v5.0
	 */
	@Override
	public List<PageEntity<CourseRound>> queryCRMaxCountCompass(
			PageEntity<CourseRound> pageEntity, String condition,
			String dataBaseName) {

		String sql = "SELECT tb_courseround.id,tb_choosecourseround.roundNo,beginTime,endTime,lastestQuitTime,tb_grade.gradeName,tb_schoollevel.levelName,tb_institution.InstitutionName,tb_publicchoosecourse.id,tb_publicchoosecourse.CurriculumsheduleId,tb_publicchoosecourse.tatolCapacity,courseName,tb_courseround.tatolCapacity,tb_publicchoosecourse.remainCapacity  "
				+ "from (((((((("
				+ dataBaseName
				+ ".tb_courseround LEFT JOIN "
				+ dataBaseName
				+ ".tb_publicchoosecourse ON "
				+ dataBaseName
				+ ".tb_publicchoosecourse.id="
				+ dataBaseName
				+ ".tb_courseround.publicchoosecourseId) LEFT JOIN "
				+ dataBaseName
				+ ".tb_choosecourseround on "
				+ dataBaseName
				+ ".tb_choosecourseround.id="
				+ dataBaseName
				+ ".tb_courseround.roundId ) LEFT JOIN"
				+ " "
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege on "
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege.courseRoundId="
				+ dataBaseName
				+ ".tb_choosecourseround.id) LEFT JOIN "
				+ dataBaseName
				+ ".tb_schoollevel on "
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege.levelId="
				+ dataBaseName
				+ ".tb_schoollevel.id) LEFT JOIN "
				+ dataBaseName
				+ ".tb_grade on tb_grade.id="
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege.gradeID) "
				+ " LEFT JOIN "
				+ dataBaseName
				+ ".tb_institution on tb_institution.id="
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege.insititutionID) LEFT JOIN "
				+ dataBaseName
				+ ".tb_curriculumschedule_now on "
				+ dataBaseName
				+ ".tb_publicchoosecourse.CurriculumsheduleId="
				+ dataBaseName
				+ ".tb_curriculumschedule_now.id)  "
				+ " LEFT JOIN "
				+ dataBaseName
				+ ".tb_trainingprograms_now on "
				+ dataBaseName
				+ ".tb_curriculumschedule_now.trainingProgramsID ="
				+ dataBaseName
				+ ".tb_trainingprograms_now.id) LEFT JOIN "
				+ dataBaseName
				+ ".tb_courseinfo ON "
				+ dataBaseName
				+ ".tb_trainingprograms_now.courseID="
				+ dataBaseName
				+ ".tb_courseinfo.id"
				+ " WHERE "
				+ dataBaseName
				+ ".tb_choosecourseround.isDelete=0 and ("
				+ dataBaseName
				+ ".tb_institution.InstitutionName LIKE  '%"
				+ condition
				+ "%' OR "
				+ dataBaseName
				+ ".tb_choosecourseround.roundNo LIKE  '%"
				+ condition
				+ "%' or "
				+ dataBaseName
				+ ".tb_schoollevel.levelName LIKE '%"
				+ condition
				+ "%' OR tb_grade.gradeName LIKE '%"
				+ condition
				+ "%' OR "
				+ dataBaseName
				+ ".tb_courseinfo.courseName LIKE '%"
				+ condition
				+ "%') ORDER BY "
				+ dataBaseName
				+ ".tb_courseround.versionStartTime";

		return this.queryBySql(sql);
	}

	@Override
	public List<PageEntity<PublicChooseCourse>> queryCourseCapacityCount(
			PageEntity<PublicChooseCourse> pageEntity, String databaseName) {

		// 有条件下的查询（如：分文史类、理工类、科教类查询，方便查看）
		String sql = "SELECT tb_publicchoosecourse.id,tb_publicchoosecourse.CurriculumsheduleId,setCapacity,choosedCapacity,remainCapacity,tatolCapacity,courseName,name from (((("
				+ databaseName
				+ ".tb_publicchoosecourse LEFT JOIN "
				+ databaseName + ".tb_curriculumschedule_now on";
		sql += " " + databaseName
				+ ".tb_publicchoosecourse.CurriculumsheduleId=" + databaseName
				+ ".tb_curriculumschedule_now.id)  LEFT JOIN " + databaseName
				+ ".tb_teacher ON ";
		sql += " " + databaseName + ".tb_curriculumschedule_now.teacherID="
				+ databaseName + ".tb_teacher.id) LEFT JOIN " + databaseName
				+ ".tb_trainingprograms_now on";
		sql += " " + databaseName
				+ ".tb_curriculumschedule_now.trainingProgramsID ="
				+ databaseName + ".tb_trainingprograms_now.id) LEFT JOIN "
				+ databaseName + ".tb_courseinfo ON ";
		sql += " " + databaseName + ".tb_trainingprograms_now.courseID="
				+ databaseName + ".tb_courseinfo.id) LEFT JOIN " + databaseName
				+ ".tb_coursetype on " + databaseName
				+ ".tb_courseinfo.courseTypeId=" + databaseName
				+ ".tb_coursetype.id ";
		sql += " WHERE " + databaseName
				+ ".tb_coursetype.courseTypeName='公共选修课'";

		return this.queryBySql(sql);
	}

	/**
	 * 查询年级机构-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryLevelGradeCollege(String dataBaseName) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT  levelId,gradeID,insititutionID from "
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege where isDelete=0");
		return this.queryObjectBySql(sql.toString(), dataBaseName);
	}

	/**
	 * 查询课程轮次id-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List querycourseroundid(String dataBaseName,
			String publicchoosecourseid) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id from " + dataBaseName
				+ ".tb_courseround where publicchoosecourseId='"
				+ publicchoosecourseid + "' and isDelete=0");
		return this.queryObjectBySql(sql.toString(), dataBaseName);
	}

	/**
	 * 查询最大数-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public int queryMaxCount(PageEntity pageEntityClass, String string,
			String dataBaseName) {

		StringBuilder myString = new StringBuilder("select " + dataBaseName
				+ ".t1.id," + dataBaseName + ".t2.content as nature,"
				+ dataBaseName + ".t9.content, " + dataBaseName
				+ ".t4.courseName," + dataBaseName + ".t5.NAME," + dataBaseName
				+ ".t6.InstitutionName," + dataBaseName + ".t7.tatolCapacity,"
				+ dataBaseName + ".t7.remainCapacity from " + dataBaseName
				+ ".tb_curriculumschedule_now t1 ");
		myString.append(" LEFT JOIN "
				+ dataBaseName
				+ ".tb_trainingprograms_now t3 ON t3.id = t1.trainingProgramsID");
		myString.append(" LEFT JOIN " + dataBaseName
				+ ".tb_courseinfo t4 ON t4.id = t3.courseID");
		myString.append(" LEFT JOIN " + dataBaseName
				+ ".tb_dictionary_now t2 ON t2.id = t4.courseNatureId");
		myString.append(" LEFT JOIN " + dataBaseName
				+ ".tb_dictionary_now t9 ON t9.id = t1.semesterID ");
		myString.append(" LEFT JOIN " + dataBaseName
				+ ".tb_teacher t5 ON t5.id = t1.teacherID");
		myString.append(" LEFT JOIN " + dataBaseName
				+ ".tb_institution t6 ON t6.id = t5.institutionId");
		myString.append(" LEFT JOIN " + dataBaseName
				+ ".tb_publicchoosecourse t7 ON t7.CurriculumsheduleId = t1.id");
		myString.append(" LEFT JOIN " + dataBaseName
				+ ".tb_coursetype t8 ON t8.id = t4.courseTypeId");
		myString.append(" WHERE t8.courseTypeName = '公共选修课'");
		myString.append(" GROUP BY 	t1.id,t2.content,t4.courseName,t5. NAME,t6.InstitutionName,t7.tatolCapacity,t7.remainCapacity");
		List list = this.queryObjectBySql(myString.toString(), dataBaseName);
		return list.size();
	};

	/**
	 * 根据轮次查询所有公共选修课-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	public List<PublicChooseCourse> queryAllPublicClassByRound(
			PageEntity pageEntity, String string, String SortName,
			String SortValue, String dataBaseName) {

		StringBuilder myString = new StringBuilder("select " + dataBaseName
				+ ".t1.id," + dataBaseName + ".t2.content as nature,"
				+ dataBaseName + ".t9.content, " + dataBaseName
				+ ".t4.courseName," + dataBaseName + ".t5.NAME," + dataBaseName
				+ ".t6.InstitutionName," + dataBaseName + ".t7.tatolCapacity,"
				+ dataBaseName + ".t7.remainCapacity from " + dataBaseName
				+ ".tb_curriculumschedule_now t1 ");
		myString.append(" LEFT JOIN "
				+ dataBaseName
				+ ".tb_trainingprograms_now t3 ON t3.id = t1.trainingProgramsID");
		myString.append(" LEFT JOIN " + dataBaseName
				+ ".tb_courseinfo t4 ON t4.id = t3.courseID");
		myString.append(" LEFT JOIN " + dataBaseName
				+ ".tb_dictionary_now t2 ON t2.id = t4.courseNatureId");
		myString.append(" LEFT JOIN " + dataBaseName
				+ ".tb_dictionary_now t9 ON t9.id = t1.semesterID ");
		myString.append(" LEFT JOIN " + dataBaseName
				+ ".tb_teacher t5 ON t5.id = t1.teacherID");
		myString.append(" LEFT JOIN " + dataBaseName
				+ ".tb_institution t6 ON t6.id = t5.institutionId");
		myString.append(" LEFT JOIN " + dataBaseName
				+ ".tb_publicchoosecourse t7 ON t7.CurriculumsheduleId = t1.id");
		myString.append(" LEFT JOIN " + dataBaseName
				+ ".tb_coursetype t8 ON t8.id = t4.courseTypeId");
		myString.append(" WHERE t8.courseTypeName = '公共选修课'");
		myString.append(" ORDER BY " + SortName + " " + SortValue + "");
		// myString.append(" GROUP BY 	t1.id,t2.content,t4.courseName,t5. NAME,t6.InstitutionName,t7.tatolCapacity,t7.remainCapacity");

		// TODO:EJB优化:将调用CommonEao修改为调用BaseEao-由于BaseDao没有该方法等待--张晗-2017年1月15日23:47:28
		return this.queryPageEntityBySql(myString.toString(), pageEntity);
	};

	/**
	 * 根据条件查询所有选修课-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 * 
	 * @param pageEntity
	 * @param conditions
	 * @param sort
	 * @param order
	 * @param databaseName
	 * @return
	 */

	public List<PageEntity<PublicChooseCourse>> queryAllPublicClassByConditions(
			PageEntity<PublicChooseCourse> pageEntity, String conditions,
			String sort, String order, String databaseName) {

		// 有条件下的查询（如：分文史类、理工类、科教类查询，方便查看）

		String sql = "SELECT tb_publicchoosecourse.id,tb_publicchoosecourse.CurriculumsheduleId,setCapacity,choosedCapacity,remainCapacity,tatolCapacity,courseName,name from (((("
				+ databaseName
				+ ".tb_publicchoosecourse LEFT JOIN "
				+ databaseName + ".tb_curriculumschedule_now on";
		sql += " " + databaseName
				+ ".tb_publicchoosecourse.CurriculumsheduleId=" + databaseName
				+ ".tb_curriculumschedule_now.id)  LEFT JOIN " + databaseName
				+ ".tb_teacher ON ";
		sql += " " + databaseName + ".tb_curriculumschedule_now.teacherID="
				+ databaseName + ".tb_teacher.id) LEFT JOIN " + databaseName
				+ ".tb_trainingprograms_now on";
		sql += " " + databaseName
				+ ".tb_curriculumschedule_now.trainingProgramsID ="
				+ databaseName + ".tb_trainingprograms_now.id) LEFT JOIN "
				+ databaseName + ".tb_courseinfo ON ";
		sql += " " + databaseName + ".tb_trainingprograms_now.courseID="
				+ databaseName + ".tb_courseinfo.id) LEFT JOIN " + databaseName
				+ ".tb_coursetype on " + databaseName
				+ ".tb_courseinfo.courseTypeId=" + databaseName
				+ ".tb_coursetype.id ";
		sql += " WHERE " + databaseName
				+ ".tb_coursetype.courseTypeName='公共选修课'";
		sql += " ORDER BY " + sort + " " + order + "";

		// EJB优化:将调用CommonEao修改为调用BaseEao-由于BaseDao没有该方法等待--张晗-2017年1月15日23:47:43
		return this.queryPageEntityBySql(sql, pageEntity);
	};

	/*
	 * public List<PublicChooseCourse> saveTotalCa(String id, int totalCapacity,
	 * String databaseName) { String sql = "SELECT * FROM " + databaseName +
	 * ".tb_publicchoosecourse WHERE curriculumsheduleId='" + id + "'"; return
	 * this.saveTotalCa(id, totalCapacity, databaseName); }
	 */

}
