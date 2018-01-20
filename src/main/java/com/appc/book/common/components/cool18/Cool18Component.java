package com.appc.book.common.components.cool18;

import com.appc.book.common.components.BookComponent;
import com.appc.book.entity.Reply;
import com.appc.book.entity.Topic;
import com.appc.book.common.utils.Charsets;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Cool18Component extends BookComponent {

    private static final String FIRST_URL = "http://www.cool18.com/bbs4/";

    @Override
    protected List<Topic> loadTopicList(Integer page) throws IOException {
        List<Topic> topics = new ArrayList<>();
        Document document = Jsoup.connect(FIRST_URL + "index.php?app=forum&act=normalview&p=" + page).get();
        Elements trs = document.select("#t_list_tab tr");
        for (int i = 0; i < trs.size(); i++) {
            if (i != 0) {
                Topic topic = new Topic();
                Element a = trs.get(i).select(".n_subject a").get(1);
                topic.setTitle(Charsets.toUTF_8(a.text()));
                topic.setUrl(a.attr("href"));
                a = trs.get(i).select(".n_author a").get(0);
                topic.setAuthor(a.text());
                topic.setLastReplyDate(trs.get(i).select(".n_last .n_dateline").text());
                Element td = trs.get(i).select(".n_view_r").get(0);
                topic.setReplyCount("-".equals(td.text()) ? 1 : Integer.parseInt(td.text()));
                topics.add(topic);
            }
        }
        return topics;
    }

    @Override
    protected List<Reply> loadReplyList(Topic topic, Integer page) throws IOException {
        List<Reply> replies = new ArrayList<>();
        Document document = Jsoup.connect(FIRST_URL + topic.getUrl() + "&p=" + page).get();
        Elements replyContents = document.select(".replycontent");
        for (Element replyContent : replyContents) {
            String content = Charsets.toUTF_8(replyContent.select(".replay_content_content").text());
            if (!StringUtils.isEmpty(content) && content.length() > IGNORE_REPLY_LENGTH) {
                Reply reply = new Reply();
                reply.setTopicId(topic.getId());
                reply.setContent(content);
                String stepStr = replyContent.select(".r_step a").attr("name");
                if (stepStr.startsWith("step_")) {
                    reply.setStep(Integer.parseInt(stepStr.replace("step_", "")));
                } else {
                    reply.setStep(0);
                }
                replies.add(reply);
            }
        }

        return replies;
    }
}
