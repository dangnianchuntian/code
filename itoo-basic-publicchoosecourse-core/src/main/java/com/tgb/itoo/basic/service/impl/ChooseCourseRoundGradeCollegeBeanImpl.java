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
import com.tgb.itoo.basic.eao.ChooseCourseRoundGradeCollegeDao;
import com.tgb.itoo.basic.entity.ChooseCourseRoundGradeCollege;
import com.tgb.itoo.basic.service.ChooseCourseRoundGradeCollegeBean;

@Stateless(name = "ChooseCourseRoundGradeCollegeBeanImpl")
@Remote(ChooseCourseRoundGradeCollegeBean.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ChooseCourseRoundGradeCollegeBeanImpl extends
		BaseBeanImpl<ChooseCourseRoundGradeCollege> implements
		ChooseCourseRoundGradeCollegeBean {

	@EJB(name = "ChooseCourseRoundGradeCollegeDaoImpl")
	private ChooseCourseRoundGradeCollegeDao choosecourRoundGradeCollegeDao;

	public ChooseCourseRoundGradeCollegeDao getChoosecourRoundGradeCollegeDao() {
		return choosecourRoundGradeCollegeDao;
	}

	public void setChoosecourRoundGradeCollegeDao(
			ChooseCourseRoundGradeCollegeDao choosecourRoundGradeCollegeDao) {
		this.choosecourRoundGradeCollegeDao = choosecourRoundGradeCollegeDao;
	}

	@Override
	public BaseEao getBaseEao() {
		return choosecourRoundGradeCollegeDao;
	}

	
	/**
	 * 增加轮次信息修改--维护人员：王孟梅--2016年6月11日14:55:38--v5.0
	 */
	@Override
	public boolean saveRoundInstitution(List<ChooseCourseRoundGradeCollege> list) {
		//返回布尔值
    return choosecourRoundGradeCollegeDao.saveRoundInstitution(list);
	}
	/**
	 * 更新选课组织机构的状态
	 */
	@Override
	public boolean updateChooseCourseRoundGradeCollege(String dataBaseName) {
		
		return choosecourRoundGradeCollegeDao.updateChooseCourseRoundGradeCollege(dataBaseName);
	}
}
