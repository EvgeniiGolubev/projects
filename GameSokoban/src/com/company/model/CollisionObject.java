package com.company.model;

/**
 * Игровые объекты типа "дом" не поддерживают логики столкновений
 * (игрок или ящики могут свободно передвигаться по ним). Что касается
 * остальных объектов, то они не должны проходить сквозь друг друга, они
 * должны сталкиваться. Например, ящик нельзя протолкнуть сквозь стену.
 */
public abstract class CollisionObject extends GameObject {
    public CollisionObject(int x, int y) {
        super(x, y);
    }

    public boolean isCollision(GameObject gameObject, Direction direction) {
        switch (direction) {
            case LEFT:
                return ((x - Model.FIELD_CELL_SIZE) == gameObject.getX()) && (y == gameObject.getY());
            case RIGHT:
                return ((x + Model.FIELD_CELL_SIZE) == gameObject.getX()) && (y == gameObject.getY());
            case UP:
                return ((x == gameObject.getX() && (y - Model.FIELD_CELL_SIZE) == gameObject.getY()));
            case DOWN:
                return ((x == gameObject.getX() && (y + Model.FIELD_CELL_SIZE) == gameObject.getY()));
            default:
                return true;
        }
    }
}
