# JLock
A jar to ensure the uniqueness of execution of an java application.

![example](https://raw.githubusercontent.com/AndreiDodu/JLock/master/example_a.png)

### How to use it?

At the application startup

```java
JLock jLock = new JLock("MY_APPLICATION_NAME", ENABLE_AUTOMATIC_UNLOCK);
if (jLock.isLocked()){
  MessageDialog.error(your_jform, "Application is already running!");
  System.exit(3);
}
```

where `MY_APPLICATION_NAME` is the application name and the boolean `ENABLE_AUTOMATIC_UNLOCK`, if true, enables the automatic unlocking when `System.exit(X)` is called.


To release automatically (ENABLE_AUTOMATIC_UNLOCK have to be true):

```java
System.exit(status);
```

To release manually:

```java
jLock.release();
```


## Maven

```xml
<dependency>
	<groupId>it.dodu</groupId>
	<artifactId>jlock</artifactId>
	<version>1.0.0</version>
</dependency>
```		
