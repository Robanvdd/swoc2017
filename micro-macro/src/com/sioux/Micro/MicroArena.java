package com.sioux.Micro;

import java.awt.*;

class MicroArena {
    private Dimension size;

    public MicroArena(Dimension size)
    {
        this.size = size;
    }

    public Integer getWidth() {
        return size.width;
    }

    public Integer getHeight() {
        return size.height;
    }

    public void Shrink(Integer delta) {
        size.height = Math.max(size.height - delta, 0);
        size.width = Math.max(size.width - delta, 0);
    }

    public boolean Playable() {
        return size.height > 0 && size.width > 0;
    }
}
