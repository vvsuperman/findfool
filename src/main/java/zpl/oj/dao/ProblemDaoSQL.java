package zpl.oj.dao;

import java.util.Map;

public class ProblemDaoSQL {
	public String getCountProblemIdbySetSiteSQL(Map<String,Object>  para){
		String sql = "select  * FROM "
				+ "(SELECT * FROM problem ORDER BY problem_id DESC) as b where belong=0";
		if(para.get("type") != null){
			sql += " and type="+para.get("type");
		}
		if(para.get("setid") != null){
			sql += " and problem_set_id="+para.get("setid");
		}
		sql += " GROUP BY uuid";
		sql = "select count(*) from ("+sql+") as a";
		return sql;
		
	}
	
	public String getProblemIdbySetSiteSQL(Map<String,Object>  para){
		String sql = "select  UUID,  PROBLEM_ID as problemId, belong, TITLE,  DESCRIPTION,  DATE, PROBLEM_SET_ID, "
				+ "CREATOR, TYPE,  LIMIT_TIME,  LIMIT_MEM,  SUBMIT,  SLOVED,   MODIFIER,   MODIFYDATE  FROM "
				+ "(SELECT * FROM problem ORDER BY problem_id DESC) as b where belong=0";

		
		if(para.get("type") != null){
			sql += " and type="+para.get("type");
		}
		if(para.get("setid") != null){
			sql += " and problem_set_id="+para.get("setid");
		}
		sql += " GROUP BY uuid";
		if(para.get("begin") != null &&para.get("end") != null){
			sql += " limit "+para.get("begin")+","+para.get("end");
		}
		return sql;
		
	}
	
	
	public String getCountProblemIdbySetUserSQL(Map<String,Object>  para){
		String sql = "select  *  FROM "
				+ "(SELECT * FROM problem ORDER BY problem_id DESC) as b";
		int flag = 0;
		if(para.get("type") != null){
			sql += " where type="+para.get("type");
			flag = 1;
		}
		if(para.get("uid") != null){
			if(flag == 0)
				sql += " where creator="+para.get("uid");
			else{
				sql += " and creator="+para.get("uid");
			}
		}
		sql += " GROUP BY uuid";
		sql = "select count(*) from ("+sql+") as a";
		return sql;
		
	}
	
	public String getProblemIdbySetUserSQL(Map<String,Object>  para){
		String sql = "select  PROBLEM_ID as problemId FROM "
				+ "(SELECT * FROM problem ORDER BY problem_id DESC) as b";
		int flag = 0;
		if(para.get("type") != null){
			sql += " where type="+para.get("type");
			flag = 1;
		}
		if(para.get("uid") != null){
			if(flag == 0)
				sql += " where creator="+para.get("uid");
			else{
				sql += " and creator="+para.get("uid");
			}
		}
		sql += " GROUP BY uuid";
		if(para.get("begin") != null &&para.get("end") != null){
			sql += " limit "+para.get("begin")+","+para.get("end");
		}
		
		return sql;
		
	}
}
