package net.htwater.hos.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.htwater.hos.service.UserService;
import cn.miao.framework.factory.ServiceFactory;

/**
 * Servlet Filter implementation class DataFileUploadFilter
 */
@WebFilter("/DataFileUploadFilter")
public class ModulePageFilter implements Filter {

    /**
     * Default constructor. 
     */
    public ModulePageFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession();
		String token = request.getParameter("token");
		if(null == token || token.equals("no")){
			response.sendRedirect(request.getContextPath() + "/nomodule.html");
		}else{
			UserService service = (UserService) ServiceFactory.getService("user");
			Map<String,Object> map = service.checkModuleToken(token);
			if(map == null){
				response.sendRedirect(request.getContextPath() + "/nomodule.html");
			}else{
				String _token="THIRD-"+
						map.get("appcode").toString()+"-"+
						map.get("session_userid").toString();
				session.setAttribute("token", _token);
				session.setAttribute("region", map.get("session_region").toString());
				if(map.get("module").toString().indexOf(".html")>0){
					response.sendRedirect(request.getContextPath() + "/"+map.get("module").toString());
				}else{
					response.sendRedirect(request.getContextPath() + "/module.html?m="+map.get("module").toString());
				}
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
