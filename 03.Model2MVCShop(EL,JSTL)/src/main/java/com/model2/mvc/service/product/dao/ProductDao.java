package com.model2.mvc.service.product.dao;

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

public class ProductDao {

	public ProductDao() {
		// TODO Auto-generated constructor stub
	}
	
	public void insertProduct(Product product) throws Exception{
		Connection con = DBUtil.getConnection();
		
		String sql ="insert \r\n"
				+ "into product\r\n"
				+ "values(seq_product_prod_no.NEXTVAL,?,?,?,?,?,sysdate)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getProdDetail());
		stmt.setString(3,product.getManuDate().replace("-", ""));
		stmt.setInt(4, product.getPrice());
		stmt.setString(5, product.getFileName());
		stmt.executeUpdate();
		
		con.close();
	}
	
	public Product findProduct(int prodNo) throws Exception{
		Connection con = DBUtil.getConnection();
		
		String sql = "select*from product where prod_no=?";		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);
		
		ResultSet rs = stmt.executeQuery();
		
		Product productVO = null;
		while(rs.next()) {
			productVO = new Product();
			productVO.setProdNo(rs.getInt("prod_no"));
			productVO.setProdName(rs.getString("prod_name"));
			productVO.setProdDetail(rs.getString("prod_detail"));
			productVO.setManuDate(rs.getString("manufacture_day"));
			productVO.setPrice(rs.getInt("price"));
			productVO.setFileName(rs.getString("image_file"));
			productVO.setRegDate(rs.getDate("reg_date"));
		}
		con.close(); 	
		return productVO;
	}
	public Map<String,Object> getProductList(Search search) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		Connection con = DBUtil.getConnection();
		String sql = "SELECT p.*, NVL(t.tran_status_code,0) NTSC from PRODUCT p, transaction t where p.prod_no = t.prod_no(+) ";
//		String sql = "SELECT p.prod_No, p.prod_name, p.price, p.reg_date, NVL(t.tran_status_code,0) NTSC from product p, transaction t where p.prod_no = t.prod_no(+) ";
//		String sql = "SELECT p.prod_no, p.prod_name, p.price, p.reg_date, NVL(t.tran_status_code,0) NTSC, count from PRODUCT p, transaction t where p.prod_no = t.prod_no(+) ";
		if(search.getSearchCondition()!=null) {
			if(search.getSearchCondition().equals("0") && !search.getSearchKeyword().equals("") ) {
				sql+= " AND p.PROD_NO='" + search.getSearchKeyword()
				+ "'";
			} else if(search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("") ) {
				sql+=" AND p.PROD_NAME='" + search.getSearchKeyword()
				+ "'";
			}
		}		
		sql+=" order by p.prod_no";
		System.out.println("ProductDao:: sql:: "+sql);
		
		//totalCount Get
		int totalCount = this.getTotalCount(sql);
		System.out.println("productDao :"+totalCount);
		
		//currentpage게시물 query 다시구성
		sql = makeCurrentPageSql(sql, search);		
		PreparedStatement pstmt = con.prepareStatement(
				sql
				,ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE
				);
		ResultSet rs = pstmt.executeQuery();
		System.out.println(search);
		
//		rs.last();
//		int total =rs.getRow();
//		System.out.println("로우의 수:"+total);
		
		
//		map.put("count", new Integer(total));
		
//		rs.absolute(search.getCurrentPage()*search.getPageSize()-search.getPageSize()+1);
//		System.out.println("search.getPage():"+search.getCurrentPage());
//		System.out.println("search.getPageUnit():"+search.getPageSize());
		
		List<Product> list = new ArrayList<Product>();
		while(rs.next()) {
			Product prod = new Product();
			prod.setProdNo(rs.getInt("prod_no"));
			prod.setProdName(rs.getString("prod_name"));
//			prod.setProdDetail(rs.getString("prod_detail"));
//			prod.setManuDate(rs.getString("manufacture_day"));
			prod.setPrice(rs.getInt("price"));
//			prod.setFileName(rs.getString("image_file"));
			prod.setRegDate(rs.getDate("reg_date"));
			prod.setProTranCode(rs.getString("NTSC"));
			
			list.add(prod);
//			if(!rs.next()) {
//				break;
//			}
		}
		map.put("totalCount", new Integer(totalCount));
		System.out.println("list.size():"+list.size());
		map.put("list",list);
		System.out.println("map().size():"+map.size());
		
		con.close();
		return map;
	}
	
	public void updateProduct(Product product) throws Exception{
		Connection con = DBUtil.getConnection();
		String sql ="update product set prod_name=?,prod_detail=?,manufacture_day=?, price =?, image_file=?\r\n"
				+ "where prod_no=?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getProdDetail());
		stmt.setString(3, product.getManuDate());
		stmt.setInt(4, product.getPrice());
		stmt.setString(5,product.getFileName());
		stmt.setInt(6, product.getProdNo());

		System.out.println("sql update complete");
		stmt.executeUpdate();
		con.close();
	}
	
	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
	private int getTotalCount(String sql) throws Exception {
		
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
	
	// 게시판 currentPage Row 만  return 
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("UserDAO :: make SQL :: "+ sql);	
		
		return sql;
	}
}
