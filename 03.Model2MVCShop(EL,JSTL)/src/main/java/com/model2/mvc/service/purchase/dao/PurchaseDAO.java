package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.dao.ProductDao;
import com.model2.mvc.service.user.dao.UserDao;

public class PurchaseDAO {

	public PurchaseDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public void insertPurchase(Purchase purchase) throws Exception{
		System.out.println("============================");
		System.out.println("purchaseDao DB 부분");
		Connection con = DBUtil.getConnection();
		
		System.out.println(purchase.getPurchaseProd());
		System.out.println(purchase.getBuyer());
		
		String sql ="insert\r\n"
				+ " into transaction\r\n"
				+ " values (seq_transaction_tran_no.NEXTVAL,?,?,?,?,?,?,?,?,sysdate,?)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchase.getPurchaseProd().getProdNo());
		stmt.setString(2, purchase.getBuyer().getUserId());
		stmt.setString(3, purchase.getPaymentOption());
		stmt.setString(4, purchase.getReceiverName());
		stmt.setString(5, purchase.getReceiverPhone());
		stmt.setString(6, purchase.getDivyAddr());
		stmt.setString(7, purchase.getDivyRequest());
		stmt.setString(8, purchase.getTranCode());
		stmt.setString(9, purchase.getDivyDate());
		stmt.executeUpdate();
		
		System.out.println("purchase insert 완료");
		System.out.println("=======================");
		
		con.close();
	}

	public Purchase findPurchase(int tranNo) throws Exception{
		Connection con = DBUtil.getConnection();
	
		String sql = "SELECT * FROM transaction WHERE tran_no = ?";
//		String sql = "SELECT * \r\n"
//				+ " FROM transaction t, product p, users u\r\n"
//				+ " WHERE t.prod_no = p.prod_no AND t.buyer_id = u.user_id and tran_no = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1,tranNo);
		
		ResultSet rs = stmt.executeQuery();
		Purchase pvo = new Purchase();
		
		while(rs.next()) {
//			ProductVO vo = new ProductVO();
//			vo.setProdNo(rs.getInt("prod_no"));
//			
//			UserVO uvo = new UserVO();
//			uvo.setUserId(rs.getString("user_id"));
//			
//			pvo.setPurchaseProd(vo);
//			pvo.setBuyer(uvo);
			pvo.setPurchaseProd(new ProductDao().findProduct(rs.getInt("prod_no")));
			pvo.setBuyer(new UserDao().findUser(rs.getString("buyer_id")));
			pvo.setPaymentOption(rs.getString("payment_option"));
			pvo.setReceiverName(rs.getString("receiver_name"));
			pvo.setReceiverPhone(rs.getString("receiver_phone"));
			pvo.setDivyAddr(rs.getString("demailaddr"));
			pvo.setDivyRequest(rs.getString("dlvy_Request"));
			pvo.setOrderDate(rs.getDate("order_data"));
			pvo.setDivyDate(rs.getString("dlvy_date"));
			pvo.setTranNo(rs.getInt("tran_no"));	
						
		}
		con.close();
		return pvo;
	}
	
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception{
		System.out.println("==========getPurchaseList========");
		Map<String,Object> map = new HashMap<String,Object>();
		Connection con = DBUtil.getConnection();
//		String sql = "SELECT*FROM transaction where buyer_id = ?";
		String sql = "SELECT t.prod_no, t.buyer_id, t.receiver_name, t.receiver_phone, t.tran_no ,t.tran_status_code \r\n"
//		String sql = "SELECT t.* \r\n"
				+ " FROM transaction t, product p, users u \r\n"
				+ " WHERE t.prod_no = p.prod_no AND t.buyer_id = u.user_id \r\n"
				+ " AND buyer_id = '"+buyerId+"' ";
		
		int totalCount = this.getTotalCount(sql);
		System.out.println("purchaseDao: "+totalCount);
		
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement stmt = con.prepareStatement(
				sql
//				,ResultSet.TYPE_SCROLL_INSENSITIVE,
//				ResultSet.CONCUR_UPDATABLE
			);
		
		System.out.println("buyerId=====>>"+buyerId);
//		stmt.setString(1, buyerId);
		ResultSet rs = stmt.executeQuery();
		System.out.println(search);
		
//		rs.last();
//		int total = rs.getRow();
//		System.out.println("total:"+total);
		
		
//		map.put("count", new Integer(total));
		
//		rs.absolute(search.getCurrentPage()*search.getPageSize()-search.getPageSize()+1);
//		System.out.println("searchVO.getPage():"+search.getCurrentPage());
//		System.out.println("searchVO.getPageUnit():"+search.getPageSize());
		
		List<Purchase> list = new ArrayList<Purchase>();
		while(rs.next()) {
			Product vo = new Product();
			vo.setProdNo(rs.getInt("prod_no"));
			
			User uvo = new User();
//			uvo.setUserId(rs.getString("user_id"));
			uvo.setUserId(buyerId);
			
			Purchase pvo = new Purchase();
			pvo.setPurchaseProd(vo);
			pvo.setBuyer(uvo);
			pvo.setReceiverName(rs.getString("receiver_name"));
			pvo.setReceiverPhone(rs.getString("receiver_phone"));
			pvo.setTranNo(rs.getInt("tran_no"));
			pvo.setTranCode(rs.getString("tran_status_code").trim());
			list.add(pvo);
		}
		
		map.put("totalCount", new Integer(totalCount));
		System.out.println("list.size()"+list.size());
		map.put("list",list);
		System.out.println("map().size():"+map.size());
		
		rs.close();
		con.close();
		stmt.close();
		return map;
	}

	public void updatePurchase(Purchase purchase) throws Exception{
		System.out.println("=============updatePurchaseDAO 부분===========");
		
		Connection con = DBUtil.getConnection();
		String sql ="UPDATE transaction\r\n"
				+ "SET payment_option=?,receiver_name=?,receiver_phone=?, demailaddr=?,dlvy_request=?,dlvy_date=?\r\n"
				+ "where tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchase.getPaymentOption());
		stmt.setString(2, purchase.getReceiverName());
		stmt.setString(3, purchase.getReceiverPhone());
		stmt.setString(4, purchase.getDivyAddr());
		stmt.setString(5, purchase.getDivyRequest());
		stmt.setString(6, purchase.getDivyDate());
		stmt.setInt(7, purchase.getTranNo());
		
		stmt.executeUpdate();
		System.out.println("update 완료");
		con.close();		
	}
	
	public void updateTranCode(Purchase purchase) throws Exception{
		Connection con = DBUtil.getConnection();
		String sql ="UPDATE transaction\r\n"
				+ "SET tran_status_code=?\r\n"
				+ "WHERE prod_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchase.getTranCode());
		stmt.setInt(2, purchase.getTranNo());
		
		stmt.executeUpdate();
		System.out.println("updatetrancode 완료");
		con.close();
	}
	
	private int getTotalCount(String sql) throws Exception{
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	
	private String makeCurrentPageSql(String sql, Search search) {
		sql = 	"SELECT * "+ 
				"FROM (	SELECT inner_table.* ,  ROWNUM AS row_seq " +
								" FROM ( "+sql+" ) inner_table "+
								" WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
				"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
	
	System.out.println("ProductDao :: make SQL :: "+ sql);	
	
	return sql;
	}
}
