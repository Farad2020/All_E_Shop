package com.springTask.All_E_Shop.services;

import com.springTask.All_E_Shop.entities.Category;
import com.springTask.All_E_Shop.entities.Picture;
import com.springTask.All_E_Shop.entities.ShopItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PictureService {
    Picture addPicture(Picture picture);
    List<Picture> getAllPictures();
    Picture getPicture(Long id);
    void deletePicture(Long id);
    Picture savePicture(Picture picture);
    List<Picture> getPicturesByShopItemId(Long id);

    List<ShopItem> getAllShopItems();
    ShopItem getShopItem(Long id);
    ShopItem saveShopItem(ShopItem item);
    ShopItem addShopItem(ShopItem item);
    void deleteShopItem(Long id);
}
