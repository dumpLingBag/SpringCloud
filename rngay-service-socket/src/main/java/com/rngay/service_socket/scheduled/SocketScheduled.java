package com.rngay.service_socket.scheduled;

import com.rngay.common.cache.RedisUtil;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.common.jpa.util.batch.ObjectParameterizedPreparedStatementSetter;
import com.rngay.service_socket.contants.RedisKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class SocketScheduled {

    private Logger logger = LoggerFactory.getLogger(SocketScheduled.class);

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private Dao dao;

    /**
     * 每天凌晨 5 点将 redis 里的聊天记录保存到数据库
     *
     * @Author pengcheng
     * @Date 2019/4/30
     **/
    @Scheduled(cron = "0 0 5 * * ?")
    private void save() {
        Set<String> keys = redisUtil.keys(RedisKeys.getMessage("*", "*"));
        if (keys != null && keys.size() > 0) {
            logger.info("查询到" + keys.size() + "组聊天记录");
            String sql = "INSERT INTO user_message(send_user_id,receive_user_id,content,send_receive_user_id,create_time) VALUES(?,?,?,?,?)";
            for (String key : keys) {
                Set<Object> range = redisUtil.range(key, 0, -1);
                if (range.size() > 0) {
                    int[][] ints = dao.batchUpdate(sql, range, 500, new ObjectParameterizedPreparedStatementSetter<>());
                    logger.info("成功插入" + ints.length + "条聊天记录");
                    redisUtil.del(key);
                    logger.info("成功删除 key 为" + key + "的聊天记录");
                }
            }
        }
    }

}
