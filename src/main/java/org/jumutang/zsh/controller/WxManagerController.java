package org.jumutang.zsh.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.write.WritableWorkbook;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.jumutang.zsh.model.CustomerModel;
import org.jumutang.zsh.model.WCUserModel;
import org.jumutang.zsh.services.CustomerServiceI;
import org.jumutang.zsh.tools.ExcelUtil;
import org.jumutang.zsh.tools.ResResult;
import org.jumutang.zsh.tools.ResponseModel;
import org.jumutang.zsh.tools.UserManagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import net.sf.json.JSONObject;

/**
 * 微信通讯录管理控制层
 *
 */
@Controller
public class WxManagerController {

	private Logger logger = Logger.getLogger(WxManagerController.class);
	@Autowired
	private CustomerServiceI customerServiceI;

	/**
	 * 上传excel员工信息
	 * @param request
	 */
	@RequestMapping(value="/uploadExcel.do" ,produces="text/html;charset=utf-8" )
	public @ResponseBody String batchInsert(HttpServletRequest request){
		Map returnMap = new HashMap();
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
		MultipartFile file = multipartHttpServletRequest.getFile("file0");
		if(file==null){
			logger.info("未选择文件");
			returnMap.put("result","error");
		}
		ResResult result = ExcelUtil.readExcel(file,customerServiceI);
		logger.info("查看文件是否更新成功---------------------------"+result);

		if(result.getStatus() == 0){
			returnMap.put("result","success");
		}else{
			returnMap.put("result","error");
			returnMap.put("describe",result.getDescribe());
		}
		String resultString=String. valueOf(JSONObject.fromObject (returnMap));
		return resultString;
	}

	/**
	 * 上传excel客户信息
	 * @param request
	 */
	@RequestMapping(value="/uploadCustomerExcel.do" ,produces="text/html;charset=utf-8" )
	public @ResponseBody String batchInsertCustomer(HttpServletRequest request){
		Map returnMap = new HashMap();
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
		MultipartFile file = multipartHttpServletRequest.getFile("file0");
		if(file==null){
			logger.info("未选择文件");
			returnMap.put("result","error");
		}
		ResResult result = ExcelUtil.readCustomerExcel(file,customerServiceI);
		logger.info("查看文件是否更新成功---------------------------"+result);

		if(result.getStatus() == 0){
			returnMap.put("result","success");
		}else{
			returnMap.put("result","error");
			returnMap.put("describe",result.getDescribe());
		}
		String resultString=String. valueOf(JSONObject.fromObject (returnMap));
		return resultString;
	}

	/**
	 * 导出excel员工信息
	 * @param request
	 */
	@RequestMapping(value="/exportExcel.do" ,produces="text/html;charset=utf-8" )
	public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String filename = "员工信息.xls";//导出excel名
		OutputStream os = response.getOutputStream();
		WritableWorkbook wwb = ExcelUtil.exportExcel(os,customerServiceI);
		filename= URLEncoder.encode(filename,"UTF-8");
		response.setContentType("application/msexcel");
		response.setHeader( "Content-Disposition", "attachment;filename=" + new String(filename.getBytes(),"ISO8859-1"));
		wwb.write();//写入excel对象
		wwb.close();//关闭可写入的Excel对象
		os.close();
	}

	/**
	 * 导出excel客户信息
	 * @param request
	 */
	@RequestMapping(value="/exportCustomerExcel.do" ,produces="text/html;charset=utf-8" )
	public void exportCustomerExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String filename = "客户资料.xls";//导出excel名
		OutputStream os = response.getOutputStream();
		WritableWorkbook wwb = ExcelUtil.exportCustomerExcel(os,customerServiceI);
		filename= URLEncoder.encode(filename,"UTF-8");
		response.setContentType("application/msexcel");
		response.setHeader( "Content-Disposition", "attachment;filename=" + new String(filename.getBytes(),"ISO8859-1"));
		wwb.write();//写入excel对象
		wwb.close();//关闭可写入的Excel对象
		os.close();
	}
}
