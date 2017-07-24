package org.jumutang.zsh.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jumutang.zsh.model.TopModel;
import org.jumutang.zsh.services.TopServiceI;
import org.jumutang.zsh.tools.ResResult;
import org.jumutang.zsh.tools.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 今日排名控制层
 *
 */
@Controller
@Transactional
@RequestMapping("/top")
public class TopController {
	@Autowired
	private TopServiceI topService;
	/**
	 * 查询所有排名
	 * @return resResult
	 */
	@ResponseBody
	@RequestMapping("/query")
	public ResResult queryTop(HttpServletRequest request){
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");
		if(userId == null){
			return new ResResult(2,"未登录");
		}
		Integer department = (Integer)session.getAttribute("department");
		if(department == 4 || department == 5){
			return new ResResult(3,"请选择用户模块");
		}
		List<TopModel> list = topService.queryAll();
		if(list.size()==0){
			return ResponseModel.errorActive("暂无数据");
		}
		return ResponseModel.returnData(list);
	}
}
