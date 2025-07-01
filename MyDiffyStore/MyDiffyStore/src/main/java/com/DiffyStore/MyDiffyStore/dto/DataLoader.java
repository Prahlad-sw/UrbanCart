//package com.DiffyStore.MyDiffyStore.dto;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import com.DiffyStore.MyDiffyStore.models.Cart;
//import com.DiffyStore.MyDiffyStore.models.CartProduct;
//import com.DiffyStore.MyDiffyStore.models.Category;
//import com.DiffyStore.MyDiffyStore.models.Product;
//import com.DiffyStore.MyDiffyStore.models.UserInfo;
//import com.DiffyStore.MyDiffyStore.repository.CartProductRepo;
//import com.DiffyStore.MyDiffyStore.repository.CartRepo;
//import com.DiffyStore.MyDiffyStore.repository.CategoryRepo;
//import com.DiffyStore.MyDiffyStore.repository.ProductRepo;
//import com.DiffyStore.MyDiffyStore.repository.UserInfoRepository;
//
////ToBeUsedLater
//@Component
//public class DataLoader implements CommandLineRunner{
//	@Autowired
//	private UserInfoRepository userRepo;
//	
//	@Autowired
//	private CategoryRepo categoryRepo;
//	
//	@Autowired
//	private CartRepo cartRepo;
//	
//	@Autowired
//	private ProductRepo productRepo;
//	
//	@Autowired
//	private CartProductRepo cartProductRepo;
//	
//	@Autowired
//	PasswordEncoder passwordEncoder;
//	
//	@Override
//	public void run(String ... args ) throws Exception{
//		loadUserData();
//		loadData();
//	}
//	
//	private void loadUserData() {
//		userRepo.save(new UserInfo("jack",passwordEncoder.encode("pass_word"),"CONSUMER"));
//		userRepo.save(new UserInfo("bob",passwordEncoder.encode("pass_word"),"SELLER"));
//		userRepo.save(new UserInfo("apple",passwordEncoder.encode("pass_word"),"SELLER"));
//		userRepo.save(new UserInfo("galazo",passwordEncoder.encode("pass_word"),"CONSUMER"));
//		
//		System.out.println("User Data got Loaded in DB");
//	}
//	private void loadData() {
//		Category category1=new Category("Fashion");
//		Category category2=new Category("Electronics");
//		Category category3=new Category("Books");
//		Category category4=new Category("Groceries");
//		Category category5=new Category("Medicines");
//		
//		categoryRepo.save(category1);
//		categoryRepo.save(category2);
//		categoryRepo.save(category3);
//		categoryRepo.save(category4);
//		categoryRepo.save(category5);
//		
//		System.out.println("Category Data got loaded in DB");
//		
//		//Fetch UserInfo for user(jack and bob) using userId
//		UserInfo user3=userRepo.findById(3).orElseThrow(()->new RuntimeException("User not found"));
//		UserInfo user4=userRepo.findById(4).orElseThrow(()->new RuntimeException("User not found"));
//		
//		Category cate1=categoryRepo.findById(2).orElseThrow(()->new RuntimeException("Category not found"));
//		Category cate2=categoryRepo.findById(5).orElseThrow(()->new RuntimeException("Category not found"));
//		
//		//Create and save Product entities
//		Product product1=new Product("Apple iPad 10.2 8th Gen WiFi iOS Tablet",29199,user3,cate1);
//		Product product2=new Product("Crokin pain relief tablet",10,user4,cate2);
//		
//		productRepo.save(product1);
//		productRepo.save(product2);
//		
//		System.out.println("Product Got Saved in DB");
//		
//		UserInfo user1=userRepo.findById(1).orElseThrow(()->new RuntimeException("User not found"));
//		UserInfo user2=userRepo.findById(2).orElseThrow(()->new RuntimeException("User not found"));
//		
//		//create and Save Cart entities
//		Cart cart1=new Cart( 20.0,user1);
//		Cart cart2=new Cart( 0.0,user2);
//		
//		cartRepo.save(cart1);
//		cartRepo.save(cart2);
//		
//		System.out.println(" Cart Data got loaded in DB");
//		
//		Product savedProduct1=productRepo.findById(product1.getProductId())
//				.orElseThrow(()->new RuntimeException("Prouct not found"));
//		
//		Product savedProduct2=productRepo.findById(product2.getProductId())
//				.orElseThrow(()->new RuntimeException("Prouct not found"));
//		//Create and Save CartProduct entities
//		
//		CartProduct cartProduct=new CartProduct(cart1,savedProduct2,2);
//		
//		cartProductRepo.save(cartProduct);
//		System.out.println("Cart Product data got saved in DB");
//
//	}
//}
