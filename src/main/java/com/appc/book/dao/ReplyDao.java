package com.appc.book.dao;

import com.appc.book.entity.Reply;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


public interface ReplyDao extends CrudRepository<Reply, Long> {

    @Transactional
    @Modifying
    @Query("delete from Reply t where t.topicId=:topicId")
    int deleteReplyByTopicId(@Param("topicId") Long topicId);
}