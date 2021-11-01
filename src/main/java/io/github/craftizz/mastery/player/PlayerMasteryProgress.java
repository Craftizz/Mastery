package io.github.craftizz.mastery.player;

public class PlayerMasteryProgress {

    private int level;
    private int progress;
    private int goal;

    public PlayerMasteryProgress(int progress,
                                 int goal,
                                 int level) {
        this.progress = progress;
        this.goal = goal;
        this.level = level;
    }

    /**
     * Progresses the progress of the player
     *
     * @param additional the additional to be added
     * @return the new progress of the player
     */
    public int progress(final int additional) {

        return this.progress += additional;
    }

    /**
     * @return The current progress
     */
    public int getProgress() {
        return progress;
    }

    /**
     * @return the goal of the mastery
     */
    public int getGoal() {
        return goal;
    }

    /**
     * Sets the new goal for this mastery
     *
     * @param goal the new goal
     */
    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
