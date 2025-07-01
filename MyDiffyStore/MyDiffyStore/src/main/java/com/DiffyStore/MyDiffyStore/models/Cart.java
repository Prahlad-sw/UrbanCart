package com.DiffyStore.MyDiffyStore.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cart {
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	private int cartId;
	
	private double totalAmount;
	
	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.REMOVE)
	@JoinColumn(name="user_id",referencedColumnName="userId")
	@JsonIgnore
	private UserInfo user;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="cart")
	private List<CartProduct> cartProducts;
	//Constructor
	public Cart() {
		super();
	}

	public Cart(Integer cartId, Double totalAmount, UserInfo user, List<CartProduct> cartProducts) {
		super();
		this.cartId = cartId;
		this.totalAmount = totalAmount;
		this.user = user;
		this.cartProducts = cartProducts;
	}
	
	//Constructor with amount and userId
	public Cart(Double amount,UserInfo user) {
		this.totalAmount=amount;
		this.user=user;
	}

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public List<CartProduct> getCartProducts() {
		return cartProducts;
	}

	public void setCartProducts(List<CartProduct> cartProducts) {
		this.cartProducts = cartProducts;
	}

	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", totalAmount=" + totalAmount + ", user=" + user + ", cartProducts="
				+ cartProducts + "]";
	}
	
	public void updateTotalAmount(Double price) {
		this.totalAmount+=price;
	}
	

}
