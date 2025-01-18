#ifndef CARDFACTORY_H
#define CARDFACTORY_H

#include "Card.h"  // Inclure Card.h car CardFactory utilise des cartes

// Déclaration anticipée de la classe Deck
class Deck; 

class CardFactory {
private:
    Deck* deck;  // Pointeur vers le Deck
    CardFactory();  // Constructeur privé pour implémentation du Singleton

public:
    static CardFactory* instance;  // Instance singleton
    static CardFactory* getFactory();  // Méthode d'accès singleton
    Deck* getDeck();  // Méthode pour obtenir le deck
};

#endif
