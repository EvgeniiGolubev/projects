package com.company.kitchen;

import com.company.ConsoleHelper;
import com.company.Tablet;

import java.io.IOException;
import java.util.List;

/**
 * В классе Order (заказ) должна быть информация, относящаяся к списку выбранных пользователем блюд.
 */
public class Order {
    private final Tablet tablet;
    protected List<Dish> dishes;

    public Order(Tablet tablet) throws IOException {
        this.tablet = tablet;
        initDishes();
    }

    public int getTotalCookingTime() {
        return dishes.stream().mapToInt(Dish::getDuration).sum();
    }

    public boolean isEmpty() {
        return dishes.isEmpty();
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public Tablet getTablet() {
        return tablet;
    }

    protected void initDishes() throws IOException {
        this.dishes = ConsoleHelper.getAllDishesForOrder();
    }

    @Override
    public String toString() {
        if (dishes.isEmpty()) return "";
        return String.format("Your order: %s of %s, cooking time %dmin", dishes.toString(), tablet.toString(), getTotalCookingTime());
    }
}
