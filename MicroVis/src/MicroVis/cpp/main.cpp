#include <iostream>
#include "json.hpp"
#include <fstream>

using json = nlohmann::json;

int main(int argc, char **argv)
{
	std::cout << "JSON file read:" << std::endl;
	/*std::ifstream input("input.json");
	json jsonInput;
	input >> jsonInput;

	std::cout << jsonInput["positions"];*/
	std:: cout << argv[1] << std::endl;

	return 0;
}