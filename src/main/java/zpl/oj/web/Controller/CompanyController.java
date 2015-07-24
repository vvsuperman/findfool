package zpl.oj.web.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foolrank.model.CompanyModel;
import com.foolrank.response.json.CompanyJson;
import com.foolrank.util.RequestUtil;

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
		CompanyJson item = null;
		if (companyId > 0) {
			CompanyModel company = companyDao.getById(companyId);
			if (company != null) {
				item = new CompanyJson();
				item.setId(company.getId());
				item.setName(company.getName());
				item.setCover(company.getCover());
				item.setAddress(company.getAddress());
				item.setTel(company.getTel());
				item.setLogo(company.getLogo());
				item.setWebsite(company.getWebsite());
				item.setDescription(company.getDescription());
			}
		}
		ResponseBase rb = new ResponseBase();
		rb.setMessage(item);

		return rb;
	}
	
	@RequestMapping(value = "/create")
	@ResponseBody
	public ResponseBase create(@RequestBody Map<String, String> params) {
		String name = RequestUtil.getStringParam(params, "name", true);
		String cover = RequestUtil.getStringParam(params, "cover", true);
		String logo = RequestUtil.getStringParam(params, "logo", true);
		String address = RequestUtil.getStringParam(params, "address", true);
		String tel = RequestUtil.getStringParam(params, "tel", true);
		String website = RequestUtil.getStringParam(params, "website", true);
		String description = RequestUtil.getStringParam(params, "description", true);
		CompanyModel company = new CompanyModel();
		company.setName(name);
		company.setCover(cover);
		company.setLogo(logo);
		company.setAddress(address);
		company.setTel(tel);
		company.setWebsite(website);
		company.setDescription(description);
		companyDao.add(company);
		ResponseBase rb = new ResponseBase();
		rb.setMessage(company.getId());

		return rb;
	}
}
