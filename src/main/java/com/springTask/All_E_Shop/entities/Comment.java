package com.springTask.All_E_Shop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="comment")
    private String comment;

    @Column(name="addedDate")
    private Date addedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private ShopItem shopItem;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

}
