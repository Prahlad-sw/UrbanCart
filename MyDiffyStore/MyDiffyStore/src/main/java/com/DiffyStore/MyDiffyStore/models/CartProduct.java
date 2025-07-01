package com.DiffyStore.MyDiffyStore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints= @UniqueConstraint(columnNames= {"cart_id","product_id"}))
public class CartProduct {
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	private int cpId;
	
	@Column(name="cart_id",insertable=false,updatable=false)
	private int cartId;
	
	@Column(name="product_id",insertable=false,updatable=false)
	private int productId;
	
	@ManyToOne()
	@JoinColumn(name="cart_id",referencedColumnName="cartId")
	@JsonIgnore
	private Cart cart;
	
	@ManyToOne()
	@JoinColumn(name="product_id",referencedColumnName="productId")
	private Product product;
	private Integer quantity=1;
	
	
	public CartProduct() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CartProduct( Cart cart, Product product, Integer quantity) {
		super();
		this.cart = cart;
		this.product = product;
		this.quantity = quantity;
	}
	public CartProduct(int cpId, int cartId, int productId) {
		super();
		this.cpId = cpId;
		this.cartId = cartId;
		this.productId = productId;
	}
	//getters & Setters
	public Integer getCpId() {
		return cpId;
	}
	public void setCpId(int cpId) {
		this.cpId = cpId;
	}
	public Integer getCartId() {
		return cartId;
	}
	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "CartProductRepo [cpId=" + cpId + ", cartId=" + cartId + ", productId=" + productId + ", cart=" + cart
				+ ", product=" + product + ", quantity=" + quantity + "]";
	}

}
