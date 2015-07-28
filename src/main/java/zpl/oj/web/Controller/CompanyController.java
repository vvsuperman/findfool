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
import zpl.oj.service.ImgUploadService;
import zpl.oj.service.imp.CompanyService;

@Controller
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ImgUploadService imgUploadService;
	
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

	// 根据用户id返回公司信息
	@RequestMapping(value = "/getByUid")
	@ResponseBody
	public ResponseBase getByUid(@RequestBody Map<String, String> params) {
		String strUserId = params.get("uid");
		int userId = strUserId == null ? 0 : Integer.parseInt(strUserId.trim());
		ResponseBase rb = new ResponseBase();
		if (userId <= 0) {
			rb.setState(1);
			rb.setMessage("参数传递错误");
			return rb;
		}
		CompanyModel company = companyService.getByUid(userId);
		rb.setMessage(company);
		return rb;
	}

	
	@RequestMapping(value = "/create")
	@ResponseBody
	public ResponseBase create(@RequestBody Map<String, String> params) {
		String name = RequestUtil.getStringParam(params, "name", true);
		String cover = RequestUtil.getStringParam(params, "cover", true);
		String logo = RequestUtil.getStringParam(params, "logo", true);
		String address = RequestUtil.getStringParam(params, "address", true);
		String website = RequestUtil.getStringParam(params, "website", true);
		String description = RequestUtil.getStringParam(params, "description", true);
		CompanyModel company = new CompanyModel();
		company.setName(name);
		company.setCover(cover);
		company.setLogo(logo);
		company.setAddress(address);
		company.setWebsite(website);
		company.setDescription(description);
		companyDao.add(company);
		ResponseBase rb = new ResponseBase();
		rb.setMessage(company.getId());

		CompanyModel cm = companyService.findCompanyByName(name);
		rb.setState(0);
		rb.setMessage(cm);
		return rb;
	}

	// 修改公司信息
	@RequestMapping(value = "/modify")
	@ResponseBody
	public ResponseBase modifyCompany(@RequestBody CompanyModel params) {
		ResponseBase rb = new ResponseBase();
		String companyName = params.getName();
		if (companyName == null) {
			rb.setState(1);
			rb.setMessage("公司名称不能为空，请重新输入");
			return rb;
		}

		companyService.modifyCompany(params);
		CompanyModel cm = companyService.findCompanyByName(companyName);
		rb.setState(0);
		rb.setMessage(cm);
		return rb;
	}

	
	
	@RequestMapping(value = "/uploadimg")
	@ResponseBody
	public ResponseBase uploadCompanyImg(@RequestBody Map<String, String> params) {
		ResponseBase rb = new ResponseBase();
		String companyName = RequestUtil.getStringParam(params, "name", true);
		if(companyName == null){
			rb.setState(1);
			rb.setMessage("公司名不得为空");
			return rb;
		}
		
		CompanyModel company = companyDao.findCompanyByName(companyName);
		if(company == null){
			rb.setState(2);
			rb.setMessage("无此公司");
			return rb;
		}
		
		String img =  RequestUtil.getStringParam(params, "img", true);
		if(img == null){
			rb.setState(3);
			rb.setMessage("图片不可为空");
			return rb;
		}
		
		Integer flag = RequestUtil.getIntParam(params, "flag");
		if(flag == null){
			rb.setState(4);
			rb.setMessage("标志不可为空");
			return rb;
		}
		
		imgUploadService.saveCompanyImg(company,img,flag);
		return null;
		
		
		
		
	}
}
