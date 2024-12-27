package com.masprog.ice_market_api.models;


import com.masprog.ice_market_api.models.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "order", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<OrderItem> orderItems = new ArrayList<>();

    @NotNull
    private LocalDate orderDate;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @NotNull
    private BigDecimal total;


    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderStatus orderStatus;  // Ex.: "pending", "shipped", "delivered"

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    public Order() {
    }

    public Order(Long orderId, String phoneNumber, List<OrderItem> orderItems, LocalDate orderDate, Payment payment, BigDecimal total, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.phoneNumber = phoneNumber;
        this.orderItems = orderItems;
        this.orderDate = orderDate;
        this.payment = payment;
        this.total = total;
        this.orderStatus = orderStatus;
        this.address = address;
    }

    public BigDecimal calculateTotal() {
        return orderItems.stream()
                .map(OrderItem::calculateSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
