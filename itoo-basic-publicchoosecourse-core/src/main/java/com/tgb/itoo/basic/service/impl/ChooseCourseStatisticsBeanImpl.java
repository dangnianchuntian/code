/**
 * 已选课程容量统计--维护人员：王孟梅--2016年6月11日14:55:38--v5.0
 * 
 */

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

import com.tgb.itoo.base.eao.BaseEao;
import com.tgb.itoo.base.service.impl.BaseBeanImpl;
import com.tgb.itoo.basic.eao.ChooseCourseStatisticsDao;
import com.tgb.itoo.basic.entity.PublicChooseCourse;
import com.tgb.itoo.basic.service.ChooseCourseStatisticsBean;
import com.tgb.itoo.tool.objectToMap.ObjectToMap;

@Stateless(name = "chooseCourseStatisticsBeanImpl")
@Remote(ChooseCourseStatisticsBean.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ChooseCourseStatisticsBeanImpl extends
		BaseBeanImpl<PublicChooseCourse> implements ChooseCourseStatisticsBean {

	private static final String COURSE_NAME = "courseName";
	private static final String ROUND_ID = "roundId";
	private static final String INSTITUTION_NAME = "InstitutionName";

	@EJB(name = "chooseCourseStatisticsDaoImpl")
	private ChooseCourseStatisticsDao chooseCourseStatisticsDao;

	public ChooseCourseStatisticsDao getChooseCourseStatisticsDao() {
		return chooseCourseStatisticsDao;
	}

	public void setChooseCourseStatisticsDao(
			ChooseCourseStatisticsDao chooseCourseStatisticsDao) {
		this.chooseCourseStatisticsDao = chooseCourseStatisticsDao;
	}

	@Override
	public BaseEao getBaseEao() {

		return this.chooseCourseStatisticsDao;
	}

	/**
	 * 查询已选课程的容量统计（分页）（页面加载和无条件查询）--维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List<Map<Serializable, Serializable>> queryRemainAndChoosedCapacity(
			int pageNum, int pageSize, String sort, String order,
			String dataBaseName) {

		// 转换map后的返回值
		List field = new ArrayList();
		// 实例化Object转Map的类
		ObjectToMap o2m = new ObjectToMap();
		// 查询返回值
		field.add("courseroundid");
		field.add("choosedCapacity");
		field.add("remainCapacity");
		field.add("courseinfoid");
		field.add(COURSE_NAME);
		field.add("publicchoosecourseid");
		return o2m.convertToMap(field, chooseCourseStatisticsDao
				.queryRemainAndChoosedCapacity(pageNum, pageSize, sort, order,
						dataBaseName));
	}

	/**
	 * 查询已选课程的容量统计--维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public int queryRemainAndChoosedCapacityCount(String sortName,
			String sortValue, String dataBaseName) {
		return chooseCourseStatisticsDao.queryRemainAndChoosedCapacityCount(
				sortName, sortValue, dataBaseName);
	}

	/**
	 * （模糊查询）根据课程名称查询课程已选和剩余容量（分页）-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryRCCapacityByCourseName(int pageNum, int pageSize,
			String condition, String dataBaseName, String sort, String order) {
		// 如果没有给出第几页，则默认显示第1页
		if (pageNum == 0) {
			pageNum = 1;
		}

		// 转换map后的返回值
		List<Map<Serializable, Serializable>> listmap = new ArrayList<>();
		List field = new ArrayList();
		// 实例化Object转Map的类
		ObjectToMap o2m = new ObjectToMap();
		// 查询返回值
		field.add("courseroundid");
		field.add("choosedCapacity");
		field.add("remainCapacity");
		field.add("courseinfoid");
		field.add(COURSE_NAME);
		field.add("publicchoosecourseid");
		listmap = o2m.convertToMap(field, chooseCourseStatisticsDao
				.queryRCCapacityByCourseName(pageNum, pageSize, condition,
						dataBaseName, sort, order));
		return listmap;
	}

	/**
	 * 根据课程名称数量查询容量-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public int queryRCCapacityCountByCourseNameCount(String dataBaseName,
			String condition) {
		return chooseCourseStatisticsDao.queryRCCapacityCountByCourseNameCount(
				dataBaseName, condition);
	}

	/**
	 * 模糊查询轮次信息-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryFuzzyRoundInfo(int pageNum, int pageSize,
			String dataBaseName, String condition, String sortName,
			String sortValue) {
		// 如果没有给出第几页，则默认显示第1页
		if (pageNum == 0) {
			pageNum = 1;
		}
		// 转换map后的返回值
		List<Map<Serializable, Serializable>> listmap = new ArrayList<>();
		List field = new ArrayList();
		// 实例化Object转Map的类
		ObjectToMap o2m = new ObjectToMap();
		// 查询返回值
		field.add(ROUND_ID);
		field.add("roundNo");
		field.add("beginTime");
		field.add("endTime");
		field.add("lastestQuitTime");
		field.add("levelId");
		field.add("levelName");
		field.add("gradeID");
		field.add("gradeName");
		field.add("insititutionID");
		field.add(INSTITUTION_NAME);

		listmap = o2m.convertToMap(field, chooseCourseStatisticsDao
				.queryFuzzyRoundInfo(pageNum, pageSize, dataBaseName,
						condition, sortName, sortValue));
		return listmap;
	}

	/**
	 * 模糊查询轮次数量-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public int queryFuzzyRoundInfoCount(String dataBaseName, String condition,
			String sortName, String sortValue) {
		return chooseCourseStatisticsDao.queryFuzzyRoundInfoCount(dataBaseName,
				condition, sortName, sortValue);
	}

	/**
	 * 查询轮次信息--维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryRoundInfo(int pageNum, int pageSize, String dataBaseName,
			String sortName, String sortValue) {
		// 如果没有给出第几页，则默认显示第1页
		if (pageNum == 0) {
			pageNum = 1;
		}

		// 转换map后的返回值
		List field = new ArrayList();
		// 实例化Object转Map的类
		ObjectToMap o2m = new ObjectToMap();
		// 查询返回值
		field.add(ROUND_ID);
		field.add("roundNo");
		field.add("beginTime");
		field.add("endTime");
		field.add("lastestQuitTime");
		field.add("levelId");
		field.add("levelName");
		field.add("gradeID");
		field.add("gradeName");
		field.add("insititutionID");
		field.add(INSTITUTION_NAME);
		return o2m.convertToMap(field, chooseCourseStatisticsDao
				.queryRoundInfo(pageNum, pageSize, dataBaseName, sortName,
						sortValue));

	}

	/**
	 * 查询轮次信息-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public int queryRoundInfoCount(String dataBaseName, String sortName,
			String sortValue) {
		return chooseCourseStatisticsDao.queryRoundInfoCount(dataBaseName,
				sortName, sortValue);

	}

	/**
	 * 查询本轮配课信息-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryCourseCapacityByRound(int pageNum, int pageSize,
			String dataBaseName, String roundId, String sortName,
			String sortValue) {
		// 如果没有给出第几页，则默认显示第1页
		if (pageNum == 0) {
			pageNum = 1;
		}

		// 转换map后的返回值
		List<Map<Serializable, Serializable>> listmap = new ArrayList<Map<Serializable, Serializable>>();
		List field = new ArrayList();
		// 实例化Object转Map的类
		ObjectToMap o2m = new ObjectToMap();
		// 查询返回值
		field.add("courseroundId");
		field.add(ROUND_ID);
		field.add("tatolCapacity");
		field.add(COURSE_NAME);
		field.add("content");
		field.add(INSTITUTION_NAME);

		listmap = o2m.convertToMap(field, chooseCourseStatisticsDao
				.queryCourseCapacityByRound(pageNum, pageSize, dataBaseName,
						roundId, sortName, sortValue));
		return listmap;
	}

	/**
	 * 模糊查询轮次信息-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryfuzzyCourseCapacitybyRound(int pageNum, int pageSize,
			String condition, String dataBaseName, String roundId,
			String sortName, String sortValue) {
		// 如果没有给出第几页，则默认显示第1页
		if (pageNum == 0) {
			pageNum = 1;
		}

		// 转换map后的返回值
		List field = new ArrayList();
		// 实例化Object转Map的类
		ObjectToMap o2m = new ObjectToMap();
		// 查询返回值

		field.add("courseroundId");
		field.add(ROUND_ID);
		field.add("tatolCapacity");
		field.add(COURSE_NAME);
		field.add("content");
		field.add(INSTITUTION_NAME);

		return o2m.convertToMap(field, chooseCourseStatisticsDao
				.queryfuzzyCourseCapacitybyRound(pageNum, pageSize, condition,
						dataBaseName, roundId, sortName, sortValue));

	}

	/**
	 * 查询统计数量-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List StatisticCount(String dataBaseName) {
		// 转换map需要添加key的返回值
		List fields = new ArrayList();
		// 转换map后的返回值
		// 查询返回值

		// 实例化Object转Map的类
		ObjectToMap o2m = new ObjectToMap();
		fields.add("name");
		fields.add("count");

		return o2m.convertToMap(fields,
				chooseCourseStatisticsDao.StatisticCount(dataBaseName));

	}

	/**
	 * 通过轮次数量查询课程容量-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public int queryCourseCapacityByRoundCount(String dataBaseName,
			String roundId) {
		return chooseCourseStatisticsDao.queryCourseCapacityByRoundCount(
				dataBaseName, roundId);
	}

	/**
	 * 模糊查询课程容量-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public int queryFuzzyCourseCapacityByRoundCount(String condition,
			String dataBaseName, String roundId) {
		return chooseCourseStatisticsDao.queryFuzzyCourseCapacityByRoundCount(
				condition, dataBaseName, roundId);
	}

}
