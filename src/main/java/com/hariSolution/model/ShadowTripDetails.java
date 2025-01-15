package com.hariSolution.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Component
public class ShadowTripDetails implements Serializable  {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id; // Add the missing id field

    @Column(name = "driver_name", nullable = false, length = 20)
    private String driverName;

    @Column(name = "month", nullable = false, length = 20)
    private String month;

    @Column(name = "loading_date", nullable = false)
    private LocalDate loadingDate;

    @Column(name = "trip_start_date", nullable = false)
    private LocalDate tripStartDate;

    @Column(name = "trip_end_date", nullable = false)
    private LocalDate tripEndDate;

    @Column(name = "payment_status", nullable = false, length = 20)
    private String paymentStatus;

    @Column(name = "loading_point", nullable = false, length = 50)
    private String loadingPoint;

    @Column(name = "delivery_point", nullable = false, length = 50)
    private String deliveryPoint;

    @Column(name = "load_quantity", nullable = false, length = 30)
    private String loadQuantity;

    @Column(name = "goods_description", nullable = false, length = 100)
    private String goodsDescription;

    @Column(name = "load_amount", nullable = false)
    private Integer loadAmount;

    @Column(name = "load_advance", nullable = false)
    private Integer loadAdvance;

    @Column(name = "weight_bridge", nullable = false)
    private Integer weightBridge;

    @Column(name = "broker_amount", nullable = false)
    private Integer brokerAmount;

    @Column(name = "up_amount", nullable = false)
    private Integer upAmount;

    @Column(name = "down_amount", nullable = false)
    private Integer downAmount;

    @Column(name = "toll_amount", nullable = false)
    private Integer tollAmount;

    @Column(name = "diesel_expenses", nullable = false)
    private Integer dieselExpenses;

    @Column(name = "rto_amount", nullable = false)
    private Integer rtoAmount;

    @Column(name = "pc_amount", nullable = false)
    private Integer pcAmount;

    @Column(name = "others_expenses", nullable = false)
    private Integer othersExpenses;

    @Column(name = "total_expenses", nullable = false)
    private Integer totalExpenses;

    // Assuming DriverInfo is another entity
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "driver_id", referencedColumnName = "id", nullable = false)
    private DriverInfo driverInfo;

    @Column(name = "client_reqid", nullable = false)
    private String clientReqId;
}
