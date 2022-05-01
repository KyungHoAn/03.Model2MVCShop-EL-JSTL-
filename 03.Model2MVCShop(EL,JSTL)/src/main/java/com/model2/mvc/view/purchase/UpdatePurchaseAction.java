package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.service.PurchaseService;

public class UpdatePurchaseAction extends Action{
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
		System.out.println("UpdatePurchaseAction");
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		System.out.println("========");
		System.out.println(tranNo);
		
		Purchase purchase = new Purchase();
		purchase.setTranNo(tranNo);
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("divyDate"));
		
		PurchaseService service = new PurchaseServiceImpl();
		service.updatePurchase(purchase);
		System.out.println("getPurchase.do∑Œ ¿Ãµø");
		return "redirect:/getPurchase.do?tranNo=" + tranNo;
	}
}
