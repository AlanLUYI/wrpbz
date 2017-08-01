//获取扫描件
package net.htwater.servlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.miao.framework.helper.UpdownloadHelper;


public class GetPDFServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GetPDFServlet() {
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
		 doPost(request, response);
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
			
//		Properties properties = new Properties();
//	    InputStream in2 = UpdownloadHelper.class.getResourceAsStream(
//	      "/updownload.properties");
//	    try {
//	      properties.load(in2);
//	      cn.miao.framework.util.Cache.uploadRoot = properties.get("upload_root_dir").toString();
//	      cn.miao.framework.util.Cache.downloadRoot = properties.get("download_root_dir").toString();
//	    } catch (IOException e) {
//	      e.printStackTrace();
//	    }
		
	    String _downloadRoot = cn.miao.framework.util.Cache.downloadRoot;
	    
		//************************heliang
 		String path = request.getParameter("path");
		  String requestfilename =  request.getParameter("filename");
		  String fileType=request.getParameter("type");
		  String fileName = path+"/"+requestfilename;
		  FileInputStream in = new FileInputStream(fileName);
		  ServletOutputStream out = response.getOutputStream();
		  byte data[]=new byte[1024*3];
		  if (fileType!=null&&fileType.equals("image/jpeg")) {
			  response.setContentType("image/jpeg");
			  //data=new byte[1024*3];
		}else {
			  response.setContentType("application/pdf");
			  //data=new byte[1024];
		}
		  int len;
		  while((len=in.read(data)) != -1){
		   out.write(data,0,len);
		  }
		  out.flush();
		  in.close();
		  out.close();
		  
		  

/*		  String path = request.getParameter("path");
		  String requestfilename =  request.getParameter("filename");
		  
		  String fileName = "D:\\htosDocument\\"+path+"\\"+requestfilename;
		  FileInputStream in = new FileInputStream(fileName);
		  ServletOutputStream out = response.getOutputStream();
		  
		  response.setContentType("application/pdf");
		  byte data[]=new byte[1024];
		  int len;
		  while((len=in.read(data)) != -1){
		   out.write(data,0,len);
		  }
		  out.flush();
		  in.close();
		  out.close();*/

	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
