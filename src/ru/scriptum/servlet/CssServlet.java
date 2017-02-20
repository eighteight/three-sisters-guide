package ru.scriptum.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.scriptum.view.handler.BeanNames;
import ru.scriptum.view.handler.UserBean;
import ru.scriptum.view.util.ClientResolution;
import ru.scriptum.view.util.FacesUtils;

public class CssServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setHeader("Content-Type", "text/css");
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute(BeanNames.USER_BEAN);
		if (userBean == null) return;
		ClientResolution res = userBean.getClientResolution();//FacesUtils.getClientResolution(request);
		int matrixWidth = res.width-50;
		StringBuilder responseString = new StringBuilder();

		responseString.append(".globalmatrix {width:"+(res.width*2+250)+"px;}");
		responseString.append("#seminarTitles {width:"+(res.width*2+100)+"px;}");
		responseString.append(".graphTitle {width:"+matrixWidth+"px;}");
		responseString.append(".matrixTitle {width:"+matrixWidth+"px;}");
		
		responseString.append("#graphContainer {width:"+(res.width*2+100)+"px;}");
		responseString.append("#graph {width:"+matrixWidth+"px;}");
		responseString.append("#matrix {width:"+matrixWidth+"px;}");
		responseString.append("#matrix {left:"+matrixWidth+"px;}");

		responseString.append("#sliders {width:"+(res.width*2)+"px;}");
		
		responseString.append(".graphSlider {width:"+((int)(matrixWidth*0.5 - 100))+"px; position: relative; float: left; margin: 0px 10px 0px 0px}");
		
		response.getWriter().write(responseString.toString());
		
	}

}
