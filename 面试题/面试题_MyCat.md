### 1.MyCat中balance属性的取值有哪些?

```
balance: 0不开启读写分离机制
balance: 1全部的readHost与stand by writeHost参与select语句的负载均衡
balance: 2所有readHost与writeHost都参与select语句的负载均衡
balance: 3所有readHost参与select语句的负载均衡
```

### 2.MyCat中switchType属性的取值有哪些

```
switchType: 1表示writeHost不可使用的时候, 可以自动切换到第二个writeHost中

switchType: -1表示writeHost不可使用的时候, 不要自动切换. 如果是一主多从架构, 并且使用了MHA等MySQL高可用管理工具时, 需要将此属性改为-1;
```

### 3.MyCat中writeType属性的取值有哪些?

```
writeType: 0表示所有的写操作全部在第一个writeHost中完成, 只有第一个writeHost失败了, 才向第二个writeHost写入.
writeType: 1表示写操作随机分配到writeHost中.
```

### 4.MyCat垂直分库以后如何进行跨数据库连表查询

```
方案一: 如果只需要跨表查询几个字段, 可以直接在对应表中添加冗余字段即可. 但是这些字段的值不能经常改变.

方案二: 使用API方式, 从对应的数据库中查询数据然后在业务代码中进行组装

方案三: 使用全局表的方式. 在需要跨库表连接的数据库中, 添加需要被连接的表, 在MyCat使用type="global", 将此表配置为全局表. 
通过MyCat修改全局数据表数据时, 所有数据库中的此表都会被修改. 表连接查询时, 会使用自己数据库中此表进行查询.
```