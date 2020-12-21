package com.springTask.All_E_Shop.services.impl;

import com.springTask.All_E_Shop.entities.Picture;
import com.springTask.All_E_Shop.entities.ShopItem;
import com.springTask.All_E_Shop.repositories.PictureRepository;
import com.springTask.All_E_Shop.repositories.ShopItemRepository;
import com.springTask.All_E_Shop.services.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private ShopItemRepository shopItemRepository;


    @Override
    public Picture addPicture(Picture picture) {
        return pictureRepository.save(picture);
    }

    @Override
    public List<Picture> getAllPictures() {
        return pictureRepository.findAll();
    }

    @Override
    public Picture getPicture(Long id) {
        if (pictureRepository.existsById(id))
            return pictureRepository.getOne(id);
        return null;
    }

    @Override
    public void deletePicture(Long id) {
        if(pictureRepository.existsById(id))
            pictureRepository.deleteById(id);
    }

    @Override
    public Picture savePicture(Picture picture) {
        return pictureRepository.save(picture);
    }

    @Override
    public List<Picture> getPicturesByShopItemId(Long id) {
        List<Picture> all_pictures =  pictureRepository.findAll();
        List<Picture> item_pictures = new ArrayList<>();
        for (Picture pic : all_pictures) {
            if(pic.containsShopItemById(id))
                item_pictures.add(pic);
        }
        return item_pictures;
    }

    @Override
    public List<ShopItem> getAllShopItems() {
        return shopItemRepository.findAll();
    }

    @Override
    public ShopItem getShopItem(Long id) {
        return shopItemRepository.getOne(id);
    }

    @Override
    public ShopItem saveShopItem(ShopItem item) {
        return shopItemRepository.save(item);
    }

    @Override
    public ShopItem addShopItem(ShopItem item) {
        return shopItemRepository.save(item);
    }

    @Override
    public void deleteShopItem(Long id) {
        shopItemRepository.deleteById(id);
    }

}
