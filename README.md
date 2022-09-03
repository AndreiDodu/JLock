# JLock
A mini library for unique execution of an java application


### How to use it?

At applciation startup

```
JLock jLock = new JLock("my-applciation-id");
if (jLock.isLocked()){
  MessageDialog.error(this, "Application is already running!");
	System.exit(3);
}
```


