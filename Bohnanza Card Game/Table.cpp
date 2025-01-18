#include "Table.h"
#include <fstream>
#include <iostream>

Table::Table(Player& p1, Player& p2, DiscardPile& dp, TradeArea& ta, Deck& d, CardFactory& cf)
    : p1(p1), p2(p2), dp(dp), tradeAr(ta), deck(d), cf(cf), currentPlayer(0) {}

void Table::reloadDeck() {
    deck = cf.getDeck();
}

Deck* Table::getDeck() {
    return &deck;
}

void Table::reloadPlayer(int p_id) {
    if (p_id == 1) {
        p1 = Player(std::cin, &cf);
    } else {
        p2 = Player(std::cin, &cf);
    }
}

void Table::reloadDiscardPile() {
    dp = DiscardPile(std::cin, &cf);
}

void Table::reloadTradeArea() {
    tradeAr = TradeArea(std::cin, &cf);
}

void Table::saveTable() {
    std::ofstream file;
    file.open("table_state.txt");
    file << p1 << p2 << std::endl;
    file.close();
}

Player* Table::getPlayer(int id) {
    return id == 0 ? &p1 : &p2;
}

DiscardPile* Table::getDiscardPile() {
    return &dp;
}

TradeArea* Table::getTradeArea() {
    return &tradeAr;
}

bool Table::win(std::string& pName) {
    bool win = false;
    if (deck.getListOfCards()->size() == 0) {
        if (p1.getNumCoins() > p2.getNumCoins()) {
            pName = p1.getName();
        } else if (p1.getNumCoins() < p2.getNumCoins()) {
            pName = p2.getName();
        } else {
            pName = "Equality";
        }
        win = true;
    }
    return win;
}

void Table::printHand(bool in) {
    Player* current = currentPlayer == 0 ? &p1 : &p2;
    current->printHand(std::cout, in);
}
