package com.OnlineFoodOrderManagement;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Online_Food_Orders")
public class OnlineFoodOrder 
{
	@Id
	@Column(name="Order_ID")
	private Long orderId;
	
	@Column(name="Customer_Name")
	private String customerName;
	
	@Column(name="Customer_Phone")
	private String customerPhone;
	
	@Column(name="Food_Name")
	private String foodName;
	
	@Column(name="Quantity")
	private int quantity;
	
	@Column(name="order_Date")
	private LocalDate orderDate;
	
	@Column(name="Restaurant_Name")
	private String restaurantName;

	@Column(name="Payment_Method")
	private String paymentMethod;
	
	@Column(name="Delivery_Address")
	private String deliveryAddress;
	
	@Column(name="Delivery_Partner")
	private String deliveryPartner;
	
	@Column(name="Order_Status")
	private String orderStatus;
	
	@Column(name="Total_Amount")
	private double totalamount;

	public OnlineFoodOrder()
	{
		
	}
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getDeliveryPartner() {
		return deliveryPartner;
	}

	public void setDeliveryPartner(String deliveryPartner) {
		this.deliveryPartner = deliveryPartner;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public double getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(double totalamount) {
		this.totalamount = totalamount;
	}

	@Override
	public String toString() {
		return "OnlineFoodOrder [orderId=" + orderId + ", customerName=" + customerName + ", customerPhone="
				+ customerPhone + ", foodName=" + foodName + ", quantity=" + quantity + ", orderDate=" + orderDate
				+ ", restaurantName=" + restaurantName + ", paymentMethod=" + paymentMethod + ", deliveryAddress="
				+ deliveryAddress + ", deliveryPartner=" + deliveryPartner + ", orderStatus=" + orderStatus
				+ ", totalamount=" + totalamount + "]";
	}
	
	

}
