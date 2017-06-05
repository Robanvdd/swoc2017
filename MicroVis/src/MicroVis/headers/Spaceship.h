#ifndef SPACESHIP_H
#define SPACESHIP_H

#include "Vector.h"
#include <SFML/Graphics.hpp>

class Spaceship
{
public:
	Spaceship(const sf::Texture& texture, const Position& startPosition);

	void SetPosition(const Position& position);
	void Draw(sf::RenderWindow& window);

private:
	const sf::Texture& m_texture;
	sf::Sprite m_sprite;
};

#endif SPACESHIP_H