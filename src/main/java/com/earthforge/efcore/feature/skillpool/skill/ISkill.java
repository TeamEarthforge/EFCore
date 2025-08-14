package com.earthforge.efcore.feature.skillpool.skill;

import noppes.npcs.controllers.ScriptContainer;
import noppes.npcs.entity.EntityNPCInterface;


public interface ISkill {
    public void tick(EntityNPCInterface npc);
    public void execute(ScriptContainer script, EntityNPCInterface npc);
    public ISkill setContainer(ScriptContainer script);
    public String getName();
}
