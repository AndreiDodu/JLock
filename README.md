# JLock
A mini jar to ensure the uniqueness of execution of an java applciation.


### How to use it?

At the application startup

```
JLock jLock = new JLock("my-applciation-id");
if (jLock.isLocked()){
  MessageDialog.error(this, "Application is already running!");
  System.exit(3);
}
```


