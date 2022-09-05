# JLock
A mini jar to ensure the uniqueness of execution of an java application.


### How to use it?

At the application startup

```
JLock jLock = new JLock("MY_APPLICATION_NAME", ENABLE_AUTOMATIC_UNLOCK);
if (jLock.isLocked()){
  MessageDialog.error(your_jform, "Application is already running!");
  System.exit(3);
}
```

where `MY_APPLICATION_NAME` is the application name and the boolean `ENABLE_AUTOMATIC_UNLOCK`, if true, enables the automatic unlocking when `System.exit(X)` is called.

To release manually:

```
jLock.release();
```

To release automatically:

```
System.exit(status);
```
