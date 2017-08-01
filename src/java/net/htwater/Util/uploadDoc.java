package net.htwater.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.htwater.mydemo.action.publicAction;
import net.sf.json.JSONObject;

import org.apache.commons.collections.map.StaticBucketMap;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import java.io.Console;
import java.io.FileOutputStream;
 
import java.io.InputStream;

import javax.servlet.http.HttpSession;

/**
 * @author
 * @createTime 2015年4月7日
 * @updateTime 2015年4月7日
 * @描述:基础数据文件上传并立即处理
 */
@WebServlet(description = "基础数据文件上传并立即处理", urlPatterns = { "/uploadDoc" })
public class uploadDoc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(uploadDoc.class.getName());
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		  
		  response.setContentType("text/html");
	      response.setCharacterEncoding("utf-8");
	      HttpSession session=request.getSession();
	      session.setAttribute("progressBar",0);      //定义指定上传进度的Session变量
	      String error = "";
	      
	      JSONObject json=new JSONObject();                                                         
	      Object info=null;
	      String filepath = "";
	      String filename="";
	      //Map<String, Object> result = new HashMap<String, Object>();
	      String result = null;
	      //下面这一行判断提交过来的表单是否为文件上传表单,如不是,后续就不再用文件上传来处理该表单
		  boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		  if (isMultipart) {
			   //下面这两行构造文件上传处理对象
			   FileItemFactory factory = new DiskFileItemFactory();
			   ServletFileUpload upload = new ServletFileUpload(factory);
	
			   try {
			        @SuppressWarnings("rawtypes")
					List items = upload.parseRequest(request);// 解析上传请求
			        @SuppressWarnings("rawtypes")
					Iterator itr = items.iterator();// 枚举方法
			        while (itr.hasNext()) {
			            FileItem item = (FileItem) itr.next();  //获取FileItem对象
			            if (!item.isFormField()) {// 判断是否为文件域
			                if (item.getName() != null && !item.getName().equals("")) {// 判断是否选择了文件
			                    long upFileSize=item.getSize();     //上传文件的大小
			                    filename=item.getName();     //获取文件名
			              //      filePathName=filepath+filename;
			                    //System.out.println("上传文件的大小:" + item.getSize());
//			                    if(upFileSize>maxSize){
//			                        error="您上传的文件太大，请选择不超过50M的文件";
//			                        break;
//			                    }
			                    // 此时文件暂存在服务器的内存中
			                    File tempFile = new File(filename);// 构造临时对象
			                    String Path = this.getServletContext().getRealPath("");
			                    String uploadPath=Path.substring(0, Path.lastIndexOf("\\"))+"\\ROOT\\qgjFiles\\";
			                    System.out.print(uploadPath);
			                    File file = new File(uploadPath,tempFile.getName());   // 获取根目录对应的真实物理路径
			                    filepath=uploadPath+filename;  //记录返回文件存储路径
			                    
			                    InputStream is=item.getInputStream();
			                    int buffer=1024;     //定义缓冲区的大小
			                    int length=0;
			                    byte[] b=new byte[buffer];
			                    double percent=0;
			                    FileOutputStream fos=new FileOutputStream(file);
			                    while((length=is.read(b))!=-1){
			                        percent+=length/(double)upFileSize*100D;        //计算上传文件的百分比
			                        fos.write(b,0,length);                      //向文件输出流写读取的数据
			                        session.setAttribute("progressBar",Math.round(percent));    //将上传百分比保存到Session中
			                    }
			                    result="success";
			                    System.out.println(result);
			                    fos.close();
			                    Thread.sleep(1000);     //线程休眠1秒
			                } else {
			                    error="没有选择上传文件！";
			                    info = "没有选择上传文件！";
			                }
			            }
			        }
			    } catch (Exception e) {
			        e.printStackTrace();
			        error = "上传文件出现错误：" + e.getMessage();
			    }finally{
				    json.put("info", info);
					json.put("result", result);
					json.put("filename", filename);
					json.put("filepath", filepath);
					response.getWriter().print(json.toString());					
				}
			
			   
		  }

	}

}
