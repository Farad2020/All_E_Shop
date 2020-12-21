# Welcome to All-E Shop!

Hi! This is Markdown file for project **All-E Shop**. To learn more about this project, just read me.


# Project Description

> **All-E Shop** is my web application, built on Spring Boot. For regular users, this app is for browsing through Shop Items, Searching, adding to basket and "buying them". 
> That means, project has some library of Shop Items, stored in database, that can be interacted. Also, Users can register, have their own account, and after that even leave comments to items. 
> Shop Item, which is main the main entity of this project, dependent on many other entities, like categories, brands.
> The task description or "project plan" files located in "Tasks"

# Starting the Project

To start the application, you should have any idea that can boot Spring Boot Project, and download following dependencies to work with this project properly:
 1. Java v. 11
 2. Spring Boot v. 2.3.4
 2. JQuery v. 3.5.*
 3. Bootstrap v. 4.5.*
 4. Spring Boot Data Jpa
 5. Spring Boot Thymeleaf
 6. Lombok
 7. Mysql Connector
 8. Spring Boot Starter Web
 9. Spring Boot Spring Security 5
 10. Spring Boot Security
 11. Spring Boot Maven plugins
 
 **If version of the dependency wasn't written, the latest version by 21.12.2020 was used.**
 Also, you should be connected to MySQL database. Configuration for that lie within application.properties file.

# About Structure, Simplified
Since this was my first "Big" project on Spring Boot, the structure is a bit flawed in a way, that I have only one controller(HomeController) that manages everything.
In other parts, the workflow of project should be acceptable. Due to having many entities, Services that try to get access to that entities through Repositories, that give access to that entities.
Also security configuration and web configurations are present.
All the mentioned element lie inside src->main->java->com->springTask->All_E_Shop.
Media Files, CSS and JS files located in src->main->resources->static.
Html templates located in src->main->resources.
Language translations located in src->main->resources under bundle messages.

To load most of the views, loading templates to **_Layout.cshtml** is used is used. 
> And thats basically it!

##### ~~Screenshots From Project~~ [_Low probability to being added_]

# Some Sources
[Zhuanyshev.kz -> Where I got all the necessary info and tasks](https://zhuanyshev.kz/home)

["How to write a good readme"](https://medium.com/@meakaakka/a-beginners-guide-to-writing-a-kickass-readme-7ac01da88ab3)

[My Github](https://docs.github.com/Farad2020)