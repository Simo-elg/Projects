package com.example.appproject;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class ComponentTest {

    // Réinistialisation du compteur "nb_comp" avant chaque test
    @Before
    public void resetComponentCount() {
        Component.nb_comp = 0;
    }

    @Rule
    public ActivityScenarioRule<ConcreteActivity> activityRule = new ActivityScenarioRule<>(ConcreteActivity.class);

    @Test
    public void testComponentInitialization() {
        activityRule.getScenario().onActivity(activity -> {
            // Créer une instance de la sous-classe concrète pour tester l'initialisation
            Component component = new Component("Type1", "SubType1", "Description1", 10, "$100") {
                // Implémentation fictive de la classe abstraite Component
            };

            // Vérification que l'objet est bien initialisé
            assertNotNull(component);
            assertNotNull(component.getType());
            assertNotNull(component.getPrice());
            assertNotNull(component.getDescription());
            assertNotNull(component.getQuantite());
        });
    }

    @Test
    public void testComponentValues() {
        activityRule.getScenario().onActivity(activity -> {
            // Créer une instance de la sous-classe concrète avec des valeurs spécifiques
            Component component = new Component("Type1", "SubType1", "Description1", 10, "$100") {
                // Implémentation fictive de la classe abstraite Component
            };

            // Vérification des valeurs des attributs
            assertEquals("Type1", component.getType());
            assertEquals("SubType1", component.getSousType());
            assertEquals("Description1", component.getDescription());
            assertEquals(10, component.getQuantite());
            assertEquals("$100", component.getPrice());
        });
    }

    @Test
    public void testToString() {
        activityRule.getScenario().onActivity(activity -> {
            // Créer une instance de la sous-classe concrète pour tester toString()
            Component component = new Component("Type1", "SubType1", "Description1", 10, "$100") {
                // Implémentation fictive de la classe abstraite Component
            };

            // Vérifier que toString() retourne bien le bon format
            String expectedString = "Component ID: 0, Type: Type1, Sous-type: SubType1, Price: $100";
            assertEquals(expectedString, component.toString());
        });
    }

    @Test
    public void testComponentCount() {
        activityRule.getScenario().onActivity(activity -> {
            // Créer plusieurs instances pour tester le compteur
            Component component1 = new Component("Type1", "SubType1", "Description1", 10, "$100") {
                // Implémentation fictive de la classe abstraite Component
            };
            Component component2 = new Component("Type2", "SubType2", "Description2", 15, "$200") {
                // Implémentation fictive de la classe abstraite Component
            };

            // Vérifier que le nombre de composants est correct
            assertEquals(2, Component.nb_comp);
        });
    }

    @Test
    public void testSettersAndGetters() {
        activityRule.getScenario().onActivity(activity -> {
            // Créer une instance de la sous-classe concrète de Component
            Component component = new Component("Type1", "SubType1", "Description1", 10, "$100") {
                // Implémentation fictive de la classe abstraite Component
            };

            // Tester les setters et getters
            component.setType("NewType");
            component.setPrice("$150");

            // Vérification des valeurs après modification
            assertEquals("NewType", component.getType());
            assertEquals("$150", component.getPrice());
        });
    }
}
