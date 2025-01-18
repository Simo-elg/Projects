cmake_minimum_required(VERSION 3.30)
project(projet)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(projet
    Card.cpp
    Card.h
    CardFactory.cpp
    CardFactory.h
    Chain.cpp
    Chain.h
    Deck.cpp
    Deck.h
    DiscardPile.cpp
    DiscardPile.h
    Hand.cpp
    Hand.h
    main.cpp
    Main.h
    Player.cpp
    Player.h
    Table.cpp
    Table.h
    TradeArea.cpp
    TradeArea.h)
