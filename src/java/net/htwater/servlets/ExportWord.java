//山洪，防汛业务，值班安排。   word导出，方浩杰
package net.htwater.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.miao.framework.util.StringUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException; 
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.htwater.hos.service.FxywService;
import net.htwater.hos.service.impl.FxywImpl;



import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;



public class ExportWord extends HttpServlet {
	/**
	 * Constructor of the object.
	 */
	public ExportWord() {
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
    	String id = request.getParameter("id");
    	
        FxywService service = new FxywImpl();
        Map<String , Object> map = service.queryFORreportbyid(id);
        String head = "<head><style>"+
	 "@font-face"+
		"{font-family:宋体;"+
		"panose-1:2 1 6 0 3 1 1 1 1 1;"+
		"mso-font-alt:SimSun;"+
		"mso-font-charset:134;"+
		"mso-generic-font-family:auto;"+
		"mso-font-pitch:variable;"+
		"mso-font-signature:3 680460288 22 0 262145 0;}"+
	"@font-face"+
		"{font-family:'Cambria Math';"+
		"panose-1:2 4 5 3 5 4 6 3 2 4;"+
		"mso-font-charset:1;"+
		"mso-generic-font-family:roman;"+
		"mso-font-format:other;"+
		"mso-font-pitch:variable;"+
		"mso-font-signature:0 0 0 0 0 0;}"+
	"@font-face"+
		"{font-family:'宋体';"+
		"panose-1:2 1 6 0 3 1 1 1 1 1;"+
		"mso-font-charset:134;"+
		"mso-generic-font-family:auto;"+
		"mso-font-pitch:variable;"+
		"mso-font-signature:3 680460288 22 0 262145 0;}"+
	 "p.MsoNormal, li.MsoNormal, div.MsoNormal"+
		"{mso-style-unhide:no;"+
		"mso-style-qformat:yes;"+
		"mso-style-parent:'';"+
		"margin:0cm;"+
		"margin-bottom:.0001pt;"+
		"text-align:justify;"+
		"text-justify:inter-ideograph;"+
		"mso-pagination:none;"+
		"font-size:10.5pt;"+
		"mso-bidi-font-size:12.0pt;"+
		"font-family:'Times New Roman','serif';"+
		"mso-fareast-font-family:宋体;"+
		"mso-font-kerning:1.0pt;}"+
	"span.GramE"+
		"{mso-style-name:'';"+
		"mso-gram-e:yes;}"+
	".MsoChpDefault"+
		"{mso-style-type:export-only;"+
		"mso-default-props:yes;"+
		"mso-fareast-font-family:宋体;}"+
	"@page"+
		"{mso-page-border-surround-header:no;"+
		 "mso-page-border-surround-footer:no;}"+
	 "@page WordSection1"+
		"{"+
		"layout-grid:15.6pt;}"+
	"div.WordSection1"+
		"{page:WordSection1;}"+
	"</style>"+
	"<!--[if gte mso 10]>"+
	"<style>"+
	"table.MsoNormalTable"+
		"{mso-style-name:普通表格;"+
		"mso-tstyle-rowband-size:0;"+
		"mso-tstyle-colband-size:0;"+
		"mso-style-noshow:yes;"+
		"mso-style-unhide:no;"+
		"mso-style-parent:'';"+
		"mso-padding-alt:0cm 5.4pt 0cm 5.4pt;"+
		"mso-para-margin:0cm;"+
		"mso-para-margin-bottom:.0001pt;"+
		"mso-pagination:widow-orphan;"+
		"font-size:10.0pt;"+
		"font-family:'Times New Roman','serif';}"+
	"</style></head>";
        
          
        String html = "<html xmlns:v='urn:schemas-microsoft-com:vml' xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:w='urn:schemas-microsoft-com:office:word' xmlns:m='http://schemas.microsoft.com/office/2004/12/omml' xmlns='http://www.w3.org/TR/REC-html40'>"+head+"<body lang=ZH-CN style='tab-interval:21.0pt;text-justify-trim:punctuation'>"
        		+ "<table style='width: 100%;' cellpadding='0' cellspacing='0' border='0'><tr><td style='color: red; font-size: 18px; font-weight: bold;'>宁波市人民政府</td><td style='text-align:right;padding-right:100px;'>第"+map.get("count").toString()+"期</td></tr><tr>  <td style='color: red; font-size: 18px; font-weight: bold;'>防汛防台抗旱指挥部办公室</td><td style='text-align:right;padding-right:80px;'>"+map.get("time").toString()+"</td></tr><tr><td colspan='2'><div style='border-top-color: red; border-top-width: 2px; border-top-style: solid; '>&nbsp;</div><div class='WordSection1'>"
        		+ map.get("report").toString()
        		+ "</div></td></tr><tr> <td colspan='2' style='border-bottom: 2px solid black; border-top: 2px solid black;'><table style='width: 100%;' cellpadding='0' cellspacing='0' border='0'><tr><td width='20px'>报:</td><td></asp:TextBox></td></tr><tr><td width='20px'>送:</td><td></td></tr> <tr><td width='20px'>发:</td><td></td> </tr></table></td></tr><tr><td colspan='2'>    <table style='width: 100%; margin-bottom: 20px;' cellpadding='0' cellspacing='0' border='0'><tr><td>签发:</td><td>核搞:</td><td>拟稿:</td></tr></table></td></tr></table></body></html>"
        		+ "</body></html>";
        String content = html;
        byte b[] = content.getBytes();   
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        POIFSFileSystem poifs = new POIFSFileSystem();    
        DirectoryEntry directory = poifs.getRoot(); 
        DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
        String name="宁波市防办"+map.get("time").toString().substring(0, 3)+"第"+map.get("count").toString()+"期山洪灾害快报";  
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
