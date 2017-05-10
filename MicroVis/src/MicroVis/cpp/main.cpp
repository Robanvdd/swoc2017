#include <iostream>
#include "json.hpp"
#include <fstream>

using json = nlohmann::json;

int main()
{
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