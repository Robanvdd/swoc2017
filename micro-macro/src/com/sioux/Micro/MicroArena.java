package com.sioux.Micro;

import java.awt.*;

class MicroArena {
    private int width;
    private int height;

    public MicroArena(Dimension size)
    {
        this.width = size.width;
        this.height = size.height;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public void Shrink(Integer delta) {
        height = Math.max(height - delta, 0);
        width = Math.max(width - delta, 0);
    }

    public boolean Playable() {
        return height > 0 && width > 0;
    }
}
