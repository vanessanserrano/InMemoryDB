public class Main {
    public static void main(String[] args) {
        InMemoryDB db = new InMemoryDBImpl();

        System.out.println("get(A) before any transaction: " + db.get("A"));

        try {
            db.put("A", 5);
        } catch (Exception e) {
            System.out.println("Expected error (no transaction): " + e.getMessage());
        }

        db.begin_transaction();
        db.put("A", 5);
        System.out.println("get(A) inside transaction (expected null): " + db.get("A"));
        db.put("A", 6);
        db.commit();

        System.out.println("get(A) after commit: " + db.get("A"));

        try {
            db.commit();
        } catch (Exception e) {
            System.out.println("Expected error (commit without transaction): " + e.getMessage());
        }

        try {
            db.rollback();
        } catch (Exception e) {
            System.out.println("Expected error (rollback without transaction): " + e.getMessage());
        }

        System.out.println("get(B) before transaction: " + db.get("B"));
        db.begin_transaction();
        db.put("B", 10);
        db.rollback();
        System.out.println("get(B) after rollback: " + db.get("B"));
    }
}
