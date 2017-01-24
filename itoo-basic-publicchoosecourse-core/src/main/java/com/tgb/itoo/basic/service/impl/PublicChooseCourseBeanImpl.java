package com.tgb.itoo.basic.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
import com.tgb.itoo.basic.eao.ChooseCourseRoundDao;
import com.tgb.itoo.basic.eao.ChooseCourseRoundGradeCollegeDao;
import com.tgb.itoo.basic.eao.CourseRoundDao;
import com.tgb.itoo.basic.eao.PublicChooseCourseDao;
import com.tgb.itoo.basic.entity.ChooseCourseRoundGradeCollege;
import com.tgb.itoo.basic.entity.CourseRound;
import com.tgb.itoo.basic.entity.PublicChooseCourse;
import com.tgb.itoo.basic.service.ChooseCourseRoundGradeCollegeBean;
import com.tgb.itoo.basic.service.CourseRoundBean;
import com.tgb.itoo.basic.service.PublicChooseCourseBean;
import com.tgb.itoo.tool.objectToMap.ObjectToMap;
import com.tgb.itoo.tool.pageModel.PageEntity;

@Stateless(name = "publicChooseCourseBeanImpl")
@Remote(PublicChooseCourseBean.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PublicChooseCourseBeanImpl extends
		BaseBeanImpl<PublicChooseCourse> implements PublicChooseCourseBean {

	private static final String TATOL_CAPACITY = "tatolCapacity";
	private static final String REMAIN_CAPACITY = "remainCapacity";
	private static final String SET_CAPACITY = "setCapacity";
	private static final String ZHANG_HUI = "zhanghui";

	ChooseCourseRoundBeanImpl chooseCourseRoundBeanImpl = new ChooseCourseRoundBeanImpl();
	private ChooseCourseRoundGradeCollegeBean chooseCourseRoundGradeCollegeBean;
	private CourseRoundBean courseRoundBean;

	public PublicChooseCourseBeanImpl() {
		/* 轮次地址 */
		String ronndAddress = "itoo-basic-publicchoosecourse-ear/itoo-basic-publicchoosecourse-core/ChooseCourseRoundBeanImpl!com.tgb.itoo.basic.service.ChooseCourseRoundBean";
		/* 课程、轮次、学院 */
		String roundCourseInstitutionAdd = "itoo-basic-publicchoosecourse-ear/itoo-basic-publicchoosecourse-core/ChooseCourseRoundGradeCollegeBeanImpl!com.tgb.itoo.basic.service.ChooseCourseRoundGradeCollegeBean";
		/* 课程、轮次关系 */
		String courseRoundAdd = "itoo-basic-publicchoosecourse-ear/itoo-basic-publicchoosecourse-core/CourseRoundBeanImpl!com.tgb.itoo.basic.service.CourseRoundBean";

		// 此处 try Catch强制加
		try {

			chooseCourseRoundGradeCollegeBean = (ChooseCourseRoundGradeCollegeBean) this
					.lookupRemoteBean(roundCourseInstitutionAdd);

			courseRoundBean = (CourseRoundBean) this
					.lookupRemoteBean(courseRoundAdd);
		} catch (NamingException e) {

			e.printStackTrace();
		}

	}

	@EJB(name = "publicChooseCourseDaoImpl")
	private PublicChooseCourseDao publicChooseCourseDao;

	// @EJB(name = "ChooseCourseRoundDaoImpl")
	private ChooseCourseRoundDao chooseChooseCourseRoundDao;
	// @EJB(name = "ChooseCourseRoundGradeCollegeDaoImpl")
	private ChooseCourseRoundGradeCollegeDao chooseCourseRoundGradeCollegeDao;
	// @EJB(name = "CuourseRoundDaoImpl")
	private CourseRoundDao courseRoundDao;

	public PublicChooseCourseDao getPublicChooseCourseDao() {
		return publicChooseCourseDao;
	}

	public void setPublicChooseCourseDao(
			PublicChooseCourseDao publicChooseCourseDao) {
		this.publicChooseCourseDao = publicChooseCourseDao;
	}

	public ChooseCourseRoundDao getChooseChooseCourseRoundDao() {
		return chooseChooseCourseRoundDao;
	}

	public void setChooseChooseCourseRoundDao(
			ChooseCourseRoundDao chooseChooseCourseRoundDao) {
		this.chooseChooseCourseRoundDao = chooseChooseCourseRoundDao;
	}

	public ChooseCourseRoundGradeCollegeDao getChooseCourseRoundGradeCollegeDao() {
		return chooseCourseRoundGradeCollegeDao;
	}

	public void setChooseCourseRoundGradeCollegeDao(
			ChooseCourseRoundGradeCollegeDao chooseCourseRoundGradeCollegeDao) {
		this.chooseCourseRoundGradeCollegeDao = chooseCourseRoundGradeCollegeDao;
	}

	public CourseRoundDao getCourseRoundDao() {
		return courseRoundDao;
	}

	public void setCourseRoundDao(CourseRoundDao courseRoundDao) {
		this.courseRoundDao = courseRoundDao;
	}

	/**
	 * 查询学校层次-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List querySchoolLevel(String dataBaseName) {
		// 转换map后的返回值
		List<Map<Serializable, Serializable>> listmap = new ArrayList<>();
		List field = new ArrayList();
		// 实例化Object转Map的类
		ObjectToMap o2m = new ObjectToMap();

		field.add("id");
		field.add("levelName");

		listmap = o2m.convertToMap(field,
				publicChooseCourseDao.querySchoolLeve(dataBaseName));
		return listmap;
	}

	/**
	 * 查询年级list-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryGradList(String dataBaseName) {
		// 转换map后的返回值
		List<Map<Serializable, Serializable>> listmap = new ArrayList<>();
		List field = new ArrayList();
		// 实例化Object转Map的类
		ObjectToMap o2m = new ObjectToMap();
		List list = new ArrayList();

		field.add("id");
		field.add("gradeName");

		listmap = o2m.convertToMap(field,
				publicChooseCourseDao.queryGradList(dataBaseName));
		return listmap;
	}

	/**
	 * 查询机构list-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryInstitutionList(String dataBaseName) {
		// 转换map后的返回值
		List<Map<Serializable, Serializable>> listmap = new ArrayList<>();
		List field = new ArrayList();
		// 实例化Object转Map的类
		ObjectToMap o2m = new ObjectToMap();

		field.add("id");
		field.add("InstitutionName");
		listmap = o2m.convertToMap(field,
				publicChooseCourseDao.queryInstitutionList(dataBaseName));
		return listmap;
	}

	/**
	 * 查询公共选修课-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List<Map<Serializable, Serializable>> queryPublicChooseCourse(
			String dataBaseName) {
		// 转换map后的返回值
		List<Map<Serializable, Serializable>> listmap = new ArrayList<>();

		// 查询返回值

		List field = new ArrayList();
		// 实例化Object转Map的类
		ObjectToMap o2m = new ObjectToMap();

		field.add("id");
		field.add(TATOL_CAPACITY);
		field.add(REMAIN_CAPACITY);
		field.add("chooseCapacity");
		field.add(SET_CAPACITY);

		listmap = o2m.convertToMap(field,
				publicChooseCourseDao.queryPublicChooseCourse(dataBaseName));

		return listmap;

	}

	@Override
	public BaseEao getBaseEao() {
		return publicChooseCourseDao;
	}

	/**
	 * 根据条件查询所有选修课-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 * 
	 * @author zhanghui 2016-1-4 16:28:56 conditions 查询条件，=null无条件查询（防止后期的需求变动）
	 */

	@Override
	public List<PageEntity<PublicChooseCourse>> queryAllPublicClassByConditions(
			PageEntity<PublicChooseCourse> pageEntity, String conditions,
			String sort, String order, String databaseName) {

		// 有条件下的查询（如：分文史类、理工类、科教类查询，方便查看）
		return publicChooseCourseDao.queryAllPublicClassByConditions(
				pageEntity, conditions, sort, order, databaseName);
	}

	/**
	 * * 4.1 根据选修课id设置某轮次容量-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 * 
	 * @author zhanghui 2016-1-7 14:43:51
	 * 
	 * @param databaseName
	 *            数据库名
	 * 
	 * @param condition
	 *            模糊匹配条件(=null,查询所有选修课)
	 * 
	 * @return
	 */
	@Override
	public Boolean updateAllPublicClassByConditions(String conditions,
			String databaseName) {
		String variable = "PublicChooseCourse p.setCapacity =:setCapacity"; // 修改此次设置容量
		String condition = "where p.id=:id and p.isDelete=:isDelete";
		Map<Serializable, Serializable> map = new HashMap<>();
		map.put(SET_CAPACITY, "测试更新");
		map.put("id", "张辉");
		map.put("isDelete", 0);

		return publicChooseCourseDao.updateByConditionGeneric(variable,
				condition, databaseName, map, "p");
	}

	/**
	 * 4.1 通过id数组ids[]设置选修课总容量（批量）-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 * 
	 * @param
	 * @param
	 * @return
	 */
	@Override
	public List<PageEntity<PublicChooseCourse>> insertAllPublicClassByConditions(
			PageEntity<PublicChooseCourse> pageEntity, String conditions,
			String databaseName) {

		// Boolean result = publicChooseCourseDao.
		return Collections.emptyList();
	}

	/**
	 * 4.1
	 * 《总容量设置》查询所有“公共选修课”的信息，包括课程信息、课程所属机构信息、容量信息-维护人员：王孟梅--2016年6月11日14:55:38
	 * --v5.0
	 * 
	 * @author zhanghui 2016-1-8 16:36:14
	 * @param
	 * @param
	 * @return
	 */
	@Override
	public List<PublicChooseCourse> queryAllPublicClassByRound(
			PageEntity pageEntity, String string, String sortName,
			String sortValue, String dataBaseName) {

		return publicChooseCourseDao.queryAllPublicClassByRound(pageEntity,
				string, sortName, sortValue, dataBaseName);
	}

	/**
	 * 添加容量--维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 * 
	 * @param id
	 *            选课课程id
	 * @param databaseName
	 *            数据库名
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean addTotalCapacity(String id, int totalCapacity,
			String databaseName) {
		String sql = "SELECT * FROM " + databaseName
				+ ".tb_publicchoosecourse WHERE curriculumsheduleId='" + id
				+ "'";
		// 根据CurriculumsheduleId查询publicchoosecourseID
		List<PublicChooseCourse> list = publicChooseCourseDao.queryBySql(sql);

		/* list转map */
		List fields = new ArrayList<>();
		// 添加要查询的字段
		fields.add("id");
		fields.add(SET_CAPACITY);
		fields.add("chooseCapacity");
		fields.add(REMAIN_CAPACITY);
		fields.add(TATOL_CAPACITY);
		fields.add("isDelete");
		fields.add("remark");

		// 实例化Object转Map的类
		ObjectToMap o2m = new ObjectToMap();
		List<Map<Serializable, Serializable>> listCourseInfo = new ArrayList<>();
		// 返回序列化的Map形式的List
		listCourseInfo = o2m.convertToMap(fields, list);

		// 定义实体
		PublicChooseCourse pc = new PublicChooseCourse();
		// 1.1(初始化总容量)对象不存在，插入
		if (null == list || list.isEmpty()) {
			pc.setTatolCapacity(Integer.toString(totalCapacity));
			pc.setRemainCapacity(Integer.toString(totalCapacity));
			pc.setCurriculumsheduleId(id);
			pc.setDataBaseName(databaseName);

			publicChooseCourseDao.saveEntityGeneric(pc);
			return true;
		} else {
			// 1.2(初始化总容量)_对象已存在，更新
			String pcID = (String) listCourseInfo.get(0).get("id"); // 选课主id
			pc.setId(pcID);

			String variable1 = "pc.tatolCapacity=:tatolCapacity,pc.remainCapacity=:remainCapacity,pc.operator =:operator";
			String condition1 = "where pc.id=:id";
			Map<Serializable, Serializable> map1 = new HashMap<>();
			map1.put(TATOL_CAPACITY, Integer.toString(totalCapacity));
			map1.put(REMAIN_CAPACITY, Integer.toString(totalCapacity));
			map1.put("id", pcID);
			map1.put("operator", ZHANG_HUI);
			// 泛型类中的方法

			return publicChooseCourseDao.updateByConditionGeneric(variable1,
					condition1, databaseName, map1, "pc");
		}

	}

	/**
	 * 设置每轮次选课信息+批量-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public boolean addPublicClass(String dataBaseName,
			List<CourseRound> cRoundlist,
			List<PublicChooseCourse> publicChoosecourselist,
			List<ChooseCourseRoundGradeCollege> courseRoundGradelist) {
		boolean flag = false;

		// 4.保存选课实体xinyang
		boolean flagcourse = false;
		for (int i = 0; i < publicChoosecourselist.size(); i++) {
			flagcourse = publicChooseCourseDao.addPubChooseCourse(dataBaseName,
					publicChoosecourselist.get(i).getId(),
					publicChoosecourselist.get(i).getRemainCapacity());

		}
		/*
		 * 2.保存轮次、课程关系信 息轮次id，publiccourseid，还有容量
		 */

		List<CourseRound> listcRound = new ArrayList<>();
		for (int i = 0; i < cRoundlist.size(); i++) {

			String pubChooseCourseid = cRoundlist.get(i)
					.getPublicchoosecourseId(); // 可选课程id
			String totalCapacity = cRoundlist.get(i).getTatolCapacity();
			String remainCapacity = cRoundlist.get(i).getRemainCapacity();
			String choosedCapacity = cRoundlist.get(i).getChoosedCapacity();
			String roundid = cRoundlist.get(i).getRoundId();
			CourseRound courseround = new CourseRound();
			courseround.setPublicchoosecourseId(pubChooseCourseid);// 课程id
			courseround.setTatolCapacity(totalCapacity);// 本轮次的总容量
			courseround.setRemainCapacity(remainCapacity);// 设置本轮次的剩余容量
			courseround.setChoosedCapacity(choosedCapacity);
			courseround.setDataBaseName(dataBaseName);
			courseround.setRoundId(roundid);
			courseround.setIsDelete(0);
			courseround.setOperator(ZHANG_HUI);
			listcRound.add(courseround);
		}

		boolean flagCourseRound = courseRoundBean.saveCourseRound(listcRound);

		// 3.保存课程、轮次、学院关系表

		List<ChooseCourseRoundGradeCollege> listcourseRoundGradelist = new ArrayList<>();
		for (int i = 0; i < courseRoundGradelist.size(); i++) {
			ChooseCourseRoundGradeCollege courseRoundGradeCollege = new ChooseCourseRoundGradeCollege();
			courseRoundGradeCollege.setCourseRoundId(courseRoundGradelist
					.get(i).getCourseRoundId());
			courseRoundGradeCollege.setDataBaseName(dataBaseName);
			courseRoundGradeCollege.setOperator(ZHANG_HUI);
			courseRoundGradeCollege.setGradeID(courseRoundGradelist.get(i)
					.getGradeID());
			courseRoundGradeCollege.setInsititutionID(courseRoundGradelist.get(
					i).getInsititutionID());
			courseRoundGradeCollege.setLevelId(courseRoundGradelist.get(i)
					.getLevelId());
			courseRoundGradeCollege.setIsDelete(0);
			listcourseRoundGradelist.add(courseRoundGradeCollege);
		}

		boolean collegeflag = chooseCourseRoundGradeCollegeBean
				.saveRoundInstitution(listcourseRoundGradelist);

		if (flagCourseRound && collegeflag && flagcourse) {
			flag = true;

		}

		return flag;

	}

	/**
	 * 模糊查询“公共选修课”-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List<PublicChooseCourse> queryPCCompass(
			PageEntity<PublicChooseCourse> pageEntity, String condition,
			String dataBaseName) {

		return publicChooseCourseDao.queryPCCompass(pageEntity, condition,
				dataBaseName);

	}

	/**
	 * 查询所有轮次配置的课程信息bImpl zhanghui 2016-1-25 09:53:15--v5.0
	 */
	@Override
	public List<PageEntity<CourseRound>> queryCourseByround(
			PageEntity<CourseRound> pageEntity, String condition, String sort,
			String order, String dataBaseName) {

		return publicChooseCourseDao.queryCourseByround(pageEntity, condition,
				sort, order, dataBaseName);
	}

	/**
	 * 查询所有轮次配置的课程信息bImpl+模糊查询 zhanghui 2016-1-25 10:50:22 -v5.0
	 */
	@Override
	public List<PageEntity<CourseRound>> queryCourseByRoundCompass(
			PageEntity<CourseRound> pageEntity, String condition,
			String dataBaseName) {

		return publicChooseCourseDao.queryCourseByRoundCompass(pageEntity,
				condition, dataBaseName);
	}

	/**
	 * 根据publicchoosecourseid更新设置容量bIMPL zhanghui 2016-1-25 17:48:09-v5.0
	 */
	@Override
	public boolean updateCapacityByPid(String dataBaseName, String pcid,
			String crid, String pcRemainCapacity, String capaCity_New) {
		String operator = "张辉";
		/* 1.根据courseroundid查询原来设置容量 */

		List<Map<Serializable, Serializable>> listTotalCapacity = courseRoundBean
				.queryTotalByid(dataBaseName, crid);
		String crTotalCapacity = (String) listTotalCapacity.get(0).get(
				TATOL_CAPACITY);

		/* 2.更新可选课程publicchoosecourse中的剩余容量 */
		String pcRemainCapacity1 = Integer.toString((Integer
				.parseInt(pcRemainCapacity) + (Integer
				.parseInt(crTotalCapacity) - Integer.parseInt(capaCity_New))));// 课程总剩余量=原有-差值
		publicChooseCourseDao.updateCapacityByPid(dataBaseName, pcid,
				pcRemainCapacity1, operator);

		/* 3.更新课程轮次关系表中的courseround中的总容量 */
		return courseRoundBean.updateCapacityByCRid(dataBaseName, crid,
				capaCity_New);

	}

	/**
	 * 更新公共选修课信息--维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public boolean updatePublicChooseCourse(
			PublicChooseCourse publicChooseCourse) {
		// 更新选课中课程的容量
		boolean flagCourse = publicChooseCourseDao
				.updatePublicChooseCourse(publicChooseCourse);

		String dataBaseName = publicChooseCourse.getDataBaseName();
		String publicchoosecourseid = publicChooseCourse.getId();
		// 根据publicchooseCourseId查询courseroundid
		String tatolCapacity = "0";
		String remainCapacity = "0";
		// 根据主键更新courseround的剩余容量和总容量

		List<CourseRound> listcRound = new ArrayList<>();
		for (int i = 0; i < publicChooseCourseDao.querycourseroundid(
				dataBaseName, publicchoosecourseid).size(); i++) {
			String roundid = publicChooseCourseDao
					.querycourseroundid(dataBaseName, publicchoosecourseid)
					.get(i).toString();
			// 根据courseroundid查询对应的courseround实体
			CourseRound courseRound = courseRoundBean.queryCourseRoundbyid(
					dataBaseName, roundid);
			// 将查询的课程轮次关系的剩余容量和总容量设为0
			courseRound.setTatolCapacity(tatolCapacity);
			courseRound.setRemainCapacity(remainCapacity);
			courseRound.setDataBaseName(dataBaseName);
			// 将更新后的课程伦次关系实体添加到list集合中
			listcRound.add(courseRound);
		}

		// 批量更新轮次课程关系表
		boolean courseroundflag = courseRoundBean
				.saveCourseRoundbyChoosecourseId(listcRound);

		// 将组织机构表的状态改为isdelete=1
		boolean flagGrade = chooseCourseRoundGradeCollegeBean
				.updateChooseCourseRoundGradeCollege(dataBaseName);
		boolean flag = false;
		if (flagCourse && courseroundflag && flagGrade) {

			flag = true;
		}

		return flag;
	}

	@Override
	/**
	 * 批量更新容量-徐志鹏-2016年6月11日20:27:52-v5.0
	 */
	public boolean updateTotalCapacity(String[] ids, int totalCapacity,
			String databaseName) {
		// ID数组长度
		int idLength = ids.length;
		boolean flag = true;
		// 循环ID
		for (int i = 0; i < idLength; i++) {
			flag = addTotalCapacity(ids[i], totalCapacity, databaseName);
			if (!flag) {
				break;
			}
		}
		return flag;

	}

	/**
	 * 查询所有的公共选修课--维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public PageEntity<PublicChooseCourse> queryAllPublicClassByRoundTest(
			PageEntity<PublicChooseCourse> pageEntity, String string,
			String dataBaseName) {
		return null;
	}

	/**
	 * 查询容量表总数-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public int queryMaxCount(PageEntity pageEntityClass, String string,
			String dataBaseName) {

		return publicChooseCourseDao.queryMaxCount(pageEntityClass, string,
				dataBaseName);
	}

	/**
	 * 查询已配课程轮次总数量（适用于检索时）BImpl zhanghui 2016-2-28 09:07:42 v5.0
	 */
	@Override
	public int queryCRMaxCountCompass(PageEntity<CourseRound> pageEntity,
			String string, String dataBaseName) {

		return publicChooseCourseDao.queryCRMaxCountCompass(pageEntity, string,
				dataBaseName).size();
	}

	/*
	 * 查询已配课程轮次总数量BImpl zhanghui 2016-2-28 09:07:59 -v5.0
	 */
	@Override
	public int queryCRMaxCount(PageEntity<CourseRound> pageEntity,
			String string, String dataBaseName) {

		return publicChooseCourseDao.queryCRMaxCount(pageEntity, string,
				dataBaseName).size();
	}

	/**
	 * 查询课程容量数量-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public int queryCourseCapacityCount(
			PageEntity<PublicChooseCourse> pageEntity, String dataBaseName) {

		return publicChooseCourseDao.queryCourseCapacityCount(pageEntity,
				dataBaseName).size();

	}

	/**
	 * 查询年级的层次-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryLevelGradeCollege(String dataBaseName) {

		return publicChooseCourseDao.queryLevelGradeCollege(dataBaseName);
	}

}
