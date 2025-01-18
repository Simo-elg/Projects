#include "Card.h"

Card::~Card() {}

// Implémentation des méthodes des classes dérivées

Blue::Blue(std::string name) {
    this->name = name;
}

int Blue::getCardsPerCoin(int coins) {
    int numCards = -1;
    switch (coins) {
        case 1: numCards = 4; break;
        case 2: numCards = 6; break;
        case 3: numCards = 8; break;
        case 4: numCards = 10; break;
        default: std::cout << "(Blue) Invalid coin value: " << coins << std::endl; break;
    }
    return numCards;
}

std::string Blue::getName() {
    return this->name;
}

void Blue::print(std::ostream& out) {
    out << this->getName()[0];
}

Chili::Chili(std::string name) {
    this->name = name;
}

int Chili::getCardsPerCoin(int coins) {
    int numCards = -1;
    switch (coins) {
        case 1: numCards = 3; break;
        case 2: numCards = 6; break;
        case 3: numCards = 8; break;
        case 4: numCards = 9; break;
        default: std::cout << "(Chili) Invalid coin value: " << coins << std::endl; break;
    }
    return numCards;
}

std::string Chili::getName() {
    return this->name;
}

void Chili::print(std::ostream& out) {
    out << this->getName()[0];
}

// Reste des classes Stink, Green, Soy, Black, Red, Garden à implémenter de manière similaire
