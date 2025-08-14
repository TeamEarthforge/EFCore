package com.earthforge.efcore.feature.skillpool;

import net.minecraft.entity.EntityLiving;
import noppes.npcs.controllers.ScriptContainer;
import noppes.npcs.entity.EntityNPCInterface;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class SkillPoolManager {
    private static SkillPoolManager instance;
    private final Map<EntityNPCInterface, SkillPool> skillPools = new WeakHashMap<>();
    private SkillPoolManager() {}
    public static SkillPoolManager getInstance()
    {
        if (instance == null)
        {
            instance = new SkillPoolManager();
        }
        return instance;
    }
    public SkillPool getOrCreate(EntityNPCInterface entity)
    {
        SkillPool skillPool = skillPools.get(entity);
        if (skillPool == null)
        {
            skillPool = new SkillPool();
            skillPools.put(entity, skillPool);
            skillPool.setNpc(entity);
            skillPool.setContainer(ScriptContainer.Current);
        }
        return skillPool;
    }
    public void tick()
    {
        for (SkillPool skillPool : skillPools.values())
        {
            skillPool.tick();
        }
    }

}
