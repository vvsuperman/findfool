package zpl.oj.web.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foolrank.model.CompanyInfo;
import com.foolrank.response.json.Company;

import zpl.oj.dao.CompanyDao;
import zpl.oj.model.responsejson.ResponseBase;

@Controller
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	private CompanyDao companyDao;
	
	@RequestMapping(value = "/getById")
	@ResponseBody
	public ResponseBase getById(@RequestBody Map<String, String> params) {
		String strCompanyId = params.get("cid");
		int companyId = strCompanyId == null ? 0 : Integer
				.parseInt(strCompanyId.trim());
		Company company = null;
		if (companyId > 0) {
			CompanyInfo companyInfo = companyDao.getInfoById(companyId);
			if (companyInfo != null) {
				company = new Company();
				company.setId(companyInfo.getCompanyId());
				company.setAddress(companyInfo.getAddress());
				company.setTel(companyInfo.getTel());
				company.setLogo(companyInfo.getLogo());
				company.setWebsite(companyInfo.getWebsite());
			}
		}
		ResponseBase rb = new ResponseBase();
		rb.setMessage(company);

		return rb;
	}
}
