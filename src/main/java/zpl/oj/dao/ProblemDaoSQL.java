package zpl.oj.dao;

import java.util.Map;

public class ProblemDaoSQL {
	public String getCountProblemIdbySetSiteSQL(Map<String,Object>  para){
		String sql ="select count(*) from problem where isdelete =0 and belong=0";
		if(para.get("type") != null){
			sql += " and type="+para.get("type");
		}
		if(para.get("setid") != null){
			sql += " and problem_set_id="+para.get("setid");
		}
		/*sql += " GROUP BY uuid";
		sql = "select count(*) from ("+sql+") as a";*/
		sql+=" order by problem_id";
		return sql;
		
	}
	
	public String getProblemIdbySetSiteSQL(Map<String,Object>  para){
		String sql = "select  PROBLEM_ID as problemId, UUID,   belong, TITLE,  DESCRIPTION,  DATE, PROBLEM_SET_ID as problemSetId, "
				+ "CREATOR, TYPE,  LIMIT_TIME,  LIMIT_MEM,  SUBMIT,  SLOVED,   MODIFIER,   MODIFYDATE  FROM "
				+ "problem where isdelete=0 and belong=0";

		
		if(para.get("type") != null){
			sql += " and type="+para.get("type");
		}
		if(para.get("setid") != null){
			sql += " and problem_set_id="+para.get("setid");
		}
		
		sql+=" ORDER BY problem_id DESC ";
		if(para.get("begin") != null &&para.get("end") != null){
			sql += " limit "+para.get("begin")+","+para.get("end");
		}
		
	
		return sql;
		
	}
	
	
	public String getCountProblemIdbySetUserSQL(Map<String,Object>  para){
		String sql = "select  count(*)  FROM problem where isdelete=0 ";
		if(para.get("type") != null){
			sql += " and type="+para.get("type");
		}
		if(para.get("uid") != null){
			sql += " and creator="+para.get("uid");
		
		}
		return sql;
		
	}
	
	public String getProblemIdbySetUserSQL(Map<String,Object>  para){
		String sql = "select  PROBLEM_ID as problemId FROM  problem where isdelete=0 ";
		if(para.get("type") != null){
			sql += " and type="+para.get("type");
		}
		if(para.get("uid") != null){
			
				sql += " and creator="+para.get("uid");
		}
		sql += " ORDER BY problem_id DESC";
		if(para.get("begin") != null &&para.get("end") != null){
			sql += " limit "+para.get("begin")+","+para.get("end");
		}
		
		return sql;
		
	}
}
