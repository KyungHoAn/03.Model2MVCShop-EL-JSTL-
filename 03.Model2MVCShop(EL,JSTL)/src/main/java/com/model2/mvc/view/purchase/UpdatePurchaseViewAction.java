package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.service.PurchaseService;

public class UpdatePurchaseViewAction extends Action {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		System.out.println(tranNo);
		PurchaseService service = new PurchaseServiceImpl();
//		PurchaseVO purchaseVO = service.getPurchase(Integer.parseInt(request.getParameter("tranNo")));
		Purchase purchase = service.getPurchase(tranNo);
		
		request.setAttribute("purchase", purchase);
		System.out.println(purchase);
		System.out.println("====updatePurcahse.jsp�� Navigation==========");
		return "forward:/purchase/updatePurchase.jsp";
	}
}
