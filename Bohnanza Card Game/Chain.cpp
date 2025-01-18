#include "Chain.h"
#include <iomanip>

/**
 * @brief Returns the number of cards in the chain
 * 
 * @return int 
 */
int Chain_Base::getSize() {
    return chain.size();
}

/**
 * @brief Sets the chain type for the current chain
 * 
 * @param chainType 
 */
void Chain_Base::setChainType(std::string chainType) {
    this->chainType = chainType;
}

/**
 * @brief Gets the type of the chain
 * 
 * @return std::string 
 */
std::string Chain_Base::getChainType() {
    return chainType;
}

/**
 * @brief Writes the chain into a file
 * 
 * @param filename 
 */
void Chain_Base::saveChain(std::ofstream& filename) {
    filename << std::endl << chainType << std::endl;
    for (int i = 0; i < chain.size(); i++) {
        chain.at(i)->saveCard(filename);
        filename << std::endl;
    }
    std::cout << "Chain saved successfully." << std::endl;
}

/**
 * @brief Insertion operator to display the chain information
 * 
 * @param output 
 * @param d 
 * @return std::ostream& 
 */
std::ostream& operator<<(std::ostream& output, const Chain<Card*>& d) {
    output << d.chainType << " ";
    for (int i = 0; i < d.chain.size(); i++) {
        d.chain.at(i)->print(output);
        output << " ";
    }
    return output;
};

/**
 * @brief Insertion operator to display the chain base information
 * 
 * @param output 
 * @param d 
 * @return std::ostream& 
 */
std::ostream& operator<<(std::ostream& output, const Chain_Base& d) {
    output << d.chainType << " " << std::setw(4);
    for (int i = 0; i < d.chain.size(); i++) {
        d.chain.at(i)->print(output);
        output << " ";
    }
    return output;
}
