#ifndef HAND_H
#define HAND_H

#include "Card.h"
#include "CardFactory.h"
#include <queue>
#include <list>

class Hand {
private:
    std::queue<Card*, std::list<Card*>> pHand;

public:
    Hand();
    Hand(std::istream& input, const CardFactory* cf);
    ~Hand();
    Hand& operator+=(Card* card);
    Card* play();
    Card* top();
    Card* getCard(int pos);
    int numCards();
    void saveHand(std::ofstream& filename);
    friend std::ostream& operator<<(std::ostream& output, Hand& hand);
    std::queue<Card*, std::list<Card*>>* getListOfCards();
};

#endif
