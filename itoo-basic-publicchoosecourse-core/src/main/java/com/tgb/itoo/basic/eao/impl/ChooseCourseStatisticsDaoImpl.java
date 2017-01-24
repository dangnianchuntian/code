package com.tgb.itoo.basic.eao.impl;

import java.util.Collections;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.tgb.itoo.base.eao.impl.BaseEaoImpl;
import com.tgb.itoo.basic.eao.ChooseCourseStatisticsDao;
import com.tgb.itoo.basic.entity.PublicChooseCourse;
import com.tgb.itoo.tool.pageModel.PageEntity;

@Stateless(name = "chooseCourseStatisticsDaoImpl")
@Remote(ChooseCourseStatisticsDao.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ChooseCourseStatisticsDaoImpl extends
		BaseEaoImpl<PublicChooseCourse> implements ChooseCourseStatisticsDao {

	private static final String LEFT_JOIN = "LEFT JOIN ";
	private static final String SELECT_1 = "SELECT cr.id,cr.roundId,cr.tatolCapacity,c.courseName,d.content,i.InstitutionName ";
	private static final String SELECT_2 = ".tb_courseround cr LEFT JOIN ";
	private static final String SELECT_3 = ".tb_publicchoosecourse pc ";
	private static final String SELECT_4 = ".cr.publicchoosecourseId= pc.id LEFT JOIN ";
	private static final String SELECT_5 = ".tb_curriculumschedule_now cn";
	private static final String SELECT_6 = " on pc.CurriculumsheduleId=cn.id LEFT JOIN ";
	private static final String SELECT_7 = "cn.trainingProgramsID =tn.id LEFT JOIN ";
	private static final String SELECT_8 = ".tb_courseinfo c on tn.courseID =c.id LEFT JOIN ";
	private static final String SELECT_9 = ".tb_dictionary_now d ";
	private static final String SELECT_10 = "on c.courseNatureId= d.id LEFT JOIN ";
	private static final String SELECT_11 = ".tb_institution i on c.institutionId=i.id ";
	private static final String SELECT_12 = "where cr.roundId='";

	/**
	 * 查询机构-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryInstitutionList(String dataBaseName) {
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("SELECT id,InstitutionName from " + dataBaseName
				+ ".tb_institution WHERE nodeLevelId='5dBU72huzpY5WHdPyqTMRt'");

		List list = this.queryObjectBySql(sbSql.toString(), dataBaseName);
		// 判断查询的数据是否为空
		if (list.isEmpty() || list == null) {
			return Collections.emptyList();
		}

		return list;

	}

	/**
	 * 查询学校层次-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List querySchoolLeve(String dataBaseName) {
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("SELECT id,levelName  from " + dataBaseName
				+ ".tb_schoollevel where isDelete=0");

		List list = this.queryObjectBySql(sbSql.toString(), dataBaseName);
		// 判断查询的数据是否为空
		if (list.isEmpty() || list == null) {
			return Collections.emptyList();
		}

		return list;
	}

	/**
	 * 查询课程性质id-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryCourseNatureId(String dataBaseName) {
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("SELECT id from " + dataBaseName
				+ ".tb_dictionary_now where type='课程性质' ORDER BY content ");

		List list = this.queryObjectBySql(sbSql.toString(), dataBaseName);
		// 判断查询的数据是否为空
		if (list.isEmpty() || list == null) {
			return Collections.emptyList();
		}

		return list;
	}

	/**
	 * 根据课程性质id查询课程id-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryCourseIdByCourseNatureId(String dataBaseName,
			String courseNatureId) {
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("SELECT id  FROM " + dataBaseName
				+ ".tb_courseinfo where courseNatureId='" + courseNatureId
				+ "'");

		List list = this.queryObjectBySql(sbSql.toString(), dataBaseName);
		// 判断查询的数据是否为空
		if (list.isEmpty() || list == null) {
			return Collections.emptyList();
		}

		return list;

	}

	/**
	 * 根据课程信息id查询教师所教班级-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryTeachClassByCouserInfoId(String dataBaseName,
			String courseId) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT id   FROM " + dataBaseName
				+ ".tb_teachclass where courseInfoId ='" + courseId + "'");

		List list = this.queryObjectBySql(sbSql.toString(), dataBaseName);
		// 判断查询的数据是否为空
		if (list.isEmpty() || list == null) {
			return Collections.emptyList();
		}

		return list;
	}

	/**
	 * 根据教师所教班级查询学生人数-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryStudenCountByTeachClassId(String dataBaseName,
			String teachClassId) {
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("SELECT COUNT(*) as studentCount  FROM " + dataBaseName
				+ ".tb_studentteachclass where teachClassId ='" + teachClassId
				+ "'");

		List list = this.queryObjectBySql(sbSql.toString(), dataBaseName);
		// 判断查询的数据是否为空
		if (list.isEmpty() || list == null) {
			return Collections.emptyList();
		}

		return list;
	}

	/**
	 * 查询年级-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryGradList(String dataBaseName) {

		StringBuilder sbSql = new StringBuilder();
		sbSql.append("SELECT id ,gradeName from " + dataBaseName + ".tb_grade");

		List list = this.queryObjectBySql(sbSql.toString(), dataBaseName);
		// 判断查询的数据是否为空
		if (list.isEmpty() || list == null) {
			return Collections.emptyList();
		}

		return list;

	}

	/**
	 * 查询可选课程-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryRemainAndChoosedCapacity(int pageNum, int pageSize,
			String sort, String order, String dataBaseName) {
		StringBuilder sbSql = new StringBuilder();
		PageEntity pageEntity = new PageEntity();
		String courseTypeid = "JbUEA7vDEeV6gyQjriPvoM";
		sbSql.append("SELECT cr.id,sum(cr.choosedCapacity) as choosedCapacity ,sum(cr.remainCapacity) as remainCapacity , c.id as courseinfoid,c.courseName,pc.id as publicchoosecourseid");
		sbSql.append("  from " + dataBaseName + ".tb_courseinfo c  RIGHT JOIN "
				+ dataBaseName
				+ ".tb_trainingprograms_now tn on c.id=tn.courseID RIGHT JOIN "
				+ dataBaseName + ".tb_curriculumschedule_now cn ");
		sbSql.append("on tn.id= cn.trainingProgramsID RIGHT JOIN "
				+ dataBaseName
				+ ".tb_publicchoosecourse pc on cn.id=pc.CurriculumsheduleId  RIGHT JOIN "
				+ dataBaseName
				+ ".tb_courseround cr on pc.id =cr.publicchoosecourseId ");
		sbSql.append("where c.courseTypeId='" + courseTypeid + "'");

		sbSql.append("GROUP BY pc.id ORDER BY " + sort + " " + order + "");

		// 设置pageentity必须的几个属性
		pageEntity.setDataBaseName(dataBaseName);

		// 为分页实体设置查询语句，设置页数，和每页显示信息数目
		pageEntity.setPageNum(pageNum);
		pageEntity.setPageSize(pageSize);

		List list = this.queryPageEntityBySql(sbSql.toString(), pageEntity);

		// 判断查询的数据是否为空
		if (list.isEmpty() || list == null) {
			return Collections.emptyList();
		}

		// 分页查询课程信息并返回
		return list;
	}

	@Override
	public int queryRemainAndChoosedCapacityCount(String sortName,
			String sortValue, String dataBaseName) {
		StringBuilder sbSql = new StringBuilder();

		String courseTypeid = "JbUEA7vDEeV6gyQjriPvoM";
		sbSql.append("SELECT cr.id,sum(cr.choosedCapacity) as choosedCapacity ,sum(cr.remainCapacity) as remainCapacity , c.id as courseinfoid,c.courseName,pc.id as publicchoosecourseid");
		sbSql.append("  from " + dataBaseName + ".tb_courseinfo c  RIGHT JOIN "
				+ dataBaseName
				+ ".tb_trainingprograms_now tn on c.id=tn.courseID RIGHT JOIN "
				+ dataBaseName + ".tb_curriculumschedule_now cn ");
		sbSql.append("on tn.id= cn.trainingProgramsID RIGHT JOIN "
				+ dataBaseName
				+ ".tb_publicchoosecourse pc on cn.id=pc.CurriculumsheduleId  RIGHT JOIN "
				+ dataBaseName
				+ ".tb_courseround cr on pc.id =cr.publicchoosecourseId ");
		sbSql.append("where c.courseTypeId='" + courseTypeid + "'");

		sbSql.append("GROUP BY pc.id ");
		List list = queryBySql(sbSql.toString());
		return list.size();

	}

	/**
	 * 已选课程容量统计-模糊查询（分页）-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 * 
	 * */
	@Override
	public List queryRCCapacityByCourseName(int pageNum, int pageSize,
			String condition, String dataBaseName, String sort, String order) {

		PageEntity pageEntity = new PageEntity();
		StringBuilder sbSql = new StringBuilder();

		String courseTypeid = "JbUEA7vDEeV6gyQjriPvoM";
		sbSql.append("SELECT cr.id,sum(cr.choosedCapacity) as choosedCapacity ,sum(cr.remainCapacity) as remainCapacity , c.id as courseinfoid,c.courseName,pc.id as publicchoosecourseid");
		sbSql.append("  from " + dataBaseName + ".tb_courseinfo c  RIGHT JOIN "
				+ dataBaseName
				+ ".tb_trainingprograms_now tn on c.id=tn.courseID RIGHT JOIN "
				+ dataBaseName + ".tb_curriculumschedule_now cn ");
		sbSql.append("on tn.id= cn.trainingProgramsID RIGHT JOIN "
				+ dataBaseName
				+ ".tb_publicchoosecourse pc on cn.id=pc.CurriculumsheduleId  RIGHT JOIN "
				+ dataBaseName
				+ ".tb_courseround cr on pc.id =cr.publicchoosecourseId ");
		sbSql.append("where c.courseTypeId='" + courseTypeid
				+ "' and  c.courseName LIKE '%" + condition
				+ "%' GROUP BY c.id");
		// 设置pageentity必须的几个属性
		pageEntity.setDataBaseName(dataBaseName);

		// 为分页实体设置查询语句，设置页数，和每页显示信息数目
		pageEntity.setPageNum(pageNum);
		pageEntity.setPageSize(pageSize);

		List list = this.queryPageEntityBySql(sbSql.toString(), pageEntity);
		// 判断查询的数据是否为空
		if (list.isEmpty() || list == null) {
			return Collections.emptyList();
		}

		// 分页查询课程信息并返回
		return list;

	}

	/**
	 * 已选课程容量统计--查询总容量 小王丹修改 2016-2-29 08:52:40-v5.0
	 * 
	 * */
	@Override
	public int queryRCCapacityCountByCourseNameCount(String dataBaseName,
			String condition) {

		StringBuilder sbSql = new StringBuilder();

		String courseTypeid = "JbUEA7vDEeV6gyQjriPvoM";// 公共选修课
		sbSql.append("SELECT cr.id,sum(cr.choosedCapacity) as choosedCapacity ,sum(cr.remainCapacity) as remainCapacity , c.id as courseinfoid,c.courseName,pc.id as publicchoosecourseid");
		sbSql.append("  from " + dataBaseName + ".tb_courseinfo c  RIGHT JOIN "
				+ dataBaseName
				+ ".tb_trainingprograms_now tn on c.id=tn.courseID RIGHT JOIN "
				+ dataBaseName + ".tb_curriculumschedule_now cn ");
		sbSql.append("on tn.id= cn.trainingProgramsID RIGHT JOIN "
				+ dataBaseName
				+ ".tb_publicchoosecourse pc on cn.id=pc.CurriculumsheduleId  RIGHT JOIN "
				+ dataBaseName
				+ ".tb_courseround cr on pc.id =cr.publicchoosecourseId ");
		sbSql.append("where c.courseTypeId='" + courseTypeid
				+ "' and  c.courseName LIKE '%" + condition
				+ "%' GROUP BY c.id");

		List list = queryBySql(sbSql.toString());
		return list.size();
	}

	/**
	 * 模糊查询轮次信息-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */

	@Override
	public List queryFuzzyRoundInfo(int pageNum, int pageSize,
			String dataBaseName, String condition, String sortName,
			String sortValue) {
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("select ccr.id as roundId,ccr.roundNo,ccr.beginTime,ccr.endTime,ccr.lastestQuitTime,ccrgc.levelId,s.levelName ,ccrgc.gradeID, g.gradeName,ccrgc.insititutionID ,i.InstitutionName ");
		sbSql.append("from "
				+ dataBaseName
				+ ".tb_choosecourseround ccr LEFT JOIN "
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege ccrgc on ccr.id= ccrgc.courseRoundId LEFT JOIN "
				+ dataBaseName + ".tb_grade g on ccrgc.gradeID=g.id LEFT JOIN "
				+ dataBaseName
				+ ".tb_institution i on ccrgc.insititutionID=i.Id LEFT JOIN "
				+ dataBaseName
				+ ".tb_schoollevel s on ccrgc.levelId=s.id WHERE ");
		sbSql.append(" i.InstitutionName like '%" + condition
				+ "%' or g.gradeName like '%" + condition
				+ "%' or s.levelName like '%" + condition
				+ "%' or ccr.roundNo like '%" + condition + "%'");
		sbSql.append(" ORDER BY " + sortName + " " + sortValue + "");
		PageEntity pageEntity = new PageEntity();
		// 设置pageentity必须的几个属性
		pageEntity.setDataBaseName(dataBaseName);

		// 为分页实体设置查询语句，设置页数，和每页显示信息数目
		pageEntity.setPageNum(pageNum);
		pageEntity.setPageSize(pageSize);

		// 分页查询课程信息并返回
		return queryPageEntityBySql(sbSql.toString(), pageEntity);
	}

	/**
	 * 选课时间安排查询记录条数 小王丹 2016-2-29 11:15:08-v5.0
	 * */
	@Override
	public int queryFuzzyRoundInfoCount(String dataBaseName, String condition,
			String sortName, String sortValue) {
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("select ccr.id as roundId,ccr.roundNo,ccr.beginTime,ccr.endTime,ccr.lastestQuitTime,ccrgc.levelId,s.levelName ,ccrgc.gradeID, g.gradeName,ccrgc.insititutionID ,i.InstitutionName ");
		sbSql.append("from "
				+ dataBaseName
				+ ".tb_choosecourseround ccr LEFT JOIN "
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege ccrgc on ccr.id= ccrgc.courseRoundId LEFT JOIN "
				+ dataBaseName
				+ ".tb_grade g on ccrgc.gradeID=g.id LEFT JOIN "
				+ dataBaseName
				+ ".tb_institution i on ccrgc.insititutionID=i.Id LEFT JOIN "
				+ dataBaseName
				+ ".tb_schoollevel s on ccrgc.levelId=s.id WHERE ccr.isDelete=0 and ");
		sbSql.append(" i.InstitutionName like '%" + condition
				+ "%' or g.gradeName like '%" + condition
				+ "%' or s.levelName like '%" + condition
				+ "%' or ccr.roundNo like '%" + condition + "%'");
		sbSql.append(" ORDER BY " + sortName + " " + sortValue + "");

		List list = queryBySql(sbSql.toString());
		return list.size();

	}

	/**
	 * 查询轮次信息-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryRoundInfo(int pageNum, int pageSize, String dataBaseName,
			String sortName, String sortValue) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("select ccr.id as roundId,ccr.roundNo,ccr.beginTime,ccr.endTime,ccr.lastestQuitTime,ccrgc.levelId,s.levelName ,ccrgc.gradeID, g.gradeName,ccrgc.insititutionID ,i.InstitutionName ");
		sbSql.append("from "
				+ dataBaseName
				+ ".tb_choosecourseround ccr inner JOIN "
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege ccrgc on ccr.id= ccrgc.courseRoundId LEFT JOIN "
				+ dataBaseName
				+ ".tb_grade g on ccrgc.gradeID=g.id LEFT JOIN "
				+ dataBaseName
				+ ".tb_institution i on ccrgc.insititutionID=i.Id LEFT JOIN "
				+ dataBaseName
				+ ".tb_schoollevel s on ccrgc.levelId=s.id where ccr.isDelete=0 ");
		sbSql.append(" ORDER BY " + sortName + " " + sortValue + "");

		PageEntity pageEntity = new PageEntity();
		// 设置pageentity必须的几个属性
		pageEntity.setDataBaseName(dataBaseName);

		// 为分页实体设置查询语句，设置页数，和每页显示信息数目
		pageEntity.setPageNum(pageNum);
		pageEntity.setPageSize(pageSize);

		// 分页查询课程信息并返回
		return queryPageEntityBySql(sbSql.toString(), pageEntity);
	}

	/**
	 * 选课时间安排---无条件查询--查询总记录条数 小王丹 2016-2-29 11:23:43-v5.0
	 * */
	@Override
	public int queryRoundInfoCount(String dataBaseName, String SortName,
			String SortValue) {

		StringBuffer sbSql = new StringBuffer();
		sbSql.append("select ccr.id as roundId,ccr.roundNo,ccr.beginTime,ccr.endTime,ccr.lastestQuitTime,ccrgc.levelId,s.levelName ,ccrgc.gradeID, g.gradeName,ccrgc.insititutionID ,i.InstitutionName ");
		sbSql.append("from "
				+ dataBaseName
				+ ".tb_choosecourseround ccr LEFT JOIN "
				+ dataBaseName
				+ ".tb_choosecourseroundgradecollege ccrgc on ccr.id= ccrgc.courseRoundId LEFT JOIN "
				+ dataBaseName
				+ ".tb_grade g on ccrgc.gradeID=g.id LEFT JOIN "
				+ dataBaseName
				+ ".tb_institution i on ccrgc.insititutionID=i.Id LEFT JOIN "
				+ dataBaseName
				+ ".tb_schoollevel s on ccrgc.levelId=s.id where ccr.isDelete=0 ");
		sbSql.append(" ORDER BY " + SortName + " " + SortValue + "");
		List list = queryBySql(sbSql.toString());
		return list.size();
	}

	/**
	 * 查询本轮已配课信息-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryCourseCapacityByRound(int pageNum, int pageSize,
			String dataBaseName, String roundId, String sortName,
			String sortValue) {
		StringBuilder sbSql = new StringBuilder();

		sbSql.append(SELECT_1);
		sbSql.append("from " + dataBaseName + SELECT_2 + dataBaseName
				+ SELECT_3);
		sbSql.append("on " + dataBaseName + SELECT_4 + dataBaseName + SELECT_5);
		sbSql.append(SELECT_6 + dataBaseName
				+ ".tb_trainingprograms_now tn on ");
		sbSql.append(SELECT_7 + dataBaseName + SELECT_8 + dataBaseName
				+ SELECT_9);
		sbSql.append(SELECT_10 + dataBaseName + SELECT_11);
		sbSql.append(SELECT_12 + roundId + "'");
		sbSql.append(" ORDER BY " + sortName + " " + sortValue + "");

		PageEntity pageEntity = new PageEntity();
		// 设置pageentity必须的几个属性
		pageEntity.setDataBaseName(dataBaseName);

		// 为分页实体设置查询语句，设置页数，和每页显示信息数目
		pageEntity.setPageNum(pageNum);
		pageEntity.setPageSize(pageSize);

		// 分页查询课程信息并返回
		return queryPageEntityBySql(sbSql.toString(), pageEntity);
	}

	/**
	 * 模糊查询容通过轮次-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryfuzzyCourseCapacitybyRound(int pageNum, int pageSize,
			String condition, String dataBaseName, String roundId,
			String sortName, String sortValue) {
		StringBuilder sbSql = new StringBuilder();

		sbSql.append(SELECT_1);
		sbSql.append("from " + dataBaseName + SELECT_2 + dataBaseName
				+ SELECT_3);
		sbSql.append("on " + dataBaseName + SELECT_4 + dataBaseName + SELECT_5);
		sbSql.append(SELECT_6 + dataBaseName
				+ ".tb_trainingprograms_now tn on ");
		sbSql.append(SELECT_7 + dataBaseName + SELECT_8 + dataBaseName
				+ SELECT_9);
		sbSql.append(SELECT_10 + dataBaseName + SELECT_11);
		sbSql.append(SELECT_12 + roundId + "'");
		sbSql.append(" and  (c.courseName like '%" + condition
				+ "%' or d.content like '%" + condition
				+ "%' or i.InstitutionName like '%" + condition + "%' ) ");
		sbSql.append(" ORDER BY " + sortName + " " + sortValue + "");
		PageEntity pageEntity = new PageEntity();
		// 设置pageentity必须的几个属性
		pageEntity.setDataBaseName(dataBaseName);

		// 为分页实体设置查询语句，设置页数，和每页显示信息数目
		pageEntity.setPageNum(pageNum);
		pageEntity.setPageSize(pageSize);

		// 分页查询课程信息并返回
		return queryPageEntityBySql(sbSql.toString(), pageEntity);

	}

	/**
	 * 统计数量-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */

	@Override
	public List StatisticCount(String dataBaseName) {
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("SELECT dn.content as name,sum(cr.choosedCapacity) as count from  "
				+ dataBaseName + ".tb_courseround cr ");
		sbSql.append(LEFT_JOIN
				+ dataBaseName
				+ ".tb_publicchoosecourse pc on cr.publicchoosecourseId= pc.id ");
		sbSql.append(LEFT_JOIN
				+ dataBaseName
				+ ".tb_curriculumschedule_now cn on pc.CurriculumsheduleId = cn.id ");
		sbSql.append(LEFT_JOIN + dataBaseName
				+ ".tb_trainingprograms_now tn on cn.trainingProgramsID=tn.id ");
		sbSql.append(LEFT_JOIN + dataBaseName
				+ ".tb_courseinfo c on tn.courseID=c.id ");
		sbSql.append(LEFT_JOIN
				+ dataBaseName
				+ ".tb_dictionary_now  dn on c.courseNatureId =dn.id GROUP BY dn.content ORDER BY count DESC");
		// 分页查询课程信息并返回

		return queryObjectBySql(sbSql.toString(), dataBaseName);
	}

	/**
	 * 根据轮次数量查询课程容量-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public int queryCourseCapacityByRoundCount(String dataBaseName,
			String roundId) {
		StringBuilder sbSql = new StringBuilder();

		sbSql.append(SELECT_1);
		sbSql.append("from " + dataBaseName + SELECT_2 + dataBaseName
				+ SELECT_3);
		sbSql.append("on " + dataBaseName + SELECT_4 + dataBaseName + SELECT_5);
		sbSql.append(SELECT_6 + dataBaseName
				+ ".tb_trainingprograms_now tn on ");
		sbSql.append(SELECT_7 + dataBaseName + SELECT_8 + dataBaseName
				+ SELECT_9);
		sbSql.append(SELECT_10 + dataBaseName + SELECT_11);
		sbSql.append(SELECT_12 + roundId + "'");

		// 分页查询课程信息并返回
		List list = this.queryBySql(sbSql.toString());
		return list.size();
	}

	/**
	 * 模糊查询课程容量通过轮次数量-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public int queryFuzzyCourseCapacityByRoundCount(String condition,
			String dataBaseName, String roundId) {
		StringBuilder sbSql = new StringBuilder();

		sbSql.append(SELECT_1);
		sbSql.append("from " + dataBaseName + SELECT_2 + dataBaseName
				+ SELECT_3);
		sbSql.append("on " + dataBaseName + SELECT_4 + dataBaseName + SELECT_5);
		sbSql.append(SELECT_6 + dataBaseName
				+ ".tb_trainingprograms_now tn on ");
		sbSql.append(SELECT_7 + dataBaseName + SELECT_8 + dataBaseName
				+ SELECT_9);
		sbSql.append(SELECT_10 + dataBaseName + SELECT_11);
		sbSql.append("where and cr.roundId='" + roundId + "'");
		sbSql.append(" and  (c.courseName like '%" + condition
				+ "%' or d.content like '%" + condition
				+ "%' or i.InstitutionName like '%" + condition + "%' ) ");

		// 分页查询课程信息并返回
		List list = queryBySql(sbSql.toString());
		return list.size();
	}

}
