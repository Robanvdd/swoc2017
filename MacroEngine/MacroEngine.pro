QT += core
QT += gui

CONFIG += c++14

TARGET = MacroEngine
CONFIG += console
CONFIG -= app_bundle

TEMPLATE = app

SOURCES += main.cpp \
    MacroBot.cpp \
    MicroGame.cpp \
    MacroGame.cpp \
    Engine.cpp \
    Universe.cpp \
    Player.cpp \
    Ufo.cpp \
    Planet.cpp \
    Fight.cpp \
    SolarSystem.cpp \
    GameObject.cpp \
    UniverseBuilder.cpp \
    UfoShop.cpp \
    CommandBase.cpp \
    BuyCommand.cpp \
    ConquerCommand.cpp \
    MoveToCoordCommand.cpp \
    MoveToPlanetCommand.cpp \
    PlayerBotFolders.cpp \
    MicroGameInput.cpp \
    MicroGameOutput.cpp

# The following define makes your compiler emit warnings if you use
# any feature of Qt which as been marked deprecated (the exact warnings
# depend on your compiler). Please consult the documentation of the
# deprecated API in order to know how to port your code away from it.
DEFINES += QT_DEPRECATED_WARNINGS

# You can also make your code fail to compile if you use deprecated APIs.
# In order to do so, uncomment the following line.
# You can also select to disable deprecated APIs only up to a certain version of Qt.
#DEFINES += QT_DISABLE_DEPRECATED_BEFORE=0x060000    # disables all the APIs deprecated before Qt 6.0.0

HEADERS += \
    MacroBot.h \
    MicroGame.h \
    MacroGame.h \
    Engine.h \
    Universe.h \
    Player.h \
    Ufo.h \
    Planet.h \
    Fight.h \
    SolarSystem.h \
    GameObject.h \
    UniverseBuilder.h \
    UfoShop.h \
    CommandBase.h \
    BuyCommand.h \
    ConquerCommand.h \
    MoveToCoordCommand.h \
    MoveToPlanetCommand.h \
    PlayerBotFolders.h \
    MicroGameInput.h \
    MicroGameOutput.h
