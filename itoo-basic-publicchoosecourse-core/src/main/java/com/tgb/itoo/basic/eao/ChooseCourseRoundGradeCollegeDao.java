package com.tgb.itoo.basic.eao;

import java.util.List;

import com.tgb.itoo.base.eao.BaseEao;
import com.tgb.itoo.basic.entity.ChooseCourseRoundGradeCollege;

/**
 * 课程轮次学院管理dao 2016-1-15 10:34:14
 * 
 * @author zhanghui
 *
 */
public interface ChooseCourseRoundGradeCollegeDao extends
		BaseEao<ChooseCourseRoundGradeCollege> {

/**
 * 添加机构-刘新阳-2016年6月11日20:31:26-v5.0
 * @param list
 * @return
 */
	public boolean saveRoundInstitution(List<ChooseCourseRoundGradeCollege> list);
	
 //继续配课时将组织机构的表的数据删除
	public boolean updateChooseCourseRoundGradeCollege(String dataBaseName);

}
