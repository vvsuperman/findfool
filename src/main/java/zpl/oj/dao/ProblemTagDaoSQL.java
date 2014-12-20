package zpl.oj.dao;

import java.util.Map;

public class ProblemTagDaoSQL {

	public String getCountProblemIdbyTagSiteSQL(Map<String,Object>  para){
		String sql = "select count(a.problemid) from tagproblem as a,tag as b,problem as c"
				+ " where a.tagid = b.tagid and b.context like '%"+para.get("tag")+"%'"
				+ " and c.belong=0 and c.isdelete=0 and c.problem_id=a.problemid";
		if(para.get("type") != null){
			sql += " and type="+para.get("type");
		}
		if(para.get("set") != null){
			sql += " and problem_set_id="+para.get("set");
		}
		return sql;
		
	}
	public String getProblemIdbyTagSiteSQL(Map<String,Object>  para){
		String sql = "select DISTINCT a.problemid from tagproblem as a,tag as b,problem as c"
				+ " where a.tagid = b.tagid and b.context like '%"+para.get("tag")+"%'"
				+ " and c.belong=0 and c.isdelete=0 and c.problem_id=a.problemid";
		if(para.get("type") != null){
			sql += " and type="+para.get("type");
		}
		if(para.get("set") != null){
			sql += " and problem_set_id="+para.get("set");
		}
		return sql;
	}
	
	public String getCountProblemIdbyTagUserSQL(Map<String,Object>  para){
		String sql = "select count(a.problemid) from tagproblem as a,tag as b,problem as c"
				+ " where a.tagid = b.tagid and b.context like '%"+para.get("tag")+"%'"
				+ " and c.isdelete=0 and c.problem_id=a.problemid and c.creator="+para.get("id");
		if(para.get("type") != null){
			sql += " and type="+para.get("type");
		}
		return sql;
	}
	
	public String getProblemIdbyTagUserSQL(Map<String,Object>  para){
		String sql = "select DISTINCT a.problemid from tagproblem as a,tag as b,problem as c"
				+ " where a.tagid = b.tagid and b.context like '%"+para.get("tag")+"%'"
				+ "  and c.isdelete=0 and c.problem_id=a.problemid and c.creator="+para.get("id");
		if(para.get("type") != null){
			sql += " and type="+para.get("type");
		}
		return sql;
	}
}
