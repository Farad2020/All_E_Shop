package com.springTask.All_E_Shop.services.impl;

import com.springTask.All_E_Shop.entities.*;
import com.springTask.All_E_Shop.repositories.BrandRepository;
import com.springTask.All_E_Shop.repositories.CategoryRepository;
import com.springTask.All_E_Shop.repositories.PictureRepository;
import com.springTask.All_E_Shop.repositories.ShopItemRepository;
import com.springTask.All_E_Shop.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ShopItemRepository shopItemRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public ShopItem addItem(ShopItem item) {
        return shopItemRepository.save(item);
    }

    @Override
    public List<ShopItem> getAllItems() {
        return shopItemRepository.findAll();
    }

    @Override
    public List<ShopItem> getTopItems() {
        return shopItemRepository.findAllByInTopPageTrue();
    }

    @Override
    public ShopItem getItem(Long id) {
        if(shopItemRepository.existsById(id))
            return shopItemRepository.getOne(id);
        else
            return null;
    }

    @Override
    public void deleteItem(ShopItem item) {
        if(shopItemRepository.existsById(item.getId()))
            shopItemRepository.delete(item);
    }

    @Override
    public void deleteItem(Long id) {
        if(shopItemRepository.existsById(id))
            shopItemRepository.deleteById(id);
    }

    @Override
    public ShopItem saveItem(ShopItem item) {
        return shopItemRepository.save(item);
    }

    @Override
    public List<ShopItem> getItemsByNameOrderedByPriceAsc(String name) {
        return shopItemRepository.findAllByNameLikeOrderByPriceAsc("%" + name + "%");
    }

    @Override
    public List<ShopItem> getItemsByNameOrderedByPriceDesc(String name) {
        return shopItemRepository.findAllByNameLikeOrderByPriceDesc("%" + name + "%");
    }

    @Override
    public List<ShopItem> getItemsByNameBetweenPricesAsc(String name, double price1, double price2) {
        return shopItemRepository.findAllByNameLikeAndPriceBetweenOrderByPriceAsc("%" + name + "%", price1, price2);
    }

    @Override
    public List<ShopItem> getItemsByNameBetweenPricesDesc(String name, double price1, double price2) {
        return shopItemRepository.findAllByNameLikeAndPriceBetweenOrderByPriceDesc("%" + name + "%", price1, price2);
    }

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand addBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand getBrand(Long id) {
        if(brandRepository.existsById(id))
            return brandRepository.getOne(id);
        else
            return null;
    }

    public List<ShopItem> getItemsByBrandNameLikeAndPriceBetweenOrderByPriceAsc( String brand_name, Double price1, Double price2 ){
        return shopItemRepository.findAllByNameLikeAndPriceBetweenOrderByPriceDesc("%" + brand_name + "%", price1, price2);
    }
    public List<ShopItem> getItemsByBrandNameLikeAndPriceBetweenOrderByPriceDesc( String brand_name, Double price1, Double price2 ){
        return shopItemRepository.findAllByNameLikeAndPriceBetweenOrderByPriceDesc("%" + brand_name + "%", price1, price2);
    }

    public List<ShopItem> getItemsByBrandNameLikeAndNameLikeAndPriceBetweenOrderByPriceAsc( String brand_name, String name, Double price1, Double price2 ){
        return shopItemRepository.findAllByBrandNameLikeAndNameLikeAndPriceBetweenOrderByPriceAsc("%" + brand_name + "%", "%" + name + "%", price1, price2);
    }
    public List<ShopItem> getItemsByBrandNameLikeAndNameLikeAndPriceBetweenOrderByPriceDesc( String brand_name, String name, Double price1, Double price2 ){
        return shopItemRepository.findAllByBrandNameLikeAndNameLikeAndPriceBetweenOrderByPriceDesc("%" + brand_name + "%", "%" + name + "%", price1, price2);

    }

    //Categories work


    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategory(Long id) {
        if(categoryRepository.existsById(id))
            return categoryRepository.getOne(id);
        else
            return null;
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        if(categoryRepository.existsById(id))
            categoryRepository.deleteById(id);
    }

}
