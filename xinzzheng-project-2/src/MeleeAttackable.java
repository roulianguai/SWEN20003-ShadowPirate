/**
 * Interface for ability to melee attack
 * @param <T> Melee attack target type
 */
public interface MeleeAttackable <T>{
    /**
     * Method that check is an object is in attackRange
     * @param o Object that can be attacked
     * @return boolean value indicating whether
     */
    boolean isInRange(T o);

    /**
     * Method that implements attack interaction to ab object being attacked
     * @param o Instance being attacked
     */
    void attackTo(T o);

    /**
     * Print log for objects when attack
     * @param o Instance being attacked
     */
    void printLog(T o);
}
