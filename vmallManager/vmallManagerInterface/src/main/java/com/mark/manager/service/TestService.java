package com.mark.manager.service;

public interface TestService {
    Long directExchange();
    Long directExchangeReceiving() throws Exception;
    void workQueues(String message);
    void workQueuesReceiving();
    void publish();
    void subscribe();
    void topic();
    void topicReceiving();
}
