package com.tgb.itoo.basic.eao.impl;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.tgb.itoo.base.eao.impl.BaseEaoImpl;
import com.tgb.itoo.basic.eao.ChooseCourseRoundDao;
import com.tgb.itoo.basic.eao.ChooseCourseRoundGradeCollegeDao;
import com.tgb.itoo.basic.eao.PublicChooseCourseDao;
import com.tgb.itoo.basic.entity.ChooseCourseRoundGradeCollege;
import com.tgb.itoo.basic.entity.CourseRound;

/**
 * 课程轮次学院管理dI 2016-1-15 10:40:47
 * 
 * @author zhanghui
 *
 */

@Stateless(name = "ChooseCourseRoundGradeCollegeDaoImpl")
@Remote(ChooseCourseRoundGradeCollegeDao.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)

public class ChooseCourseRoundGradeCollegeDaoImpl extends
		BaseEaoImpl<ChooseCourseRoundGradeCollege> implements
		ChooseCourseRoundGradeCollegeDao {

	/**
	 * 添加机构轮次-维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public boolean saveRoundInstitution(List<ChooseCourseRoundGradeCollege> list) {
		
		// 2.根据课程轮次主键id更新/保存课程轮次实体(注意：roundid如何生成还是插入？？A.插入)
	 return this.saveEntitysGeneric(list);
	}

	
	/**
	 * 更新选课组织机构的表状态删除
	 */
	@Override
	public boolean updateChooseCourseRoundGradeCollege(String dataBaseName) {
		StringBuilder sqlstring= new StringBuilder();
		sqlstring.append("UPDATE "+dataBaseName+".tb_choosecourseroundgradecollege SET isDelete = 1");
		 int i= this.executeBySql(sqlstring.toString());
		boolean flag=false;
		 if (i > 0){
			flag=true;
		}
		
		 return flag;
	}

}
