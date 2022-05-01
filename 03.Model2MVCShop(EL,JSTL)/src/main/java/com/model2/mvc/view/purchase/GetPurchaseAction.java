package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.service.PurchaseService;

public class GetPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PurchaseService service = new PurchaseServiceImpl();
		Purchase purchase = service.getPurchase(Integer.parseInt(request.getParameter("tranNo")));
		
		request.setAttribute("purchase", purchase);
		
		System.out.println("========getPurchase.jsp Navigation==========");
		return "forward:/purchase/getPurchase.jsp";
	}

}

//public class GetPurchaseAction extends Action{
//	public String execute(HttpServletRequest request,
//			HttpServletResponse response) throws Exception{		
//		SearchVO searchVO = new SearchVO();
//		UserVO userVO = new UserVO();
//		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
//		
//		PurchaseService service = new PurchaseServiceImpl();
////		System.out.println(tranNo);		
//		PurchaseVO purchaseVO = service.getPurchase(tranNo);
//		
//		request.setAttribute("purchaseVO", purchaseVO);
//		
////		System.out.println("========================");
////		System.out.println(purchaseVO);
////		System.out.println("getPurchase.jsp·Î Navigation");
////		System.out.println("========================");
//		
//		return "forward:/purchase/getPurchase.jsp";
//	}
//}
