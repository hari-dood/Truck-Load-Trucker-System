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

@Data  // Lombok annotation to generate getters, setters, toString(), equals(), and hashCode() methods
@NoArgsConstructor  // Lombok annotation to generate a no-argument constructor
@AllArgsConstructor  // Lombok annotation to generate a constructor with all arguments
@Entity  // Marks the class as a JPA entity
@Component  // Marks the class as a Spring bean, which can be auto-wired
public class ShadowTripDetails implements Serializable {
    private static final long serialVersionUID = 1L;  // Serial version UID for serialization

    @Id  // Marks the field as the primary key
    private Integer id;  // The ID of the trip detail record (assuming unique for each trip)

    // Driver details
    @Column(name = "driver_name", nullable = false, length = 20)  // Maps the field to a column in the database
    private String driverName;

    // Month for the trip
    @Column(name = "month", nullable = false, length = 20)
    private String month;

    // Date when the loading occurred
    @Column(name = "loading_date", nullable = false)
    private LocalDate loadingDate;

    // Start date of the trip
    @Column(name = "trip_start_date", nullable = false)
    private LocalDate tripStartDate;

    // End date of the trip
    @Column(name = "trip_end_date", nullable = false)
    private LocalDate tripEndDate;

    // Payment status for the trip
    @Column(name = "payment_status", nullable = false, length = 20)
    private String paymentStatus;

    // The location where the loading happened
    @Column(name = "loading_point", nullable = false, length = 50)
    private String loadingPoint;

    // The location where the delivery occurred
    @Column(name = "delivery_point", nullable = false, length = 50)
    private String deliveryPoint;

    // The amount of load carried in the trip
    @Column(name = "load_quantity", nullable = false, length = 30)
    private String loadQuantity;

    // Description of the goods carried
    @Column(name = "goods_description", nullable = false, length = 100)
    private String goodsDescription;

    // Amount charged for the load
    @Column(name = "load_amount", nullable = false)
    private Integer loadAmount;

    // Advance payment for the load
    @Column(name = "load_advance", nullable = false)
    private Integer loadAdvance;

    // Weight bridge charges
    @Column(name = "weight_bridge", nullable = false)
    private Integer weightBridge;

    // Broker charges
    @Column(name = "broker_amount", nullable = false)
    private Integer brokerAmount;

    // Amount paid for the "up" journey (or any applicable charge)
    @Column(name = "up_amount", nullable = false)
    private Integer upAmount;

    // Amount paid for the "down" journey (or any applicable charge)
    @Column(name = "down_amount", nullable = false)
    private Integer downAmount;

    // Toll charges
    @Column(name = "toll_amount", nullable = false)
    private Integer tollAmount;

    // Diesel expenses for the trip
    @Column(name = "diesel_expenses", nullable = false)
    private Integer dieselExpenses;

    // RTO charges
    @Column(name = "rto_amount", nullable = false)
    private Integer rtoAmount;

    // "PC" amount (possibly referring to some other cost category)
    @Column(name = "pc_amount", nullable = false)
    private Integer pcAmount;

    // Other miscellaneous expenses
    @Column(name = "others_expenses", nullable = false)
    private Integer othersExpenses;

    // Total expenses incurred during the trip
    @Column(name = "total_expenses", nullable = false)
    private Integer totalExpenses;

    // Assuming `DriverInfo` is another entity that contains information about the driver
    @ManyToOne(fetch = FetchType.EAGER)  // Many trips can belong to one driver
    @JoinColumn(name = "driver_id", referencedColumnName = "id", nullable = false)  // Maps the driver to this trip
    private DriverInfo driverInfo;

    // Client request ID associated with the trip
    @Column(name = "client_reqid", nullable = false)
    private String clientReqId;
}
