package com.kireally.Currency.controller.test;

class KafkaLatencyStorage {
    private static final java.util.concurrent.ConcurrentLinkedQueue<KafkaLatencyRecord> records = new java.util.concurrent.ConcurrentLinkedQueue<>();

    public static void addLatency(String messageId, long sendTime, long receiveTime, long latency) {
        records.add(new KafkaLatencyRecord(messageId, sendTime, receiveTime, latency));
    }

    public static java.util.List<KafkaLatencyRecord> getRecords() {
        return new java.util.ArrayList<>(records);
    }

    public static void clear() {
        records.clear();
    }
}
