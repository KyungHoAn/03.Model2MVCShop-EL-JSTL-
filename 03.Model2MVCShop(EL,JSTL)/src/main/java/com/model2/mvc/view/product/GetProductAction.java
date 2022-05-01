package com.model2.mvc.view.product;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
public class GetProductAction extends Action{
	
	public String execute(	HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));

		String no = request.getParameter("prodNo");
		
		
		ProductService service = new ProductServiceImpl();
		Product vo = service.getProduct(prodNo);

		System.out.println(no);
//		Cookie c = new Cookie("history",no);
//		response.addCookie(c);
//		System.out.println("cookie에 번호저장 완료");
//		Cookie[] cookies = request.getCookies();		
		
		String history;
	      
		Cookie cookie = null;
	      
		Cookie[] cookies = request.getCookies();
		if (cookies!=null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if (cookie.getName().equals("history")) {
            	
					history = URLDecoder.decode(cookie.getValue(),"euc-kr");
					history +=","+vo.getProdNo();
					System.out.println(history);
					cookie = new Cookie("history",URLEncoder.encode(history,"euc-kr"));
				}else {
					cookie = new Cookie("history", Integer.toString(prodNo));
				}
				
			}
		}
		cookie.setMaxAge(-1);
		response.addCookie(cookie);
		
		request.setAttribute("product", vo);
		return "forward:/product/readProduct.jsp";
	}

}
