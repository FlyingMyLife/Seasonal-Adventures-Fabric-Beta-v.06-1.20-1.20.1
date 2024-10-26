package net.packages.seasonal_adventures.engine.characters;

import net.minecraft.entity.EntityType;

public class Character {
    private String name;
    private EntityType<?> entityType;
    private boolean relationsEnabled;

    // Constructor
    public Character(String name, EntityType<?> entityType, boolean relationsEnabled) {
        this.name = name;
        this.entityType = entityType;
        this.relationsEnabled = relationsEnabled;
    }
    public String getName() {
        return name;
    }
    public EntityType<?> getEntityType() {
        return entityType;
    }
    public boolean isRelationsEnabled() {
        return relationsEnabled;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setEntityType(EntityType<?> entityType) {
        this.entityType = entityType;
    }

    public void setRelationsEnabled(boolean relationsEnabled) {
        this.relationsEnabled = relationsEnabled;
    }
}
