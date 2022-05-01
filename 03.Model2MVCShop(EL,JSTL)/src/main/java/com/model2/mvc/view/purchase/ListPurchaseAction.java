//package com.model2.mvc.view.purchase;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import com.model2.mvc.common.Search;
//import com.model2.mvc.framework.Action;
//import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
//import com.model2.mvc.service.purchase.service.PurchaseService;
//import com.model2.mvc.service.domain.User;
//
//public class ListPurchaseAction extends Action{
//   
//   @Override
//   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//      System.out.println("list purchase Action ����");
//      Search search = new Search();
//      
//      int page = 1;
//      
//      if(request.getParameter("page")!=null)
//         page=Integer.parseInt(request.getParameter("page"));
//      
//      search.setCurrentPage(page);
//      
//      String pageUnit=getServletContext().getInitParameter("pageSize");
//      search.setPageSize(Integer.parseInt(pageUnit));
//      
//      HttpSession session = request.getSession(); //user id������ �ʿ��� session �α��� ���� ������
//      User user = (User)session.getAttribute("user");
//      String buyerId = user.getUserId(); 
//      
//      PurchaseService service=new PurchaseServiceImpl();
//      Map<String,Object> map=service.getPurchaseList(search, buyerId); //id ���� �ʿ��� service�� �߰�
//      //service ������ Impl�� �������ֱ�;
//      System.out.println(2);
//      request.setAttribute("map", map);
//      request.setAttribute("search", search);
//      System.out.println(map);
//      System.out.println("list purchase Action ����");
//      
//      return "forward:/purchase/listPurchase.jsp";
//   }
//}



package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.service.PurchaseService;


public class ListPurchaseAction extends Action{
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Search search = new Search();
		
		//page�߰��� �߰��Ǵ� �ڵ�
		int page=1;

		if(request.getParameter("currentPage") != null){
			page=Integer.parseInt(request.getParameter("currentPage"));
		}
		
		search.setCurrentPage(page);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		// web.xml  meta-data �� ���� ��� ���� 
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		
		
		User user = new User();
		user = (User)request.getSession().getAttribute("user");
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		Map<String,Object> map = purchaseService.getPurchaseList(search,user.getUserId());
			
		Page resultPage = 
				new Page(page,((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListPurchaseAction:: "+resultPage);
		
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		
		request.setAttribute("map", map);
		request.setAttribute("search", search);
		System.out.println("========<<map>>=======");
		System.out.println(map);
		return "forward:/purchase/listPurchase.jsp";
	}
}
