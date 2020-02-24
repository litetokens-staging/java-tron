package org.tron.core.metrics;

import com.codahale.metrics.*;
import java.util.SortedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MonitorMetric {

  public static final String BLOCKCHAIN_TPS = "blockchain.TPS";
  public static final String BLOCKCHAIN_BLOCKPROCESS_TIME = "blockchain.blockProcessTime";
  public static final String BLOCKCHAIN_BLOCK_COUNT = "blockchain.blockCount";
  public static final String BLOCKCHAIN_BLOCK_TX_COUNT = "blockchain.blockTxCount";
  public static final String BLOCKCHAIN_SUCCESS_FORK_COUNT = "blockchain.successForkCount";
  public static final String BLOCKCHAIN_FAIL_FORK_COUNT = "blockchain.failForkCount";
  public static final String NET_BLOCK_LATENCY = "net.block.latency";
  public static final String NET_BLOCK_LATENCY_WITNESS = "net.block.latency.witness.";
  public static final String NET_DISCONNECTION_COUNT = "net.disconnection.count";
  public static final String NET_DISCONNECTION_REASON = "net.disconnection.reason.";
  public static final String NODE_STATUS = "node.status";
  public static final String NET_API = "net.api";
  public static final String NET_ERROR_PROTO_COUNT = "net.errorProtoCount";
  public static final String NET_TCP_IN_TRAFFIC = "net.TCPInTraffic";
  public static final String NET_TCP_OUT_TRAFFIC = "net.TCPOutTraffic";
  public static final String NET_UDP_IN_TRAFFIC = "net.UDPInTraffic";
  public static final String NET_UDP_OUT_TRAFFIC = "net.UDPOutTraffic";

  @Autowired
  private MetricRegistry metricRegistry;

  public Histogram getHistogram(String name) {
    return metricRegistry.histogram(name);
  }

  public SortedMap<String, Histogram> getHistograms(String name) {
    return metricRegistry.getHistograms((s, metric) -> s.startsWith(name));
  }

  public Meter getMeter(String name) {
    return metricRegistry.meter(name);
  }

  public Counter getCounter(String name) {
    return metricRegistry.counter(name);
  }

  public SortedMap<String, Counter> getCounters(String name) {
    return metricRegistry.getCounters((s, metric) -> s.startsWith(name));
  }
}