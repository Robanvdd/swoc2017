// SwocIO.h

#pragma once

using namespace System;

namespace SwocIO {

public ref class Pipeline sealed abstract
{
public:
	static void WriteLine(System::String^ value);
	static System::String^ ReadLine();
};

}
