package com.tgb.itoo.basic.eao;

import java.util.List;

import com.tgb.itoo.base.eao.BaseEao;
import com.tgb.itoo.basic.entity.CourseRound;

/**
 * 课程轮次关系管理dao 2016-1-15 10:37:50
 * 
 * @author zhanghui
 *
 */
public interface CourseRoundDao extends BaseEao<CourseRound> {

	/**
	 * 添加课程轮次-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 * 
	 * @param pubChooseCourseid
	 * @param roundID
	 * @param rTotalCapacity
	 * @param databaseName
	 * @return
	 */
	public CourseRound saveCourseRound(String pubChooseCourseid,
			String roundID, String rTotalCapacity, String databaseName);

	/**
	 * 根据courseroundid更新设置容量D zhanghui 2016-1-25 17:34:08-v5.0
	 */
	public boolean updateCapacityByCRid(String dataBaseName, String condition,
			String capacity);

	/**
	 * 根据courseroundid查询原来设置容量d zhanghui 2016-1-27 11:47:14-v5.0
	 */
	public List queryTotalByid(String dataBaseName, String crid);

	/**
	 * 根据publicchoosecourseid批量更新课程轮次关系表的总容量，剩余容量为0-维护人员：王孟梅--2016年6月11日14:55:38
	 * --v5.0
	 * 
	 */

	public boolean saveCourseRoundbyChoosecourseId(List<CourseRound> list);
}
