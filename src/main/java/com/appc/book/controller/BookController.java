package com.appc.book.controller;

import com.appc.book.common.components.BookComponent;
import com.appc.book.common.components.cool18.Cool18Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;

@RestController
@RequestMapping("book")
public class BookController {
    @Autowired
    private Executor executor;

    @Async
    @RequestMapping(value = "loadCool18TopicList", method = RequestMethod.GET)
    void loadCool18TopicList() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                BookComponent bookComponent = new Cool18Component();
                bookComponent.loadTopicList(executor);
            }
        });

    }

}
