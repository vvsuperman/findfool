package com.foolrank.provider;

import java.util.List;
import java.util.Map;

import zpl.oj.model.request.User;

public class QuizProvider {

	public String getChallengeListByUsers(Map<String, Object> map) {
		List<User> users = (List<User>) map.get("users");
		int status = (int) map.get("status");
		int offset = (int) map.get("offset");
		int count = (int) map.get("count");
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT quizid,owner,name,date,time,extra_info as extraInfo,uuid,emails,type,logo,description,start_time as startTime,end_time as endTime,signed_key as signedKey,create_time as createTime,status FROM quiz WHERE type=1 AND owner in (");
		for (int i = 0; i < users.size(); i++) {
			sb.append(users.get(i).getUid());
			if (i < users.size() - 1) {
				sb.append(",");
			}
		}
		sb.append(") AND status=").append(status);
		sb.append(" ORDER BY start_time ASC LIMIT ").append(offset);
		sb.append(",").append(count);

		return sb.toString();
	}
}
