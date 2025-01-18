#ifndef TABLE_H
#define TABLE_H

#include "Player.h"
#include "Deck.h"
#include "DiscardPile.h"
#include "TradeArea.h"
#include "CardFactory.h"

class Table {
private:
    Player& p1;
    Player& p2;
    DiscardPile& dp;
    TradeArea& tradeAr;
    Deck& deck;
    CardFactory& cf;
    int currentPlayer;

public:
    Table(Player& p1, Player& p2, DiscardPile& dp, TradeArea& ta, Deck& d, CardFactory& cf);
    void reloadDeck();
    Deck* getDeck();
    void reloadPlayer(int p_id);
    void reloadDiscardPile();
    void reloadTradeArea();
    void saveTable();
    Player* getPlayer(int id);
    DiscardPile* getDiscardPile();
    TradeArea* getTradeArea();
    bool win(std::string& pName);
    void printHand(bool in);
};

#endif
