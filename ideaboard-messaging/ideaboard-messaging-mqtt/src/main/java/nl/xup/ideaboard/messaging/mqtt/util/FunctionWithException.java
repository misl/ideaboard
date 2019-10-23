package nl.xup.ideaboard.messaging.mqtt.util;

@FunctionalInterface
public interface FunctionWithException<T, R, E extends Exception> {

  R apply( T t ) throws E;
}