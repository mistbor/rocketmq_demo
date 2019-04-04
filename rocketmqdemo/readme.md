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
