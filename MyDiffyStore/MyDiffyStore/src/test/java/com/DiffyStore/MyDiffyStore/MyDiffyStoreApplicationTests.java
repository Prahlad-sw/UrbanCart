//package com.DiffyStore.MyDiffyStore;
//
//import static org.assertj.core.api.Assertions.not;
//import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.CoreMatchers.notNullValue;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.net.URL;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@AutoConfigureMockMvc
//class MyDiffyStoreApplicationTests {
//	
//	private MockMvc mvc;
//	
//	String c_u="jack",s_u="apple" ,p="pass_word";
//	
//	@Autowired
//	WebApplicationContext context;
//	
//	@BeforeEach
//	void setMockMvc() {
//		mvc=MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
//	}
//
//	@Test
//	@Order(4)
//	public void productSearchStatus() throws Exception {
//		mvc.perform(get("/api/public/product/search").param("keyword","tablet")).andExpect(status().is(200))
//		.andExpect(jsonPath("$",notNullValue()));
//	}
//	
//	@Test
//	@Order(5)
//	public void productSearchWithoutKeyword() throws Exception{
//		mvc.perform(get("/api/public/product/search")).andExpect(status().is(400));
//	}
//	
//	@Test
//	@Order(6)
//	public void productSearchWithProductName() throws Exception{
//		MvcResult res =mvc.perform(get("/api/public/product/search").param("keyword","tablet"))
//				.andExpect(status().is(200))
//				.andReturn();
//		
//		JSONArray arr=new JSONArray(res.getResponse().getContentAsString());
//		
//		assert(arr.length()> 0);
//		
//		for(int i=0 ;i<arr.length();i++) {
//			JSONObject obj=arr.getJSONObject(i);
//			assert(obj.getString("productName").toLowerCase().contains("tablet"));
//		}
//	}
//	
//	@Test
//	@Order(7)
//	public void productSearchWithCategoryName() throws Exception{
//		MvcResult res=mvc.perform(get("/api/public/product/search").param("keyword", "medicine"))
//				.andExpect(status().is(200))
//				.andReturn();
//		
//		JSONArray arr=new JSONArray(res.getResponse().getContentAsString());
//		
//		for(int i=0;i<arr.length();i++) {
//			JSONObject obj=arr.getJSONObject(i);
//			
//			JSONObject categoryObj=obj.getJSONObject("category");
//			
//			String categoryName=categoryObj.getString("categoryName");
//			
//			assert(categoryName.toLowerCase().contains("medicine"));
//		}
//	}
//	
//	
//	@Test
//	@Order(8)
//	public void consumerLoginWithBadCreds() throws Exception{
//		mvc.perform(post("/api/public/login").contentType(MediaType.APPLICATION_JSON)
//				.content(getJSONCreds(c_u,"password"))).andExpect(status().is(401));
//	}
//	public String getJSONCreds(String u,String p) {
//		Map<String , String> map=new HashMap<>();
//		map.put("username", u);
//		map.put("password", p);
//		return new JSONObject(map).toString();
//	}
//	
//	public MockHttpServletResponse loginHelper(String u,String p) throws Exception{
//		return mvc
//				.perform(post("/api/public/login").contentType(MediaType.APPLICATION_JSON)
//						.content(getJSONCreds(u,p)))
//						.andReturn().getResponse();
//	}
//	
//	@Test
//	@Order(9)
//	public void consumerLoginWithValidCreds() throws Exception{
//		assertEquals(200,loginHelper(c_u,p).getStatus());
//		assertNotEquals("",loginHelper(c_u,p).getContentAsString());
//	}
//	
//	@Test
//	@Order(10)
//	public void consumerGetCartWithValidJWT() throws Exception {
//		
//		String responseContent=loginHelper(c_u,p).getContentAsString();
//		
//		JSONObject jsonResponse = new JSONObject(responseContent);
//		String jwtToken= jsonResponse.getString("accessToken");
//		
//		mvc.perform(get("/api/auth/consumer/cart").header("Authorization","Bearer " + jwtToken))
//		.andExpect(status().is(200))
//		.andExpect(jsonPath("$.cartId",is(not(equalTo("")))))
//		.andExpect(jsonPath("$.cartProducts[0].quantity",is(2)))
//		.andExpect(jsonPath("$.cartProducts[0].product.productName",containsStringIgnoringCase("Crokin pain relief rablet")))
//		.andExpect(jsonPath("$.cartProducts[0].product.category.categoryName",is("Books")));
//
//	}
//	
//	@Test
//	@Order(11)
//	public void sellerApiWithConsumerJWT() throws Exception{
//		String responseContent = loginHelper(c_u,p).getContentAsString();
//		
//		JSONObject jsonResponse = new JSONObject(responseContent);
//		String jwtToken=jsonResponse.getString("accessToken");
//		
//		mvc.perform(get("/api/auth/seller/product")
//				.header("Authorization","Bearer "+ jwtToken))
//			.andExpect(status().is(403));
//	}
//	
//	@Test
//	@Order(12)
//	public void sellerLoginWithValidCreds() throws Exception{
//		assertEquals(200,loginHelper(s_u,p).getStatus());
//		assertNotEquals("",loginHelper(s_u,p).getContentAsString());
//	}
//	
//	@Test
//	@Order(13)
//	public void sellerGetProductsWithValidJWT() throws Exception{
//		String responseContent = loginHelper(s_u,p).getContentAsString();
//		
//		JSONObject jsonResponse = new JSONObject(responseContent);
//		String jwtToken=jsonResponse.getString("accessToken");
//		
//		mvc.perform(get("/api/auth/seller/product")
//				.header("Authorization","Bearer "+ jwtToken))
//		
//		.andExpect(status().is(200))
//		.andExpect(jsonPath("$.[0].productId",is(not(equalTo("")))))
//		.andExpect(jsonPath("$.[0].productName",containsStringIgnoringCase("Apple iPad 10.2 8th Gen Wifi iOS Tablet")))
//		.andExpect(jsonPath("$.[0].category.categoryName",is("Electronics")));
//
//	}
//	
//	@Test
//	@Order(14)
//	public void consumerApiWithSellerJWT() throws Exception{
//		String  responseContent=loginHelper(s_u,p).getContentAsString();
//		
//		JSONObject jsonResponse=new JSONObject(responseContent);
//		String jwtToken=jsonResponse.getString("accessToken");
//		
//		mvc.perform(get("/api/auth/consumer/cart")
//				.header("Authorization", "Bearer "+jwtToken))
//				.andExpect(status().is(403));
//	}
//	public JSONObject getProduct(int id,String name,Double price,int cId,String cName) {
//		Map<String,String > mapC=new HashMap<>();
//		mapC.put("categoryId",String.valueOf(cId));
//		mapC.put("CategoryName", cName);
//		Map<String,Object> map =new HashMap<>();
//		map.put("ProductId", id);
//		map.put("riductName",name);
//		map.put("price", String.valueOf(price));
//		map.put("category", mapC);
//		return new JSONObject(map);
//		
//	}
//	
//	static String createdURI;
//	
//	@Test
//	@Order(15)
//	public void sellerAddNewProduct() throws Exception{
//		String responseContent = loginHelper(s_u, p).getContentAsString();
//		
//		JSONObject jsonResponse=new JSONObject(responseContent);
//		String jwtToken =jsonResponse.getString("accesstoken");
//		
//		createdURI =mvc
//				.perform(post("/api/auth/seller/product")
//						.header("Authorization","Bearer" +jwtToken)
//						.contentType(MediaType.APPLICATION_JSON)
//						.content(getProduct(0,"iPhone 11",49000.0,2,"Electonics").toString()))
//				.andExpect(status().is(201)).andReturn().getResponse().getRedirectedUrl();
//							
//	}
// 
//	@Test
//	@Order(16)
//	public void sellerCeckAddedNewProduct() throws Exception{
//		String responseContent = loginHelper(s_u, p).getContentAsString();
//		
//		JSONObject jsonResponse=new JSONObject(responseContent);
//		String jwtToken =jsonResponse.getString("accesstoken");
//		
//		mvc.perform(get(new URL(createdURI).getPath()).header("Authorization","Bearer"+jwtToken))
//		.andExpect(status().is(200))
//		.andExpect(jsonPath("$.[0].productId",is(3)))
//		.andExpect(jsonPath("$.[0].productName",is("iPhone 11")))
//		.andExpect(jsonPath("$.[0].price",is(49000.0)))
//		.andExpect(jsonPath("$.[0].category.categoryName",is("Electronics")));
//
//		mvc.perform(get(new URL(createdURI).getPath()).header("Authorization","Bearer"+jwtToken))
//		.andExpect(status().is(200))
//		.andExpect(jsonPath("$.[0].productId",is(3)))
//		.andExpect(jsonPath("$.[0].productName",is("iPhone 11")))
//		.andExpect(jsonPath("$.[0].price",is(49000.0)))
//		.andExpect(jsonPath("$.[0].category.categoryName",is("Electronics")));	
//	}
//	
//	
//	@Test
//	@Order(21)
//	public void consumerAddProductToCartAgain() throws Exception {
//		String responseContent = loginHelper (c_u, p).getContentAsString();
//
//		JSONObject jsonResponse = new JSONObject (responseContent);
//
//		String jwtToken = jsonResponse.getString("accessToken");
//
//		String[] arr = createdURI.split("/");
//
//		mvc.perform(post("/api/auth/consumer/cart") .header("Authorization", "Bearer "+ jwtToken)
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(getProduct ( Integer.parseInt(arr[arr.length - 1]),"iPhone 12",98000.0,2,  "Electronics").toString()))
//				.andExpect(status().is(409));
//	}
//
//	public JSONObject getCartProduct(JSONObject product, int q) {
//
//		Map<String, Object> map = new HashMap();
//		map.put("product", product);
//		map.put("quantity", q);
//		return new JSONObject(map);
//
//	}
//	
//	@Test
//	@Order(22)
//	public void consumerUpdateProductInCart() throws Exception {
//
//		String responseContent = loginHelper (c_u, p).getContentAsString();
//		JSONObject jsonResponse = new JSONObject (responseContent);
//		String jwtToken = jsonResponse.getString("accessToken");
//
//		String[] arr = createdURI.split("/");
//
//		mvc.perform(put("/api/auth/consumer/cart").header( "Authorization", "Bearer " +jwtToken)
//		.contentType(MediaType.APPLICATION_JSON)
//		.content(getCartProduct(getProduct(Integer.parseInt(arr[arr.length-1]),"iPhone 12", 98000.0,2,"Electronics"),3)
//		.toString()))
//		.andExpect (status().is(200));
//
//		mvc.perform(get("/api/auth/consumer/cart") .header( "Authorization", "Bearer "+jwtToken))
//		.andExpect(status().is(200)).andExpect(jsonPath("$.cartId", is (not (equalTo("")))))
//		.andExpect(jsonPath("$.cartProducts [1] quantity", is (3)))
//		.andExpect(jsonPath("$.cartProducts [1] product.productName",containsStringIgnoringCase("iphone 12")))
//		.andExpect(jsonPath("$.cartProducts[1].product.category.categoryName", is ("Electronics")));
//	}
//	
////	@Test
////	@Order(23)
////	public void consumerUpdateProductInCartWithZeroQuantity() throws Exception {
////		String responseContent = loginHelper(c_u, p).getContentAsString();
////		JSONObject jsonResponse = new JSONObject (responseContent);
////
////		String jwtToken = jsonResponse.getString("accessToken");
////
////		String[] arr = createdURI.split("/");
////
////		mvc.perform(put("/api/auth/consumer/cart") .header( "Authorization", "Bearer "+ jwtToken)
////				.contentType(MediaType.APPLICATION_JSON)
////				.content(getCartProduct(
////						getProduct(Integer.parseInt(arr[arr.length- 1]), "iPhone 12", 98000.0,2, "Electronics"),0).toString()))
////				.andExpect(status().is(200));
////
////		mvc.perform(get("/api/auth/consumer/cart") .header( "Authorization", "Bearer "+jwtToken))
////				.andExpect (status().is( 200)).andExpect(jsonPath( "$.cartId", is (not (equalTo("")))))
////				.andExpect(jsonPath("$.cartProducts", hasSize(1)))
////				.andExpect (jsonPath("$.cartProducts[0].quantity", is(2)))
////				.andExpect(jsonPath("$.cartProducts[0].product.productName",
////						containsStringIgnoringCase( "Crocin pain relief tablet")))
////				.andExpect ( jsonPath("$.cartProducts[0].product.category.categoryName", is ( "Medicines")));
////	}
////	@Test
////	void contextLoads() {
////	}
//
//}
