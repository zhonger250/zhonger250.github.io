### 1.Linux的常用命令有哪些
```
cat                 查看文件的内容
touch               创建文件
mkdir               创建文件夹
ls                  查看文件列表
vi                  文件编辑器
firewalld-cmd       操作防火墙
tail(尾巴)           查看文件末尾内容
```
### 2.Vi命令的几种模式
```
1.命令模式:
命令模式是启动vi后进入的工作模式, 并可转换为文本编辑模式和最后行模式.
2.文本编辑模式:
文本编辑模式用于字符编辑. 在命令模式下输入i(插入命令)、a(附加命令)等命令后进入文本编辑模式. 
此时输入的任何字符都被vi当作文件内容显示在屏幕上. 按Esc键可从文本编辑模式返回到命令模式.
3.最后行模式:
在命令模式下, 按":"键进入最后行模式, 此时vi会在屏幕的底部显示":"符号作为最后行模式的提示
符, 等待用户输入相关命令. 输入":wq"保存退出, 输入":q!"直接退出.
```
### 3.Linux中如何查看日志信息
```
使用tail命令
-n<行数>                  显示行数
tail -n 10 test.log      查询日志尾部最后10行的日志;
tail -n +10 test.log     查询10行之后的所有日志;
-f                       循环读取
tail -fn 1000 test.log   循环实时查看最后1000行记录(最常用的)
还可以配合着grep命令一起使用
tail -fn 1000 test.log | grep '关键字'
```
### 4.Docker的常用命令有哪些
```
docker images             查看所有镜像
docker ps                 查看所有正在运行的容器
docker ps -a              查看启动过的历史容器
docker pull               拉取镜像
docker run                启动容器
docker exec               进入容器
docker rmi                删除镜像
docker rm                 删除容器
docker stop               停止容器
docker network ls         查看所有网络
docker network create     创建网路
docker network remove     删除网络
```