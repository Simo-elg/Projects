package com.example.appproject;

import android.content.Intent;
import android.view.View;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.contrib.RecyclerViewActions;

import com.example.appproject.CartActivity;
import com.example.appproject.CartModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class CartActivityTest {

    @Rule
    public ActivityScenarioRule<CartActivity> activityRule =
            new ActivityScenarioRule<>(new Intent(ApplicationProvider.getApplicationContext(), CartActivity.class));

    private CartActivity cartActivity;
    public String clientId = "client123";
    private DDB ddb;
    private Products testProduct1;

    @Before
    public void setUp() {
        // Préparer des éléments de test dans le panier
        activityRule.getScenario().onActivity(activity -> {
            cartActivity = activity;
            cartActivity.ddb = new DDB(ApplicationProvider.getApplicationContext());
            cartActivity.cartItems = new ArrayList<>();
            cartActivity.cartItems.add(new CartModel("product1", "Test Product 1", "100", "Description 1", "image1.png", "1", "Electronics"));
            cartActivity.cartItems.add(new CartModel("product2", "Test Product 2", "200", "Description 2", "image2.png", "2", "Books"));

            activity.cart_adapter.notifyDataSetChanged();
        });
    }


}
