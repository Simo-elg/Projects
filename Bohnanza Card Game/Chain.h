#ifndef CHAIN_H
#define CHAIN_H

#include <vector>
#include <typeinfo>
#include <sstream>
#include <cstdlib>
#include <stdexcept>
#include "Card.h"

class CardFactory;

class Chain_Base {
protected:
    std::vector<Card*> chain;
    std::string chainType;
public:
    int getSize();
    std::string getChainType();
    void setChainType(std::string chainType);
    void saveChain(std::ofstream& filename);
    Chain_Base& operator+=(Card* card) {
        chain.push_back(card);
        return *this;
    };
    virtual ~Chain_Base() {};
    friend std::ostream& operator<<(std::ostream& output, const Chain_Base& d);
};

template <typename T = Card> 
class Chain : public virtual Chain_Base {
public:
    Chain() { 
        chainType = typeid(T).name();
        if (std::isdigit(chainType.at(0))) {
            chainType = chainType.substr(1, chainType.size());
        }
    };

    Chain(std::istream& input, const CardFactory* cf) {
        std::string line;
        Card* card = nullptr;
        int count = 0;
        while (std::getline(input, line)) {
            std::istringstream iss(line);
            std::string data;
            if (!(iss >> data)) { continue; }
            count++;
            if (data == "B") card = new Blue;
            else if (data == "C") card = new Chili;
            else if (data == "S") card = new Stink;
            else if (data == "G") card = new Green;
            else if (data == "s") card = new Soy;
            else if (data == "b") card = new Black;
            else if (data == "R") card = new Red;
            else if (data == "g") card = new Garden;
            else {
                std::cout << "(Chain Constructor) Invalid card name in file: " << data << std::endl;
                exit(1);
            }
            if (card != nullptr) (*this) += card;
        }
        std::cout << "Chain with " << count << " cards initialized from file successfully." << std::endl;
    };

    Chain<T>& operator+=(Card* card) {
        if (getSize() == 0)
            chainType = typeid(card).name();
        if (typeid(T) == typeid(card)) {
            chain.push_back(card);
            return *this;
        } else {
            throw std::runtime_error("IllegalType");
        }
    };

    int sell() {
        T elem;
        int value = 0;
        int max_coins = 4;
        if (chain.size() > 0) {
            for (int coin = 1; coin <= max_coins; coin++) {
                if (chain.size() == elem.getCardsPerCoin(coin)) {
                    value = coin;
                    break;
                } else if (chain.size() < elem.getCardsPerCoin(coin)) {
                    value = coin - 1;
                    break;
                }
            }
        }
        if (value == -1) value = 0;
        return value;
    }

    friend std::ostream& operator<<(std::ostream& output, const Chain<Card*>& d);
};

#endif
