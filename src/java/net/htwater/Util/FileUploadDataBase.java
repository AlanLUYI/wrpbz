package net.htwater.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.htwater.Util.TideExcelIn;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

/**
 * @author luyi 
 * @createTime 2015年4月7日
 * @updateTime 2015年4月7日
 * @描述:基础数据文件上传并立即处理
 */
@WebServlet(description = "基础数据文件上传并立即处理", urlPatterns = { "/FileUploadDataBase" })
public class FileUploadDataBase extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(FileUploadDataBase.class.getName());
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		  response.setContentType("text/html");
	      response.setCharacterEncoding("utf-8");
	      
	      JSONObject json=new JSONObject();
	      Object info=null;
	      int status = 0;
	      String name = null;
	      String xmlName = request.getParameter("myxml");//或取xml文件名称
	      List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
	      
	      //下面这一行判断提交过来的表单是否为文件上传表单,如不是,后续就不再用文件上传来处理该表单
		  boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		  if (isMultipart) {
			   //下面这两行构造文件上传处理对象
			   FileItemFactory factory = new DiskFileItemFactory();
			   ServletFileUpload upload = new ServletFileUpload(factory);
	
			   @SuppressWarnings("rawtypes")
			   Iterator items;
			   try {
					   //下面这行解析表单提交的所有文件内容
				       items = upload.parseRequest(request).iterator();				       
				       while (items.hasNext()) {				       
					       FileItem item = (FileItem) items.next();					    		   					       			     					 
					       if (!item.isFormField()) {
			
					       //下面这段代码取出文件名,和服务器的存储路径,上传的文件将被存储在当前项目的file文件夹
					       name = item.getName();
					       if (name.equals("")) {
					    	   info = "你没有选择任何要上传的文件,请选择需要上传的数据文件！";
							   status=0;
					    	   return;
					       }
					       
					       String fileTyArr=name.substring(name.indexOf("."));
					       if(!fileTyArr.equals(".xls")){
					    	   info = "文件类型不是规定的  *.xls文件,请上传正确格式的数据文件！";
							   status=0;
					    	   return;
					       }	
					       String fileName = name.substring(
					       name.lastIndexOf("//") + 1, name.length());
					       String path = request.getRealPath("file")
					       + File.separatorChar + fileName;
					       // 上传文件
					       File uploadedFile = new File(path);
					    	   
					       item.write(uploadedFile);				       				    
					       				       
					       info = "上传成功！";
						   status=0;
					       //上传基础数据文件处理				    
					       try {
							String xmlpath=request.getRealPath("file")
									   + File.separatorChar + xmlName;
							TideExcelIn demo=
							   new TideExcelIn(new File(path), new File(xmlpath));						
							   					   					    
						       info = demo.ProcReport;
						       result=demo.getMyData();
							   status=demo.allProcLogs.length()>0?0:1;							   
							   log.info(name+"上传解析成功！");
							   
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								info = "解析入库失败！";
								status=0;
							}
					       				  
					     }
		
				    }
	
			   } catch (Exception e) {
				    log.error(e);
				    info="上传失败！";
				    status=0;
				    
			   }finally{
				    json.put("info", info);
					json.put("status", status);
					json.put("result", result);
					response.getWriter().print(json.toString());					
				}
		  }

	}

}
