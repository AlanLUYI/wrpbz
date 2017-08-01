//山洪，防汛业务，值班安排，excel导出    方浩杰
package net.htwater.servlets;

import java.io.FileNotFoundException;
import java.io.FileOutputStream; 
import java.io.IOException; 
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.print.attribute.Size2DSyntax;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.htwater.hos.service.FxywService;
import net.htwater.hos.service.impl.FxywImpl;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook; 
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell; 
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row; 
import org.apache.poi.ss.usermodel.Sheet; 
import org.apache.poi.ss.usermodel.Workbook; 

public class ExportExcel { 
    //使用POI创建excel工作簿 
    public static void createWorkBook(HttpServletRequest request,
			HttpServletResponse response, PrintWriter out,String param) throws IOException {  	
        //创建excel工作簿 
    	response.setContentType("text/html");
    	String year = request.getParameter("year");
    	String month = request.getParameter("month");
    	HSSFWorkbook wb = new HSSFWorkbook(); 
        //创建第一个sheet（页），命名为 new sheet 
        Sheet sheet = wb.createSheet("new sheet"); 
        //创建一个文件 命名为workbook.xls 
        FxywService service = new FxywImpl();
        List<Map<String, Object>> list = service.queryFORyearmonth(year, month);
        //创建样式      
        /*------------------style-----------------*/
        CellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = (HSSFFont) wb.createFont();  
        font.setColor(HSSFColor.RED.index);  
        font.setFontName("Arial Unicode MS");
        font.setFontHeightInPoints((short) 18);          
        style.setFont(font); 
        /*--------------style1-----------------*/
        CellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style1.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        HSSFFont font1 = (HSSFFont) wb.createFont();  
        font1.setColor(HSSFColor.BLACK.index);        
        font1.setFontHeightInPoints((short) 10);
        font1.setFontName("Arial Unicode MS");
        style1.setFont(font1); 
        /*--------------style2-----------------*/
        CellStyle style2 = wb.createCellStyle();
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style1.setFont(font1); 
        /*---------------Row cell--------------*/
        Row[] ROWarr = new Row[19];
        Cell[][] CELLarr = new Cell[19][15];
        for(int i=0;i<=18;i++){
        	ROWarr[i] = sheet.createRow((short) i);   
        	for(int j=0;j<=14;j++){
        		CELLarr[i][j] = ROWarr[i].createCell(j);   
        		CELLarr[i][j].setCellStyle(style1);
        	}
        }
        /*----------------设置样式----------------*/
        for(int i =1;i<=18;i++){
        	CELLarr[i][7].setCellStyle(style2);
        }
        for(int i =0;i<=19;i++){
        	if(i==0 || i==1 ||i==8||i==9){
        		sheet.setColumnWidth(i, 1500);
        	}else if(i==7){
        		sheet.setColumnWidth(i, 800);
        	}else{
        		sheet.setColumnWidth(i, 2400);
        	}
        }      
      /* -----------------------第一行------------------*/
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 14));
        CELLarr[0][0].setCellValue(year+"年"+month+"月份值班安排表");
        CELLarr[0][0].setCellStyle(style);
/*         -----------------------第二行------------------  */
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
        CELLarr[1][0].setCellValue("日期");
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 1));
        CELLarr[1][1].setCellValue("星期");   
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 6));
        CELLarr[1][2].setCellValue("姓名");
        CELLarr[2][2].setCellValue("领导");
        CELLarr[2][3].setCellValue("组长");
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 4, 5));
        CELLarr[2][4].setCellValue("组员");
        CELLarr[2][6].setCellValue("值班");
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 8, 8));
        CELLarr[1][8].setCellValue("日期");
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 9, 9));
        CELLarr[1][9].setCellValue("星期");   
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 10, 14));
        CELLarr[1][10].setCellValue("姓名");
        CELLarr[2][10].setCellValue("领导");
        CELLarr[2][11].setCellValue("组长");
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 12, 13));
        CELLarr[2][12].setCellValue("组员");
        CELLarr[2][14].setCellValue("值班");
        int length = list.size();
        /*--------------list数据加载--------------*/                    
       for(int i=0;i<=15;i++){
        	/*--------------左侧--------------*/
        	if(i<15){
        		if(list.get(i)!=null){
        			CELLarr[i+3][0].setCellValue(i+1);
                	CELLarr[i+3][1].setCellValue(list.get(i).get("week").toString());
                	CELLarr[i+3][2].setCellValue(list.get(i).get("leader_name").toString());
                	CELLarr[i+3][3].setCellValue(list.get(i).get("master_name").toString());
                	//输出组员     	
                	String[] strarray=list.get(i).get("member_name").toString().split(";");
                    for(int j=0;j<strarray.length;j++){
                    	CELLarr[i+3][j+4].setCellValue(strarray[j]);
                    }
                    CELLarr[i+3][6].setCellValue(list.get(i).get("dirver_name").toString());
        		}
        	}                
        	/*       ---------------右侧--------------*/
        	if( i+15 < length){
            	if(list.get(i+15)!=null){
            		CELLarr[i+3][8].setCellValue(i+16);
                    CELLarr[i+3][9].setCellValue(list.get(i+15).get("week").toString());
                	CELLarr[i+3][10].setCellValue(list.get(i+15).get("leader_name").toString());
                	CELLarr[i+3][11].setCellValue(list.get(i+15).get("master_name").toString());
                	String[] _strarray=list.get(i+15).get("member_name").toString().split(";");
                    for(int j=0;j<_strarray.length;j++){
                    	CELLarr[i+3][j+12].setCellValue(_strarray[j]);
                    }
                    CELLarr[i+3][14].setCellValue(list.get(i+15).get("dirver_name").toString());           	
            	}           		
        	}
   
        }  
        //打印
        export(year+"年"+month+"月份值班安排表", response, wb);
    }
    
	private static void export(String prefix,
			HttpServletResponse response, Workbook wb) {
		String fileName = prefix + "_" + ".xls";
		// 导出文件到历史文件夹
		exportFile(fileName,wb);
		// 下载
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes(), "iso-8859-1"));
			OutputStream outFile = response.getOutputStream();
			wb.write(outFile);
			outFile.flush();
			outFile.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出excel到指定目录
	 * 
	 * @param filePath
	 * @param wb
	 * @return void
	 * @since v 1.0
	 */
	public static void exportFile(String filePath, Workbook wb) {
		FileOutputStream out;
		try {
			out = new FileOutputStream(filePath);
			wb.write(out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    

} 