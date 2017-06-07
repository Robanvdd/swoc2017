#include <iostream>
#include "json.hpp"
#include <fstream>
#include <SFML/Graphics.hpp>
#include "Spaceship.h"
#include "Vector.h"

using json = nlohmann::json;

int main()
{
	std::cout << "Starting SFML stuff" << std::endl;
	sf::RenderWindow window(sf::VideoMode(800, 600), "Hello World!");

	const std::string ufoFilename = "ufo.png";
	std::cout << "Looking for " << ufoFilename << std::endl;
	sf::Texture ufoTexture;
	if (!ufoTexture.loadFromFile(ufoFilename))
	{
		std::cout << "Could not find it!" << std::endl;
		return -1;
	}
	ufoTexture.setSmooth(true);
	ufoTexture.setRepeated(false);

	Spaceship ufo(ufoTexture, Position(50.f, 50.f));

	while (window.isOpen())
	{
		sf::Event event;
		while (window.pollEvent(event))
		{
			if (event.type == sf::Event::Closed)
				window.close();
		}

		window.clear();
		ufo.Draw(window);
		window.display();
	}

	const std::string gameFilename("microgame1.json");
	std::cout << "Looking for " << gameFilename << std::endl;

	std::ifstream input(gameFilename);
	if (input.is_open())
	{
		std::cout << "Found it!" << std::endl;
		json jsonInput;
		input >> jsonInput;
		std::cout << jsonInput;
	}
	else
	{
		std::cout << "Could not find it!" << std::endl;
		return -1;
	}

	return 0;
}