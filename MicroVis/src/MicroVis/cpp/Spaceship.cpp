#include "Spaceship.h"

Spaceship::Spaceship(const sf::Texture& texture, const Position& startPosition) :
	m_texture(texture)
{
	m_sprite.setTexture(m_texture);
	m_sprite.setPosition(startPosition.x, startPosition.y);
}

void Spaceship::SetPosition(const Position& position)
{
	m_sprite.setPosition(position.x, position.y);
}

void Spaceship::Draw(sf::RenderWindow& window)
{
	window.draw(m_sprite);
}