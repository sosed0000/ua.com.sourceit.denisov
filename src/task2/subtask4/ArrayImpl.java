package task2.subtask4;

import java.util.Iterator;

public class ArrayImpl<T> implements Array {
    public static void main(String[] args) {
        ArrayImpl<Object> array = new ArrayImpl<>();
        array.add(new String("e1rtw"));
        array.add(255);
        array.add(355);
        array.add(null);
        array.add(555);
        array.add(6535);
        array.add(755);
        array.add(855);
        array.add(955);
        array.add(1055);
        array.add(1155);
        array.add(55);
        array.remove(4);
        System.out.println(array);

    }

    private Object[] objects = new Object[10];
    private int nextIndex = 0;

    public static class IteratorImpl<T> implements Iterator<T> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            return null;
        }
    }


    private boolean isFull() {
        return nextIndex == (objects.length - 1);
    }

    private void increaseObjectsLength() {
        Object[] increasedObjects = new Object[objects.length * 2];
        // Зробив через цикл, але Идея підказала, що так кращею
        System.arraycopy(objects, 0, increasedObjects, 0, objects.length);
        objects = increasedObjects;
    }

    /*  Вирішів зробити по іншому так як в цьому варіанті неможна зберегати null
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

     */
    @Override
    public void add(Object element) {
        if (isFull()) {
            increaseObjectsLength();
        }
        objects[nextIndex++] = element;
    }

    @Override
    public void set(int index, Object element) {
        objects[index] = element;
    }

    @Override
    public Object get(int index) {
        return objects[index];
    }

    @Override
    public int indexOf(Object element) {
        for (int i = 0; i <= nextIndex; i++) {
            if (objects[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void remove(int index) {
        for (int i = index; i < nextIndex ; i++) {
            objects[i] = objects[i + 1];
        }
        nextIndex--;
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
        for (int i = 0; i < nextIndex; i++) {
            sb.append(objects[i]).append(", ");
        }
        sb.replace(sb.lastIndexOf(", "), sb.length(),"]");
        return sb.toString();
    }
}
