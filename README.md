# Redis Scala Support to Play! Framework 2.3

This is a plugin for Play 2.3, enabling support for [Rediscala](https://github.com/etaty/rediscala) - A Redis client for Scala (2.10+) and (AKKA 2.2+) with non-blocking and asynchronous I/O operations.

## How to use it

Add the plugin to your dependencies

```scala
resolvers += "play2-rediscala" at "http://dl.bintray.com/yorrick/maven"
"fr.njin" %% "play2-rediscala" % "1.0.2"
```

And declare it in conf/play.plugins

```
200:play.modules.rediscala.RedisPlugin
```

Get your client

```scala
val client = RedisPlugin.client()(app, Akka.system(app))
```

or

```scala
import play.api.Play.current
implicit val system = Akka.system

val client = RedisPlugin.client()
```

[Use it](https://github.com/etaty/rediscala)

## Configuration

Rediscala Plugin can handle multiple databases. You can configure them in your app configuration file `application.conf`

```
redis {
	# The default database.
	default {
		# You can use uri to configure a database
		uri: "redis://user:password@an.host.com:9092/"
	}
	mydb {
		host: localhost
		port: 6379
		password: ...
	}
	...
}
```

```scala
RedisPlugin.client() //Give you the default database client
RedisPlugin.client("mydb") //Give you 'mydb' database client
```
