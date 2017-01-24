package com.tgb.itoo.basic.eao.impl;

import java.io.Serializable;
import java.util.Collections;
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
import com.tgb.itoo.basic.eao.ChooseCourseRoundDao;
import com.tgb.itoo.basic.entity.ChooseCourseRound;
import com.tgb.itoo.tool.pageModel.PageEntity;

/**
 * 轮次管理dI 2016-1-15 10:40:20
 * 
 * @author zhanghui
 *
 */
@Stateless(name = "ChooseCourseRoundDaoImpl")
@Remote(ChooseCourseRoundDao.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ChooseCourseRoundDaoImpl extends BaseEaoImpl<ChooseCourseRound>
		implements ChooseCourseRoundDao {

	/**
	 * 查询轮次名称是否已经存在--王孟梅-2016年9月13日10:36:35-v5.0
	 * 
	 * @param dbName
	 * @param roundName
	 * @return
	 */
	@Override
	public boolean isExistRoundName(String dbName, String roundName) {
		boolean flag = true;

		StringBuilder sql = new StringBuilder();
		sql.append("select tccr.id from ");
		sql.append(dbName);
		sql.append(".tb_choosecourseround tccr ");
		sql.append("WHERE tccr.roundNo=");
		sql.append("'");
		sql.append(roundName);
		sql.append("'");

		List<?> list = this.queryBySql(sql.toString());

		if (list != null && !(list.isEmpty())) {
			flag = false;
		}

		return flag;

	}

	/**
	 * 添加课程轮次-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public ChooseCourseRound saveCourseRound(String beginTime, String endTime,
			String lastestTime, String roundNo, String databaseName) {
		ChooseCourseRound chooseCourseRound = new ChooseCourseRound();
		chooseCourseRound.setBeginTime(beginTime);
		chooseCourseRound.setEndTime(endTime);
		chooseCourseRound.setLastestQuitTime(lastestTime);
		chooseCourseRound.setDataBaseName(databaseName);
		chooseCourseRound.setRoundNo(roundNo);
		chooseCourseRound.setOperator("zhanghui");
		chooseCourseRound = this.saveAndReturnGeneric(chooseCourseRound);

		return chooseCourseRound;
	}

	/**
	 * 查询所有配置轮次信息dImpl -维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 * 
	 * @author zhanghui
	 *
	 */

	@Override
	public PageEntity<ChooseCourseRound> queryCourseRound(
			PageEntity<ChooseCourseRound> pageEntity, String dataBaseName) {

		pageEntity
				.setHql("From ChooseCourseRound h where h.isDelete=0 ORDER BY h.beginTime desc ");
		return super.queryPageEntityByHql(pageEntity, null, "h");

	}

	/**
	 * 查询最大的轮次---王孟梅---2016年9月17日19:58:27-v5.0
	 * 
	 * @param dbName
	 * @return
	 */
	@Override
	public String queryMaxRoundNo(String dbName) {
		StringBuilder sql = new StringBuilder();
		sql.append("select MAX(roundNo) as maxRoundNo from ");
		sql.append(dbName);
		sql.append(".tb_choosecourseround tccr where tccr.isDelete=0 ");

		List list = this.queryBySql(sql.toString());

		if (list != null && !(list.isEmpty()) && list.get(0) != null) {
			return list.get(0).toString();
		} else {
			return "";
		}

	}

	/*
	 * 根据轮次编号查询轮次详细信息dImpl -维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List<?> queryByRoundNo(int roundNo, String dataBaseName) {
		String sql = "select endTime from " + dataBaseName
				+ ".tb_choosecourseround where roundNo=" + "'第" + roundNo
				+ "轮'  and isDelete=0";
		List list = this.queryObjectBySql(sql, dataBaseName);
		// 判断查询的数据是否为空
		if (list.isEmpty() || list == null) {
			return Collections.emptyList();
		}
		return list;
	}

	/*
	 * 添加轮次dImpl -维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public boolean addRound(String roundNo, String beginTime, String endTime,
			String lastestQuitTime, String operator, String dataBaseName) {
		ChooseCourseRound chooseCourseRound = new ChooseCourseRound();

		chooseCourseRound.setRoundNo(roundNo);
		chooseCourseRound.setBeginTime(beginTime);
		chooseCourseRound.setEndTime(endTime);
		chooseCourseRound.setLastestQuitTime(lastestQuitTime);
		chooseCourseRound.setOperator(operator);
		chooseCourseRound.setDataBaseName(dataBaseName);
		chooseCourseRound.setIsDelete(0);

		// EJB优化:将调用CommonEao修改为调用BaseEao-张晗-2017年1月15日23:21:58
		return this.saveEntityGeneric(chooseCourseRound);

	}

	/*
	 * 修改轮次dImpl -维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public boolean updateRoundByid(String id, String roundNo, String beginTime,
			String endTime, String lastestQuitTime, String operator,
			String dataBaseName) {

		String variable1 = "cr.roundNo =:roundNo,cr.beginTime =:beginTime,cr.endTime=:endTime,cr.lastestQuitTime=:lastestQuitTime,cr.operator =:operator";
		String condition1 = "where cr.isDelete =:isDelete and cr.id=:id";
		Map<Serializable, Serializable> map1 = new HashMap<>();
		map1.put("roundNo", roundNo);
		map1.put("beginTime", beginTime);
		map1.put("endTime", endTime);
		map1.put("lastestQuitTime", lastestQuitTime);
		map1.put("id", id);
		map1.put("operator", operator);

		map1.put("isDelete", 0);
		// 泛型类中的方法
		return this.updateByConditionGeneric(variable1, condition1,
				dataBaseName, map1, "cr");
	}

	/*
	 * 删除dImpl -维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public boolean deleteRoundById(String id, String dataBaseName) {
		ChooseCourseRound chooseCourseRound;

		/* 更新前需要先查找这条记录 */
		// EJB优化:将调用CommonEao修改为调用BaseEao-张晗-2017年1月15日23:25:15
		chooseCourseRound = this.findEntityByIdGeneric(id, dataBaseName);

		int i = 1;
		chooseCourseRound.setIsDelete(i);
		chooseCourseRound.setDataBaseName(dataBaseName);

		// EJB优化:将调用CommonEao修改为调用BaseEao-张晗-2017年1月15日23:26:21
		return this.updateEntityGeneric(chooseCourseRound);

	}

	/*
	 * 根据id查询该轮次是否配置-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public int queryRoundById(String id, String dataBaseName) {
		String sql = "select * from " + dataBaseName
				+ ".tb_courseround where roundId='" + id + "'  and isDelete=0";

		List list = this.queryObjectBySql(sql, dataBaseName);
		// 判断查询的数据是否为空
		if (list.isEmpty() || list == null) {
			return 0;
		}

		return list.size();
	}

	/**
	 * 查询课程轮次信息-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryCourseRoundforcom(String databaseName) {
		StringBuilder sql = new StringBuilder();
		sql.append("select id,roundNo,beginTime,endTime,lastestQuitTime ");
		sql.append("from "
				+ databaseName
				+ ".tb_choosecourseround  cr where isDelete=0 and cr.id not in (SELECT  DISTINCT c.courseRoundId from "
				+ databaseName
				+ ".tb_choosecourseroundgradecollege c ) ORDER BY roundNo desc");

		List list = this.queryObjectBySql(sql.toString(), dataBaseName);
		// 判断查询的数据是否为空
		if (list.isEmpty() || list == null) {
			return Collections.emptyList();
		}

		return list;
	}

	/**
	 * 查询课程轮次-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public List queryRoundInfoForCom(String databaseName, String id) {
		StringBuilder sql = new StringBuilder();

		sql.append("select id,roundNo,beginTime,endTime,lastestQuitTime ");
		sql.append("from " + databaseName
				+ ".tb_choosecourseround where isDelete=0 and id='" + id + "'");

		List list = this.queryObjectBySql(sql.toString(), dataBaseName);
		// 判断查询的数据是否为空
		if (list.isEmpty() || list == null) {
			return Collections.emptyList();
		}

		return list;
	}
}
