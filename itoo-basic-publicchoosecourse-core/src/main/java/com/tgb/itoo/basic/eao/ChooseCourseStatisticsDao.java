package com.tgb.itoo.basic.eao;

import java.util.List;

import com.tgb.itoo.base.eao.BaseEao;
import com.tgb.itoo.basic.entity.PublicChooseCourse;

public interface ChooseCourseStatisticsDao extends BaseEao<PublicChooseCourse> {
	/**
	 * 查询课程性质
	 * 
	 * @param dataBaseName
	 * @return
	 */
	public List queryCourseNatureId(String dataBaseName);

	/**
	 * 根据课程性质查询寻课程id-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param dataBaseName
	 * @param CourseNatureId
	 * @return
	 */
	public List queryCourseIdByCourseNatureId(String dataBaseName,
			String courseNatureId);

	/**
	 * 根据课程id查询上课班id-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param dataBaseName
	 * @param CourseId
	 * @return
	 */
	public List queryTeachClassByCouserInfoId(String dataBaseName,
			String courseId);

	/**
	 * 根据班级信息查询学生数量-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param dataBaseName
	 * @param TeachClassId
	 * @return
	 */
	public List queryStudenCountByTeachClassId(String dataBaseName,
			String teachClassId);

	/**
	 * 查询层次信息-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param dataBaseName
	 * @return
	 */
	public List querySchoolLeve(String dataBaseName);

	/**
	 * 查询在校的年级-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param dataBaseName
	 * @return
	 */
	public List queryGradList(String dataBaseName);

	/**
	 * 根据节点id查询学院-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param dataBaseName
	 * @param nodelevelid
	 * @return
	 */
	public List queryInstitutionList(String dataBaseName);

	/**
	 * 查询课程的剩余和已选容量(分页)-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param dataBaseName
	 * @return
	 */
	public List queryRemainAndChoosedCapacity(int pageNum, int pageSize,
			String sort, String order, String dataBaseName);

	/**
	 * 查询课程的剩余和已选容量-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param SortName
	 * @param SortValue
	 * @param dataBaseName
	 * @return
	 */
	public int queryRemainAndChoosedCapacityCount(String sortName,
			String sortValue, String dataBaseName);

	/**
	 * 根据课程名称模糊查询课程的剩余容量和已选容量（分页）-刘新阳-2016年6月11日20:31:26-v5.0
	 */
	public List queryRCCapacityByCourseName(int pageNum, int pageSize,
			String condition, String dataBaseName, String sort, String order);

	/**
	 * 根据课程名称模糊查询课程的剩余容量和已选容量，查询记录条数-刘新阳-2016年6月11日20:31:26-v5.0
	 */
	public int queryRCCapacityCountByCourseNameCount(String dataBaseName,
			String condition);

	/**
	 * 查询已配置的轮次信息-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param dataBaseName
	 * @param condition
	 *            学院，年级，层次，轮次
	 * @return
	 */
	public List queryFuzzyRoundInfo(int pageNum, int pageSize,
			String dataBaseName, String condition, String sortName,
			String sortValue);

	/**
	 * 查询所有已配置轮次的信息-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param dataBaseName
	 * @return
	 */
	public List queryRoundInfo(int pageNum, int pageSize, String dataBaseName,
			String sortName, String sortValue);

	/**
	 * 选课时间安排--无条件查询--查询总记录条数 -刘新阳-2016年6月11日20:31:26-v5.0
	 * */
	public int queryRoundInfoCount(String dataBaseName, String sortName,
			String sortValue);

	/**
	 * 选课时间安排--查询记录总条数 小王丹 2016-2-29 11:17:21-v5.0
	 * */
	public int queryFuzzyRoundInfoCount(String dataBaseName, String condition,
			String sortName, String sortValue);

	/**
	 * 根据轮次id查询已经配置课程信息-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param dataBaseName
	 * @param roundId
	 * @param SortName
	 * @param SortValue
	 * @return
	 */
	public List queryCourseCapacityByRound(int pageNum, int pageSize,
			String dataBaseName, String roundId, String sortName,
			String sortValue);

	/**
	 * 根据轮次id查询已经配置课程信息-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param dataBaseName
	 * @param roundId
	 * @param SortName
	 * @param SortValue
	 * @return
	 */
	public List queryfuzzyCourseCapacitybyRound(int pageNum, int pageSize,
			String condition, String dataBaseName, String roundId,
			String sortName, String sortValue);

	/**
	 * 统计各个课程性质已选课程的容量-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param dataBaseName
	 * @return
	 */
	public List StatisticCount(String dataBaseName);

	/**
	 * 根据轮次id查询该轮次的课程容量配置条数-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param dataBaseName
	 * @param roundId
	 * @return
	 */
	public int queryCourseCapacityByRoundCount(String dataBaseName,
			String roundId);

	/**
	 * 模糊查询 根据轮次id查询该轮次的课程容量配置条数-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param condition
	 * @param dataBaseName
	 * @param roundId
	 * @return
	 */
	public int queryFuzzyCourseCapacityByRoundCount(String condition,
			String dataBaseName, String roundId);

}
