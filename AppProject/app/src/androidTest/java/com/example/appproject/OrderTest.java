package com.example.appproject;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class OrderTest {

    @Rule
    public ActivityScenarioRule<RequesterActivity> activityScenarioRule = new ActivityScenarioRule<>(RequesterActivity.class);

    private Order testOrder;
    private HardwareComponent testComponent1;
    private HardwareComponent testComponent2;

    @Before
    public void setUp() {
        // Cette partie garantit que l'activité est correctement initialisée avant d'exécuter les tests
        activityScenarioRule.getScenario().onActivity(activity -> {
            // Initialisation de la commande
            testOrder = new Order();

            // Initialisation des composants
            testComponent1 = new HardwareComponent("Hardware", "TypeA", "Test Description", 5, "100.0", "220V", "50W", "30x30x30 cm");
            testComponent2 = new HardwareComponent("Hardware", "TypeB", "Another Description", 3, "150.0", "110V", "75W", "20x20x20 cm");
        });
    }

    @Test
    public void testOrderInitialization() {
        // Vérifier l'initialisation de l'objet Order
        assertEquals("0", testOrder.getOrderId()); // L'ID de la commande doit être '0' au départ
        assertEquals("awaiting acceptance", testOrder.getState()); // L'état initial de la commande doit être "awaiting acceptance"
        assertTrue(testOrder.getComponents().isEmpty()); // La commande ne doit pas avoir de composants au départ
    }

    @Test
    public void testAddComponentToOrder() {
        // Ajouter un composant à la commande
        testOrder.getComponents().add(testComponent1);

        // Vérifier que le composant a été ajouté correctement
        assertEquals(1, testOrder.getComponents().size()); // La commande doit contenir 1 composant
        assertEquals(testComponent1, testOrder.getComponents().get(0)); // Le composant ajouté doit être testComponent1
    }

    @Test
    public void testAddMultipleComponents() {
        // Ajouter plusieurs composants à la commande
        testOrder.getComponents().add(testComponent1);
        testOrder.getComponents().add(testComponent2);

        // Vérifier que les composants ont été ajoutés correctement
        assertEquals(2, testOrder.getComponents().size()); // La commande doit contenir 2 composants
        assertEquals(testComponent1, testOrder.getComponents().get(0)); // Le premier composant doit être testComponent1
        assertEquals(testComponent2, testOrder.getComponents().get(1)); // Le deuxième composant doit être testComponent2
    }

    @Test
    public void testOrderStateChange() {
        // Changer l'état de la commande
        testOrder.setState("shipped");

        // Vérifier que l'état a changé
        assertEquals("shipped", testOrder.getState()); // L'état de la commande doit être "shipped"
    }

    @Test
    public void testOrderMessage() {
        // Ajouter un message à la commande
        testOrder.setMessage("Order shipped successfully");

        // Vérifier que le message a été ajouté
        assertEquals("Order shipped successfully", testOrder.getMessage());
    }
}
