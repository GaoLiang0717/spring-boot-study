package consumer;


import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 需要视频资料或咨询课程的同学可以添加若曦老师的QQ:2746251334
 * @author 86176
 */
public class SimpleConsumer {


    public static void main(String[] args) throws InterruptedException, MQClientException {
        // Instantiate with specified consumer group name.
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test");
        // Specify name server addresses.
        consumer.setNamesrvAddr("121.41.88.160:9876");
        // Subscribe one more more topics to consume.
        consumer.subscribe("test-consumer", "*");

        //Concurrently多线程无序消费
        orderly(consumer);

        //单线程对应单队列
        concurrently(consumer);

        //Launch the consumer instance.
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }

    /**
     * //单线程对应单队列
     * @param consumer
     */
    private static void orderly(DefaultMQPushConsumer consumer) {
        consumer.registerMessageListener(new MessageListenerOrderly() {

            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs,
                                                       ConsumeOrderlyContext context) {
                for (MessageExt msg : msgs) {
                    System.out.printf("%s Receive New Messages: %s %s 延迟时间 %s %n", Thread.currentThread().getName(), new String(msg.getBody()),msg.getQueueId(),(System.currentTimeMillis()-msg.getStoreTimestamp()));
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
    }

    /**
     * //Concurrently多线程无序消费
     * @param consumer
     * @throws MQClientException
     */
    private static void concurrently(DefaultMQPushConsumer consumer) throws MQClientException {
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    System.out.printf("%s Receive New Messages: %s %s %n", Thread.currentThread().getName(), new String(msg.getBody()),msg.getQueueId());
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

    }
}
