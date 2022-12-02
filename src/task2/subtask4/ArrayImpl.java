package task2.subtask4;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayImpl implements Array {
    public static void main(String[] args) {
        System.out.println("Створення колекції");
        System.out.println("ArrayImpl array = new ArrayImpl();\n");
        ArrayImpl array = new ArrayImpl();

        System.out.println("Додавання елементів (11 об'єктів для перевірки роботи методу розширення масиву)");
        System.out.println("array.add(\"Object01\");\n" +
                "array.add(\"Object02\");\n" +
                "array.add(\"Object03\");\n" +
                "array.add(\"Object04\");\n" +
                "array.add(\"Object05\");\n" +
                "array.add(\"Object06\");\n" +
                "array.add(\"Object07\");\n" +
                "array.add(\"Object08\");\n" +
                "array.add(\"Object09\");\n" +
                "array.add(\"Object10\");\n" +
                "array.add(\"Object11\");");
        array.add("Object01");
        array.add("Object02");
        array.add("Object03");
        array.add("Object04");
        array.add("Object05");
        array.add("Object06");
        array.add("Object07");
        array.add("Object08");
        array.add("Object09");
        array.add("Object10");
        array.add("Object11");
        System.out.println("array.toString: " + array + "\n");

        System.out.println("Перевірка методу set()");
        System.out.println("array.set(4, \"NewObject05\");\n" + "array.set(10, \"NewObject11\");");
        array.set(4, "NewObject05");
        array.set(10, "NewObject11");
        System.out.println("array.toString: " + array + "\n");

        System.out.println("Перевірка методу get()");
        System.out.println("array.toString: " + array);
        System.out.println("array.get(5);: " + array.get(5));
        System.out.println("array.get(10);: " + array.get(10));
        System.out.println();

        System.out.println("Перевірка методу indexOf()");
        System.out.println("array.toString: " + array);
        System.out.println("array.indexOf(\"Object03\");: " + array.indexOf("Object03"));
        System.out.println("array.indexOf(\"NotExistingObject\"): " + array.indexOf("NotExistingObject"));
        System.out.println();

        System.out.println("Перевірка методу remove()");
        System.out.println("array.toString: " + array);
        System.out.println("array.remove(2);\n" + "array.remove(9);");
        array.remove(2);
        array.remove(9);
        System.out.println("array.toString: " + array + "\n");

        System.out.println("Перевірка методу size()");
        System.out.println("array.toString: " + array);
        System.out.println("array.size();: " + array.size());
        System.out.println();

        System.out.println("Перевірка методу clear()");
        System.out.println("array.clear();");
        array.clear();
        System.out.println("array.toString: " + array + "\n");

        System.out.println("Перевірка класу IteratorImpl");
        System.out.println("Додавання елементів");
        System.out.println("array.add(\"Object01\");\n" +
                "array.add(\"Object02\");\n" +
                "array.add(\"Object03\");\n" +
                "array.add(\"Object04\");\n" +
                "array.add(\"Object05\");\n");
        array.add("Object01");
        array.add("Object02");
        array.add("Object03");
        array.add("Object04");
        array.add("Object05");
        System.out.println("Створення ітератору");
        System.out.println("Iterator<Object> iterator = array.iterator();");
        Iterator<Object> iterator = array.iterator();
        System.out.println("Перевірка методів hasNext() і next()");
        System.out.println("iterator.hasNext();: " + iterator.hasNext());
        System.out.println("iterator.next();: " + iterator.next());
        System.out.println("iterator.next();: " + iterator.next());
        System.out.println("iterator.next();: " + iterator.next());
        System.out.println("iterator.next();: " + iterator.next());
        System.out.println("iterator.next();: " + iterator.next());
        System.out.println("Перевірка методу remove()");
        System.out.println("iterator.remove();\n" +
                "iterator.remove();");
        iterator.remove();
        iterator.remove();
        System.out.println("array.toString: " + array);
        System.out.println("iterator.hasNext();: " + iterator.hasNext());

    }

    private Object[] objects = new Object[10];
    private int nextIndex = 0;

    private class IteratorImpl implements Iterator<Object> {

        int cursor;
        int lastReturned = -1;

        private IteratorImpl() {
        }

        @Override
        public boolean hasNext() {
            return cursor != nextIndex;
        }

        @Override
        public Object next() {
            if (cursor >= nextIndex)
                throw new NoSuchElementException();

            return objects[lastReturned = cursor++];
        }

        @Override
        public void remove() {
            ArrayImpl.this.remove(lastReturned);
            cursor = lastReturned;
            lastReturned--;
        }
    }


    private boolean isFull() {
        return nextIndex == (objects.length - 1);
    }

    private void increaseObjectsLength() {
        Object[] increasedObjects = new Object[objects.length + 10];
        // Зробив через цикл, але Ідея підказала, що так краще.
        System.arraycopy(objects, 0, increasedObjects, 0, objects.length);
        objects = increasedObjects;
    }

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
        if (index >= nextIndex) throw new NoSuchElementException();
        return objects[index];
    }

    @Override
    public int indexOf(Object element) {
        int index = -1;
        for (int i = 0; i < nextIndex; i++) {
            if (objects[i].equals(element)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public void remove(int index) {
        if (index >= nextIndex) throw new NoSuchElementException();
        for (int i = index; i < nextIndex; i++) {
            objects[i] = objects[i + 1];
        }
        nextIndex--;
    }

    @Override
    public void clear() {
        objects = new Object[10];
        nextIndex = 0;
    }

    @Override
    public int size() {
        return nextIndex;
    }

    @Override
    public Iterator<Object> iterator() {
        return new IteratorImpl();
    }

    @Override
    public String toString() {
        if (nextIndex == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < nextIndex; i++) {
            sb.append(objects[i]).append(", ");
        }
        sb.replace(sb.lastIndexOf(", "), sb.length(), "]");
        return sb.toString();
    }
}
