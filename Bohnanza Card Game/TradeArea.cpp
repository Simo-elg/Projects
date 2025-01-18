#include "TradeArea.h"
#include "Card.h"
#include <iostream>
#include <fstream>

bool TradeArea::legal(Card* card) {
    for (Card* c : tradeAr) {
        if (c->getName() == card->getName()) {
            return true;
        }
    }
    return false;
}

Card* TradeArea::trade(std::string card) {
    Card* cardFound = nullptr;
    for (auto it = tradeAr.begin(); it != tradeAr.end(); ++it) {
        if ((*it)->getName() == card) {
            cardFound = *it;
            tradeAr.erase(it);
            break;
        }
    }
    return cardFound;
}

int TradeArea::numCards() {
    return tradeAr.size();
}

std::ostream& operator<<(std::ostream& output, const TradeArea& tr_ar) {
    for (auto card : tr_ar.tradeAr) {
        output << card->getName()[0] << " ";
    }
    return output;
}

void TradeArea::saveTradeArea(std::ofstream& filename) {
    for (auto card : tradeAr) {
        card->saveCard(filename);
        filename << std::endl;
    }
}

std::list<Card*> TradeArea::getListOfCards() {
    return tradeAr;
}
