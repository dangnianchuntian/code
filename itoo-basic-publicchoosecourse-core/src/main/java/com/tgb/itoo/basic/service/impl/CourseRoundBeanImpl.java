package com.tgb.itoo.basic.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.tgb.itoo.base.eao.BaseEao;
import com.tgb.itoo.base.service.impl.BaseBeanImpl;
import com.tgb.itoo.basic.eao.CourseRoundDao;
import com.tgb.itoo.basic.entity.CourseRound;
import com.tgb.itoo.basic.service.CourseRoundBean;

@Stateless(name = "CourseRoundBeanImpl")
@Remote(CourseRoundBean.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CourseRoundBeanImpl extends BaseBeanImpl<CourseRound> implements
		CourseRoundBean {

	@EJB(name = "CourseRoundDaoImpl")
	private CourseRoundDao courseroundDao;

	public CourseRoundDao getCourseroundDao() {
		return courseroundDao;
	}

	public void setCourseroundDao(CourseRoundDao courseroundDao) {
		this.courseroundDao = courseroundDao;
	}

	@Override
	public BaseEao getBaseEao() {

		return courseroundDao;
	}

	@Override
	public boolean saveCourseRound(List<CourseRound> list) {

		return courseroundDao.saveEntitysGeneric(list);

	}

	/**
	 * 根据courseroundid更新设置容量bImpl -张辉 2016-1-25 17:32:53-v5.0
	 */
	@Override
	public boolean updateCapacityByCRid(String dataBaseName, String condition,
			String capacity) {
		return courseroundDao.updateCapacityByCRid(dataBaseName, condition,
				capacity);
	}

	/**
	 * 根据courseroundid查询原来设置容量bImpl zhanghui 2016-1-27 11:46:12-v5.0
	 */
	@Override
	public List queryTotalByid(String dataBaseName, String crid) {
		return courseroundDao.queryTotalByid(dataBaseName, crid);
	}

	/**
	 * 添加课程轮次-张辉 2016-1-25 17:32:53-v5.0
	 */
	@Override
	public boolean saveCourseRoundbyChoosecourseId(List<CourseRound> list) {
		return courseroundDao.saveCourseRoundbyChoosecourseId(list);
	}

	/**
	 * 通过id查询课程轮次-张辉 2016-1-25 17:32:53-v5.0
	 */
	@Override
	public CourseRound queryCourseRoundbyid(String dataBaseName, String roundid) {

		return this.findEntityByIdGeneric(roundid, dataBaseName);
	}
}
