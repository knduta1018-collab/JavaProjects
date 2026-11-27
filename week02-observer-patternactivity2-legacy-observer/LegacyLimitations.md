
# Limitations of java.util.Observer and java.util.Observable

## Limitation 1: Single Inheritance

- Observable is a class, not an interface. Java only allows a class to extend one class, so a class extending Observable cannot extend another class. This limits flexibility and code reuse.

## Limitation 2: Cannot Change the Notification Order

-Observers are notified in an unspecified order. You cannot control which observer receives the update first.

## Limitation 3: setChanged() is Confusing

- You must call setChanged() before notifyObservers() to indicate that the data has changed. If you forget, the observers will not be notified.

## Limitation 4: Pull Model by Default

- Observers receive a notification and must call methods on the Observable to retrieve the updated data. This requires extra method calls and can be inefficient.

## Limitation 5: Deprecated Since Java 9

- Java deprecated these classes because the API is limited, not thread-safe, and difficult to extend. It also has problems with single inheritance, notification order, and the confusing setChanged() method. Modern alternatives can provide more flexibility.