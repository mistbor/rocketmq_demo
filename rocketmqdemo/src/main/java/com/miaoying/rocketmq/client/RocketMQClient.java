package com.miaoying.rocketmq.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 　@Description:
 * 　@author miaoying
 * 　@date 2019/2/27
 */
@Slf4j
@Component
public class RocketMQClient {
    /**
     * 生产者的组名
     */
    @Value("${apache.rocketmq.producer.producerGroup}")
    private String producerGroup;

    /**
     * NameServer 地址
     */
    @Value("${apache.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    /**
     * 同步发送消息（具有可靠性，用于重要的消息通知、短信通知、短信营销系统等）
     *
     * @throws InterruptedException
     */
    @PostConstruct
    public void defaultMQProducer() throws InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(namesrvAddr);

        int messageCount = 10000;
        final CountDownLatch countDownLatch = new CountDownLatch(messageCount);

        try {
            producer.start();
            producer.setRetryTimesWhenSendAsyncFailed(0);

            for (int i = 0; i < messageCount; i++) {
                Message message = new Message("serendipity", "push", "keyTest", ("message" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                producer.send(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        countDownLatch.await(5, TimeUnit.SECONDS);
        producer.shutdown();
    }
}