package com.earthforge.efcore.feature.skillpool;

import com.earthforge.efcore.feature.skillpool.skill.ISkill;
import noppes.npcs.controllers.ScriptContainer;
import noppes.npcs.entity.EntityNPCInterface;
import scala.xml.dtd.PublicID;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class SkillPool {
    private EntityNPCInterface npc;
    private ScriptContainer container;
    Set<ISkill> skills = new HashSet<>();
    public void tick(){
        for (ISkill skill : new ArrayList<>(skills)) {
            skill.tick(npc);
        }

    }
    public void setNpc(EntityNPCInterface npc){
        this.npc = npc;
    }
    public void setContainer(ScriptContainer container){
        this.container = container;
    }
    public void addSkill(ISkill skill){
        if(skill.getName() != null && !skill.getName().isEmpty()) {
            boolean isDuplicate = skills.stream()
                .anyMatch(existingSkill -> existingSkill.getName().equals(skill.getName()));
            if (!isDuplicate) {
                skills.add(skill.setContainer(container));
            }
        }
    }

}


