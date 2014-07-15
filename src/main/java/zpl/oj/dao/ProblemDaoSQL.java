package zpl.oj.dao;

import java.util.Map;

public class ProblemDaoSQL {
	public String getCountProblemIdbySetSiteSQL(Map<String,Object>  para){
		String sql = "select count(*) from problem"
			+ " where belong=0";
		if(para.get("type") != null){
			sql += " and type="+para.get("type");
		}
		if(para.get("setid") != null){
			sql += " and problem_set_id="+para.get("setid");
		}
		return sql;
		
	}
	
	public String getProblemIdbySetSiteSQL(Map<String,Object>  para){
		String sql = "select problem_id from problem"
			+ " where belong=0";
		if(para.get("type") != null){
			sql += " and type="+para.get("type");
		}
		if(para.get("setid") != null){
			sql += " and problem_set_id="+para.get("setid");
		}
		if(para.get("begin") != null &&para.get("end") != null){
			sql += " limit "+para.get("begin")+","+para.get("end");
		}
		return sql;
		
	}
	
	
	public String getCountProblemIdbySetUserSQL(Map<String,Object>  para){
		String sql = "select count(*) from problem"
			+ " where belong=1";
		if(para.get("type") != null){
			sql += " and type="+para.get("type");
		}
		if(para.get("uid") != null){
			sql += " and creator="+para.get("uid");
		}
		return sql;
		
	}
	
	public String getProblemIdbySetUserSQL(Map<String,Object>  para){
		String sql = "select problem_id from problem"
			+ " where belong=1";
		if(para.get("type") != null){
			sql += " and type="+para.get("type");
		}
		if(para.get("uid") != null){
			sql += " and creator="+para.get("uid");
		}
		if(para.get("begin") != null &&para.get("end") != null){
			sql += " limit "+para.get("begin")+","+para.get("end");
		}
		return sql;
		
	}
}
