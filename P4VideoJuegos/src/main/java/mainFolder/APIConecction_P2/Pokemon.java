package mainFolder.APIConecction_P2;

public class Pokemon {

    public String name;
    public int height;
    public int weight;
    public TypeSlot[] types;

    static class TypeSlot {
        Type type;
    }

    static class Type {
        String name;
    }
}
