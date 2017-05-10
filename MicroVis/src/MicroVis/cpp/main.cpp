#include <iostream>
#include "json.hpp"
#include <fstream>
#include <SFML/Graphics.hpp>

using json = nlohmann::json;

int main()
{
	std::cout << "Starting SFML stuff" << std::endl;
	sf::RenderWindow window(sf::VideoMode(400,400), "Hello World!");
	sf::CircleShape shape(100.f);
	shape.setFillColor(sf::Color::Green);

	while (window.isOpen())
	{
		sf::Event event;
		while (window.pollEvent(event))
		{
			if (event.type == sf::Event::Closed)
				window.close();
		}

		window.clear();
		window.draw(shape);
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