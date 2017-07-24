package org.jumutang.zsh.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jumutang.zsh.model.OilModel;
import org.jumutang.zsh.model.TopModel;
import org.jumutang.zsh.services.OilServiceI;
import org.jumutang.zsh.services.TopServiceI;
import org.jumutang.zsh.tools.DateUtil;
import org.jumutang.zsh.tools.ResResult;
import org.jumutang.zsh.tools.ResponseModel;
import org.jumutang.zsh.tools.ZshUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * excel导入控制器
 *
 */
@Controller
@Transactional
@RequestMapping("excel")
public class ExcelController {
	
	//判断文件类型
	private final static String XLS_TYPE="xls";
	private final static String XLSX_TYPE="xlsx";
	@Autowired
	private OilServiceI oilService;
	
	@Autowired
	private TopServiceI topService;
	
	//上传文件
	@ResponseBody
	@RequestMapping("/upload")
	public ResResult uploadExcel(HttpServletRequest request, HttpServletResponse response){
		
		try{
			MultipartHttpServletRequest mhr = (MultipartHttpServletRequest)request;
			MultipartFile file = mhr.getFile("excelFile");
			
			if(file==null){
				return ResponseModel.errorActive("请选择文件");
			}
			//文件全小写
			String fileName = file.getOriginalFilename().toLowerCase();
			String end = fileName.substring(fileName.indexOf(".")+1);
			//xls类型
			if(XLS_TYPE.equals(end)){
				return readExcelByxls(topService,file);
			}else if(XLSX_TYPE.equals(end)){
				return readExcelXlsx(topService,file);
			}else{
				return ResponseModel.errorActive("文件格式不对");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return ResponseModel.errorActive("上传失败");
	}
	
	/**
	 * 读取excel
	 * 
	 * @param topServiceI
	 * @param MultipartFile
	 * 
	 * @return resResult
	 */
	public ResResult readExcelByxls(TopServiceI topServiceI,MultipartFile file){
		Workbook workbook = null;
		try{
		workbook = Workbook.getWorkbook(file.getInputStream());
		//获取第一张工作表对象
		Sheet sheet = workbook.getSheet(0);
		//获取第一行表格标题
		String title = sheet.getCell(0,0).getContents().trim();
		List<TopModel> topList = new ArrayList<TopModel>();
		List<OilModel> oilList = new ArrayList<OilModel>();
		//定义参数用于排序
		int desc = 1;
		if(!"".equals(title) && "国内油价".equals(title)){
			for(int i = 2;i<sheet.getRows();i++){
				OilModel oilModel = new OilModel();
				Cell cell1 = sheet.getCell(0, i);
				Cell cell2 = sheet.getCell(1, i);
				Cell cell3 = sheet.getCell(2, i);
				//判段油品类型
				if("普通燃油料".equals(cell3.getContents().trim())){
					/*oilModel.setOilCategory(cell1.getContents().trim());
					oilModel.setOilId(ZshUtil.createUuid());
					oilModel.setOilDate(DateUtil.getDate());
					oilModel.setOilPriceType(new Short("1"));
					oilModel.setOilStatus(new Short("1"));
					oilModel.setOilCategoryType(new Short("1"));
					oilModel.setOilPrice(cell2.getContents().trim());
					oilModel.setDesc(desc);*/
					oilList.add(oilModel);
					
				}else if("轻质燃油料".equals(cell3.getContents().trim())){
					/*oilModel.setOilCategory(cell1.getContents().trim());
					oilModel.setOilId(ZshUtil.createUuid());
					oilModel.setOilDate(DateUtil.getDate());
					oilModel.setOilPriceType(new Short("1"));
					oilModel.setOilStatus(new Short("1"));
					oilModel.setOilCategoryType(new Short("2"));
					oilModel.setOilPrice(cell2.getContents().trim());
					oilModel.setDesc(desc);*/
					oilList.add(oilModel);
				}	
				desc++;
		}
		}
		if(!"".equals(title) && "服务之星".equals(title)){
			for(int i = 2;i< sheet.getRows();i++){
				Cell cell1 = sheet.getCell(0, i);
				Cell cell2 = sheet.getCell(1, i);
				Cell cell3 = sheet.getCell(2, i);
				Cell[] cells = new Cell[]{cell1,cell2,cell3};
				Short status = new Short("1");
				TopModel topModel = saveTopModelByXls(cells, status);
				topList.add(topModel);
		}
		}
		//解析第二个工作表
		Sheet sheet1 = workbook.getSheet(1);
		String title1 = sheet1.getCell(0,0).getContents().trim();
		if(!"".equals(title1) && "国际油价".equals(title1)){
			for(int i = 2;i< sheet1.getRows();i++){
				OilModel oilModel = new OilModel();
				Cell cell1 = sheet1.getCell(0, i);
				Cell cell2 = sheet1.getCell(1, i);
				/*oilModel.setOilCategory(cell1.getContents().trim());
				oilModel.setOilId(ZshUtil.createUuid());
				oilModel.setOilDate(DateUtil.getDate());
				oilModel.setOilStatus(new Short("1"));
				oilModel.setOilPriceType(new Short("2"));
				oilModel.setOilCategoryType(new Short("1"));
				oilModel.setOilPrice(cell2.getContents().trim());
				oilModel.setDesc(desc);*/
				oilList.add(oilModel);
				desc++;
		}
			OilModel oilModel = new OilModel();
			List<OilModel> resultList = oilService.queryOil(oilModel);
			if(resultList.size()==0){
			int result = oilService.batchInsertOil(oilList);
			if( result >0){
				return ResponseModel.successActive();
			}else if( result ==0){
				return ResponseModel.errorActive("读取的数据格式不正确");
			}
			}
			int result1 = oilService.updateOil();
			int result = oilService.batchInsertOil(oilList);
			if(result!=0 && result1!=0){
				return ResponseModel.successActive();
			}else if(result==0 && result1==0){
				return ResponseModel.errorActive("读取的数据格式不正确");
			}
		}
		if(!"".equals(title1) && "销售之星".equals(title1)){
			for(int i = 2;i< sheet1.getRows();i++){
				Cell cell1 = sheet1.getCell(0, i);
				Cell cell2 = sheet1.getCell(1, i);
				Cell cell3 = sheet1.getCell(2, i);
				Short status = new Short("2");
				Cell[] cells = new Cell[]{cell1,cell2,cell3};
				TopModel topModel = saveTopModelByXls(cells, status);
				topList.add(topModel);
			}	
		}
		//获取第三个工作表对象
		Sheet sheet2 = workbook.getSheet(2);
		//作用于解析排名表
		if(sheet2.getRows()!=0 && sheet2.getColumns()!=0){
			String title2 = sheet2.getCell(0,0).getContents().trim();
			if(!"".equals(title2) && "明日之星".equals(title2)){
				for(int i = 2;i< sheet2.getRows();i++){
					Cell cell1 = sheet2.getCell(0, i);
					Cell cell2 = sheet2.getCell(1, i);
					Cell cell3 = sheet2.getCell(2, i);
					Cell[] cells = new Cell[]{cell1,cell2,cell3};
					Short status = new Short("3");
					TopModel topModel = saveTopModelByXls(cells, status);
					topList.add(topModel);
				}
			}
		}
		
		//获取第四个工作表对象
		Sheet sheet3 = workbook.getSheet(3);
		if(sheet3.getRows()!=0 && sheet3.getColumns()!=0){
			String title3 = sheet3.getCell(0,0).getContents().trim();
			if(!"".equals(title3) && "其他".equals(title3)){
				for(int i = 2;i< sheet3.getRows();i++){
					Cell cell1 = sheet3.getCell(0, i);
					Cell cell2 = sheet3.getCell(1, i);
					Cell cell3 = sheet3.getCell(2, i);
					Cell[] cells = new Cell[]{cell1,cell2,cell3};
					Short status = new Short("4");
					TopModel topModel = saveTopModelByXls(cells, status);
					topList.add(topModel);
				}	
			}
		}
		//查询是否是第一次插入数据
		List<TopModel> resultList = topService.queryAll();
		if(resultList.size()==0){
		int result = topService.batchInsertTop(topList);
		if( result >0){
			return ResponseModel.successActive();
		}else if( result ==0){
			return ResponseModel.errorActive("读取的数据格式不正确");
		}
		}
		int result = topService.updateTop();
		int result1 = topService.batchInsertTop(topList);
		if(result !=0 && result1 != 0){
			return ResponseModel.successActive();
		}else if(result ==0 && result1 ==0){
			return ResponseModel.errorActive("读取的数据格式不正确");
		}
		
		}catch(Exception  e ){
			e.printStackTrace();
		}finally{
			workbook.close();
		}
		return ResponseModel.errorActive("解读失败");
	}
	/**
	 * 读取xlsx
	 * @param topServiceI
	 * @param MultipartFile
	 * 
	 * @return resResult
	 */
	public ResResult readExcelXlsx(TopServiceI serviceI,MultipartFile file){
		XSSFWorkbook workbook = null;
		try{
			InputStream is = file.getInputStream();
			workbook = new XSSFWorkbook(is);
			int maxSheet = workbook.getNumberOfSheets();
			//用于排序
			int desc = 1;
			//油价excel
			if(maxSheet==2){
				List<OilModel> paramList = new ArrayList<OilModel>();
				for(int s=0 ; s<maxSheet;s++){
					XSSFSheet sheet = workbook.getSheetAt(s);
					XSSFRow row = sheet.getRow(0);
					 XSSFCell cell = row.getCell(0);
					if("国内油价".equals(cell.getStringCellValue().trim())){
						
						for(int r = 2;r<=sheet.getLastRowNum();r++){
							XSSFRow row1 = sheet.getRow(r);
							XSSFCell cell1 = row1.getCell(0);
							XSSFCell cell2 = row1.getCell(1);
							XSSFCell cell3 = row1.getCell(2);
							XSSFCell[] cells = new XSSFCell[]{cell1,cell2};
							if("普通燃油料".equals(cell3.getStringCellValue().trim())){
								Short priceType = new Short("1");
								Short categoryType = new Short("1");
								OilModel oilModel = saveOilModelXlsx(cells, priceType, categoryType,desc);
								paramList.add(oilModel);
							}else if("轻质燃油料".equals(cell3.getStringCellValue().trim())){
								Short priceType = new Short("1");
								Short categoryType = new Short("2");
								OilModel oilModel = saveOilModelXlsx(cells, priceType, categoryType,desc);
								paramList.add(oilModel);
							}
							desc++;
						}
				
					}else if("国际油价".equals(cell.getStringCellValue().trim())){
						for(int r = 2;r<=sheet.getLastRowNum();r++){
							XSSFRow row1 = sheet.getRow(r);
							XSSFCell cell1 = row1.getCell(0);
							XSSFCell cell2 = row1.getCell(1);
							XSSFCell cell3 = row1.getCell(2);
							XSSFCell[] cells = new XSSFCell[]{cell1,cell2};
							if("普通燃油料".equals(cell3.getStringCellValue().trim())){
								Short priceType = new Short("2");
								Short categoryType = new Short("1");
								OilModel oilModel = saveOilModelXlsx(cells, priceType, categoryType,desc);
								paramList.add(oilModel);
							}else if("轻质燃油料".equals(cell3.getStringCellValue().trim())){
								Short priceType = new Short("2");
								Short categoryType = new Short("2");
								OilModel oilModel = saveOilModelXlsx(cells, priceType, categoryType,desc);
								paramList.add(oilModel);
							}
							desc++;
						}
					}
				}
				OilModel oilModel = new OilModel();
				List<OilModel> resultList = oilService.queryOil(oilModel);
				if(resultList.size()==0){
				int result = oilService.batchInsertOil(paramList);
				if( result >0){
					return ResponseModel.successActive();
				}else if( result ==0){
					return ResponseModel.errorActive("读取的数据格式不正确");
				}
				}
				int result1 = oilService.updateOil();
				int result = oilService.batchInsertOil(paramList);
				if(result !=0 && result1 !=0){
					return ResponseModel.successActive();
				}else if(result ==0 && result1 ==0){
					return ResponseModel.errorActive("读取的数据格式不正确");
				}
			}else if(maxSheet ==4){
				List<TopModel> paramList = new ArrayList<TopModel>();
				for(int s=0 ; s<maxSheet;s++){
					XSSFSheet sheet = workbook.getSheetAt(s);
					XSSFRow row = sheet.getRow(0);
					 XSSFCell cell = row.getCell(0);
					 String title = cell.getStringCellValue();
					 if("服务之星".equals(title)){
						 for(int r=2;r<=sheet.getLastRowNum();r++){
							 XSSFRow row1 = sheet.getRow(r);
							 XSSFCell cell1 = row1.getCell(0);
							 XSSFCell cell2 = row1.getCell(1);
							 XSSFCell cell3 = row1.getCell(2);
							 XSSFCell[] cells = new XSSFCell[]{cell1,cell2,cell3};
							 Short status = new Short("1");
							 TopModel topModel = saveTopModelXlsx(cells, status);
							 paramList.add(topModel);
						 }
					 }else if("销售之星".equals(title)){
						 for(int r=2;r<=sheet.getLastRowNum();r++){
							 XSSFRow row1 = sheet.getRow(r);
							 XSSFCell cell1 = row1.getCell(0);
							 XSSFCell cell2 = row1.getCell(1);
							 XSSFCell cell3 = row1.getCell(2);
							 XSSFCell[] cells = new XSSFCell[]{cell1,cell2,cell3};
							 Short status = new Short("2");
							 TopModel topModel = saveTopModelXlsx(cells, status);
							 paramList.add(topModel);
						 }
					 }else if("明日之星".equals(title)){
						 for(int r=2;r<=sheet.getLastRowNum();r++){
							 XSSFRow row1 = sheet.getRow(r);
							 XSSFCell cell1 = row1.getCell(0);
							 XSSFCell cell2 = row1.getCell(1);
							 XSSFCell cell3 = row1.getCell(2);
							 XSSFCell[] cells = new XSSFCell[]{cell1,cell2,cell3};
							 Short status = new Short("3");
							 TopModel topModel = saveTopModelXlsx(cells, status);
							 paramList.add(topModel);
						 }
					 }else if("其他".equals(title)){
						 for(int r=2;r<=sheet.getLastRowNum();r++){
							 XSSFRow row1 = sheet.getRow(r);
							 XSSFCell cell1 = row1.getCell(0);
							 XSSFCell cell2 = row1.getCell(1);
							 XSSFCell cell3 = row1.getCell(2);
							 XSSFCell[] cells = new XSSFCell[]{cell1,cell2,cell3};
							 Short status = new Short("4");
							 TopModel topModel = saveTopModelXlsx(cells, status);
							 paramList.add(topModel);
						 }
					 }
				}
				//查询是否是第一次插入数据
				List<TopModel> resultList = topService.queryAll();
				if(resultList.size()==0){
					int result =topService.batchInsertTop(paramList);
					if(result !=0){
						return ResponseModel.successActive();
					}else{
						return ResponseModel.errorActive("读取的数据格式不正确");
					}
				}
				int result = topService.updateTop();
				int result1 =topService.batchInsertTop(paramList);
			 if(result!=0 && result1!=0){
				 return ResponseModel.successActive();
			 }else if(result==0 && result1==0){
				 return ResponseModel.errorActive("读取的数据格式不正确");
			 }
			}
			
		}catch(Exception e){
			
		}finally{
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ResponseModel.errorActive("解读失败");
	}
	
	/**
	 * 用于解析xls封装排名对象
	 * 
	 * @param cells
	 * @param status
	 * 
	 * @return
	 */
	public TopModel saveTopModelByXls(Cell[] cells,Short status){
		TopModel topModel = new TopModel();
		topModel.setEmployeeTop(Short.parseShort(cells[0].getContents()));
		topModel.setEmployeeHead(cells[1].getContents().trim());
		topModel.setEmployeeName(cells[2].getContents().trim());
		topModel.setTopStatus(new Short("1"));
		topModel.setTopId(ZshUtil.createUuid());
		topModel.setTopDate(DateUtil.getDate());
		topModel.setTopType(status);
		return topModel;
	}
	
	/**
	 * 用于解析xlsx格式封装油对象
	 * 
	 * @param cells
	 * @param status
	 * 
	 * @return
	 */
	public OilModel saveOilModelXlsx(XSSFCell[] objs,Short priceType,Short categoryType,int desc){
		OilModel oilModel = new OilModel();
		/*oilModel.setOilId(ZshUtil.createUuid());
		oilModel.setOilDate(DateUtil.getDate());
		oilModel.setOilPriceType(priceType);
		oilModel.setOilStatus(new Short("1"));
		oilModel.setDesc(desc);
		oilModel.setOilCategoryType(categoryType);
		oilModel.setOilCategory(objs[0].getStringCellValue().trim());
		String str = objs[1].toString();
		oilModel.setOilPrice(objs[1].toString().trim());
		if(str.contains(".")){
			StringBuffer sb = new StringBuffer(str);
			sb.delete(str.indexOf("."),str.length());
			oilModel.setOilPrice(sb.toString());
		}*/
		return oilModel;
	}
	
	/**
	 * 用于解析xls封装排名对象
	 * 
	 * @param cells
	 * @param status
	 * 
	 * @return
	 */
	public TopModel saveTopModelXlsx(XSSFCell[] cells,Short status){
		TopModel topModel = new TopModel();
		topModel.setEmployeeTop((short)(cells[0].getNumericCellValue()));
		topModel.setEmployeeHead(cells[1].getStringCellValue().trim());
		topModel.setEmployeeName(cells[2].getStringCellValue().trim());
		topModel.setTopStatus(new Short("1"));
		topModel.setTopId(ZshUtil.createUuid());
		topModel.setTopDate(DateUtil.getDate());
		topModel.setTopType(status);
		return topModel;
	}
} 
