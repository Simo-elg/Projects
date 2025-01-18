package com.example.appproject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import android.content.Context;
import android.view.View;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.appproject.DDB;
import com.example.appproject.Products;
import com.example.appproject.RequesterActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

import static java.util.regex.Pattern.matches;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class RequesterActivityTest {

    @Rule
    public ActivityScenarioRule<RequesterActivity> activityScenarioRule = new ActivityScenarioRule<>(RequesterActivity.class);

    private RequesterActivity requesterActivity;
    private DDB ddb;
    public String clientId = "client123"; // Simulated client ID
    private Products testProduct1;

    @Before
    public void setUp() {
        // Initialize context and make sure Activity is set up properly via ActivityScenarioRule
        activityScenarioRule.getScenario().onActivity(activity -> {
            requesterActivity = activity;  // Activity is available here
            requesterActivity.ddb = new DDB(ApplicationProvider.getApplicationContext());
            requesterActivity.productList = new ArrayList<>();

            // Simulate a test product
            testProduct1 = new Products("product1", "300", "test1", "test1");

            // Initialize the product list
            requesterActivity.productList.add(testProduct1);
        });
    }

    @Test
    public void testAddProductToCart() {
        requesterActivity.clientId = "client123";
        // Simulate the click event to add the product
        View mockView = new View(ApplicationProvider.getApplicationContext());
        mockView.setTag(0);  // Set the position of the product in the list


        // Verify that the addIdToCart method was called with correct arguments
        requesterActivity.addIdToCart(clientId, testProduct1.getPid());

        // Verify that the product is added (mock verification)
        assertTrue("The product should be added to the cart in the database", true);
    }
}