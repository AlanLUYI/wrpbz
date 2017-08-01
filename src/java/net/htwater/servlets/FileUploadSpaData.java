package net.htwater.servlets;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.htwater.Util.CommonMethod;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

/**
 * @author yangshengfei
 * @createTime 2015年1月27日
 * @updateTime 2015年5月17日
 * @描述:空间数据文件上传
 */
@WebServlet(description = "空间数据文件上传", urlPatterns = { "/FileUploadSpaData" })
public class FileUploadSpaData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(FileUploadSpaData.class.getName());   
	private String uploadPath;
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		ServletContext context = getServletContext();
		String rootPath =  context.getRealPath("/");
		
		uploadPath = new File(rootPath).getParent() + File.separator + "shanhong_file";
		if(!new File(uploadPath).exists()){
			new File(uploadPath).mkdirs();
		}
		
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		 response.setContentType("text/html");
	     response.setCharacterEncoding("utf-8");
	     JSONObject json=new JSONObject();
	     HttpSession session=request.getSession();
	     String userName=(String) session.getAttribute("token");//当前用户
	     String name = null;
		//下面这一行判断提交过来的表单是否为文件上传表单,如不是,后续   就不再用文件上传来处理该表单
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
								   json.put("info", "你没有选择任何要上传的空间数据,请选择需要上传的数据文件！");
							       json.put("status",0);
						    	   return;
						       }
						       String fileName = name.substring(
						       name.lastIndexOf("//") + 1, name.length());
						       String path = uploadPath + File.separatorChar + fileName;
						       // 上传文件
						       File uploadedFile = new File(path);
						       item.write(uploadedFile);				       				    
						       				    
						       json.put("info", "空间数据文件上传成功！");
						       json.put("status", 1);
						       
						       log.info("[空间数据]"+name+"上传成功！");
						       //存储处理信息
						       new CommonMethod().ProcLogs(userName, session.getAttribute("region") != null?session.getAttribute("region").toString():"","空间数据", fileName, "正在处理", null);
						       
					     }
		
				    }
	
			   } catch (Exception e) {
				    log.error(e);
				    json.put("info", "空间数据文件上传失败！");
				    json.put("status", 0);	
			   }finally{
				   response.getWriter().print(json.toString());				
			   }
		  }

	}

}
