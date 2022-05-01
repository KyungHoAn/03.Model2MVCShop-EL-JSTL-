package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.service.PurchaseService;

public class UpdateTranCodeAction extends Action{
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
		System.out.println("UpdateTranCodeAction");
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		String tranCode = request.getParameter("tranCode");
		
		Purchase purchase = new Purchase();
		purchase.setTranNo(prodNo);
		purchase.setTranCode(tranCode);
		PurchaseService service = new PurchaseServiceImpl();
		service.updateTranCode(purchase);
		
		System.out.println("list로 이동");
		//if admin이면 produvt user면 purchase
		return "/listProduct.do?menu=manage";
	}
}