package com.springTask.All_E_Shop.db;

import com.springTask.All_E_Shop.models.ShopItemLike;

import java.util.ArrayList;

public class DBManager {
/*
    private static ArrayList<ShopItemLike> items = new ArrayList<>();
    private static Long id = 4L;

    static {
        items.add( new ShopItemLike(1L, "PlayStation 5", "Real Desc", 6900, 420, 5, "https://www.ixbt.com/img/n1/news/2020/8/3/PlayStation-5-UI_large.jpg") );
        items.add( new ShopItemLike(2L, "PlayStation 3", "Real Desc", 6999, 300, 4, "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7e/PS4-Console-wDS4.jpg/1200px-PS4-Console-wDS4.jpg") );
        items.add( new ShopItemLike(3L, "PlayStation 4", "Real Desc", 5970, 50, 5, "https://www.ixbt.com/short/images/2017/Mar/dims_008.jpg") );
    }

    public static ArrayList<ShopItemLike> getAllItems(){
        return items;
    }

    public static void addShopItem( ShopItemLike item ){
        item.setId(id);
        items.add(item);
        id++;
    }
*/
    /*
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static Long id = 4L;
    static {
        tasks.add( new Task(1L, "Task 1", "Real Desc", new Date(System.currentTimeMillis()), false ) );
        tasks.add( new Task(2L, "Task 2", "Task 2 Real Desc", new Date(System.currentTimeMillis()), true ) );
        tasks.add( new Task(3L, "Task 3", "Real Desc for Task 3", new Date(System.currentTimeMillis()), false ) );
    }

    public static ArrayList<Task> getTasks(){
        return tasks;
    }

    public static void addTask( Task task ){
        task.setId(id);
        tasks.add(task);
        id++;
    }

    public static Task getTask( Long id ){
        for ( Task t: tasks ) {
            if( t.getId() == id)
                return t;
        }
        return null;
    }

    public static void updateTask( Task task ){
        for ( Task t: tasks ) {
            if( t.getId().equals(task.getId())){
                t.setName(task.getName());
                t.setDescription(task.getDescription());
                t.setDeadlineDate(task.getDeadlineDate());
                t.setCompleted(task.isCompleted());
                break;
            }
        }
    }

    public static void deleteTask( Long id ){
        for ( Task t: tasks ) {
            if( t.getId() == id){
                tasks.remove(t);
                return;
            }
        }
    }

    public static ArrayList<Task> getFilteredTasks( String name, Date start_d, Date end_d, Boolean isCompleted ){
        //bad filter, redo later
        ArrayList<Task> filtered_tasks = new ArrayList<>();
            for ( Task t: tasks ) {
                if(!start_d.before (end_d) && !start_d.after (end_d)
                        && t.getName().toLowerCase().contains(name.toLowerCase())
                        && t.isCompleted() == isCompleted){
                    filtered_tasks.add(t);
                }else if( t.getName().toLowerCase().contains(name.toLowerCase()) && t.getDeadlineDate().before (end_d) &&
                        t.getDeadlineDate().after (start_d) && t.isCompleted() == isCompleted){
                    filtered_tasks.add(t);
                }
        }
        return filtered_tasks;
    }
    */

}
