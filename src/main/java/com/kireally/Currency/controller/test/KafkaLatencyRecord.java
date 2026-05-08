package com.kireally.Currency.controller.test;

class KafkaLatencyRecord {
    public final String messageId;
    public final long sendTime;
    public final long receiveTime;
    public final long latency;

    public KafkaLatencyRecord(String messageId, long sendTime, long receiveTime, long latency) {
        this.messageId = messageId;
        this.sendTime = sendTime;
        this.receiveTime = receiveTime;
        this.latency = latency;
    }
}