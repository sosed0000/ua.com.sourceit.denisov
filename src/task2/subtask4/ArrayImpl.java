package task2.subtask4;

import java.util.Iterator;

public class ArrayImpl implements Array {
    public static void main(String[] args) {
        ArrayImpl array = new ArrayImpl();
        array.add(new String("e1rtw"));
        array.add(255);
        array.add(355);
        array.add(4545);
        array.add(555);
        array.add(6535);
        array.add(755);
        array.add(855);
        array.add(955);
        array.add(1055);
        array.add(1155);
        array.add(55);
        System.out.println(array);

    }

    private Object[] objects = new Object[10];

    private void increaseObjectsLength() {
        Object[] increasedObjects = new Object[objects.length * 2];
        // Зробив через цикл, але Идея підказала, що так кращею
        System.arraycopy(objects, 0, increasedObjects, 0, objects.length);
        objects = increasedObjects;
    }

    @Override
    public void add(Object element) {
        boolean isAdded = false;
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] == null) {
                objects[i] = element;
                isAdded = true;
                break;
            }
        }
        if (!isAdded) {
            increaseObjectsLength();
            add(element);
        }
    }

    @Override
    public void set(int index, Object element) {

    }

    @Override
    public Object get(int index) {
        return null;
    }

    @Override
    public int indexOf(Object element) {
        return 0;
    }

    @Override
    public void remove(int index) {

    }

    @Override
    public void clear() {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterator<Object> iterator() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] == null) {
                if (i == 0) {
                    break;
                }
                sb.delete(sb.lastIndexOf(", "), sb.length());
                break;
            }
            sb.append(objects[i]).append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
