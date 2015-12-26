package com.mitty.db.utils.dao.jdbctemplate;

import com.mitty.db.utils.date.DateFormatEntry;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by PILIANG on 2015/11/20.
 */
public class AbstractJDBCTemplateBaseDao implements DateFormatEntry {

    // SPRING JDBC模板接口
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Resource
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 获取日期
     *
     * @param timestamp
     * @return Date
     */
    public Date getDate(Timestamp timestamp) {
        return toDate(timestamp, null);
    }

    /**
     * 获取日期
     *
     * @param timestamp
     * @param format
     * @return Date
     */
    public Date getDate(Timestamp timestamp, String format) {
        return toDate(timestamp, format);
    }

    /**
     * Timestamp按格式转换成Date
     *
     * @param timestamp
     * @param format
     * @return Date
     */
    public Date toDate(Timestamp timestamp, String format) {
        Date date = null;
        if (null == format || "".equals(format))
            format = DEFAULT_FORMAT;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            date = sdf.parse(sdf.format(timestamp));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}

