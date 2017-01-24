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
import com.tgb.itoo.basic.eao.CourseRoundDao;
import com.tgb.itoo.basic.entity.CourseRound;

/**
 * 课程轮次关系管理dI 2016-1-15 10:41:14
 * 
 * @author zhanghui
 *
 */

@Stateless(name = "CuourseRoundDaoImpl")
@Remote(CourseRoundDao.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CourseRoundDaoImpl extends BaseEaoImpl<CourseRound> implements
		CourseRoundDao {

	/**
	 * 添加课程轮次--维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public CourseRound saveCourseRound(String pubChooseCourseid,
			String roundID, String rTotalCapacity, String databaseName) {

		// 1.根据选课id查询课程轮次主键id

		// 2.根据课程轮次主键id更新/保存课程轮次实体(注意：roundid如何生成还是插入？？A.插入)
		CourseRound courseRound = new CourseRound();
		courseRound.setRoundId(roundID);
		courseRound.setPublicchoosecourseId(pubChooseCourseid);
		courseRound.setTatolCapacity(rTotalCapacity);
		courseRound.setRemainCapacity(rTotalCapacity);
		courseRound.setOperator("zhanghui");

		// this.saveAndReturnGeneric(entity); 保存并返回

		return this.saveAndReturnGeneric(courseRound);
	}

	/*
	 * 根据courseroundid更新设置容量DImpl zhanghui 2016-1-25 17:34:50-v5.0
	 */
	@Override
	public boolean updateCapacityByCRid(String dataBaseName, String condition,
			String capacity) {
		String variable1 = "cr.tatolCapacity=:tatolCapacity,cr.operator =:operator";
		String condition1 = "where cr.id=:id and cr.isDelete =:isDelete";
		Map<Serializable, Serializable> map1 = new HashMap<>();
		map1.put("tatolCapacity", capacity);
		map1.put("id", condition);
		map1.put("operator", "zhanghui");
		map1.put("isDelete", 0);
		// 泛型类中的方法
		return this.updateByConditionGeneric(variable1, condition1,
				dataBaseName, map1, "cr");

	}

	/*
	 * 根据courseroundid查询原来设置容量dImpl zhanghui 2016-1-27 11:47:44-v5.0
	 */
	@Override
	public List queryTotalByid(String dataBaseName, String crid) {

		String[] select = { "tatolCapacity" };
		// 查询条件
		String condtions = "cr.id=:id";
		// 查询条件对应的值
		Map<Serializable, Serializable> mapValue = new HashMap();
		mapValue.put("id", crid);

		return this.queryByFields(select, condtions, mapValue, "cr",
				dataBaseName);
	}

	/**
	 * 通过选修课id更新课程轮次-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public boolean saveCourseRoundbyChoosecourseId(List<CourseRound> list) {
		return this.updateEntitysGeneric(list);
	}
}
