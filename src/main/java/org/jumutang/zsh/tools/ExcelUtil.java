package org.jumutang.zsh.tools;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import jxl.format.*;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.Label;
import org.apache.log4j.Logger;
import org.jumutang.zsh.controller.WxManagerController;
import org.jumutang.zsh.model.CustomerModel;
import org.jumutang.zsh.model.DailyModel;
import org.jumutang.zsh.model.WCUserModel;
import org.jumutang.zsh.services.CustomerServiceI;
import org.jumutang.zsh.services.DailyServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONObject;

import javax.annotation.PostConstruct;

/**
 * excel工具类
 * 
 * @author 鲁雨
 * @since 20170223
 * @version v1.0
 * 
 * copyright Luyu(18994139782@163.com)
 *
 *
 * @update Tinny.liang
 *
 */
@Component
public class ExcelUtil {
	private static Logger logger = Logger.getLogger(ExcelUtil.class);

	/**
	 * @Auther: Tinny.liang
	 * @Description: 读取员工信息
	 * @Date: 16:23 2017/4/14
	 */
	public static ResResult readExcel(MultipartFile file,CustomerServiceI customerServiceI){
		boolean success = true;
		Workbook workbook = null;
		try{
		 workbook = Workbook.getWorkbook(file.getInputStream());
		 logger.info("获取工作簿-----------------------");
		 Sheet[] sheet = workbook.getSheets();
		 logger.info("工作簿的集合数为---------------------"+sheet.length);
		 List<CustomerModel> list = new ArrayList<CustomerModel>();
		 for(Sheet sheet1 : sheet){
			 for(int i = 0;i<sheet1.getRows();i++){
				 CustomerModel customerModel = new CustomerModel();
				 WCUserModel wcUserModel = new WCUserModel();
			 	if(sheet1.getCell(0,i)==null||sheet1.getCell(0,i).getContents().contains("基本信息")||sheet1.getCell(0,i).getContents().contains("姓名")){
			 		continue;
				}
				 Cell name = sheet1.getCell(0,i);
				 logger.info("添加成员姓名-----------------------");
				 if(name !=null && !"".equals(name.getContents())){
					 customerModel.setCustomerName(name.getContents());
				 }
				 Cell employeeNo = sheet1.getCell(1,i);
				 logger.info("添加成员员工号-----------------");
				 if(employeeNo !=null && !"".equals(employeeNo.getContents())){
					 customerModel.setEmployeeNo(employeeNo.getContents());
				 }
				 Cell gender = sheet1.getCell(2,i);
				 logger.info("添加员工性别------------------------------");
				 if(gender !=null && !"".equals(gender.getContents())){
					 if("男".equals(gender.getContents())){
						 customerModel.setGender("1");
					 } else if("女".equals(gender.getContents())){
					 	customerModel.setGender("2");
					 }
				 }
				 Cell weixinId = sheet1.getCell(3,i);
				 logger.info("添加员工微信------------------------------");
				 if(weixinId !=null && !"".equals(weixinId.getContents())){
					 customerModel.setWeiXinId(weixinId.getContents());
				 }
				 Cell phone = sheet1.getCell(4,i);
				 logger.info("添加员工手机号-----------------------");
				 if(phone !=null && !"".equals(phone.getContents())){
					 customerModel.setPhone(phone.getContents());
				 }
				 Cell email = sheet1.getCell(5,i);
				 logger.info("添加员工邮箱-----------------------");
				 if(email !=null && !"".equals(email.getContents())){
					 customerModel.setEmail(email.getContents());
				 }
				 Cell oilStation = sheet1.getCell(6,i);
				 logger.info("添加员工所属岗位地-------------------------------------");
				 if(oilStation !=null && !"".equals(oilStation.getContents())){
					 customerModel.setOilStation(oilStation.getContents());
				 }
				 Cell position = sheet1.getCell(7,i);
				 logger.info("添加员工岗位------------------------------");
				 if(position !=null && !"".equals(position.getContents())){
					 customerModel.setPosition(position.getContents());
				 }
				 customerModel.setCustomerType((short)2);

				 CustomerModel param = new CustomerModel();
				 param.setPhone(phone.getContents());
				 List<CustomerModel> listResult = customerServiceI.queryCustomer(param);
				 logger.info("查看"+name.getContents()+"是否存在------------------------"+(listResult.size() == 0 ? "不存在":"存在"));
				 if(listResult.size()!=0){
					 continue;
				 }
				 //微信模型
				 wcUserModel.setUserid(customerModel.getPhone());
				 if(wcUserModel.getUserid()==null||"".equals(wcUserModel.getUserid())){
					 logger.info(customerModel.getCustomerName()+"没有账号请重新填写");
					 continue;
				 }
				 wcUserModel.setName(customerModel.getCustomerName());
				 if(wcUserModel.getName()==null||"".equals(wcUserModel.getName())){
					 logger.info(customerModel.getCustomerName()+"没有姓名请重新填写");
					 continue;
				 }
				 String company = customerModel.getOilStation();
				 if(company.contains("经营管理部")){
					 wcUserModel.setDepartment(new String[]{"5"});
				 }else if(company.contains("江宁县公司")){
					 wcUserModel.setDepartment(new String[]{"9"});
				 }else if(company.contains("浦口县公司")){
					 wcUserModel.setDepartment(new String[]{"11"});
				 }else if(company.contains("高淳县公司")){
					 wcUserModel.setDepartment(new String[]{"12"});
				 }else if(company.contains("溧水县公司")){
					 wcUserModel.setDepartment(new String[]{"13"});
				 }else if(company.contains("六合县公司")){
					 wcUserModel.setDepartment(new String[]{"14"});
				 }else{
				 	logger.info(customerModel.getCustomerName()+"没有部门请重新填写");
				 	continue;
				 }
				 wcUserModel.setPosition(customerModel.getPosition());
				 wcUserModel.setMobile(customerModel.getPhone());
				 wcUserModel.setGender(customerModel.getGender());
				 wcUserModel.setEmail(customerModel.getEmail());
				 wcUserModel.setWeixinid(customerModel.getWeiXinId());
				 if(wcUserModel.getMobile()==null&&wcUserModel.getWeixinid()==null&&wcUserModel.getEmail()==null){
					 logger.info(customerModel.getCustomerName()+"缺少三选一条件");
					 return ResponseModel.errorActive(customerModel.getCustomerName()+"缺少三选一条件");
				 }
				 String result = UserManagerUtil.createUser(wcUserModel);
				 JSONObject jsonObject = JSONObject.fromObject(result);
				 if(jsonObject.getInt("errcode")!=0){
					 logger.info("调用微信接口出错"+jsonObject.getString("errmsg"));
					 return ResponseModel.errorActive(jsonObject.getString("errmsg"));
				 }
				 customerModel.setCustomerOpenId(wcUserModel.getUserid());

				 int result1 = customerServiceI.addCustomer(customerModel);
				 logger.info("插入数据条数--------------------"+result1);
				 if(result1 == 1 ){
					 logger.info("插入员工"+customerModel.getCustomerName()+"数据成功");
				 }else{
				 	logger.info("插入员工"+customerModel.getCustomerName()+"数据失败");
				 	success = false;
				 	return ResponseModel.errorActive("插入员工"+customerModel.getCustomerName()+"数据失败");
				 }
			 }
		 }
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		if(success){
			return ResponseModel.successActive();
		}else {
			return ResponseModel.errorActive("插入失败");
		}
	}

	/**
	 * @Auther: Tinny.liang
	 * @Description: 读取客户信息
	 * @Date: 16:23 2017/4/14
	 */
	public static ResResult readCustomerExcel(MultipartFile file,CustomerServiceI customerServiceI){
		boolean success = true;
		WritableWorkbook wwb;
		Workbook workbook = null;
		try{
			workbook = Workbook.getWorkbook(file.getInputStream());
			logger.info("获取工作簿-----------------------");
			Sheet[] sheet = workbook.getSheets();
			logger.info("工作簿的集合数为---------------------"+sheet.length);
			List<CustomerModel> list = new ArrayList<CustomerModel>();
			for(Sheet sheet1 : sheet){
				CustomerModel customerModel = new CustomerModel();
				WCUserModel wcUserModel = new WCUserModel();
				Cell customerName = sheet1.getCell(0,1);
				logger.info("添加客户名称-----------------------");
				if(customerName !=null && !"".equals(customerName.getContents())){
					customerModel.setCustomerName(customerName.getContents());
				}
				Cell customerCode = sheet1.getCell(1,2);
				logger.info("添加客户代码-----------------------");
				if(customerCode !=null && !"".equals(customerCode.getContents())){
					customerModel.setCustomerCode(customerCode.getContents());
				}
				Cell customerAddress = sheet1.getCell(1,3);
				logger.info("添加客户地址-----------------------");
				if(customerAddress !=null && !"".equals(customerAddress.getContents())){
					customerModel.setCustomerAddress(customerAddress.getContents());
				}
				Cell phonePerson = sheet1.getCell(1,7);
				logger.info("添加联系人-----------------------");
				if(phonePerson !=null && !"".equals(phonePerson.getContents())){
					customerModel.setPhonePerson(phonePerson.getContents());
				}
				Cell phone = sheet1.getCell(4,7);
				logger.info("添加手机-----------------------");
				if(phone !=null && !"".equals(phone.getContents())){
					customerModel.setPhone(phone.getContents());
				}
				Cell weixinId = sheet1.getCell(4,8);
				logger.info("添加微信-----------------------");
				if(weixinId !=null && !"".equals(weixinId.getContents())){
					customerModel.setWeiXinId(weixinId.getContents());
				}
				Cell hobby = sheet1.getCell(1,9);
				logger.info("添加爱好-----------------------");
				if(hobby !=null && !"".equals(hobby.getContents())){
					customerModel.setHobby(	hobby.getContents());
				}
				Cell manager = sheet1.getCell(1,26);
				logger.info("添加客户经理-----------------------");
				if(manager !=null && !"".equals(manager.getContents())){
					customerModel.setManager(manager.getContents());
				}
				customerModel.setCustomerType((short)1);

				CustomerModel param = new CustomerModel();
				param.setPhone(phone.getContents());
				List<CustomerModel> listResult = customerServiceI.queryCustomer(param);
				logger.info("查看是否存在------------------------"+(listResult.size() == 0 ? "不存在":"存在"));
				if(listResult.size()!=0){
					continue;
				}

				//微信模型
				wcUserModel.setUserid(customerModel.getPhone());
				if(wcUserModel.getUserid()==null||"".equals(wcUserModel.getUserid())){
					logger.info(customerModel.getCustomerName()+"没有账号请重新填写");
					continue;
				}
				wcUserModel.setName(customerModel.getCustomerName());
				if(wcUserModel.getName()==null||"".equals(wcUserModel.getName())){
					logger.info(customerModel.getCustomerName()+"没有姓名请重新填写");
					continue;
				}
				wcUserModel.setDepartment(new String[]{"5"});
				wcUserModel.setMobile(customerModel.getPhone());
				wcUserModel.setWeixinid(customerModel.getWeiXinId());
				if(wcUserModel.getMobile()==null&&wcUserModel.getWeixinid()==null){
					logger.info(customerModel.getCustomerName()+"缺少三选一条件");
					return ResponseModel.errorActive(customerModel.getCustomerName()+"缺少三选一条件");
				}
				String result = UserManagerUtil.createUser(wcUserModel);
				JSONObject jsonObject = JSONObject.fromObject(result);
				if(jsonObject.getInt("errcode")!=0){
					logger.info("调用微信接口出错"+jsonObject.getString("errmsg"));
					return ResponseModel.errorActive(jsonObject.getString("errmsg"));
				}
				customerModel.setCustomerOpenId(wcUserModel.getUserid());

				int result1 = customerServiceI.addCustomer(customerModel);
				logger.info("插入数据条数--------------------"+result1);
				if(result1 == 1 ){
					logger.info("插入客户"+customerModel.getCustomerName()+"数据成功");
				}else{
					success=false;
					logger.info("插入客户"+customerModel.getCustomerName()+"数据失败");
					return ResponseModel.errorActive("插入客户"+customerModel.getCustomerName()+"数据失败");
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		if(success){
			return ResponseModel.successActive();
		}else {
			return ResponseModel.errorActive("插入失败");
		}
	}

	/**
	 * @Auther: Tinny.liang
	 * @Description: 下载员工信息
	 * @Date: 16:23 2017/4/14
	 */
	public static WritableWorkbook  exportExcel(OutputStream os,CustomerServiceI customerServiceI) throws Exception {
		WritableWorkbook  wwb = Workbook.createWorkbook(os);
		WritableSheet ws = wwb.createSheet("员工信息统计", 0);
		//去掉整个sheet中的网格线
		ws.getSettings().setShowGridLines(false);

		//    设置单元格的文字格式
		WritableFont wf = new WritableFont(WritableFont.createFont("宋体"), 12, WritableFont.BOLD,
				false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
		WritableCellFormat wcf = new WritableCellFormat(wf);
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
		wcf.setAlignment(Alignment.CENTRE);
		//整个表格线为粗线、黑色
		wcf.setBorder(Border.ALL, BorderLineStyle.MEDIUM, Colour.BLACK);

		ws.setRowView(1, 500);
		ws.mergeCells(0, 0, 2, 0);
		ws.addCell(new Label(0, 0, "基本信息(姓名账号必填)",wcf));
		ws.mergeCells(3, 0, 5, 0);
		ws.addCell(new Label(3, 0, "身份验证信息(三者不可同时为空)",wcf));
		ws.mergeCells(6, 0, 7, 0);
		ws.addCell(new Label(6, 0, "职位信息",wcf));

		ws.addCell(new Label(0, 1, "姓名",wcf));
		ws.addCell(new Label(1, 1, "账号",wcf));
		ws.addCell(new Label(2, 1, "性别",wcf));
		ws.addCell(new Label(3, 1, "微信号",wcf));
		ws.addCell(new Label(4, 1, "手机号(国际手机号码请加“+国际区号”)",wcf));
		ws.addCell(new Label(5, 1, "邮箱",wcf));
		ws.addCell(new Label(6, 1, "所在部门",wcf));
		ws.addCell(new Label(7, 1, "职位",wcf));

		//    设置单元格的文字格式
		WritableFont wf2 = new WritableFont(WritableFont.createFont("宋体"), 12, WritableFont.NO_BOLD,
				false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
		WritableCellFormat wcf2 = new WritableCellFormat(wf2);
		wcf2.setVerticalAlignment(VerticalAlignment.CENTRE);
		wcf2.setAlignment(Alignment.CENTRE);
		wcf2.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		List<CustomerModel> customerModelList = customerServiceI.queryCustomer(new CustomerModel());
		int i=2;
		for(CustomerModel customerModel:customerModelList){
			if(customerModel != null&&customerModel.getCustomerType() != null&&customerModel.getCustomerType() == (short)2){
				ws.addCell(new Label(0, i,customerModel.getCustomerName(),wcf2));
				ws.addCell(new Label(1,i,customerModel.getEmployeeNo(),wcf2));
				ws.addCell(new Label(2,i,customerModel.getGender(),wcf2));
				ws.addCell(new Label(3,i,customerModel.getWeiXinId(),wcf2));
				ws.addCell(new Label(4,i,customerModel.getPhone(),wcf2));
				ws.addCell(new Label(5,i,customerModel.getEmail(),wcf2));
				ws.addCell(new Label(6,i,customerModel.getOilStation(),wcf2));
				ws.addCell(new Label(7,i,customerModel.getPosition(),wcf2));
				i++;
			}
		}
		return wwb;
	}

	/**
	 * @Auther: Tinny.liang
	 * @Description: 下载客户信息
	 * @Date: 16:24 2017/4/14
	 */
	public static WritableWorkbook  exportCustomerExcel(OutputStream os,CustomerServiceI customerServiceI) throws Exception {
		WritableWorkbook  wwb = Workbook.createWorkbook(os);
		List<CustomerModel> customerModelList = customerServiceI.queryCustomer(new CustomerModel());
		int i=0;
		for(CustomerModel customerModel:customerModelList){
			if(customerModel != null&&customerModel.getCustomerType() != null&&customerModel.getCustomerType()==(short)1){
				WritableSheet ws = wwb.createSheet(customerModel.getCustomerName(), i);
				//去掉整个sheet中的网格线
				ws.getSettings().setShowGridLines(false);
				ws.setRowView(1, 500);
				//    设置单元格的文字格式
				WritableFont wf = new WritableFont(WritableFont.createFont("宋体"), 12, WritableFont.NO_BOLD,
						false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
				WritableCellFormat wcf = new WritableCellFormat(wf);
				wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
				wcf.setAlignment(Alignment.CENTRE);
				//整个表格线为粗线、黑色
				wcf.setBorder(Border.ALL, BorderLineStyle.MEDIUM, Colour.BLACK);


				ws.mergeCells(0, 0, 5, 0);
				ws.addCell(new Label(0, 0, "客户情况调查表",wcf));

				ws.mergeCells(0, 1, 5, 1);
				ws.addCell(new Label(0, 1, customerModel.getCustomerName(),wcf));

				ws.addCell(new Label(0, 2, "客户代码",wcf));
				ws.mergeCells(1, 2, 2, 2);
				ws.addCell(new Label(1, 2, customerModel.getCustomerCode(),wcf));
				ws.addCell(new Label(3, 2, "开发时间",wcf));
				ws.mergeCells(4, 2, 5, 2);
				ws.addCell(new Label(4, 2, "",wcf));

				ws.addCell(new Label(0, 3, "客户地址",wcf));
				ws.mergeCells(1, 3, 5, 3);
				ws.addCell(new Label(1, 3, customerModel.getCustomerAddress(),wcf));

				ws.addCell(new Label(0, 4, "业务类型",wcf));
				ws.mergeCells(1, 4, 2, 4);
				ws.addCell(new Label(1, 4, "",wcf));
				ws.addCell(new Label(3, 4, "主营业务",wcf));
				ws.mergeCells(4, 4, 5, 4);
				ws.addCell(new Label(4, 4, "",wcf));

				ws.addCell(new Label(0, 5, "社会信用代码",wcf));
				ws.mergeCells(1, 5, 2, 5);
				ws.addCell(new Label(1, 5, "",wcf));
				ws.addCell(new Label(3, 5, "销售渠道（S8/S5）",wcf));
				ws.mergeCells(4, 5, 5, 5);
				ws.addCell(new Label(4, 5, "",wcf));

				ws.addCell(new Label(0, 6, "每月用油量",wcf));
				ws.mergeCells(1, 6, 5, 6);
				ws.addCell(new Label(1, 6, "",wcf));

				ws.mergeCells(0, 7, 0, 8);
				ws.addCell(new Label(0, 7, "经办人",wcf));
				ws.mergeCells(1, 7, 2, 8);
				ws.addCell(new Label(1, 7, customerModel.getPhonePerson(),wcf));
				ws.addCell(new Label(3, 7, "手机",wcf));
				ws.mergeCells(4, 7, 5, 7);
				ws.addCell(new Label(4, 7, customerModel.getPhone(),wcf));
				ws.addCell(new Label(3, 8, "微信",wcf));
				ws.mergeCells(4, 8, 5, 8);
				ws.addCell(new Label(4, 8, customerModel.getWeiXinId(),wcf));

				ws.mergeCells(0, 9, 0, 10);
				ws.addCell(new Label(0, 9, "爱好",wcf));
				ws.mergeCells(1, 9, 5, 10);
				ws.addCell(new Label(1, 9, customerModel.getHobby(),wcf));

				ws.mergeCells(0, 11, 0, 12);
				ws.addCell(new Label(0, 11, "分管领导",wcf));
				ws.mergeCells(1, 11, 2, 12);
				ws.addCell(new Label(1, 11, "",wcf));
				ws.addCell(new Label(3, 11, "手机",wcf));
				ws.mergeCells(4, 11, 5, 11);
				ws.addCell(new Label(4, 11, "",wcf));
				ws.addCell(new Label(3, 12, "微信",wcf));
				ws.mergeCells(4, 12, 5, 12);
				ws.addCell(new Label(4, 12, customerModel.getWeiXinId(),wcf));

				ws.mergeCells(0, 13, 0, 14);
				ws.addCell(new Label(0, 13, "爱好",wcf));
				ws.mergeCells(1, 13, 5, 14);
				ws.addCell(new Label(1, 13, "",wcf));

				ws.addCell(new Label(0, 15, "采购方式（询价、招标、直采）",wcf));
				ws.mergeCells(1, 15, 2, 15);
				ws.addCell(new Label(1, 15, "",wcf));
				ws.addCell(new Label(3, 15, "采购品种",wcf));
				ws.mergeCells(4, 15, 5, 15);
				ws.addCell(new Label(4, 15, "",wcf));

				ws.addCell(new Label(0, 16, "价格敏感度",wcf));
				ws.mergeCells(1, 16, 2, 16);
				ws.addCell(new Label(1, 16, "",wcf));
				ws.addCell(new Label(3, 16, "价格承受区间",wcf));
				ws.mergeCells(4, 16, 5, 16);
				ws.addCell(new Label(4, 16, "",wcf));

				ws.addCell(new Label(0, 17, "购油偏好（价格、品质）",wcf));
				ws.mergeCells(1, 17, 5, 17);
				ws.addCell(new Label(1, 17, "",wcf));

				ws.mergeCells(0, 18, 0, 19);
				ws.addCell(new Label(0, 18, "增值服务",wcf));
				ws.mergeCells(1, 18, 5, 19);

				ws.mergeCells(0, 20, 0, 24);
				ws.addCell(new Label(0, 20, "非油需求",wcf));
				ws.mergeCells(1, 20, 2, 20);
				ws.mergeCells(3, 20, 5, 20);
				ws.addCell(new Label(1, 20, "尾气处理液",wcf));
				ws.mergeCells(1, 21, 2, 21)
				;ws.mergeCells(3, 21, 5, 21);
				ws.addCell(new Label(1, 21, "燃油宝",wcf));
				ws.mergeCells(1, 22, 2, 22);
				ws.mergeCells(3, 22, 5, 22);
				ws.addCell(new Label(1, 22, "润滑油",wcf));
				ws.mergeCells(1, 23, 2, 23);
				ws.mergeCells(3, 23, 5, 23);
				ws.addCell(new Label(1, 23, "卓玛泉",wcf));
				ws.mergeCells(1, 24, 2, 24);
				ws.mergeCells(3, 24, 5, 24);
				ws.addCell(new Label(1, 24, "其他商品",wcf));

				ws.addCell(new Label(0, 25, "配送方式",wcf));
				ws.mergeCells(1, 25, 5, 25);
				ws.addCell(new Label(1, 25, "",wcf));

				ws.addCell(new Label(0, 26, "维护经理",wcf));
				ws.addCell(new Label(1, 26, customerModel.getManager(),wcf));
				i++;
			}
		}
		return wwb;
	}


	public static int readDailyExcel(MultipartFile file,DailyServiceI dailyServiceI){
		boolean success = true;
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(file.getInputStream());
			logger.info("获取工作簿-----------------------");
			Sheet[] sheet = workbook.getSheets();
			logger.info("工作簿的集合数为---------------------" + sheet.length);
			List<CustomerModel> list = new ArrayList<CustomerModel>();
			for (Sheet sheet1 : sheet) {
				for (int i = 0; i < sheet1.getRows(); i++) {
					if(sheet1.getCell(0,i)==null||"".equals(sheet1.getCell(0,i).getContents())||sheet1.getCell(0,i).getContents().contains("日报表")||sheet1.getCell(0,i).getContents().contains("姓名")){
						continue;
					}
					DailyModel dailyModel = new DailyModel();
					Cell name = sheet1.getCell(0,i);
					logger.info("添加姓名-----------------------");
					if(name !=null && !"".equals(name.getContents())){
						dailyModel.setCustomerName(name.getContents());
					}
					Cell monthAttendance = sheet1.getCell(1,i);
					logger.info("添加外勤通当月-----------------------");
					if(monthAttendance !=null && !"".equals(monthAttendance.getContents())){
						dailyModel.setMonthAttendance(monthAttendance.getContents());
					}
					Cell attendanceRank = sheet1.getCell(2,i);
					logger.info("添加外勤通排名-----------------------");
					if(attendanceRank !=null && !"".equals(attendanceRank.getContents())){
						dailyModel.setAttendanceRank(attendanceRank.getContents());
					}

					Cell todayDaily = sheet1.getCell(3,i);
					logger.info("添加当日日报-----------------------");
					if(todayDaily !=null && !"".equals(todayDaily.getContents())){
						dailyModel.setTodayDaily(todayDaily.getContents());
					}
					Cell monthDaily = sheet1.getCell(4,i);
					logger.info("添加当月日报-----------------------");
					if(monthDaily !=null && !"".equals(monthDaily.getContents())){
						dailyModel.setMonthDaily(monthDaily.getContents());
					}
					Cell dailyRate = sheet1.getCell(5,i);
					logger.info("添加完成率-----------------------");
					if(dailyRate !=null && !"".equals(dailyRate.getContents())){
						dailyModel.setDailyRate(dailyRate.getContents());
					}
					Cell dailyRank = sheet1.getCell(6,i);
					logger.info("添加日报排名-----------------------");
					if(dailyRank !=null && !"".equals(dailyRank.getContents())){
						dailyModel.setDailyRank(dailyRank.getContents());
					}
					Cell monthDevelop = sheet1.getCell(7,i);
					logger.info("添加当月开发-----------------------");
					if(monthDevelop !=null && !"".equals(monthDevelop.getContents())){
						dailyModel.setMonthDevelop(monthDevelop.getContents());
					}
					Cell reward = sheet1.getCell(8,i);
					logger.info("添加奖励-----------------------");
					if(reward !=null && !"".equals(reward.getContents())){
						dailyModel.setReward(reward.getContents());
					}
					Cell remark = sheet1.getCell(8,i);
					logger.info("添加备注-----------------------");
					if(remark !=null && !"".equals(remark.getContents())){
						dailyModel.setRemark(remark.getContents());
					}

					DailyModel param = new DailyModel();
					param.setCustomerName(dailyModel.getCustomerName());
					param.setCreateDate(DateUtil.formatDate());
					List<DailyModel> listResult = dailyServiceI.listDaily(param);
					logger.info("查看是否存在------------------------"+(listResult.size() == 0 ? "不存在":"存在"));
					if(listResult.size()!=0){
						continue;
					}
					int result1 = dailyServiceI.saveDaily(dailyModel);
					logger.info("插入数据条数--------------------"+result1);
					if(result1 == 1 ){
						logger.info("插入成功");
					}else{
						success=false;
						logger.info("插入失败");
					}
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		if(success){
			return 1;
		}else {
			return 0;
		}
	}
}