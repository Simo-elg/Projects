#include "CardFactory.h"
#include "Deck.h"
#include <algorithm>
#include <random>

using namespace std;

CardFactory* CardFactory::instance = nullptr;  // initialize the instance to null

/**
 * @brief Creates an instance of CardFactory following the singleton pattern
 * 
 * @return CardFactory* 
 */
CardFactory* CardFactory::getFactory() {
    if (!instance)
        instance = new CardFactory;
    return instance;
}

/**
 * @brief Returns the deck of cards
 * 
 * @return Deck* 
 */
Deck* CardFactory::getDeck() {
    unsigned seed = 0;
    shuffle(deck->begin(), deck->end(), default_random_engine(seed)); // shuffle 
    return deck;
}

/**
 * @brief Construct a new Card Factory object
 * 
 */
CardFactory::CardFactory() {
    deck = new Deck();

    // Add 20 Blue Cards
    for (int i = 0; i < 20; i++) {
        Blue* b = new Blue;
        deck->push_back(b);
    }

    // Add 18 Chili Cards
    for (int i = 0; i < 18; i++) {
        Chili* c = new Chili;
        deck->push_back(c);
    }

    // Add 16 Stink Cards
    for (int i = 0; i < 16; i++) {
        Stink* s = new Stink;
        deck->push_back(s);
    }

    // Add 14 Green Cards
    for (int i = 0; i < 14; i++) {
        Green* g = new Green;
        deck->push_back(g);
    }

    // Add 12 Soy Cards
    for (int i = 0; i < 12; i++) {
        Soy* s = new Soy;
        deck->push_back(s);
    }

    // Add 10 Black Cards
    for (int i = 0; i < 10; i++) {
        Black* b = new Black;
        deck->push_back(b);
    }

    // Add 8 Red Cards
    for (int i = 0; i < 8; i++) {
        Red* r = new Red;
        deck->push_back(r);
    }

    // Add 6 Garden Cards
    for (int i = 0; i < 6; i++) {
        Garden* g = new Garden;
        deck->push_back(g);
    }
}
