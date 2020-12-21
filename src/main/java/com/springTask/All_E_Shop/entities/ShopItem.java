package com.springTask.All_E_Shop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopItem{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="description", columnDefinition = "TEXT")
    private String description;

    @Column(name="price")
    private Double price;

    @Column(name="rating")
    private Integer rating;

    @Column(name="smallPicURL")
    private String smallPicURL;

    @Column(name="largePicURL")
    private String largePicURL;

    @Column(name="addedDate")
    private Date addedDate;

    @Column(name="inTopPage")
    private boolean inTopPage; // if true, then this item will be in top of index page

    @ManyToOne(fetch = FetchType.EAGER)
    private Brand brand;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Category> categories;

    public ShopItem(Long id, String name, String description, Double price, Integer rating, String smallPicURL, String largePicURL, Date addedDate, boolean inTopPage, Brand brand) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.smallPicURL = smallPicURL;
        this.largePicURL = largePicURL;
        this.addedDate = addedDate;
        this.inTopPage = inTopPage;
        this.brand = brand;
    }

    public Integer getEmptyStars(){
        return 5 - rating;
    }

    public boolean containsCategory(Category cat){
        return categories.contains(cat);
    }
}
