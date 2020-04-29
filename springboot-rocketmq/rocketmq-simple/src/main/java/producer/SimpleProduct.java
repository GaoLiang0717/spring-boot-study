package producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.common.message.Message;

/**
 * 需要视频资料或咨询课程的同学可以添加若曦老师的QQ:2746251334
 * @author 86176
 */
public class SimpleProduct {

        public static void main(String[] args) throws Exception {
            //Instantiate with a producer group name.
            DefaultMQProducer producer = new DefaultMQProducer("test");
            // Specify name server addresses.
            producer.setNamesrvAddr("121.41.88.160:9876");
            //Launch the instance.
            producer.start();
            for (int i = 0; i < 10; i++) {
                //Create a message instance, specifying topic, tag and message body.
                Message msg = new Message("test2", "TagA", ("testMessage"+i).getBytes());
                //如果你要保证你有一系列消息需要有序的话 你只需要把他们这几条消息统一的标识 传递进来就可以了
                //Call send message to deliver message to one of brokers.
                SendResult sendResult = producer.send(msg, new SelectMessageQueueByHash(),"userID");
                System.out.printf("%s%n", sendResult);
            }
//
//            for (int i = 10; i < 20; i++) {
//                //Create a message instance, specifying topic, tag and message body.
//                Message msg = new Message("test2" /* Topic */,
//                        "TagA" /* Tag */,
//                        ("testMessage"+i).getBytes() /* Message body */
//                );
//                //如果你要保证你有一系列消息需要有序的话 你只需要把他们这几条消息统一的标识 传递进来就可以了
//                //orderid
//                //                //Call send message to deliver message to one of brokers.
//                SendResult sendResult = producer.send(msg, new SelectMessageQueueByHash(),"2");
//                System.out.printf("%s%n", sendResult);
//            }



            //Shut down once the producer instance is not longer in use.
            producer.shutdown();
        }
}
