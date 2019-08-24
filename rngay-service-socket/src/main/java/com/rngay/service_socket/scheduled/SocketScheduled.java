package com.rngay.service_socket.scheduled;

import com.riicy.common.cache.RedisUtil;
import com.riicy.common.contants.RedisKeys;
import com.riicy.socket.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Set;

@Component
public class SocketScheduled {

    private Logger logger = LoggerFactory.getLogger(SocketScheduled.class);

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 每天凌晨 5 点将 redis 里的聊天记录保存到数据库
     *
     * @Author pengcheng
     * @Date 2019/4/30
     **/
    //@Scheduled(cron = "0 0 5 * * ?")
    private void save() {
        Set<String> keys = redisUtil.keys(RedisKeys.getMessage("*", "*"));
        if (keys != null && keys.size() > 0) {
            logger.info("查询到" + keys.size() + "组聊天记录");
            String sql = "INSERT INTO user_message(`send`, `receive`, `content`, `send_receive`, `sms_type`, `create_time`) VALUES(?,?,?,?,?,?)";
            for (String key : keys) {
                Set<Object> range = redisUtil.zrange(key, 0, -1);
                if (range.size() > 0) {
                    int[][] ints = jdbcTemplate.batchUpdate(sql, range, 500, (ps, argument) -> {
                        Field[] fields = argument.getClass().getDeclaredFields();
                        try {
                            int i = 0;
                            for (Field field : fields) {
                                boolean accessible = field.isAccessible();
                                field.setAccessible(true);

                                Object o = field.get(argument);
                                if (o != null && !"".equals(o)) {
                                    boolean anEnum = field.getType().isEnum();
                                    ps.setObject(i = i + 1, anEnum ? ((Enum)o).name() : o);
                                }

                                field.setAccessible(accessible);
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });
                    logger.info("成功插入" + (ints.length > 0 ? ints.length : 0) + "条聊天记录");
                    redisUtil.del(key);
                    logger.info("成功删除 key 为" + key + "的聊天记录");
                }
            }
        }
    }

    @Scheduled(cron = "0 0 5 * * ?")
    public void updateAccessKey() {
        MessageUtil.accessToken(redisUtil);
    }

}
