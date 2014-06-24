package zpl.oj.service.webservice.imp;

import javax.jws.WebService;

import zpl.oj.service.webservice.UserWebServiceApi;
@WebService(endpointInterface="zpl.oj.service.webservice.UserWebServiceApi")
public class UserWebServiceImp implements UserWebServiceApi {

	public String sayHello() {
			return "hello world";
	}
}
