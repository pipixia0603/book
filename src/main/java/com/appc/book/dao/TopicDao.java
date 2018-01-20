package com.appc.book.dao;

import com.appc.book.entity.Topic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TopicDao extends CrudRepository<Topic, Long> {
    @Query(" from Topic t where t.url=:url")
    Topic findTopic(@Param("url") String url);
}