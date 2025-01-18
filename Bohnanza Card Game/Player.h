#ifndef PLAYER_H
#define PLAYER_H

#include <string>
#include <vector>
#include "Card.h"
#include "Hand.h"
#include "Chain.h"
#include "Deck.h"
#include "CardFactory.h"
#include "DiscardPile.h"
#include "TradeArea.h"

class Player {
private:
    std::string pName;  // Nom du joueur
    Hand* pHand;        // Main du joueur
    int pCoins;         // Nombre de pièces du joueur
    std::vector<Chain_Base*> pChains;  // Chaînes du joueur

public:
    Player(std::string name);
    Player(std::istream& input, const CardFactory* cf);

    ~Player();

    std::string getName();  // Retourne le nom du joueur
    int getNumCards();  // Retourne le nombre de cartes dans la main
    int getNumCoins();  // Retourne le nombre de pièces du joueur
    Hand* getHand();  // Retourne la main du joueur
    std::vector<Chain_Base*>* getChains();  // Retourne les chaînes du joueur
    void takeCard(Card* card);  // Ajoute une carte à la main
    void printHand(std::ostream& out, bool full);  // Affiche la main du joueur
    Card* playCard(Card* input = nullptr, bool specified_input = false);  // Joue une carte
    void removeCard(int pos);  // Retire une carte de la main

    void savePlayer(int p_id);  // Sauvegarde l'état du joueur
    void buyThirdChain();  // Permet d'acheter une troisième chaîne
    int getMaxNumChains();  // Retourne le nombre maximum de chaînes autorisées
    int getNumChains();  // Retourne le nombre de chaînes non vides
};

#endif
