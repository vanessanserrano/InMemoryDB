public interface InMemoryDB {
    void begin_transaction();
    void put(String key, int value);
    Integer get(String key);
    void commit();
    void rollback();
}
