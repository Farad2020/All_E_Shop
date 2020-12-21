package com.springTask.All_E_Shop.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopItemLike {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private Integer rating; // Just rating, from 0 to 5
    private String smallPicURL;
    private String largePicURL;
    private Date addedDate;
    private boolean inTopPage;

    public int getEmptyStars(){
        return 5 - rating;
    }
}
