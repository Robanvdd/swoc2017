package com.sioux.Micro;

import java.awt.*;

class MicroArena {
    private int width;
    private int height;
    private int shrinkRate;
    private int shrinkThreshold;

    public MicroArena(Dimension size, int shrinkRate, int shrinkThreshold)
    {
        this.width = size.width;
        this.height = size.height;
        this.shrinkRate = shrinkRate;
        this.shrinkThreshold = shrinkThreshold;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public boolean Playable() {
        return height > 0 && width > 0;
    }

    public void UpdateArena(int tick) {
        if (tick > shrinkThreshold) {
            Shrink(shrinkRate);
        }
    }

    private void Shrink(Integer delta) {
        height = Math.max(height - delta, 0);
        width = Math.max(width - delta, 0);
    }
}
