#include "Hand.h"

Hand& Hand::operator+=(Card* card) {
    pHand.push(card);  // Ajoute le card à la main
    return *this;
}


/**
 * @brief Returns and removes the top card from the player's hand
 * 
 * @return Card* 
 */
Card* Hand::play() {
    Card* card = pHand.front();
    pHand.pop(); // remove the first element
    return card;
}

/**
 * @brief Returns the card at the top of the hand
 * 
 * @return Card* 
 */
Card* Hand::top() {
    return pHand.front();
}

/**
 * @brief Returns the number of cards inside the hand
 * 
 * @return int 
 */
int Hand::numCards() {
    return pHand.size();
}

/**
 * @brief Returns the card at a specific position in the hand
 * 
 * @param pos 
 * @return Card* 
 */
Card* Hand::getCard(int pos) {
    Card* card = nullptr; // removed card to return
    if (pos > pHand.size() - 1) {
        std::cout << "(getCard) The index " << pos << " cannot be used. Current size of the hand = " << pHand.size() << std::endl;
    } else {
        std::queue<Card*, std::list<Card*>> temp;
        Card* temp_card = nullptr;
        int find_idx = 0;
        while (!pHand.empty()) {
            if (find_idx++ == pos) {
                card = pHand.front();
            }
            temp_card = pHand.front();
            pHand.pop();
            temp.push(temp_card);
        }

        while (!temp.empty()) {
            temp_card = temp.front();
            temp.pop();
            pHand.push(temp_card);
        }
    }

    return card;
}

/**
 * @brief Insertion operator to display the content of the Hand object
 * 
 * @param output 
 * @param hand 
 * @return std::ostream& 
 */
std::ostream& operator<<(std::ostream& output, Hand& hand) {
    for (int pos = 0; pos < hand.numCards(); pos++) {
        output << hand.getCard(pos) << std::endl;
    }
    return output;
}

/**
 * @brief Writes the hand's cards inside a file
 * 
 * @param filename 
 */
void Hand::saveHand(std::ofstream& filename) {
    Card* card = nullptr;
    std::queue<Card*, std::list<Card*>> temp;
    Card* temp_card = nullptr;
    int find_idx = 0;
    while (!pHand.empty()) {
        temp_card = pHand.front();
        temp_card->saveCard(filename);
        filename << std::endl;
        pHand.pop();
        temp.push(temp_card);
    }

    while (!temp.empty()) {
        temp_card = temp.front();
        temp.pop();
        pHand.push(temp_card);
    }

    std::cout << "Hand saved successfully." << std::endl;
}

/**
 * @brief Get the List Of Cards inside the hand
 * 
 * @return std::queue<Card*, std::list<Card*>> 
 */
std::queue<Card*, std::list<Card*>>* Hand::getListOfCards() {
    return &pHand;
}
