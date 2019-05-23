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
     * 每一个域可出现的字符如下：
     *
     * Seconds:可出现", - * /"四个字符，有效范围为0-59的整数
     * Minutes:可出现", - * /"四个字符，有效范围为0-59的整数
     * Hours:可出现", - * /"四个字符，有效范围为0-23的整数
     * DayOfMonth:可出现", - * / ? L W C"八个字符，有效范围为0-31的整数
     * Month:可出现", - * /"四个字符，有效范围为1-12的整数或JAN-DEc
     * DayOfWeek:可出现", - * / ? L C #"四个字符，有效范围为1-7的整数或SUN-SAT两个范围。1表示星期天，2表示星期一，依次类推
     * Year:可出现", - * /"四个字符，有效范围为1970-2099年
     *
     * (1)*:表示匹配该域的任意值，假如在 Minutes 域使用*，即表示每分钟都会触发事件
     * (2)?:只能用在 DayOfMonth 和 DayOfWeek 两个域。它也匹配域的任意值，但实际不会。因为 DayOfMonth 和 DayOfWeek 会相互影响
     * (3)-:表示范围，例如在 Minutes 域使用5-20，表示从5分到20分钟每分钟触发一次
     * (4)/：表示起始时间开始触发，然后每隔固定时间触发一次，例如在 Minutes 域使用5/20，则意味着5分钟触发一次，而25，45等分别触发一次
     * (5),:表示列出枚举值值。例如：在 Minutes 域使用5,20，则意味着在5和20分每分钟触发一次
     * (6)L:表示最后，只能出现在 DayOfWeek 和 DayOfMonth 域，如果在 DayOfWeek 域使用 5L，意味着在最后的一个星期四触发
     * (7)W: 表示有效工作日(周一到周五)，只能出现在 DayOfMonth 域，系统将在离指定日期的最近的有效工作日触发事件
     * (8)LW:这两个字符可以连用，表示在某个月最后一个工作日，即最后一个星期五
     * (9)#:用于确定每个月第几个星期几，只能出现在 DayOfMonth 域
     *
     * @Author pengcheng
     * @Date 2019/4/30
     * */
    @Scheduled(cron = "0 0 5 * * ?")
    private void save() {
        Set<String> keys = redisUtil.keys(RedisKeys.getScheduledMessage("*", "*"));
        if (keys != null && keys.size() > 0) {
            logger.info("查询到" + keys.size() + "组聊天记录");
            String sql = "INSERT INTO user_message(`send`, `receive`, `content`, `send_receive`, `sms_type`, `create_time`) VALUES(?,?,?,?,?,?)";
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
