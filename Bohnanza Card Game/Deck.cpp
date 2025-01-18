#include "Deck.h"
#include "CardFactory.h"
#include <iostream>
#include <fstream>
#include <string>
#include <stdexcept>

/**
 * @brief Construct a new Deck object
 */
Deck::Deck() {
    // Logique par défaut pour l'initialisation du deck
}

/**
 * @brief Construct a new Deck object from an input stream (such as a file)
 *
 * @param input Le flux d'entrée
 * @param cf Le CardFactory utilisé pour créer des cartes
 */
Deck::Deck(std::istream& input, const CardFactory* cf) {
    std::string cardName;
    while (input >> cardName) {
        Card* card = nullptr;

        // Créer la carte en fonction du nom lu
        if (cardName == "Blue") {
            card = new Blue("Blue");
        } else if (cardName == "Chili") {
            card = new Chili("Chili");
        } else if (cardName == "Stink") {
            card = new Stink("Stink");
        } else if (cardName == "Green") {
            card = new Green("Green");
        } else if (cardName == "Soy") {
            card = new Soy("Soy");
        } else if (cardName == "Black") {
            card = new Black("Black");
        } else if (cardName == "Red") {
            card = new Red("Red");
        } else if (cardName == "Garden") {
            card = new Garden("Garden");
        } else {
            std::cerr << "Carte inconnue : " << cardName << std::endl;
            continue;
        }

        this->push_back(card);  // Ajouter la carte au deck
    }
}

/**
 * @brief Constructeur de copie pour Deck
 */
Deck::Deck(const Deck& d) {
    for (Card* card : d) {
        this->push_back(card);  // Copier chaque carte de l'autre deck
    }
}

/**
 * @brief Destructeur pour Deck, supprime toutes les cartes
 */
Deck::~Deck() {
    for (Card* card : *this) {
        delete card;  // Libérer la mémoire des cartes
    }
}

/**
 * @brief Tirer (retirer et retourner) la carte du dessus du deck
 */
Card* Deck::draw() {
    if (!this->empty()) {
        Card* card = this->back();
        this->pop_back();
        return card;
    }
    return nullptr;
}

/**
 * @brief Opérateur d'assignation pour Deck
 */
Deck& Deck::operator=(const Deck& d) {
    if (this != &d) {  // Empêcher l'auto-assignment
        this->clear();  // Vider le deck actuel

        for (Card* card : d) {
            this->push_back(card);  // Copier chaque carte
        }
    }
    return *this;
}

/**
 * @brief Opérateur d'insertion pour afficher le deck
 */
std::ostream& operator<<(std::ostream& output, const Deck& d) {
    for ( Card* card : d) {
        output << card->getName()[0] << " ";  // Afficher la première lettre de chaque carte
    }
    return output;
}

/**
 * @brief Sauvegarder le deck dans un fichier
 */
void Deck::saveDeck(std::ofstream& filename) {
    for (Card* card : *this) {
        card->saveCard(filename);  // Sauvegarder chaque carte avec sa méthode saveCard
        filename << std::endl;  // Ajouter un saut de ligne après chaque carte
    }
    std::cout << "Deck sauvegardé avec succès." << std::endl;
}
