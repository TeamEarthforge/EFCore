package com.earthforge.efcore.feature.skillpool.skill;

import noppes.npcs.controllers.ScriptContainer;
import noppes.npcs.entity.EntityNPCInterface;

public class HealthBasedSkill implements ISkill {
    boolean used = false;
    private float health;
    private String name;
    private ScriptContainer container;




    @Override
    public void tick(EntityNPCInterface npc) {
        if (!used&&npc.getHealth()<npc.getMaxHealth()*health){
            used = true;
            execute(container,npc);
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

    public HealthBasedSkill(String name, float health){
        this.name = name;
        this.health = health;
    }
}
