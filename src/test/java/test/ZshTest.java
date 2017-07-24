package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sql.DataSource;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ZshTest {
	
	@Test
	public void test(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring-core.xml");
		System.out.println(ac.getBean("basicDataSource",DataSource.class ));
	}
	
	@Test
	public void test1(){
		try {
			Workbook workbook = Workbook.getWorkbook(new FileInputStream("C:\\Users\\Administrator\\Desktop\\2017.xls"));
			Sheet sheet = workbook.getSheet(0);
			if(sheet.getColumns()==0){
				System.out.println("122");
				return;
			}
			System.out.println(sheet.getColumns());
			System.out.println(sheet.getRows());
			
			for(int i=0;i<=sheet.getRows();i++){
					System.out.println(sheet.getCell(0,i).getContents());
					System.out.println("".equals(sheet.getCell(1,i).getContents()));
					
					
			}
			workbook.close();
		} catch (Exception e) {
		
		} 
	}
	
	@Test
	public void test2(){
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(new FileInputStream("C:\\Users\\Administrator\\Desktop\\2017��ڼ���.xlsx"));
			XSSFSheet xssfSheet = workbook.getSheetAt(0);
			XSSFRow xssfRow = xssfSheet.getRow(0);
			System.out.println(xssfRow.getLastCellNum());
			XSSFCell xssfCell = xssfRow.getCell(0);
			System.out.println(workbook.getNumberOfSheets());
			System.out.println(xssfSheet.getLastRowNum());
			System.out.println(xssfCell.getStringCellValue());
			for(int i = 0;i<=xssfSheet.getLastRowNum();i++){
				XSSFRow xssfRow1 = xssfSheet.getRow(i);
				XSSFCell xssfCell3 = xssfRow1.getCell(0);
				XSSFCell xssfCell2 = xssfRow1.getCell(1);
				System.out.println("--------");
				System.out.println(xssfCell3.getNumericCellValue());
				System.out.println(xssfCell2.getNumericCellValue());
			}
		} catch (Exception e) {
	
		}finally{
			try {
				workbook.close();
			} catch (IOException e) {
			}
		}
	}
}

