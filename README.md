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


## Maven

```
		<dependency>
			<groupId>com.andreidodu</groupId>
			<artifactId>jlock</artifactId>
			<version>0.7</version>
		</dependency>
```		


```
	<repositories>
		<repository>
			<id>github</id>
			<name>GitHub AndreiDodu Apache Maven Packages</name>
			<url>https://AndreiDodu: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@maven.pkg.github.com/AndreiDodu/jlock</url>
		</repository>
	</repositories>
```
