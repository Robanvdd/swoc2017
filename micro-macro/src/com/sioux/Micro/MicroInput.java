package com.sioux.Micro;

import java.util.ArrayList;
import java.util.List;

class MicroInput {
    private List<BotInput> commands;

    public MicroInput() {
        this.commands = new ArrayList<>();
    }

    public List<BotInput> getCommands() {
        return commands;
    }
}
