import java.util.HashMap;
import java.util.Map;

public class InMemoryDBImpl implements InMemoryDB {

    private final Map<String, Integer> committed = new HashMap<>();
    private Map<String, Integer> transactionChanges = null;
    private boolean inTransaction = false;

    @Override
    public void begin_transaction() {
        if (inTransaction) {
            throw new IllegalStateException("Transaction already in progress");
        }
        inTransaction = true;
        transactionChanges = new HashMap<>();
    }

    @Override
    public void put(String key, int value) {
        if (!inTransaction) {
            throw new IllegalStateException("No active transaction");
        }
        transactionChanges.put(key, value);
    }
    @Override
    public Integer get(String key) {
    return committed.getOrDefault(key, null);
}


    @Override
    public void commit() {
        if (!inTransaction) {
            throw new IllegalStateException("No active transaction to commit");
        }
        for (Map.Entry<String, Integer> entry : transactionChanges.entrySet()) {
            committed.put(entry.getKey(), entry.getValue());
        }
        transactionChanges.clear();
        inTransaction = false;
    }

    @Override
    public void rollback() {
        if (!inTransaction) {
            throw new IllegalStateException("No active transaction to rollback");
        }
        transactionChanges.clear();
        inTransaction = false;
    }
}
