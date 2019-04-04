package com.miaoying.rocketmq.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class AsyncRocketMQClient {

    /**
     * 异步发送消息（针对时间敏感的业务场景）
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("Producer");
        producer.setNamesrvAddr("localhost:9876");

        int messageCount = 10000;
        final CountDownLatch countDownLatch = new CountDownLatch(messageCount);

        try {
            producer.start();
            producer.setRetryTimesWhenSendAsyncFailed(0);

            for (int i = 0; i < messageCount; i++) {
                Message message = new Message("TopicMiaoAsync", "push", "keyTest", ("message" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                producer.send(message, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        log.info("send success");
                    }

                    @Override
                    public void onException(Throwable e) {
                        log.error("send error");
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        countDownLatch.await(5, TimeUnit.SECONDS);
        producer.shutdown();
    }
}
