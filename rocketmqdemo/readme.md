克隆代码
git clone git@github.com:apache/rocketmq.git
编译成jar包	
mvn -Prelease-all -DskipTests clean install -U

执行脚本mqnamesrv.cmd或者命令：start mqnamesrv.cmd
执行脚本mqbroker.cmd或者命令：start mqbroker.cmd

org.apache.rocketmq.client.exception.MQClientException: No route info of this topic, TopicMiao
查看当前拥有的topic
nameserve 地址为127.0.0.1:9876
start mqadmin.cmd topicList -n 127.0.0.1:9876  
发现里面并没有我们使用的topic： TopicMiao，所以就创建一个吧
start mqadmin.cmd updateTopic -b 127.0.0.1:10911 -t TopicMiao -n 127.0.0.1:9876
输出信息如下表示创建topic成功：
create topic to 127.0.0.1:10911 success.
TopicConfig [topicName=TopicMiao, readQueueNums=8, writeQueueNums=8, perm=RW-, topicFilterType=SINGLE_TAG, topicSysFlag=0, order=false]


Accessors

start mqbroker.cmd -n 127.0.0.1:9876 autoCreateTopicEnable=true

我成功创建了一个名称为 TopicMiao 的topic


可以使用rocketmq-consol进行查看消息在mq上的情况(是否产生、消费)

producer: 消息生产者，负责产生消息，一般由业务系统负责产生消息。
consumer: 消息消费者，负责消费消息，一般是后台系统负责异步消费。
Push Consumer: Consumer 的一种，应用通常向 Consumer 对象注册一个 Listener 接口，一旦收到消息，Consumer 对象立刻回调 Listener 接口方法。
Pull Consumer: Consumer 的一种，应用通常主动调用 Consumer 的拉消息方法从 Broker 拉消息，主动权由应用控制。
Producer Group: 一类 Producer 的集合名称，这类 Producer 通常发送一类消息，且发送逻辑一致。
Consumer Group: 一类 Consumer 的集合名称，这类 Consumer 通常消费一类消息，且消费逻辑一致。
Broker: 消息中转角色，负责存储消息，转发消息，一般也称为 Server。在 JMS 规范中称为 Provider。
