package com.masprog.ice_market_api.models;


import com.masprog.ice_market_api.models.enums.pgStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @OneToOne(mappedBy = "payment", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Order order;

    @NotBlank
    @Size(min = 20, message = "Payment method must contain at least 20 characters")
    private String paymentType;

    // pg = payment getaway

    private String pgPaymentId;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Payment status cannot be null")
    private com.masprog.ice_market_api.models.enums.pgStatus pgStatus;
    private String pgResponseMessage;
    private String pgName;

    public Payment() {
    }

    public Payment(String paymentType, String pgPaymentId, pgStatus pgStatus, String pgResponseMessage, String pgName) {
        this.paymentType = paymentType;
        this.pgPaymentId = pgPaymentId;
        this.pgStatus = pgStatus;
        this.pgResponseMessage = pgResponseMessage;
        this.pgName = pgName;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public @NotBlank @Size(min = 20, message = "Payment method must contain at least 4 characters") String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(@NotBlank @Size(min = 20, message = "Payment method must contain at least 4 characters") String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPgPaymentId() {
        return pgPaymentId;
    }

    public void setPgPaymentId(String pgPaymentId) {
        this.pgPaymentId = pgPaymentId;
    }

    public pgStatus getPgStatus() {
        return pgStatus;
    }

    public void setPgStatus(pgStatus pgStatus) {
        this.pgStatus = pgStatus;
    }

    public String getPgResponseMessage() {
        return pgResponseMessage;
    }

    public void setPgResponseMessage(String pgResponseMessage) {
        this.pgResponseMessage = pgResponseMessage;
    }

    public String getPgName() {
        return pgName;
    }

    public void setPgName(String pgName) {
        this.pgName = pgName;
    }
}
