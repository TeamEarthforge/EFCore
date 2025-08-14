package com.earthforge.efcore.feature.skillpool.skill;

import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.controllers.ScriptContainer;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.scripted.NpcAPI;

public class FixedTimeSkill implements ISkill {


    private ScriptContainer container;
    private int cd;
    private int time = 0;
    private String name;




    @Override
    public void tick(EntityNPCInterface npc) {
        time++;
        if (time >= cd){
            time = 0;
            execute(container,npc);
        }
    }

    @Override
    public void execute(ScriptContainer script, EntityNPCInterface npc) {
        script.run(name, NpcAPI.Instance().getIEntity(npc));
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

    public FixedTimeSkill(String name, int cd){
        this.name = name;
        this.cd = cd;
    }
}
