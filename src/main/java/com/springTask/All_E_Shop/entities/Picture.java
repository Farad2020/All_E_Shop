package com.springTask.All_E_Shop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "pictures")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="url")
    private String url;

    @Column(name="largePicURL")
    private Date addedDate;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<ShopItem> shopItems;

    public Picture(Long id, String url, Date addedDate) {
        this.id = id;
        this.url = url;
        this.addedDate = addedDate;
    }

    public boolean containsShopItem(ShopItem item){
        return shopItems.contains(item);
    }

    public boolean containsShopItemById(Long id){
        return shopItems.stream()
                .anyMatch(item -> item.getId().equals(id));
    }

    //https://www.youtube.com/watch?v=gW2IDLCEFPo&feature=emb_logo
    //41:14

}
