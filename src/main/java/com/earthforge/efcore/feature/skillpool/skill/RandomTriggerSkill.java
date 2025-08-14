package com.earthforge.efcore.feature.skillpool.skill;

import noppes.npcs.controllers.ScriptContainer;
import noppes.npcs.entity.EntityNPCInterface;

public class RandomTriggerSkill implements ISkill {
    private double chance;
    private ScriptContainer container;
    private String name;



    @Override
    public void tick(EntityNPCInterface npc) {
        if (Math.random() < chance){
            execute(container, npc);
        }
    }

    @Override
    public void execute(ScriptContainer script, EntityNPCInterface npc) {
        script.run(name,npc);
    }

    @Override
    public ISkill setContainer(ScriptContainer script) {
        this.container = script;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    public RandomTriggerSkill(String name, double chance) {
        this.name = name;
        this.chance = chance;
    }
}
