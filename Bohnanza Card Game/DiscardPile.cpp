#include "DiscardPile.h"

/**
 * @brief Returns and removes the top card from the discard pile
 * 
 * @return Card* 
 */
Card* DiscardPile::pickUp() {
    Card* card;
    card = this->back();
    this->pop_back();
    return card;
}

/**
 * @brief Returns but does not remove the top card from the discard pile
 * 
 * @return Card* 
 */
Card* DiscardPile::top() {
    return this->back();
}

/**
 * @brief Inserts all cards in the DiscardPile to the ostream
 * 
 * @param os 
 */
void DiscardPile::print(std::ostream& os) {
    for (int i = 0; i < this->size(); i++) {
        os << this->at(i)->getName()[0] << " ";
    };
}

/**
 * @brief Insertion operator to display the discard pile object (only the card on top of the discard pile)
 * 
 * @param output 
 * @param dp 
 * @return std::ostream& 
 */
std::ostream& operator<<(std::ostream& output, const DiscardPile& dp) {
    if (dp.size() > 0)
        (dp.back())->print(output);
    else
        output << "";

    return output;
}

/**
 * @brief Write the discard pile inside a file
 * 
 * @param filename 
 */
void DiscardPile::saveDiscardPile(std::ofstream& filename) {
    for (int i = 0; i < this->size(); i++) {
        this->at(i)->saveCard(filename);
        filename << std::endl;
    }
    std::cout << "Discard pile saved successfully." << std::endl;
}
