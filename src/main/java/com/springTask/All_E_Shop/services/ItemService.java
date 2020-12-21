package com.springTask.All_E_Shop.services;

import com.springTask.All_E_Shop.entities.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemService {

    ShopItem addItem(ShopItem item);
    List<ShopItem> getAllItems();
    List<ShopItem> getTopItems();
    ShopItem getItem(Long id);
    void deleteItem(ShopItem item);
    void deleteItem(Long id);
    ShopItem saveItem(ShopItem item);

    List<ShopItem> getItemsByNameOrderedByPriceAsc(String name);
    List<ShopItem> getItemsByNameOrderedByPriceDesc(String name);
    List<ShopItem> getItemsByNameBetweenPricesAsc(String name, double price1, double price2);
    List<ShopItem> getItemsByNameBetweenPricesDesc(String name, double price1, double price2);

    List<Brand> getAllBrands();
    Brand addBrand(Brand brand);
    Brand saveBrand(Brand brand);
    Brand getBrand(Long id);

    List<ShopItem> getItemsByBrandNameLikeAndPriceBetweenOrderByPriceAsc( String brand_name, Double price1, Double price2 );
    List<ShopItem> getItemsByBrandNameLikeAndPriceBetweenOrderByPriceDesc( String brand_name, Double price1, Double price2 );

    List<ShopItem> getItemsByBrandNameLikeAndNameLikeAndPriceBetweenOrderByPriceAsc( String brand_name, String name, Double price1, Double price2 );
    List<ShopItem> getItemsByBrandNameLikeAndNameLikeAndPriceBetweenOrderByPriceDesc( String brand_name, String name, Double price1, Double price2 );

    List<Category> getAllCategories();
    Category getCategory(Long id);
    Category saveCategory(Category category);
    Category addCategory(Category category);
    void deleteCategory(Long id);

}
