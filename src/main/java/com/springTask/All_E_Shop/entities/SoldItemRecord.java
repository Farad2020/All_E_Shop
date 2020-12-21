package com.springTask.All_E_Shop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "soldItems")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoldItemRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="amount")
    private Long amount;

    @Column(name="soldDate")
    private Date soldDate;

    @ManyToOne
    //@Column(name="country")
    private ShopItem shopItem;
}
