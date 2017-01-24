package com.tgb.itoo.basic.eao;

import java.util.List;

import com.tgb.itoo.base.eao.BaseEao;
import com.tgb.itoo.basic.entity.CourseRound;
import com.tgb.itoo.basic.entity.PublicChooseCourse;
import com.tgb.itoo.tool.pageModel.PageEntity;

/**
 * 查询轮次-刘新阳-2016年1月2日
 * 
 * @author yang
 *
 */
public interface PublicChooseCourseDao extends BaseEao<PublicChooseCourse> {
	/**
	 * 查询选课信息
	 * 
	 * @return
	 */
	public List queryPublicChooseCourse(String dataBaseName);

	/**
	 * 查询层次信息
	 * 
	 * @param dataBaseName
	 * @return
	 */
	public List querySchoolLeve(String dataBaseName);

	/**
	 * 查询在校的年级
	 * 
	 * @param dataBaseName
	 * @return
	 */
	public List queryGradList(String dataBaseName);

	/**
	 * 根据节点id查询学院
	 * 
	 * @param dataBaseName
	 * @param nodelevelid
	 * @return
	 */
	public List queryInstitutionList(String dataBaseName);

	/*
	 * zhanghui 2016-1-19 15:13:33 模糊查询“公共选修课” eao
	 */
	public List<PublicChooseCourse> queryPCCompass(
			PageEntity<PublicChooseCourse> pageEntity, String string,
			String dataBaseName);

	/*
	 * 根据publicchoosecourseid更新剩余容量lxy
	 */
	public boolean updatePublicChooseCourse(
			PublicChooseCourse publicChooseCourse);

	/*
	 * 根据排课，保存、更新可选课程表信息 zhanghui 2016-1-23 11:08:11
	 */
	public boolean addPubChooseCourse(String dataBaseName,
			String pubChooseCourseid, String remainCapacity);

	/*
	 * 查询所有轮次配置的课程信息dao zhanghui 2016-1-25 09:57:33
	 */
	public List<PageEntity<CourseRound>> queryCourseByround(
			PageEntity<CourseRound> pageEntity, String condition, String sort,
			String order, String dataBaseName);

	/*
	 * 查询所有轮次配置的课程信息dao+模糊查询 zhanghui 2016-1-25 14:11:27
	 */
	public List<PageEntity<CourseRound>> queryCourseByRoundCompass(
			PageEntity<CourseRound> pageEntity, String condition,
			String dataBaseName);

	/*
	 * 根据publicchoosecourseid更新设置容量d zhanghui 2016-1-25 17:49:16
	 */
	public boolean updateCapacityByPid(String dataBaseName, String condition,
			String capacity, String operator);

	/*
	 * 根据publicchoosecourseid更新设置容量d zhanghui 2016-1-25 17:49:16
	 */
	public List<PageEntity<CourseRound>> queryCRMaxCount(
			PageEntity<CourseRound> pageEntity, String string,
			String dataBaseName);

	/*
	 * 根据publicchoosecourseid更新设置容量d zhanghui 2016-1-25 17:49:16
	 */
	public List<PageEntity<CourseRound>> queryCRMaxCountCompass(
			PageEntity<CourseRound> pageEntity, String string,
			String dataBaseName);

	/**
	 * 查询课程容量-王孟梅
	 * 
	 * @param pageEntity
	 * @param dataBaseName
	 * @return
	 */
	public List<PageEntity<PublicChooseCourse>> queryCourseCapacityCount(
			PageEntity<PublicChooseCourse> pageEntity, String dataBaseName);

	/**
	 * 查询轮次，年级，学院，层次关系表中 层次，年级，学院id
	 * 
	 * @param dataBaseName
	 * @return
	 */
	public List queryLevelGradeCollege(String dataBaseName);

	/**
	 * 根据publicchooseCourseId查询courseroundid
	 */
	public List querycourseroundid(String dataBaseName,
			String publicchoosecourseid);

	/**
	 * 分页查询中的总数查询
	 * 
	 * @param pageEntityClass
	 * @param string
	 * @param dataBaseName
	 * @return
	 */
	public int queryMaxCount(PageEntity pageEntityClass, String string,
			String dataBaseName);

	/**
	 * 根据轮次查询所有的班级-王孟梅
	 * 
	 * @param pageEntity
	 * @param string
	 * @param SortName
	 * @param SortValue
	 * @param dataBaseName
	 * @return
	 */
	public List<PublicChooseCourse> queryAllPublicClassByRound(
			PageEntity pageEntity, String string, String sortName,
			String sortValue, String dataBaseName);

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
			String sort, String order, String databaseName);
}
