package org.dyrka.sanitar.bot.levels.generator;

public class Rank {
    private Long xp;
    private Long id;

    public Rank setXp(Long xp) {
        this.xp = xp;
        return this;
    }

    public Rank setId(Long id) {
        this.id = id;
        return this;
    }

    public void sendGenerationTask() {
        //TODO: Implement generator
    }
}
