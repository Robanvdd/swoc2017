package com.sioux.Micro;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.awt.*;
import java.io.IOException;

class PointAdapter extends TypeAdapter<Point.Double> {
    public Point.Double read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
        String[] point = reader.nextString().split(",");
        Double x = Double.parseDouble(point[0]);
        Double y = Double.parseDouble(point[1]);
        return new Point.Double(x, y);
    }

    public void write(JsonWriter writer, Point.Double value) throws IOException {
        if (value == null) {
            writer.nullValue();
            return;
        }
        String point = value.getX() + "," + value.getY();
        writer.value(point);
    }
}
