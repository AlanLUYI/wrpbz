//山洪，防汛业务，值班安排，excel导入，方浩杰
package net.htwater.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.htwater.hos.service.impl.FxywImpl;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.record.formula.functions.Days360;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ImportExcelServlet extends HttpServlet {
	
	private String uploadPath; // 上传文件的目录
	
	/**
	 * Constructor of the object.
	 */
	public ImportExcelServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {      
		try {
			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);
			@SuppressWarnings("unchecked")
			List<FileItem> items = upload.parseRequest(request);// 得到所有的文件
			Iterator<FileItem> i = items.iterator();
			while (i.hasNext()) {
				System.out.println("开始读取文件");
				FileItem fi = (FileItem) i.next();
				String fileName = fi.getName();
				if (fileName != null) {
					File savedFile = new File("D://shanhong_fxyw/file/", "excel.xls");
					fi.write(savedFile);
					if (fileName.indexOf('\\') > -1) {
						fileName = fileName.substring(fileName
								.lastIndexOf('\\') + 1);
					}
					InputStream inputStream = new FileInputStream("D://shanhong_fxyw/file/"
							+ "excel.xls");
					HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
					/*-------------获取excel信息------------*/
					List<Map<String,Object>> list = this.spreadSheetListT(workbook,null);
					System.out.println(list);
					/*-------------执行更新或者保存工作-------------*/
					new FxywImpl().importTargetExcel(list);
					inputStream.close();
					savedFile.delete();
				}
			}			
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("success", "success");
			String result =JSONObject.fromObject(map).toString();		
			out.print(result);
			out.flush();
			out.close();
		} catch (Exception e) {
			// 可以跳转出错页面
			e.printStackTrace();
		}
	}

	public static List<Map<String, Object>> spreadSheetListT(HSSFWorkbook wb,String sheetName) {
		return spreadSheetListTarget(wb, sheetName);
	}	
	public static List<Map<String, Object>> spreadSheetListTarget(HSSFWorkbook wb, String sheetName) {
		HSSFSheet sheet = wb.getSheet(sheetName);
		if (null == sheetName) {
			sheet = wb.getSheetAt(0);
		}
		Iterator<Row> rowIterator = sheet.rowIterator();
		rowIterator.next(); // 跳过第一行
		rowIterator.next(); // 跳过第二行
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
		String last_dirver = "";
		while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Map<String, Object> objs = new HashMap<String, Object>();
				//objs.put("devicecd", getCellStringValue(row.getCell(0)));
				/*-----------------------获取excel信息-------------------------*/
				if( row.getCell(0) == null || row.getCell(0).getCellType() == HSSFCell.CELL_TYPE_STRING ){
					break;
				}
				String time = getNumericCellValue(row.getCell(0));	
				java.util.Date date;
				try {
					java.util.Date d2 = sdf.parse("2015-01-01");
					date = sdf.parse( time );
					long diff = date.getTime() - d2.getTime();    
				    long days = diff / (1000 * 60 * 60 * 24);
					objs.put("id", days);
					objs.put("leader",getCellStringValue(row.getCell(2)));
					objs.put("master",getCellStringValue(row.getCell(3)));
					objs.put("member1",getCellStringValue(row.getCell(4)));
					objs.put("member2",getCellStringValue(row.getCell(5)));
					objs.put("member3","");
					if( getCellStringValue(row.getCell(6)).equals("") ){
						objs.put("dirver",last_dirver);
					}else{
						objs.put("dirver",getCellStringValue(row.getCell(6)));
						last_dirver = getCellStringValue(row.getCell(6));
					}
										
					list.add(objs);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	 			

		}
    	return list;
	}	
	private static String getCellStringValue(Cell cell) {
		if(cell==null){return "";}cell.getCellType();
		if (null == cell.getRichStringCellValue()) {
			return "";
		} else
			return cell.getRichStringCellValue().getString();
	}
	private static String getNumericCellValue(Cell cell) {
		if(cell==null){return "";}cell.getCellType();
		if (null == cell.getDateCellValue()) {
			return "";
		} else{
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			return sdf.format(cell.getDateCellValue());
		}
	}
	
	public void init() throws ServletException {
		String _downloadRoot = cn.miao.framework.util.Cache.downloadRoot;	    
		uploadPath = _downloadRoot + "/tempmcg/";
		File uploadFile = new File(uploadPath);
		if (!uploadFile.exists()) {
			uploadFile.mkdirs();
		}
	}
	
	
}
