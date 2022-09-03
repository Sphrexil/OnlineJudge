package com.oj.mq.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

//MQ接收，并根据结果运行内部逻辑
@SuppressWarnings("all")
@RocketMQTransactionListener(txProducerGroup = "Tx-Group", corePoolSize = 5, maximumPoolSize = 10)
public class TransactionListenerImpl implements RocketMQLocalTransactionListener {
    /**
     * 执行本地事务：也就是执行本地业务逻辑
     *
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        Object num = msg.getHeaders().get("test");

        if ("1".equals(num)) {
            System.out.println("executer: " + new String((byte[]) msg.getPayload()) + " unknown");
            return RocketMQLocalTransactionState.UNKNOWN;
        }
        else if ("2".equals(num)) {
            System.out.println("executer: " + new String((byte[]) msg.getPayload()) + " rollback");
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        System.out.println("executer: " + new String((byte[]) msg.getPayload()) + " commit");
        return RocketMQLocalTransactionState.COMMIT;
    }

    /**
     * 回调检查
     *
     * @param msg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        System.out.println("check: " + new String((byte[]) msg.getPayload()));
        return RocketMQLocalTransactionState.COMMIT;
    }
}
