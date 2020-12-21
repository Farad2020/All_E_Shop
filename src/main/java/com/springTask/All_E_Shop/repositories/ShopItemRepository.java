package com.springTask.All_E_Shop.repositories;

import com.springTask.All_E_Shop.entities.ShopItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ShopItemRepository extends JpaRepository<ShopItem, Long> {
    List<ShopItem> findAllByInTopPageTrue();

    List<ShopItem> findAllByNameLikeOrderByPriceAsc(String name);
    List<ShopItem> findAllByNameLikeOrderByPriceDesc(String name);
    List<ShopItem> findAllByNameLikeAndPriceBetweenOrderByPriceAsc(String name, Double price1, Double price2);
    List<ShopItem> findAllByNameLikeAndPriceBetweenOrderByPriceDesc(String name, Double price1, Double price2);

    List<ShopItem> findAllByBrandNameLikeAndPriceBetweenOrderByPriceAsc( String brand_name, Double price1, Double price2 );
    List<ShopItem> findAllByBrandNameLikeAndPriceBetweenOrderByPriceDesc( String brand_name, Double price1, Double price2 );

    List<ShopItem> findAllByBrandNameLikeAndNameLikeAndPriceBetweenOrderByPriceAsc( String brand_name, String name, Double price1, Double price2 );
    List<ShopItem> findAllByBrandNameLikeAndNameLikeAndPriceBetweenOrderByPriceDesc( String brand_name, String name, Double price1, Double price2 );

}
