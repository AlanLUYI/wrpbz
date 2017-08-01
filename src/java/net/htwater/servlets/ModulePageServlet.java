package net.htwater.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.htwater.Util.PropertiesHelper;
import net.htwater.hos.service.UserService;
import cn.miao.framework.factory.ServiceFactory;

/**
 * Servlet implementation class TestServlet
 */
public class ModulePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModulePageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	PropertiesHelper propHelper = new PropertiesHelper("/htpms.properties");

	String apiUrl = propHelper.getPropertyValue("htpms.url");
	String key = propHelper.getPropertyValue("htpms.key");
	String secret = propHelper.getPropertyValue("htpms.secret");
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String appcode = request.getParameter("appcode");
		String module = request.getParameter("module");
		String account = request.getParameter("user");
		String region = request.getParameter("region");
		
		String token = "no";
		String host = propHelper.getPropertyValue("module_out_link_direct_url");
		response.setContentType("text/plain; charset=utf-8");
		//检查appcode\module有没有在sys_auth_modules表里
		UserService service = (UserService) ServiceFactory.getService("user");
		Boolean b = service.checkModuleAuth(appcode, module);
		//如果在表示可以授权
		//如果在：插入一条记录到sys_auth_module_log表里
		if(b){
			token = service.createModuleToken(appcode,module, account, region);
		}
		//返回网址加token
		//如果不在表示不能授权
		response.getWriter().print(host+token);
		return;
	}

}
