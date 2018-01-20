package com.appc.book.common.components;

import com.appc.book.entity.Reply;
import com.appc.book.entity.Topic;
import com.appc.book.common.utils.Springs;
import com.appc.book.dao.ReplyDao;
import com.appc.book.dao.TopicDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

public abstract class BookComponent {
    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 每页小于多少字数忽略
     */
    protected static final Integer IGNORE_REPLY_LENGTH = 2500;


    private void loadTopic(Topic topic) throws IOException {
        logger.info(topic.getTitle());
        TopicDao topicDao = Springs.getBean(TopicDao.class);
        Topic topic1 = topicDao.findTopic(topic.getUrl());
        if (topic1 == null || !(topic.getLastReplyDate().equals(topic1.getLastReplyDate()) || topic.getReplyCount().equals(topic1.getReplyCount()))) {
            topicDao.save(topic);
            ReplyDao replyDao = Springs.getBean(ReplyDao.class);
            replyDao.deleteReplyByTopicId(topic.getId());
            Integer replyPage = 0;
            List<Reply> replyLists = null;
            while (!CollectionUtils.isEmpty((replyLists = loadReplyList(topic, ++replyPage)))) {
                replyDao.saveAll(replyLists);
            }
        }


    }

    public void loadTopicList(Executor executor) {
        loadTopicList(executor, 0);
    }

    protected void loadTopicList(Executor executor, Integer page) {

        executor.execute(() -> {
            logger.info("加载第" + page + "页");

            try {
                List<Topic> topicList = loadTopicList(page);
                if (!CollectionUtils.isEmpty(topicList)) {
                    for (Topic topic : topicList)
                        executor.execute(() -> {
                            try {
                                loadTopic(topic);
                            } catch (IOException e) {
                                logger.error(e.getMessage(), e);
                            }
                        });
                    loadTopicList(executor, page + 1);
                }

            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }

        });
    }


    protected abstract List<Topic> loadTopicList(Integer page) throws IOException;

    protected abstract List<Reply> loadReplyList(Topic topic, Integer page) throws IOException;


}
