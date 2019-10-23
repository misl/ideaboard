package nl.xup.ideaboard.messaging.mqtt.util;

import java.util.Objects;

/**
 * The Pair type is a common typ ein functional languages and not (yet) part of Java.
 *
 * @param <L> Generic type parameter for left.
 * @param <R> Generic type parameter for right.
 */
public class Pair<L, R> {

  // --------------------------------------------------------------------------
  // Object attributes
  // --------------------------------------------------------------------------

  private final L left;
  private final R right;

  // --------------------------------------------------------------------------
  // Constructor
  // --------------------------------------------------------------------------

  protected Pair( L left, R right ) {
    this.left = left;
    this.right = right;
  }

  /**
   * Factory method to create a Pair object with a left and a right.
   * <p/>
   * Pattern: Factory method
   *
   * @param <L>   type for the left part
   * @param <R>   type for the right part
   * @param key   the left
   * @param value the right
   * @return a Pair object with the given left,right.
   */
  public static <L, R> Pair<L, R> of( final L key, final R value ) {
    Objects.requireNonNull( key );
    Objects.requireNonNull( value );

    return new Pair<L, R>( key, value );
  }

  // --------------------------------------------------------------------------
  // Interface
  // --------------------------------------------------------------------------

  /**
   * Gives the left value.
   *
   * @return the left value.
   */
  public L getLeft() {
    return left;
  }

  /**
   * Gives the right value.
   *
   * @return the right value.
   */
  public R getRight() {
    return right;
  }


  // --------------------------------------------------------------------------
  // Object overrides
  // --------------------------------------------------------------------------

  @Override
  public String toString() {
    return "(" + left.toString() + "," + right.toString() + ")";
  }
}
