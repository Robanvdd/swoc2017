#ifndef VECTOR_H
#define VECTOR_H

struct Vector
{
	float x, y;
	Vector() : x(0.f), y(0.f) {}
	Vector(float x, float y) : x(x), y(y) {}
};

using Position = Vector;

#endif