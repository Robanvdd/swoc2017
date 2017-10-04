// This is the main DLL file.

#include <msclr\marshal_cppstd.h>
#include <iostream>
#include "SwocIO.h"

namespace SwocIO {

void Pipeline::WriteLine(System::String^ value)
try
{
	std::cout << msclr::interop::marshal_as<std::string>(value) << std::endl;
}
catch (std::exception& ex)
{
	throw gcnew System::Exception("Writeline failed");
}

System::String^ Pipeline::ReadLine()
try
{
	std::string value;
	std::getline(std::cin, value);
	return msclr::interop::marshal_as<System::String^>(value);
}
catch (std::exception& ex)
{
	throw gcnew System::Exception("Readline failed");
}

bool Pipeline::HasInput()
try
{
	return std::cin.rdbuf()->in_avail();
}
catch (std::exception& ex)
{
	throw gcnew System::Exception("HasInput failed");
}

}
