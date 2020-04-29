package com.gaoliang.springbootrocketmqconsumer.simple;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author GaoLiang
 * @date 2020/4/29 15:07
 */
@Component
public class UserConsumer {
    /**
     * 消费者的组名
     */
    @Value("${rocketmq.consumer.group}")
    private String consumerGroup;

    /**
     * NameServer 地址
     */
    @Value("${rocketmq.name-server}")
    private String namesrvAddr;

    @PostConstruct
    public void consumer() {
        System.err.println("init defaultMQPushConsumer");
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(namesrvAddr);
        try {
            consumer.subscribe("user-topic", "user-tag");
            consumer.registerMessageListener((MessageListenerConcurrently) (list, context) -> {
                try {
                    for (MessageExt messageExt : list) {

                        System.err.println("消费消息: " + new String(messageExt.getBody()));//输出消息内容
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER; //稍后再试
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; //消费成功
            });
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}