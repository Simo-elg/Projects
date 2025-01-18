#ifndef CARD_H
#define CARD_H

#include <iostream>
#include <fstream>

class Card {
public:
    // Méthodes virtuelles pures à implémenter dans les classes dérivées
    virtual int getCardsPerCoin(int coins) = 0;
    virtual std::string getName() = 0;
    virtual void print(std::ostream& out) = 0;
    virtual ~Card();

    // Sauvegarde la carte dans un fichier
    void saveCard(std::ofstream& file) {
        file << this->getName();  // Sauvegarde du nom de la carte
    }

    // Opérateur d'insertion pour afficher la première lettre du nom de la carte
    std::ostream& operator<<(std::ostream& output) {
        output << getName()[0] << std::endl;
        return output;
    }
};

// Définition des classes dérivées pour chaque type de carte

class Blue : public virtual Card {
    std::string name;
public:
    Blue(std::string name = "Blue");
    int getCardsPerCoin(int coins) override;
    std::string getName() override;
    void print(std::ostream& out) override;
};

class Chili : public virtual Card {
    std::string name;
public:
    Chili(std::string name = "Chili");
    int getCardsPerCoin(int coins) override;
    std::string getName() override;
    void print(std::ostream& out) override;
};

class Stink : public virtual Card {
    std::string name;
public:
    Stink(std::string name = "Stink");
    int getCardsPerCoin(int coins) override;
    std::string getName() override;
    void print(std::ostream& out) override;
};

class Green : public virtual Card {
    std::string name;
public:
    Green(std::string name = "Green");
    int getCardsPerCoin(int coins) override;
    std::string getName() override;
    void print(std::ostream& out) override;
};

class Soy : public virtual Card {
    std::string name;
public:
    Soy(std::string name = "Soy");
    int getCardsPerCoin(int coins) override;
    std::string getName() override;
    void print(std::ostream& out) override;
};

class Black : public virtual Card {
    std::string name;
public:
    Black(std::string name = "Black");
    int getCardsPerCoin(int coins) override;
    std::string getName() override;
    void print(std::ostream& out) override;
};

class Red : public virtual Card {
    std::string name;
public:
    Red(std::string name = "Red");
    int getCardsPerCoin(int coins) override;
    std::string getName() override;
    void print(std::ostream& out) override;
};

class Garden : public virtual Card {
    std::string name;
public:
    Garden(std::string name = "Garden");
    int getCardsPerCoin(int coins) override;
    std::string getName() override;
    void print(std::ostream& out) override;
};

#endif
