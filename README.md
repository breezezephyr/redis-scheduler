# redis-scheduler
use redis as middle ware store task. support distribute multi-node executor.


# requirement

##redis Installation
From source code
Download, extract and compile Redis with:
~~~ 

$ wget https://download.redis.io/releases/redis-6.0.10.tar.gz
$ tar xzf redis-6.0.10.tar.gz
$ cd redis-6.0.10
$ make
~~~

The binaries that are now compiled are available in the src directory. Run Redis with:
~~~ 
$ src/redis-server
~~~ 