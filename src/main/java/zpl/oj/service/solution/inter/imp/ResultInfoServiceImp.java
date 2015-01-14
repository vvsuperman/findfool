package zpl.oj.service.solution.inter.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.ResultInfoDao;
import zpl.oj.model.common.ResultInfo;
import zpl.oj.service.solution.inter.ResultInfoService;

@Service
public class ResultInfoServiceImp implements ResultInfoService {

	@Autowired
	ResultInfoDao resultInfoDao;
	@Override
	public List<ResultInfo> getResultInfoBySolutionId(int solutionId) {
		return resultInfoDao.getResultsBySolutionId(solutionId);
	}

}
