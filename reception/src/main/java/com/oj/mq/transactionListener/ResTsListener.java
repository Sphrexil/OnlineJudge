package com.oj.mq.transactionListener;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

import java.util.Objects;

//@RocketMQTransactionListener(txProducerGroup = "res-group", corePoolSize = 5, maximumPoolSize = 10)
public class ResTsListener implements RocketMQLocalTransactionListener {
    /**
     * 执行本地事务：也就是执行本地业务逻辑
     *
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        // 此处进行对消息的过滤，让指定的消息回滚，让指定消息提交
        if (Objects.isNull(msg)) {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        // RocketMQLocalTransactionState.UNKNOWN会进入到二次确认环节
        // 可以在此处加上二次确认的条件
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
        // 这里进行二次确认
        System.out.println("check: " + new String((byte[]) msg.getPayload()));
        return RocketMQLocalTransactionState.COMMIT;
    }
}
