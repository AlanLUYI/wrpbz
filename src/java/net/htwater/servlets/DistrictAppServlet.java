package net.htwater.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.htwater.hos.service.FloodService;
import net.htwater.hos.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import cn.miao.framework.factory.ServiceFactory;
import cn.miao.framework.util.JsonDateProcessor;

/**
 * Servlet implementation class TestServlet
 */
public class DistrictAppServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DistrictAppServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter("nbfb_token");
		FloodService service = (FloodService) ServiceFactory.getService("flood");
		Map<String,Object> map = service.checkDistricApp(token);
		Map<String,Object> result = new HashMap<String,Object>();
		if(map != null){
			result.put("account", "NBFB_TOP_USER");
			result.put("realname", map.get("name"));
			result.put("success", true);
			result.put("message", "验证通过");
		}else{
			result.put("account", null);
			result.put("realname", null);
			result.put("success", false);
			result.put("message", "验证通过");
		}
		response.getWriter().print(parseJSON(result));
		return;
	}
	/**
	 * 转对象Json
	 * 
	 * @param object
	 * @return String
	 * @since v 1.0
	 */
	public String parseJSON(Object object) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateProcessor());
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class,
				new JsonDateProcessor());
		jsonConfig.registerJsonValueProcessor(Timestamp.class,
				new JsonDateProcessor());
		if (object instanceof Map<?, ?>) {
			return JSONObject.fromObject(object, jsonConfig).toString();
		} else if (object instanceof List<?>) {
			return JSONArray.fromObject(object, jsonConfig).toString();
		} else {
			return JSONObject.fromObject(object, jsonConfig).toString();
		}
	}
}
