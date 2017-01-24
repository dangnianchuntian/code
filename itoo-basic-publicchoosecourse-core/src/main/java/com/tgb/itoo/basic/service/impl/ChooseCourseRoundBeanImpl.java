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
import com.tgb.itoo.basic.eao.ChooseCourseRoundDao;
import com.tgb.itoo.basic.entity.ChooseCourseRound;
import com.tgb.itoo.basic.service.ChooseCourseRoundBean;
import com.tgb.itoo.tool.objectToMap.ObjectToMap;
import com.tgb.itoo.tool.pageModel.PageEntity;

@Stateless(name = "ChooseCourseRoundBeanImpl")
@Remote(ChooseCourseRoundBean.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ChooseCourseRoundBeanImpl extends BaseBeanImpl<ChooseCourseRound>
		implements ChooseCourseRoundBean {

	@EJB(name = "ChooseCourseRoundDaoImpl")
	private ChooseCourseRoundDao choosecourseroundDao;

	public ChooseCourseRoundDao getChoosecourseroundDao() {
		return choosecourseroundDao;
	}

	public void setChoosecourseroundDao(
			ChooseCourseRoundDao choosecourseroundDao) {
		this.choosecourseroundDao = choosecourseroundDao;
	}

	@Override
	public BaseEao getBaseEao() {
		return choosecourseroundDao;
	}

	/**
	 * 判断轮次名称是否存在--王孟梅---2016年9月13日10:33:35-v5.0
	 * 
	 * @param dbName
	 * @param roundName
	 * @return
	 */
	@Override
	public boolean isExistRoundName(String dbName, String roundName) {
		return choosecourseroundDao.isExistRoundName(dbName, roundName);
	}

	/**
	 * 轮次管理bean -维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public ChooseCourseRound saveRound(String roundid, String beginTime,
			String endTime, String lastestTime, String roundNo,
			String databaseName) {
		// 返回布尔值
		return choosecourseroundDao.saveCourseRound(beginTime, endTime,
				lastestTime, roundNo, databaseName);

	}

	/**
	 * 查询所有配置轮次信息bImpl-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public PageEntity<ChooseCourseRound> queryCourseRound(
			PageEntity<ChooseCourseRound> pageEntity, String dataBaseName) {
		// 返回布尔值
		return choosecourseroundDao.queryCourseRound(pageEntity, dataBaseName);

	}

	/**
	 * 查询最大的轮次---王孟梅---2016年9月17日19:58:27-v5.0
	 * 
	 * @param dbName
	 * @return
	 */
	@Override
	public String queryMaxRoundNo(String dbName) {
		return choosecourseroundDao.queryMaxRoundNo(dbName);
	}

	/**
	 * 根据轮次编号查询轮次详细信息bImpl -维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List<?> queryByRoundNo(int condition, String dataBaseName) {
		// 返回具体list
		return choosecourseroundDao.queryByRoundNo(condition, dataBaseName);
	}

	/**
	 * 添加轮次信息bImpl -维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public boolean addRound(String roundNo, String beginTime, String endTime,
			String lastestQuitTime, String operator, String dataBaseName) {
		// 返回布尔值
		return choosecourseroundDao.addRound(roundNo, beginTime, endTime,
				lastestQuitTime, operator, dataBaseName);
	}

	/**
	 * 修改轮次信息bImpl -维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public boolean updateRoundByid(String id, String roundNo, String beginTime,
			String endTime, String lastestQuitTime, String operator,
			String dataBaseName) {
		// 返回布尔值
		return choosecourseroundDao.updateRoundByid(id, roundNo, beginTime,
				endTime, lastestQuitTime, operator, dataBaseName);
	}

	/**
	 * 删除轮次信息bImpl -维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public boolean deleteRoundById(String id, String dataBaseName) {
		// 返回布尔值
		return choosecourseroundDao.deleteRoundById(id, dataBaseName);
	}

	/*
	 * 根据id查询该轮次是否配置 2016年3月4日08:40:53 徐志鹏-v5.0
	 * 
	 * @see
	 * com.tgb.itoo.basic.service.ChooseCourseRoundBean#queryRoundById(java.
	 * lang.String)
	 */
	@Override
	public int queryRoundById(String id, String databaseName) {
		// 返回是否配置的int值
		return choosecourseroundDao.queryRoundById(id, databaseName);
	}

	/**
	 * 查询课程轮次信息-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryCourseRoundforcom(String databaseName) {
		// 转换map后的返回值
		List field = new ArrayList();
		// 实例化Object转Map的类
		ObjectToMap o2m = new ObjectToMap();

		field.add("id");
		field.add("roundNo");
		field.add("beginTime");
		field.add("endTime");
		field.add("lastestQuitTime");

		return o2m.convertToMap(field,
				choosecourseroundDao.queryCourseRoundforcom(databaseName));

	}

	/**
	 * 查询轮次信息-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryRoundInfoForCom(String databaseName, String id) {
		// 转换map后的返回值
		List<Map<Serializable, Serializable>> listmap = new ArrayList<>();
		List field = new ArrayList();
		// 实例化Object转Map的类
		ObjectToMap o2m = new ObjectToMap();

		field.add("id");
		field.add("roundNo");
		field.add("beginTime");
		field.add("endTime");
		field.add("lastestQuitTime");

		listmap = o2m.convertToMap(field,
				choosecourseroundDao.queryRoundInfoForCom(databaseName, id));
		return listmap;

	}
}
