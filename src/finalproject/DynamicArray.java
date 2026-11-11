package finalproject;

public class DynamicArray <T>{

    private Object[] data;
    private int size;
    private int capacity;

    private static final int INITIAL_CAPACITY = 2;

    public DynamicArray() {
        capacity = INITIAL_CAPACITY;
        data = new Object[capacity];
        size = 0;
    }

    public void add(T value) {

        if (size == capacity) {
            resize();
        }

        data[size] = value;
        size++;
    }

    private void resize() {

        int newCapacity = capacity * 2;
        Object[] newData = new Object[newCapacity];

        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }

        data = newData;
        capacity = newCapacity;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {

        checkIndex(index);

        return (T) data[index];
    }

    public void set(int index, T value) {

        checkIndex(index);

        data[index] = value;
    }

    public T removeLast() {

        if (size == 0) {
            throw new IllegalStateException(
                    "Dynamic Array is empty."
            );
        }

        T value = get(size - 1);

        data[size - 1] = null;
        size--;

        return value;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {

        for (int i = 0; i < size; i++) {
            data[i] = null;
        }

        size = 0;
    }

    private void checkIndex(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Invalid index: " + index
            );
        }
    }
}
