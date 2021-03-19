package net.lwenstrom.mock;

public interface TableMock<Access, Entity> {
    void save(Entity entity);

    boolean contains(Access access);

    Entity search(Access access);

    void delete(Access access);

    int count();
}
