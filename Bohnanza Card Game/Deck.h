#ifndef DECK_H
#define DECK_H

#include "Card.h"
#include <vector>
#include <iostream>

class CardFactory;

class Deck : public std::vector<Card*> {
public:
    Deck();
    Deck(std::istream& input, const CardFactory* cf);
    Deck(const Deck& d);
    ~Deck();
    Card* draw();
    Deck& operator=(const Deck& d);
    friend std::ostream& operator<<(std::ostream& output, const Deck& d);
    void saveDeck(std::ofstream& filename);
};

#endif
