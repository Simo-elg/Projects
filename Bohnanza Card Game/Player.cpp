#include "Player.h"
#include <iostream>
#include <iomanip>
#include <stdexcept>

Player::Player(std::string name) {
    pName = name;
    pCoins = 0;
    pHand = new Hand();
}

Player::Player(std::istream& input, const CardFactory* cf) {
    input >> pName;  // Lire le nom du joueur
    input >> pCoins;  // Lire le nombre de pièces du joueur
    pHand = new Hand(input, cf);  // Lire la main du joueur à partir du flux d'entrée

    // Lire et initialiser les chaînes du joueur à partir du flux d'entrée
    std::string line;
    while (std::getline(input, line)) {
        if (line == "-chains") {
            while (std::getline(input, line) && line != "-end-chains") {
                // Lecture et ajout des chaînes
                pChains.push_back(new Chain_Base(input, cf));
            }
        }
    }
}

Player::~Player() {
    delete pHand;
    for (auto chain : pChains) {
        delete chain;
    }
}

std::string Player::getName() {
    return pName;
}

int Player::getNumCards() {
    return pHand->numCards();
}

int Player::getNumCoins() {
    return pCoins;
}

Hand* Player::getHand() {
    return pHand;
}

std::vector<Chain_Base*>* Player::getChains() {
    return &pChains;
}

void Player::takeCard(Card* card) {
    *pHand += card;
}

void Player::printHand(std::ostream& out, bool full) {
    pHand->print(out, full);
}

Card* Player::playCard(Card* input, bool specified_input) {
    Card* card = nullptr;
    if (pHand->numCards() < 0) {
        std::cout << "(PlayCard) Not enough card in hand for player: " << pName << std::endl;
    } else {
        if (!specified_input)
            card = pHand->top();
        else
            card = input;

        Chain_Base* new_chain;
        bool ExistingChain = false;
        char user_input[2];

        // Vérification si la carte peut être ajoutée à une chaîne existante
        for (Chain_Base* chain : pChains) {
            if (chain->getChainType() == card->getName()) {
                ExistingChain = true;
                new_chain = chain;
                if (!specified_input)
                    card = pHand->play();

                if (card == nullptr)
                    card = pHand->play();

                *new_chain += card;  // Ajout de la carte à la chaîne
                break;
            }
        }

        // Si aucune chaîne existante n'a été trouvée, créer une nouvelle chaîne pour ce type de carte
        if (!ExistingChain) {
            if (card->getName() == "Blue") {
                new_chain = new Chain<Blue>;
            } else if (card->getName() == "Chili") {
                new_chain = new Chain<Chili>;
            } else if (card->getName() == "Stink") {
                new_chain = new Chain<Stink>;
            } else if (card->getName() == "Green") {
                new_chain = new Chain<Green>;
            } else if (card->getName() == "Soy") {
                new_chain = new Chain<Soy>;
            } else if (card->getName() == "Black") {
                new_chain = new Chain<Black>;
            } else if (card->getName() == "Red") {
                new_chain = new Chain<Red>;
            } else if (card->getName() == "Garden") {
                new_chain = new Chain<Garden>;
            } else {
                std::cout << "(playCard) Invalid card name: " << card->getName() << std::endl;
                new_chain = nullptr;
                exit(1);
            }

            // Si le joueur a atteint le nombre maximum de chaînes, vendre une chaîne pour faire de la place
            if (pChains.size() == MAX_NUM_CHAINS) {
                std::cout << "Player " << pName << " has reached the maximum number of chains (" << MAX_NUM_CHAINS << ")." << std::endl;
                std::cout << "> Selling Chain of type: " << pChains.front()->getChainType() << std::endl;

                // Vendre la chaîne en utilisant la logique de vente des cartes de la chaîne
                // Exemple:
                // if (pChains.front()->getChainType() == "Blue") { ... }

                pChains.erase(pChains.begin());  // Supprimer la chaîne vendue
            } else if (pChains.size() < ALLOWED_CHAINS) {
                pChains.push_back(new_chain);  // Ajouter la nouvelle chaîne
                if ((*new_chain).getSize() == 0)
                    (*new_chain).setChainType(card->getName());
                card = pHand->play();
                *new_chain += card;  // Ajouter la carte à la nouvelle chaîne
            } else {
                std::cout << ">>> Player " << pName << " has reached the maximum allowed number of chains (" << ALLOWED_CHAINS << ")." << std::endl;
                std::cout << "Do you want to buy a third chain? (y/n)" << std::endl;
                std::cin >> user_input;
                if (user_input[0] == 'y') {
                    buyThirdChain();  // Acheter une troisième chaîne
                    for (Chain_Base* chain : pChains) {
                        if (chain->getChainType() == card->getName()) {
                            new_chain = chain;
                            card = pHand->play();
                            *new_chain += card;  // Ajouter la carte à la chaîne existante
                            break;
                        }
                    }
                } else {
                    // Logique similaire de vente de chaîne (peut être gérée avec une carte)
                }
            }
        }
    }
    return card;
}


void Player::removeCard(int pos) {
    pHand->removeCard(pos);
}

void Player::savePlayer(int p_id) {
    std::ofstream file;
    char id[2];

    sprintf(id, "%d", p_id);
    std::string filename = "Saved-P" + std::string(id) + ".txt";

    file.open(filename, std::ios::trunc);

    // Sauvegarde du nom du joueur et des pièces
    file << pName << std::endl;
    file << pCoins << std::endl;

    pHand->saveHand(file);

    // Sauvegarde des chaînes
    file << std::endl << "-chains" << std::endl;
    for (int i = 0; i < pChains.size(); i++) {
        file << std::endl << "---" << std::endl;
        pChains.at(i)->saveChain(file);
    }

    file << std::endl << "-end-chains" << std::endl;

    file.close();
    std::cout << "Player-" + std::string(id) << " saved." << std::endl;
}

void Player::buyThirdChain() {
    Card* card = nullptr;
    if ((pCoins % 3 == 0) && (pCoins > 0)) {
        if (pChains.size() < MAX_NUM_CHAINS) {
            pCoins -= 3;
            card = pHand->top();
            Chain_Base* new_chain;
            // Similar chain creation logic for buying third chain
            pChains.push_back(new_chain);
        } else {
            std::cout << "A new chain cannot be bought. The maximum number of chains has been reached (" << pChains.size() << ")." << std::endl;
        }
    } else {
        throw std::runtime_error("NotEnoughCoins");
    }
}

int Player::getMaxNumChains() {
    return MAX_NUM_CHAINS;
}

int Player::getNumChains() {
    int counter = 0;
    for (int i = 0; i < pChains.size(); i++) {
        if ((pChains.at(i))->getSize() > 0)
            counter++;
    }
    return counter;
}
