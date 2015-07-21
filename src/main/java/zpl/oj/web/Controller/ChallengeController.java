/**
 * 公开挑战赛控制器
 * 
 * @author Moon
 */
package zpl.oj.web.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foolrank.model.Challenge;

import zpl.oj.dao.ChallengeDao;
import zpl.oj.model.responsejson.ResponseBase;

@Controller
@RequestMapping("/challenge")
public class ChallengeController {

	@Autowired
	private ChallengeDao challengeDao;
	
	@RequestMapping(value = "/{signedId}")
	@ResponseBody
	public String index(@PathVariable("signedId") String signedId) {
		System.out.println(signedId);
		// return new ModelAndView("login");
		return signedId;
	}

	@RequestMapping(value = "/getListByStatus")
	@ResponseBody
	public ResponseBase getListByStatus(@RequestBody Map<String, String> params) {
		int status = Integer.parseInt(params.get("status"));
		int companyId = Integer.parseInt(params.get("cid"));
		int page = Integer.parseInt(params.get("p"));
		if (page <= 0) {
			page = 1;
		}
		int pageSize = 10;
		int offset = (page - 1) * pageSize;
		List<Challenge> challenges = null;
		if (companyId > 0) {
			challenges = challengeDao.getList(companyId, status, offset, pageSize);
		} else {
			challenges = challengeDao.getList(status, offset, pageSize);
		}
		ResponseBase rb = new ResponseBase();
		rb.setMessage(challenges);

		return rb;
	}

	@RequestMapping(value = "/getListByType")
	@ResponseBody
	public ResponseBase getListByType(@RequestBody Map<String, String> params) {
		int corporateId = Integer.parseInt(params.get("cid"));
		List<Challenge> challenges = new ArrayList<Challenge>();
		ResponseBase rb = new ResponseBase();
		rb.setMessage(challenges);

		return rb;
	}
}
