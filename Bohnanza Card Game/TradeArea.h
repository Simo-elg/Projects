#ifndef TRADEAREA_H
#define TRADEAREA_H

#include <list>
#include "Card.h"
#include <fstream>

class TradeArea {
private:
    std::list<Card*> tradeAr;

public:
    bool legal(Card* card);
    Card* trade(std::string card);
    int numCards();
    void saveTradeArea(std::ofstream& filename);
    std::list<Card*> getListOfCards();
    friend std::ostream& operator<<(std::ostream& output, const TradeArea& tr_ar);
};

#endif
