package com.tgb.itoo.basic.eao.impl;

import java.io.Serializable;
import java.util.ArrayList;
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
import com.tgb.itoo.basic.eao.QuestionDao;
import com.tgb.itoo.basic.entity.Question;
import com.tgb.itoo.tool.pageModel.PageEntity;

/**
 * 教师留作业 问题
 * 
 * @author xinyang
 *
 */
@Stateless(name = "questionDaoImpl")
@Remote(QuestionDao.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class QuestionDaoImpl extends BaseEaoImpl<Question> implements
		QuestionDao {
	/**
	 * 查询教师布置的作业-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param dateBaseName
	 * @param teacherId
	 * @return
	 */
	@Override
	public PageEntity queryQuestion(int pageNum, int pageSize,
			String conditions, String teacherId, String dataBaseName) {
		// 声明一个分页查询实体
		PageEntity page = new PageEntity();
		page.setPageNum(pageNum);
		page.setPageSize(pageSize);
		StringBuilder sb = new StringBuilder();
		if (conditions != null && !("").equals(conditions)) {
			sb.append("SELECT ");
			sb.append("q.id,q.questionName,q.questionTime,");
			sb.append("c.courseName,");
			sb.append("tc.teachClassName,");
			sb.append("sc.termName ");
			sb.append("FROM ");
			sb.append("" + dataBaseName + ".tb_question q ");
			sb.append("LEFT JOIN "
					+ dataBaseName
					+ ".tb_curriculumschedule_now cn ON q.teacherId = cn.teacherID and q.teachClassId=cn.teachClassId ");
			sb.append("LEFT JOIN "
					+ dataBaseName
					+ ".tb_trainingprograms_now tn ON cn.trainingProgramsID = tn.id ");
			sb.append("LEFT JOIN " + dataBaseName
					+ ".tb_courseinfo c ON tn.courseID = c.id ");
			sb.append("LEFT JOIN " + dataBaseName
					+ ".tb_teachclass tc ON q.teachClassId = tc.id ");
			sb.append("LEFT JOIN " + dataBaseName
					+ ".tb_schoolcalendar sc ON q.schoolcalendarId = sc.id ");
			sb.append("WHERE (");
			sb.append("c.courseName LIKE '%" + conditions.trim() + "%'");
			sb.append("OR tc.teachClassName LIKE '%" + conditions.trim()
					+ "%')");
			sb.append("and q.isDelete='0' and ");
			sb.append("q.teacherId = '" + teacherId + "'");
			sb.append("AND c.courseTypeId = 'JbUEA7vDEeV6gyQjriPvoM' order by q.questionTime desc ");// c.courseTypeId='JbUEA7vDEeV6gyQjriPvoM'表示公共选修课
		} else {
			sb.append("SELECT ");
			sb.append("q.id,q.questionName,q.questionTime,");
			sb.append("c.courseName,");
			sb.append("tc.teachClassName,");
			sb.append("sc.termName ");
			sb.append("FROM ");
			sb.append("" + dataBaseName + ".tb_question q ");
			sb.append("LEFT JOIN "
					+ dataBaseName
					+ ".tb_curriculumschedule_now cn ON q.teacherId = cn.teacherID and q.teachClassId=cn.teachClassId ");
			sb.append("LEFT JOIN "
					+ dataBaseName
					+ ".tb_trainingprograms_now tn ON cn.trainingProgramsID = tn.id ");
			sb.append("LEFT JOIN " + dataBaseName
					+ ".tb_courseinfo c ON tn.courseID = c.id ");
			sb.append("LEFT JOIN " + dataBaseName
					+ ".tb_teachclass tc ON q.teachClassId = tc.id ");
			sb.append("LEFT JOIN " + dataBaseName
					+ ".tb_schoolcalendar sc ON q.schoolcalendarId = sc.id ");
			sb.append("WHERE q.isDelete='0' and ");
			sb.append("q.teacherId = '" + teacherId + "'");
			sb.append("AND c.courseTypeId = 'JbUEA7vDEeV6gyQjriPvoM' order by q.questionTime desc");// c.courseTypeId='JbUEA7vDEeV6gyQjriPvoM'表示公共选修课
		}
		page = this.queryPageEntityBySqlWithTotal(sb.toString(), page);
		PageEntity newPage = new PageEntity();
		List<Map<Serializable, Serializable>> newList = new ArrayList<>();
		for (int j = 0; j < page.getRows().size(); j++) {
			Map<Serializable, Serializable> mapRoom = new HashMap<>();
			Object[] obj = (Object[]) page.getRows().get(j);
			if (obj != null && obj.length > 0) {
				mapRoom.put("id", obj[0] == null ? "" : obj[0].toString());
				mapRoom.put("questionName",
						obj[1] == null ? "" : obj[1].toString());
				mapRoom.put("questionTime",
						obj[2] == null ? "" : obj[2].toString());
				mapRoom.put("courseName",
						obj[3] == null ? "" : obj[3].toString());
				mapRoom.put("teachClassName",
						obj[4] == null ? "" : obj[4].toString());
				mapRoom.put("termName", obj[5] == null ? "" : obj[5].toString());
				newList.add(mapRoom);
			}
			newPage.setRows(newList);
		}
		newPage.setTotal(page.getTotal());
		return newPage;

	}

	/**
	 * 查询所有学年-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param dataBaseName
	 * @return
	 */
	@Override
	public List queryTermName(String dataBaseName) {
		String sql = " select t.id,t.termName from " + dataBaseName
				+ ".tb_schoolcalendar t order by t.termName desc";
		return this.queryBySql(sql);
	}

	/**
	 * 查询该教师所教的所有课程-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param dataBaseName
	 * @param teacherId
	 * @return
	 */
	@Override
	public List queryCourseName(String dataBaseName, String teacherId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select t2.id,t2.courseCode,t2.courseName from "
				+ dataBaseName + ".tb_curriculumschedule_now t ");
		sb.append("LEFT JOIN " + dataBaseName
				+ ".tb_trainingprograms_now t1 ON t.trainingProgramsID=t1.id ");
		sb.append("LEFT JOIN " + dataBaseName
				+ ".tb_courseinfo t2 ON t1.courseID=t2.id ");
		sb.append("where t.teacherID='" + teacherId + "' ");
		String sql = sb.toString();
		return this.queryObjectBySql(sql, dataBaseName);
	}

	/**
	 * 查询该教师所教的所有班级-刘新阳-2016年6月11日20:31:26-v5.0
	 * 
	 * @param dataBaseName
	 * @param teacherId
	 * @return
	 */
	@Override
	public List queryTeachClassName(String dataBaseName, String teacherId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select t1.id,t1.teachClassCode,t1.teachClassName from "
				+ dataBaseName + ".tb_curriculumschedule_now t ");
		sb.append("LEFT JOIN " + dataBaseName
				+ ".tb_teachclass t1 ON t.teachClassId=t1.id ");
		sb.append(" where t.teacherID='" + teacherId + "' ");
		String sql = sb.toString();
		return this.queryObjectBySql(sql, dataBaseName);
	}

	/**
	 * 根据教师id和课程id查询teachClassId-刘新阳-2016年6月11日20:31:26-v5.0
	 */
	@Override
	public List queryTeachClassBycat(String dataBaseName, String teacherId,
			String courseId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select t.teachClassId from " + dataBaseName
				+ ".tb_curriculumschedule_now t where ");
		sb.append("t.trainingProgramsID in(select t1.id from " + dataBaseName
				+ ".tb_trainingprograms_now t1 where t1.courseID='" + courseId
				+ "') ");
		sb.append("and  t.teacherID='" + teacherId + "'");
		String sql = sb.toString();
		return this.queryObjectBySql(sql, dataBaseName);
	}

	/**
	 * 保存作业-刘新阳-2016年6月11日20:31:26-v5.0
	 */
	@Override
	public boolean addQuestionInfo(Question question) {
		return this.saveEntityGeneric(question);
	}

	/**
	 * 删除作业-刘新阳-2016年6月11日20:31:26-v5.0
	 */
	@Override
	public boolean deleteQuestions(String dataBaseName, String[] ids) {
		return this.deleteByIds(Question.class, ids, dataBaseName);
	}

	/**
	 * 查询教师id-刘新阳-2016年6月11日20:31:26-v5.0
	 */
	@Override
	public String queryTeachId(String dataBaseName, String teachCode) {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT id from " + dataBaseName
				+ ".tb_teacher where `code`='" + teachCode + "'");
		String sql = sb.toString();
		List list = this.queryObjectBySql(sql, dataBaseName);
		if (list.size() > 0) {
			return list.get(0).toString();
		} else {
			return null;
		}
	}

	/**
	 * 根据id查询答案-刘新阳-2016年6月11日20:31:26-v5.0
	 */
	@Override
	public List queryContextById(String dataBaseName, String id) {
		String sql = "select t.context from " + dataBaseName
				+ ".tb_question t where t.id='" + id + "'";
		return this.queryObjectBySql(sql, dataBaseName);
	}

	/**
	 * 根据id更新答案内容-刘新阳-2016年6月11日20:31:26-v5.0
	 */
	@Override
	public boolean updateContextById(String dataBaseName, String id,
			String content) {
		String variable = "h.context =:context";
		String condition = "where h.isDelete =:isDelete and h.id=:id";
		Map<Serializable, Serializable> map = new HashMap<Serializable, Serializable>();
		map.put("context", content);
		map.put("isDelete", 0);
		map.put("id", id);
		return this.updateByCondition(Question.class, variable, condition,
				dataBaseName, map, "h");
	}

	/**
	 * 根据学期Name查询学期id--王孟梅-2016年9月25日20:53:49
	 * 
	 * @param dataBaseName
	 * @param termName
	 * @return
	 */
	public String queryschoolcalendarIdByName(String dataBaseName,
			String termName) {
		String sql = "select tsl.id from " + dataBaseName
				+ ".tb_schoolcalendar tsl where tsl.termName='" + termName
				+ "'";

		List list = this.queryObjectBySql(sql.toString(), dataBaseName);

		if (list != null || list.size() > 0) {
			return list.get(0).toString();
		} else {
			return null;
		}
	}

}
