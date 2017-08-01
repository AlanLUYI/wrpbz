//山洪，防汛业务，水雨情简报。   excel导出，方浩杰
package net.htwater.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.miao.framework.entity.Responser;
import cn.miao.framework.util.StringUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException; 
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.htwater.hos.action.SyqAction;
import net.htwater.hos.service.FxywService;
import net.htwater.hos.service.SyqService;
import net.htwater.hos.service.impl.FxywImpl;



import net.htwater.hos.service.impl.SyqServiceImpl;

import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;



public class ExportSYQ_report extends HttpServlet {
	/**
	 * Constructor of the object.
	 */
	public ExportSYQ_report() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doDelete(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPut(req, resp);
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
		// response.getWriter().write("doGet is not supported");
		// response.getWriter().close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 解析URL
		//System.out.println(request.getQueryString());
		response.setContentType("text/html");
    	
       SyqAction action = new SyqAction();
       Responser res = action.getReport();
       String htmlString = res.getRtString();
       htmlString =  htmlString.replaceAll("border:1px solid black;", "").replaceAll("font-size:28px", "font-size:20px");
        String a = (htmlString.split("<body style=\"background:#808080;\">")[1]).split("</body>")[0];
           
        String head = "<head><style>"+
		"<style>"+
		".tbtitle {font-size: 23px;}"+
	".tdbg {background: #E7E4E4;}"+
	"table {background: #000000;text-align:center;}"+
	"td {background: #FFFFFF;padding: 2px;font-size: 20px;width: 120px;}"+
	".textbody {width:100%;padding:6px;}"+	
       
	
	"</style></head>";
        
          
        String html = "<html xmlns:v='urn:schemas-microsoft-com:vml' xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:w='urn:schemas-microsoft-com:office:word' xmlns:m='http://schemas.microsoft.com/office/2004/12/omml' xmlns='http://www.w3.org/TR/REC-html40'>"+head+"<body style='backgroung:white' lang=ZH-CN style='tab-interval:21.0pt;text-justify-trim:punctuation'><div style='background:white'>"
        		+ a
        		+ "</div></body></html>";
        String content = html;
        byte b[] = content.getBytes();   
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        POIFSFileSystem poifs = new POIFSFileSystem();    
        DirectoryEntry directory = poifs.getRoot(); 
        DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
        String name="水雨情简报";  
        response.reset();  
        response.setHeader("Content-Disposition",  
                "attachment;filename=" +  
                new String( (name + ".doc").getBytes(),  
                           "iso-8859-1")); 
        response.setContentType("application/msword");  	
        OutputStream ostream = response.getOutputStream();   
        poifs.writeFilesystem(ostream);    
        bais.close();    
        ostream.close();   

	}
}
