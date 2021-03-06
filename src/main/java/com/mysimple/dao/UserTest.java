package com.mysimple.dao;

import java.io.Serializable;
import java.util.Date;

public class UserTest implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_test.id
     *
     * @mbggenerated Wed Jul 11 15:11:46 CST 2018
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_test.account
     *
     * @mbggenerated Wed Jul 11 15:11:46 CST 2018
     */
    private String account;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_test.password
     *
     * @mbggenerated Wed Jul 11 15:11:46 CST 2018
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_test.nick_name
     *
     * @mbggenerated Wed Jul 11 15:11:46 CST 2018
     */
    private String nickName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_test.create_time
     *
     * @mbggenerated Wed Jul 11 15:11:46 CST 2018
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user_test
     *
     * @mbggenerated Wed Jul 11 15:11:46 CST 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_test.id
     *
     * @return the value of user_test.id
     *
     * @mbggenerated Wed Jul 11 15:11:46 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_test.id
     *
     * @param id the value for user_test.id
     *
     * @mbggenerated Wed Jul 11 15:11:46 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_test.account
     *
     * @return the value of user_test.account
     *
     * @mbggenerated Wed Jul 11 15:11:46 CST 2018
     */
    public String getAccount() {
        return account;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_test.account
     *
     * @param account the value for user_test.account
     *
     * @mbggenerated Wed Jul 11 15:11:46 CST 2018
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_test.password
     *
     * @return the value of user_test.password
     *
     * @mbggenerated Wed Jul 11 15:11:46 CST 2018
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_test.password
     *
     * @param password the value for user_test.password
     *
     * @mbggenerated Wed Jul 11 15:11:46 CST 2018
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_test.nick_name
     *
     * @return the value of user_test.nick_name
     *
     * @mbggenerated Wed Jul 11 15:11:46 CST 2018
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_test.nick_name
     *
     * @param nickName the value for user_test.nick_name
     *
     * @mbggenerated Wed Jul 11 15:11:46 CST 2018
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_test.create_time
     *
     * @return the value of user_test.create_time
     *
     * @mbggenerated Wed Jul 11 15:11:46 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_test.create_time
     *
     * @param createTime the value for user_test.create_time
     *
     * @mbggenerated Wed Jul 11 15:11:46 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}