package com.springTask.All_E_Shop.controllers;

import com.springTask.All_E_Shop.entities.*;
import com.springTask.All_E_Shop.services.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
public class HomeController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private UserService userService;

    @Autowired
    private SoldItemRecordsService soldItemRecordsService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PictureService pictureService;


    @Value("${file.avatar.viewPath}")
    private String viewPath;

    @Value("${file.avatar.uploadPath}")
    private String uploadPath;

    @Value("${file.avatar.defaultPicture}")
    private String defaultPicture;



    @GetMapping(value = "/")
    public String index(Model model){
        model.addAttribute("current_user", getUserData());

        List<ShopItem> items = itemService.getAllItems();

        model.addAttribute("items", items);

        List<Brand> brands = itemService.getAllBrands();

        model.addAttribute("brands", brands);

        List<Category> categories = itemService.getAllCategories();

        model.addAttribute("categories", categories);

        return "index";
    }

    @GetMapping(value = "/item/{id}")
    public String details(Model model,
                        @PathVariable (name = "id") Long id){
        model.addAttribute("current_user", getUserData());

        if(itemService.getItem(id) == null){
            return "redirect:/404";
        }

        ShopItem item = itemService.getItem(id);

        model.addAttribute("item", item);

        List<Brand> brands = itemService.getAllBrands();

        model.addAttribute("brands", brands);

        List<Category> categories = itemService.getAllCategories();

        model.addAttribute("categories", categories);

        List<Picture> pictures = pictureService.getPicturesByShopItemId(id);
        model.addAttribute("pictures", pictures);

        List<Comment> comments = commentService.getAllCommentsByItem(item);
        model.addAttribute("comments", comments);

        Role admin = userService.getRole(1L);
        model.addAttribute("admin", admin);

        Role moderator = userService.getRole(2L);
        model.addAttribute("moderator", moderator);


        return "details";
    }


    @GetMapping(value = "/admin")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    //todo, place Preauthorize to all nexessary places
    public String admin(Model model){
        model.addAttribute("current_user", getUserData());

        List<ShopItem> items = itemService.getAllItems();

        model.addAttribute("items", items);

        List<Brand> brands = itemService.getAllBrands();

        model.addAttribute("brands", brands);

        List<Country> countries = brandService.getAllCountries();

        model.addAttribute("countries", countries);

        List<Category> categories = itemService.getAllCategories();

        model.addAttribute("categories", categories);

        return "admin_items";
    }

    @PostMapping(value = "/addItem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String addItem(
            @RequestParam (name = "itemName", defaultValue = "empty") String itemName,
            @RequestParam (name = "itemDesc", defaultValue = "empty") String itemDesc,
            @RequestParam (name = "itemPrice", defaultValue = "0") Double itemPrice,
            @RequestParam (name = "itemRating", defaultValue = "0") Integer itemRating,
            @RequestParam (name = "itemSmallPicUrl", defaultValue = "empty") String itemSmallPicUrl,
            @RequestParam (name = "itemLargePicUrl", defaultValue = "empty") String itemLargePicUrl,
            @RequestParam (name = "itemInTopPage", defaultValue = "false") Boolean itemInTopPage,
            @RequestParam (name = "brand_id", defaultValue = "0L") Long brand_id
            ){

        Brand brand = itemService.getBrand(brand_id);

        if( brand != null ){
            itemService.addItem( new ShopItem(null, itemName, itemDesc, itemPrice, itemRating,
                    itemSmallPicUrl, itemLargePicUrl, new java.sql.Date(System.currentTimeMillis()), itemInTopPage, brand));
        }
        return "redirect:/admin";
    }

    ////////////////////////////////////CHANGE THIS SHIT!!!!//////////////////////////////////////////////////////////
    @GetMapping(value = "/search")
    public String simpleSearch(Model model,
                        @RequestParam (name = "itemName", defaultValue = "") String itemName){
        model.addAttribute("current_user", getUserData());

        List<ShopItem> items = itemService.getItemsByNameOrderedByPriceAsc(itemName);


        model.addAttribute("items", items);

        List<Brand> brands = itemService.getAllBrands();

        model.addAttribute("brands", brands);

        List<Category> categories = itemService.getAllCategories();

        model.addAttribute("categories", categories);

        return "search";
    }

    ////////////////////////////////////CHANGE THIS SHIT!!!!////////////////////////////////////////////////////////
    @GetMapping(value = "/advanced_search")
    public String advancedSearch(Model model,
                        @RequestParam (name = "itemName", defaultValue = "") String itemName,
                        @RequestParam (name = "itemPriceFrom", defaultValue = "0") Double itemFromPrice,
                        @RequestParam (name = "itemPriceTo", defaultValue = "1000000000") Double itemToPrice,
                        @RequestParam (name = "orderAsc", defaultValue = "true") Boolean itemInTAsc,
                        @RequestParam (name = "brandName", defaultValue = "") String brandName){
        model.addAttribute("current_user", getUserData());

        List<ShopItem> items;
        if( itemInTAsc == true ){
            items = itemService.getItemsByBrandNameLikeAndNameLikeAndPriceBetweenOrderByPriceAsc(brandName, itemName, itemFromPrice, itemToPrice);
        }else {
            items = itemService.getItemsByBrandNameLikeAndNameLikeAndPriceBetweenOrderByPriceDesc(brandName, itemName, itemFromPrice, itemToPrice);
        }

        model.addAttribute("items", items);

        List<Brand> brands = itemService.getAllBrands();

        model.addAttribute("brands", brands);

        List<Category> categories = itemService.getAllCategories();

        model.addAttribute("categories", categories);

        return "search";
    }

    @GetMapping(value = "/delete_item/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String deleteItem(Model model,
                             @PathVariable (name = "id") Long id){
        model.addAttribute("current_user", getUserData());

        itemService.deleteItem(id);

        return "redirect:/admin";
    }

    @GetMapping(value = "/update_item/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String updateItem(Model model,
                             @PathVariable (name = "id") Long id){
        model.addAttribute("current_user", getUserData());

        if(itemService.getItem(id) != null)
            model.addAttribute("item", itemService.getItem(id));

        List<Brand> brands = itemService.getAllBrands();

        model.addAttribute("brands", brands);

        List<Country> countries = brandService.getAllCountries();

        model.addAttribute("countries", countries);

        List<Category> categories = itemService.getAllCategories();

        model.addAttribute("categories", categories);

        List<Picture> pictures = pictureService.getAllPictures();

        model.addAttribute("pictures", pictures);

        return "admin_item_edit";
    }

    @PostMapping(value = "/editItem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String editItem(
            @RequestParam (name = "itemId", defaultValue = "0") Long id,
            @RequestParam (name = "itemName", defaultValue = "empty") String itemName,
            @RequestParam (name = "itemDesc", defaultValue = "empty") String itemDesc,
            @RequestParam (name = "itemPrice", defaultValue = "0") Double itemPrice,
            @RequestParam (name = "itemRating", defaultValue = "0") Integer itemRating,
            @RequestParam (name = "itemSmallPicUrl", defaultValue = "empty") String itemSmallPicUrl,
            @RequestParam (name = "itemLargePicUrl", defaultValue = "empty") String itemLargePicUrl,
            @RequestParam (name = "itemInTopPage", defaultValue = "false") Boolean itemInTopPage,
            @RequestParam (name = "brand_id", defaultValue = "0L") Long brand_id
    ){
        ShopItem item = itemService.getItem(id);
        if(item == null){
            return "redirect:/404";
        }

        item.setName(itemName);
        item.setDescription(itemDesc);
        item.setPrice(itemPrice);
        item.setRating(itemRating);
        item.setSmallPicURL(itemSmallPicUrl);
        item.setLargePicURL(itemLargePicUrl);
        item.setInTopPage(itemInTopPage);

        Brand brand = itemService.getBrand(brand_id);
        if(brand != null){
            item.setBrand(brand);
        }

        itemService.saveItem(item);

        return "redirect:/admin";
    }

    @PostMapping(value = "/assign_category")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String assign_category(
            @RequestParam (name = "item_id", defaultValue = "0") Long item_id,
            @RequestParam (name = "category_id", defaultValue = "0") Long category_id
    ){
        Category cat = itemService.getCategory(category_id);
        ShopItem item = itemService.getItem(item_id);

        if(cat != null && item != null){
            List<Category> categories = item.getCategories();
            if( categories == null){
                categories = new ArrayList<>();
            }

            //after checking for presence of category list, based on if category is already in item, remove or add that category
            if( item.containsCategory(cat) ){
                categories.remove(cat);
            }else{
                categories.add(cat);
            }

            itemService.saveItem(item);

        }

        return "redirect:/update_item/" + item_id;
    }

    @GetMapping(value = "/admin_countries")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String admin_countries(Model model){
        model.addAttribute("current_user", getUserData());

        List<Country> countries = brandService.getAllCountries();

        model.addAttribute("countries", countries);
        return "admin_countries";
    }

    @GetMapping(value = "/admin_brands")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String admin_brands(Model model){
        model.addAttribute("current_user", getUserData());

        List<Brand> brands = itemService.getAllBrands();

        model.addAttribute("brands", brands);

        List<Country> countries = brandService.getAllCountries();

        model.addAttribute("countries", countries);
        return "admin_brands";
    }

    @PostMapping(value = "/addCountry")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addCountry(
            @RequestParam (name = "countryName", defaultValue = "empty") String countryName,
            @RequestParam (name = "countryCode", defaultValue = "empty") String countryCode
    ){

        if(countryName != "empty" && countryCode != "empty")
            countryService.addCountry( new Country(null, countryName, countryCode) );

        return "redirect:/admin_countries";
    }

    @PostMapping(value = "/editCountry")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editCountry(
            @RequestParam (name = "countryId", defaultValue = "0") Long id,
            @RequestParam (name = "countryName", defaultValue = "empty") String countryName,
            @RequestParam (name = "countryCode", defaultValue = "empty") String countryCode
    ){
        Country country = countryService.getCountry(id);
        if( country != null){
            country.setCode(countryCode);
            country.setName(countryName);
        }

        countryService.saveCountry(country);

        return "redirect:/admin_countries";
    }

    @GetMapping(value = "/delete_country/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteCountry(Model model,
                             @PathVariable (name = "id") Long id){
        model.addAttribute("current_user", getUserData());

        countryService.deleteCountry(id);

        return "redirect:/admin_countries";
    }

    @PostMapping(value = "/addBrand")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addBrand(
            @RequestParam (name = "brandName", defaultValue = "empty") String brandName,
            @RequestParam (name = "countryId", defaultValue = "0L") Long countryId
    ){
        Country cnt = countryService.getCountry(countryId);

        if( cnt != null ){
            brandService.addBrand(new Brand(null, brandName, cnt));
        }

        return "redirect:/admin_brands";
    }

    @PostMapping(value = "/editBrand")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editBrand(
            @RequestParam (name = "brandId", defaultValue = "0") Long brandId,
            @RequestParam (name = "brandName", defaultValue = "empty") String brandName,
            @RequestParam (name = "countryId", defaultValue = "0") Long countryId
    ){
        Brand brand = brandService.getBrand(brandId);
        Country country = countryService.getCountry(countryId);
        if( brand != null && country != null && brandName != "empty"){
            brand.setName(brandName);
            brand.setCountry(country);
        }

        brandService.saveBrand(brand);

        return "redirect:/admin_brands";
    }

    @GetMapping(value = "/delete_brand/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete_brand(Model model,
                                @PathVariable (name = "id") Long id){
        model.addAttribute("current_user", getUserData());

        brandService.deleteBrand(id);

        return "redirect:/admin_brands";
    }

    @GetMapping(value = "/admin_categories")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String admin_categories(Model model){
        model.addAttribute("current_user", getUserData());

        List<Category> categories = itemService.getAllCategories();

        model.addAttribute("categories", categories);
        return "admin_categories";
    }

    @GetMapping(value = "/delete_category/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete_category(Model model,
                              @PathVariable (name = "id") Long id){
        model.addAttribute("current_user", getUserData());

        itemService.deleteCategory(id);

        return "redirect:/admin_categories";
    }

    @PostMapping(value = "/edit_category")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String edit_category(
            @RequestParam (name = "categoryId", defaultValue = "0") Long id,
            @RequestParam (name = "categoryName", defaultValue = "empty") String categoryName,
            @RequestParam (name = "categoryLogoURL", defaultValue = "empty") String categoryLogoURL
    ){
        Category category = itemService.getCategory(id);
        if( category != null && categoryName != "empty" && categoryLogoURL != "empty"){
            category.setName(categoryName);
            category.setLogoURL(categoryLogoURL);
        }

        itemService.saveCategory(category);

        return "redirect:/admin_categories";
    }

    @PostMapping(value = "/add_category")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String add_category(
            @RequestParam (name = "categoryName", defaultValue = "empty") String categoryName,
            @RequestParam (name = "categoryLogoURL", defaultValue = "empty") String categoryLogoURL
    ){
        if(categoryName != "empty" && categoryLogoURL != "empty")
            itemService.addCategory( new Category(null, categoryName, categoryLogoURL) );

        return "redirect:/admin_categories";
    }


    @GetMapping(value = "/403")
    public String access_denied(Model model){
        model.addAttribute("current_user", getUserData());

        return "403";
    }

    @GetMapping(value = "/404")
    public String page_not_found(Model model){
        model.addAttribute("current_user", getUserData());

        return "404";
    }

    @GetMapping(value = "/login")
    public String login(Model model){
        model.addAttribute("current_user", getUserData());

        List<Category> categories = itemService.getAllCategories();

        model.addAttribute("categories", categories);

        List<Brand> brands = itemService.getAllBrands();

        model.addAttribute("brands", brands);

        return "login";
    }

    @GetMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model){
        model.addAttribute("current_user", getUserData());

        List<Category> categories = itemService.getAllCategories();

        model.addAttribute("categories", categories);

        List<Brand> brands = itemService.getAllBrands();

        model.addAttribute("brands", brands);

        return "profile";
    }

    private User getUserData(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            org.springframework.security.core.userdetails.User secUser
                    = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

            User myUser = userService.getUserByEmail(secUser.getUsername());
            return myUser;
        }

        return null;
    }

    @GetMapping(value = "/register")
    public String register(Model model){
        model.addAttribute("current_user", getUserData());

        List<Category> categories = itemService.getAllCategories();

        model.addAttribute("categories", categories);

        List<Brand> brands = itemService.getAllBrands();

        model.addAttribute("brands", brands);

        return "register";
    }

    @PostMapping(value = "/register")
    public String toRegister(Model model,
                             @RequestParam (name = "user_email", defaultValue = "0") String user_email,
                             @RequestParam (name = "user_full_name", defaultValue = "0") String user_full_name,
                             @RequestParam (name = "user_password", defaultValue = "0") String user_password,
                             @RequestParam (name = "re_user_password", defaultValue = "0") String re_user_password){
        model.addAttribute("current_user", getUserData());



        if( user_password.equals(re_user_password)){

            User newUser = new User();
            newUser.setFullName(user_full_name);
            newUser.setEmail(user_email);
            newUser.setPassword(user_password);

            if(userService.createUser(newUser) != null){
                return "redirect:/login?reg_success";
            }
        }

        return "redirect:/register?error";
    }

    @GetMapping(value = "/admin_users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String show_users(Model model
    ){
        model.addAttribute("current_user", getUserData());

        List<User> users = userService.getAllUsers();

        model.addAttribute("users", users);

        return "admin_users";
    }

    @PostMapping(value = "/addUser")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String add_user(Model model,
                           @RequestParam (name = "user_email", defaultValue = "0") String user_email,
                           @RequestParam (name = "user_full_name", defaultValue = "0") String user_full_name,
                           @RequestParam (name = "user_password", defaultValue = "0") String user_password,
                           @RequestParam (name = "re_user_password", defaultValue = "0") String re_user_password
    ){
        model.addAttribute("current_user", getUserData());

        if( user_password.equals(re_user_password) && user_password != "0"){

            User newUser = new User();
            newUser.setFullName(user_full_name);
            newUser.setEmail(user_email);
            newUser.setPassword(user_password);

            if(userService.createUser(newUser) != null){
                return "redirect:/admin_users";
            }
        }

        return "redirect:/add_user";
    }

    @GetMapping(value = "/delete_user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete_user(Model model,
                                  @PathVariable (name = "id") Long id){
        model.addAttribute("current_user", getUserData());

        userService.deleteUser(id);

        return "redirect:/admin_users";
    }


    @GetMapping(value = "/edit_user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String edit_user(Model model,
                            @PathVariable (name = "id") Long id
    ){
        model.addAttribute("current_user", getUserData());

        User user = userService.getUser(id);

        if(user != null){
            model.addAttribute("user", user);

            List<Role> roles = userService.getRoles();

            model.addAttribute("roles", roles);
            return "admin_user_edit";
        }

        return "403";
    }


    @PostMapping(value = "/edit_user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String to_edit_user(Model model,
                               @RequestParam (name = "user_id", defaultValue = "0") Long user_id,
                               @RequestParam (name = "user_full_name", defaultValue = "") String user_full_name,
                               @RequestParam (name = "new_user_password", defaultValue = "") String new_user_password,
                               @RequestParam (name = "prev_user_password", defaultValue = "") String prev_user_password,
                               @RequestParam (name = "re_user_password", defaultValue = "") String re_user_password
    ){
        model.addAttribute("current_user", getUserData());
        User user = userService.getUser(user_id);

        if( user != null ){
            if( !user_full_name.isEmpty() )
                user.setFullName(user_full_name);

            if(userService.checkEncryptedPassword(user, prev_user_password) && !new_user_password.isEmpty() && new_user_password.equals(re_user_password)){
                userService.setUserPassword(user, new_user_password);
            }

            if(userService.saveUser(user) != null){
                return "redirect:/edit_user/" + user.getId();
            }

        }

        return "redirect:/admin_users?error";
    }

    @PostMapping(value = "/assignRole")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String assign_role(
            @RequestParam (name = "user_id", defaultValue = "0") Long user_id,
            @RequestParam (name = "role_id", defaultValue = "0") Long role_id
    ){
        User user = userService.getUser(user_id);
        Role role = userService.getRole(role_id);

        if(user != null && role != null){
            List<Role> userRoles = user.getRoles();
            if( userRoles == null){
                userRoles = new ArrayList<>();
            }

            //after checking for presence of category list, based on if category is already in item, remove or add that category
            if( user.containsRole(role) ){
                userRoles.remove(role);
            }else{
                userRoles.add(role);
            }

            userService.saveUser(user);

        }

        return "redirect:/edit_user/" + user.getId();
    }


    @PostMapping(value = "/edit_profile")
    @PreAuthorize("isAuthenticated()")
    public String to_edit_profile(Model model,
                               @RequestParam (name = "user_id", defaultValue = "0") Long user_id,
                               @RequestParam (name = "user_full_name", defaultValue = "") String user_full_name,
                               @RequestParam (name = "new_user_password", defaultValue = "") String new_user_password,
                               @RequestParam (name = "prev_user_password", defaultValue = "") String prev_user_password,
                               @RequestParam (name = "re_user_password", defaultValue = "") String re_user_password
    ){
        model.addAttribute("current_user", getUserData());
        User user = userService.getUser(user_id);

        if( user != null ){
            String passwordSet = ""; // used as url parameter

            if( !user_full_name.isEmpty() ){
                user.setFullName(user_full_name);
            }


            if(userService.checkEncryptedPassword(user, prev_user_password) && !new_user_password.isEmpty() && new_user_password.equals(re_user_password)){
                userService.setUserPassword(user, new_user_password);
                passwordSet = "pass_success";
            }

            if(userService.saveUser(user) != null){
                return "redirect:/profile?"+passwordSet;
            }
        }

        return "redirect:/profile?error";
    }


    @GetMapping(value = "/admin_roles")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String show_roles(Model model
    ){
        model.addAttribute("current_user", getUserData());

        List<Role> roles = userService.getRoles();

        model.addAttribute("roles", roles);

        return "admin_roles";
    }

    @GetMapping(value = "/delete_role/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete_role(Model model,
                               @PathVariable (name = "id") Long id){
        model.addAttribute("current_user", getUserData());

        userService.deleteRole(id);

        return "redirect:/admin_roles";
    }

    @PostMapping(value = "/add_role")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String add_role(
            @RequestParam (name = "role_name", defaultValue = "empty") String role_name,
            @RequestParam (name = "role_desc", defaultValue = "empty") String role_desc
    ){
        userService.createRole(new Role(null, role_name, role_desc));

        return "redirect:/admin_roles";
    }

    @PostMapping(value = "/edit_role")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String edit_role(
            @RequestParam (name = "role_id", defaultValue = "0") Long role_id,
            @RequestParam (name = "role_name", defaultValue = "empty") String role_name,
            @RequestParam (name = "role_desc", defaultValue = "empty") String role_desc
    ){
        Role role = userService.getRole(role_id);
        if( role != null){
            role.setName(role_name);
            role.setDescription(role_desc);
        }
        userService.saveRole(role);

        return "redirect:/admin_roles";
    }

    @GetMapping(value="/viewphoto/{url}", produces = {MediaType.IMAGE_JPEG_VALUE} )
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody byte[] viewProfilePhoto(@PathVariable(name="url") String url) throws IOException {
        String pictureUrl = viewPath + defaultPicture;
        if( url != null ){
            pictureUrl = viewPath+url + ".jpg";
        }

        InputStream in;

        try {
            ClassPathResource resource = new ClassPathResource(pictureUrl);
            in = resource.getInputStream();

        }catch (Exception e){
            ClassPathResource resource = new ClassPathResource(viewPath + defaultPicture);
            in = resource.getInputStream();

            e.printStackTrace();
        }

        return IOUtils.toByteArray(in);
    }


    @PostMapping(value = "/upload_avatar")
    @PreAuthorize("isAuthenticated()")
    public String upload_avatar(
            @RequestParam (name = "user_ava") MultipartFile file
    ){

        if(file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/jpg") || file.getContentType().equals("image/png")){
            try{
                User currentUser = getUserData();

                String picname = DigestUtils.sha1Hex("avatar_" + currentUser.getId() + "_!Picture");

                byte[] bytes = file.getBytes();
                Path path = Paths.get(uploadPath + picname + ".jpg");
                Files.write(path, bytes);

                currentUser.setUserAvatar(picname);
                userService.saveUser(currentUser);

                return "redirect:/profile?success";
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "redirect:/profile";
    }


    @GetMapping(value = "/admin_pictures")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String admin_pictures(Model model){
        model.addAttribute("current_user", getUserData());

        List<Picture> pictures = pictureService.getAllPictures();

        model.addAttribute("pictures", pictures);
        return "admin_pictures";
    }

    @GetMapping(value = "/delete_picture/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String delete_picture(Model model,
                                  @PathVariable (name = "id") Long id){
        model.addAttribute("current_user", getUserData());

        pictureService.deletePicture(id);

        return "redirect:/admin_pictures";
    }

    @PostMapping(value = "/edit_picture")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String edit_picture(
            @RequestParam (name = "pictureId", defaultValue = "0") Long id,
            @RequestParam (name = "pictureURL", defaultValue = "empty") String pictureURL
    ){
        Picture picture = pictureService.getPicture(id);
        if( picture != null){
            picture.setUrl(pictureURL);
            pictureService.savePicture(picture);
        }
        return "redirect:/admin_pictures";
    }

    @PostMapping(value = "/add_picture")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String add_picture(
            @RequestParam (name = "pictureURL", defaultValue = "empty") String pictureURL
    ){

        pictureService.addPicture( new Picture(null, pictureURL, new java.sql.Date(System.currentTimeMillis())) );

        return "redirect:/admin_pictures";
    }

    @PostMapping(value = "/assign_picture")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String assign_picture(
            @RequestParam (name = "item_id", defaultValue = "0") Long item_id,
            @RequestParam (name = "picture_id", defaultValue = "0") Long picture_id
    ){
        Picture picture = pictureService.getPicture(picture_id);
        ShopItem item = itemService.getItem(item_id);

        if(picture != null && item != null){
            List<ShopItem> shopItems = picture.getShopItems();
            if( shopItems == null){
                shopItems = new ArrayList<>();
            }

            //after checking for presence of category list, based on if category is already in item, remove or add that category
            if( picture.containsShopItem(item) ){
                shopItems.remove(item);
            }else{
                shopItems.add(item);
            }

            pictureService.savePicture(picture);

        }

        return "redirect:/update_item/" + item_id;
    }

    @GetMapping(value = "/basket")
    public String basket( Model model,
                          HttpSession session){
        model.addAttribute("current_user", getUserData());

        Hashtable<Long, Long> session_basket = (Hashtable<Long, Long>)session.getAttribute("session_basket");

        if(session_basket != null){
            Set<Long> item_ids = session_basket.keySet();
            HashSet<ShopItem> items_set = new HashSet<ShopItem>();

            for (Long item_id : item_ids) {
                if(itemService.getItem(item_id) != null)
                    items_set.add( itemService.getItem(item_id) );
            }

            model.addAttribute("items_set", items_set);
        }

        model.addAttribute("session_basket", session_basket);


        List<Brand> brands = itemService.getAllBrands();

        model.addAttribute("brands", brands);

        List<Category> categories = itemService.getAllCategories();

        model.addAttribute("categories", categories);

        return "basket";
    }

    @PostMapping(value = "/create_basket")
    public String create_basket(Model model,
                                HttpSession session,
                                HttpServletRequest request,
                                HttpServletResponse response,
                                @RequestParam(name = "session_name") String session_name){
        session.setAttribute("user_basket_session", session_name);

        Cookie [] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("JSESSIONID")){
                cookie.setMaxAge(60*60*24); // one day
                response.addCookie(cookie);
                break;
            }
        }

        return "redirect:/basket";
    }

    @PostMapping(value = "/add_to_basket")
    public String add_to_basket(Model model,
                                HttpSession session,
                                HttpServletRequest request,
                                HttpServletResponse response,
                                @RequestParam(name = "item_id") Long item_id,
                                @RequestParam(name = "submit") String button_value){
        Hashtable<Long, Long> session_basket = (Hashtable<Long, Long>)session.getAttribute("session_basket");
        Long session_basket_size = (Long) session.getAttribute("session_basket_size");
        Double session_basket_total = (Double) session.getAttribute("session_basket_total");

        ShopItem desired_item = itemService.getItem(item_id);

        if(session_basket == null){
            session_basket = new Hashtable<>();
            session_basket_size = 0L;
            session_basket_total = 0.0;
        }

        if(desired_item != null){
            if(!session_basket.containsKey(item_id)){
                session_basket.put(item_id, 1L);
                session_basket_size++;
                session_basket_total += desired_item.getPrice();
            }else{

                if(button_value.equals("add")){
                    session_basket.replace(item_id, session_basket.get(item_id) + 1);
                    session_basket_size++;
                    session_basket_total += desired_item.getPrice();
                }else if(button_value.equals("remove")){
                    session_basket_size--;
                    session_basket_total -= desired_item.getPrice();
                    if( session_basket.get(item_id) - 1 > 0 )
                        session_basket.replace(item_id, session_basket.get(item_id) - 1);
                    else
                        session_basket.remove(item_id);

                }

            }
        }

        session.setAttribute("session_basket", session_basket);
        session.setAttribute("session_basket_size", session_basket_size);
        session.setAttribute("session_basket_total", session_basket_total);

        Cookie [] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("JSESSIONID")){
                cookie.setMaxAge(60*60*24); // one day
                response.addCookie(cookie);
                break;
            }
        }

        return "redirect:/basket";
    }

    @PostMapping(value = "/clear_basket")
    public String clear_basket(Model model,
                                HttpSession session,
                                HttpServletRequest request,
                                HttpServletResponse response){
        Hashtable<Long, Long> session_basket = (Hashtable<Long, Long>)session.getAttribute("session_basket");
        Long session_basket_size = (Long) session.getAttribute("session_basket_size");
        Double session_basket_total = (Double) session.getAttribute("session_basket_total");

        if(session_basket != null){

            session_basket = new Hashtable<>();
            session_basket_size = 0L;
            session_basket_total = 0.0;
        }

        session.setAttribute("session_basket", session_basket);
        session.setAttribute("session_basket_size", session_basket_size);
        session.setAttribute("session_basket_total", session_basket_total);

        Cookie [] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("JSESSIONID")){
                cookie.setMaxAge(60*60*24); // one day
                response.addCookie(cookie);
                break;
            }
        }

        return "redirect:/basket";
    }

    @PostMapping(value = "/check_in")
    public String check_in(Model model,
                               HttpSession session,
                               HttpServletRequest request,
                               HttpServletResponse response){
        Hashtable<Long, Long> session_basket = (Hashtable<Long, Long>)session.getAttribute("session_basket");

        if (session_basket != null){
            Set<Long> item_ids = session_basket.keySet();
            HashSet<ShopItem> items_set = new HashSet<>();

            for (Long item_id : item_ids) {
                if(itemService.getItem(item_id) != null)
                    items_set.add( itemService.getItem(item_id) );
            }
            SoldItemRecord record;
            for (ShopItem item : items_set) {
                record = new SoldItemRecord
                        (null, session_basket.get(item.getId()), new java.sql.Date(System.currentTimeMillis()) ,item);
                soldItemRecordsService.addSoldItemRecord(record);
            }
        }

        //Just to delete all prev records. I know that doing this
        session_basket = new Hashtable<>();
        session.setAttribute("session_basket", session_basket);
        session.setAttribute("session_basket_size", 0L);
        session.setAttribute("session_basket_total", 0.0);

        return "redirect:/basket";
    }

    @GetMapping(value = "/admin_sold_item_records")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String admin_sold_item_records(Model model){
        model.addAttribute("current_user", getUserData());

        List<SoldItemRecord> itemRecords = soldItemRecordsService.getAllSoldItemRecords();

        model.addAttribute("itemRecords", itemRecords);
        return "admin_sold_items";
    }

    //Comments
    @PostMapping(value = "/add_comment")
    @PreAuthorize("isAuthenticated()")
    public String add_comment(Model model,
                              @RequestParam (name = "comment_text", defaultValue = "empty") String comment_text,
                              @RequestParam (name = "item_id", defaultValue = "0L") Long item_id){
        model.addAttribute("current_user", getUserData());

        ShopItem shopItem = itemService.getItem(item_id);

        if(shopItem != null){
            Comment comment = new Comment(null, comment_text,
                    new java.sql.Date(System.currentTimeMillis()),
                    shopItem, getUserData() );
            commentService.addComment(comment);
        }

        return "redirect:/item/"+item_id;
    }


    @PostMapping(value = "/edit_comment")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String edit_comment(
            @RequestParam (name = "item_id", defaultValue = "0") Long item_id,
            @RequestParam (name = "comment_id", defaultValue = "0") Long comment_id,
            @RequestParam (name = "comment_text", defaultValue = "empty") String comment_text
    ){
        Comment comment = commentService.getComment(comment_id);
        if(comment != null && getUserData().equals(comment.getAuthor())){
            comment.setComment(comment_text);
        }
        commentService.saveComment(comment);

        return "redirect:/item/"+item_id;
    }

    @GetMapping(value = "/delete_comment/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete_comment(Model model,
                               @PathVariable (name = "id") Long id){
        model.addAttribute("current_user", getUserData());

        Comment comment = commentService.getComment(id);

        if(comment == null){
            return "redirect:/404";
        }

        Long item_id = comment.getShopItem().getId();

        Role admin = userService.getRole(1L);
        Role moderator = userService.getRole(2L);

        if(getUserData().getRoles().contains(admin)
                || getUserData().getRoles().contains(moderator)
                || getUserData().getId().equals(comment.getAuthor().getId())){
            commentService.deleteComment(id);
        }

        return "redirect:/item/"+item_id;
    }
}
