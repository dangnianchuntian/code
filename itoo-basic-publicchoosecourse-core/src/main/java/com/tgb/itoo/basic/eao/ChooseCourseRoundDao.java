package com.tgb.itoo.basic.eao;

import java.util.List;

import com.tgb.itoo.base.eao.BaseEao;
import com.tgb.itoo.basic.entity.ChooseCourseRound;
import com.tgb.itoo.tool.pageModel.PageEntity;

/**
 * 轮次管理dao 2016-1-15 10:31:48
 * 
 * @author zhanghui
 *
 */
public interface ChooseCourseRoundDao extends BaseEao<ChooseCourseRound> {
	public ChooseCourseRound saveCourseRound(String beginTime, String endTime,
			String lastestTime, String roundNo, String databaseName);

	/**
	 * 根据轮次id查询轮次信息forcombobox -刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param databaseName
	 * @param id
	 * @return
	 */
	public List queryRoundInfoForCom(String databaseName, String id);

	/**
	 * 查询所有轮次信息forcombobox -刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param databaseName
	 * @return
	 */
	public List queryCourseRoundforcom(String databaseName);

	/**
	 * 查询所有配置轮次信息-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @author zhanghui
	 *
	 */
	public PageEntity<ChooseCourseRound> queryCourseRound(
			PageEntity<ChooseCourseRound> pageEntity, String dataBaseName);

	/**
	 * 根据轮次编号查询轮次详细信息d-刘新阳-2016年6月11日20:31:26-v5.0
	 */
	public List<?> queryByRoundNo(int condition, String dataBaseName);

	/**
	 * 添加轮次信息d -刘新阳-2016年6月11日20:31:26-v5.0
	 */
	public boolean addRound(String roundNo, String beginTime, String endTime,
			String lastestQuitTime, String operator, String dataBaseName);

	/**
	 * 修改轮次信息-刘新阳-2016年6月11日20:31:26-v5.0
	 */
	public boolean updateRoundByid(String id, String roundNo, String beginTime,
			String endTime, String lastestQuitTime, String operator,
			String dataBaseName);

	/**
	 * 删除轮次信息-刘新阳-2016年6月11日20:31:26-v5.0
	 */
	public boolean deleteRoundById(String id, String dataBaseName);

	/**
	 * 根据id查询该轮次是否已经配置-刘新阳-2016年6月11日20:31:26-v5.0
	 */
	public int queryRoundById(String id, String dataBaseName);

	/**
	 * 查询轮次名称是否已经存在--王孟梅-2016年9月13日10:36:35-v5.0
	 * 
	 * @param dbName
	 * @param roundName
	 * @return
	 */
	public boolean isExistRoundName(String dbName, String roundName);

	/**
	 * 查询最大的轮次---王孟梅---2016年9月17日19:58:27-v5.0
	 * 
	 * @param dbName
	 * @return
	 */
	public String queryMaxRoundNo(String dbName);

}
