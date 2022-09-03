# JLock
A mini jar to ensure the uniqueness of execution of an java application.


### How to use it?

At the application startup

```
JLock jLock = new JLock("my-application-id");
if (jLock.isLocked()){
  MessageDialog.error(this, "Application is already running!");
  System.exit(3);
}
```

To release:

```
jLock.release();
```

