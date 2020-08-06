package org.tron.core.capsule;

import com.google.protobuf.InvalidProtocolBufferException;
import java.util.List;
import java.util.Map;
import org.tron.common.utils.StringUtil;
import org.tron.core.exception.BadItemException;
import org.tron.protos.contract.BalanceContract.BlockBalanceTrace;
import org.tron.protos.contract.BalanceContract.TransactionBalanceTrace;

import java.util.Objects;

public class BlockBalanceTraceCapsule implements ProtoCapsule<BlockBalanceTrace> {
  private BlockBalanceTrace balanceTrace;

  public BlockBalanceTraceCapsule() {
    balanceTrace = BlockBalanceTrace.newBuilder().build();
  }

  public BlockBalanceTraceCapsule(BlockCapsule blockCapsule) {
    this();
    BlockBalanceTrace.BlockIdentifier blockIdentifier = BlockBalanceTrace.BlockIdentifier.newBuilder()
        .setHash(blockCapsule.getBlockId().getByteString())
        .setNumber(blockCapsule.getNum())
        .build();

    BlockBalanceTrace.BlockIdentifier parentBlockIdentifier = BlockBalanceTrace.BlockIdentifier.newBuilder()
        .setHash(blockCapsule.getNum() == 0 ? blockCapsule.getBlockId().getByteString() : blockCapsule.getParentHashStr())
        .setNumber(blockCapsule.getNum() == 0 ? 0 : blockCapsule.getNum() - 1)
        .build();
    balanceTrace = balanceTrace.toBuilder()
        .setBlockIdentifier(blockIdentifier)
        .setParentBlockIdentifier(parentBlockIdentifier)
        .setTimestamp(blockCapsule.getTimeStamp())
        .build();
  }

  public BlockBalanceTraceCapsule(byte[] data) throws BadItemException {
    try {
      this.balanceTrace = BlockBalanceTrace.parseFrom(data);
    } catch (InvalidProtocolBufferException e) {
      throw new BadItemException("TransactionInfoCapsule proto data parse exception");
    }
  }

  public BlockBalanceTraceCapsule(BlockBalanceTrace blockBalanceTrace) {
    this.balanceTrace = blockBalanceTrace;
  }

  public void addTransactionBalanceTrace(TransactionBalanceTrace transactionBalanceTrace) {
    balanceTrace = balanceTrace.toBuilder()
        .addTransactionBalanceTrace(transactionBalanceTrace)
        .build();
  }

  public void setTransactionBalanceTrace(int index, TransactionBalanceTrace transactionBalanceTrace) {
    balanceTrace = balanceTrace.toBuilder()
        .setTransactionBalanceTrace(index, transactionBalanceTrace)
        .build();
  }

  public void recordBalance(byte[] key, AccountCapsule accountCapsule) {
    String address = StringUtil.encode58Check(key);
    balanceTrace = balanceTrace.toBuilder()
        .putBalance(address, accountCapsule.getBalance())
        .build();
  }

  @Override
  public byte[] getData() {
    if (Objects.isNull(balanceTrace)) {
      return null;
    }
    return balanceTrace.toByteArray();
  }

  @Override
  public BlockBalanceTrace getInstance() {
    return balanceTrace;
  }

  public BlockBalanceTrace.BlockIdentifier getBlockIdentifier() {
    return balanceTrace.getBlockIdentifier();
  }

  public BlockBalanceTrace.BlockIdentifier getParentBlockIdentifier() {
    return balanceTrace.getParentBlockIdentifier();
  }

  public long getTimestamp() {
    return balanceTrace.getTimestamp();
  }

  public List<TransactionBalanceTrace> getTransactions() {
    return balanceTrace.getTransactionBalanceTraceList();
  }
}