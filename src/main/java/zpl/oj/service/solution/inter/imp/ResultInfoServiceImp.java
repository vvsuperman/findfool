package zpl.oj.service.solution.inter.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.ResultInfoDao;
import zpl.oj.dao.TuserProblemDao;
import zpl.oj.model.common.ResultInfo;
import zpl.oj.service.solution.inter.ResultInfoService;

@Service
public class ResultInfoServiceImp implements ResultInfoService {

	@Autowired
	private ResultInfoDao resultInfoDao;
	@Autowired
	private TuserProblemDao tuserProblemDao;
	
	@Override
	public List<ResultInfo> getResultInfoBySolutionId(int solutionId) {
		List<ResultInfo> resultInfo  = resultInfoDao.getResultsBySolutionId(solutionId);
		//若只有一个，说明编译错误，直接返回结果即可
		if(resultInfo.size()==1){
			return resultInfo;
		//若不止一个，表示运行了测试用例
		}else{
			return tuserProblemDao.getProResult(solutionId);
		}
	}

}
