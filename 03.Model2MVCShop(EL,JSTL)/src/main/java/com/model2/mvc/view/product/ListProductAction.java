package com.model2.mvc.view.product;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class ListProductAction extends Action{
	public String execute(	HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Search search = new Search();
		
		//page추가로 추가되는 코드
		int currentPage=1;

		if(request.getParameter("currentPage") != null){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		// web.xml  meta-data 로 부터 상수 추출 
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		
//		int page =1;
//		if(request.getParameter("page")!=null)
//			page = Integer.parseInt(request.getParameter("page"));
		
//		search.setCurrentPage(page);
//		search.setSearchCondition(request.getParameter("searchCondition"));
//		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
//		String pageunit = getServletContext().getInitParameter("pageSize");
//		search.setPageSize(Integer.parseInt(pageunit));

		
//		ProductService service = new ProductServiceImpl();
//		HashMap<String,Object> map = service.getProductList(search);
		
		
		ProductService productService = new ProductServiceImpl();
		Map<String, Object> map = productService.getProductList(search);	
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListProductAction ::"+resultPage);
//		request.setAttribute("map", map);
		//User 추가한 model과 View 연결
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		
		request.setAttribute("search", search);		
		
		return "forward:/product/listProduct.jsp";
	}
}
