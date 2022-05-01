package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.service.PurchaseService;


public class AddPurchaseAction extends Action{
	public String execute(	HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("===================");
		System.out.println("test");
		
		Purchase purchase = new Purchase();
		Product product = new Product();
		User user = new User();
		
		//userVO session 媛� �꽔湲�
		user = (User)request.getSession().getAttribute("user");
		product.setProdNo(Integer.parseInt(request.getParameter("prodNo")));
		
		System.out.println("====== userVO / ProductVO ======");
		System.out.println(user);
		System.out.println(product);
		
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);	
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("receiverDate"));
		purchase.setTranCode("1");
		
		PurchaseService service = new PurchaseServiceImpl();
		service.addPurchase(purchase);
		
		request.setAttribute("purchase", purchase);
		
		System.out.println("test1");
		return "forward:/purchase/addPurchase.jsp";
	}
}