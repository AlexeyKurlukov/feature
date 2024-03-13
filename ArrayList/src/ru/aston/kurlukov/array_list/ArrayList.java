package ru.aston.kurlukov.array_list;

import java.util.*;

public class ArrayList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 10;

    private E[] items;
    private int size;
    private int modificationsCount;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Начальная вместимость не может быть отрицательной. Передана вместимость: " + capacity);
        }

        //noinspection unchecked
        items = (E[]) new Object[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object object) {
        return indexOf(object) != -1;
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements Iterator<E> {
        private int currentIndex;
        private int expectedModificationsCount;
        private boolean isRemoved;

        public ArrayListIterator() {
            expectedModificationsCount = modificationsCount;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public E next() {
            checkModification();

            if (!hasNext()) {
                throw new NoSuchElementException("Нет больше элементов в списке");
            }

            isRemoved = false;
            E item = items[currentIndex];
            currentIndex++;
            return item;
        }

        private void checkModification() {
            if (modificationsCount != expectedModificationsCount) {
                throw new ConcurrentModificationException("Список был изменен");
            }
        }

        @Override
        public void remove() {
            checkModification();

            if (isRemoved) {
                throw new IllegalStateException("Метод remove() может быть вызван только один раз после каждого вызова next()");
            }

            ArrayList.this.remove(currentIndex - 1);
            currentIndex--;
            expectedModificationsCount = modificationsCount;
            isRemoved = true;
        }
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, size);
    }

    @Override
    public <T> T[] toArray(T[] array) {
        if (array.length < size) {
            // noinspection unchecked
            return (T[]) Arrays.copyOf(items, size, array.getClass());
        }

        // noinspection  SuspiciousSystemArraycopy
        System.arraycopy(items, 0, array, 0, size);

        if (array.length > size) {
            array[size] = null;
        }

        return array;
    }

    @Override
    public boolean add(E item) {
        add(size, item);
        return true;
    }

    @Override
    public boolean remove(Object object) {
        int index = indexOf(object);

        if (index != -1) {
            remove(index);
            return true;
        }

        return false;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);

        E removedItem = items[index];

        if (index < size - 1) {
            System.arraycopy(items, index + 1, items, index, size - index - 1);
        }

        items[size - 1] = null;
        size--;
        modificationsCount++;
        return removedItem;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Предоставленный индекс " + index +
                    " находится за пределами списка. Индекс должен быть в диапазоне [0, " + (size - 1) + "]");
        }
    }

    private void checkInsertIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Предоставленный индекс " + index +
                    " находится за пределами списка. Индекс для вставки должен быть в диапазоне [0, " + size + "]");
        }
    }

    public void ensureCapacity(int minCapacity) {
        if (minCapacity > items.length) {
            items = Arrays.copyOf(items, minCapacity);
        }
    }

    public void trimToSize() {
        if (size < items.length) {
            items = Arrays.copyOf(items, size);
        }
    }

    private void checkCollectionIsNull(Collection<?> collection) {
        if (collection == null) {
            throw new IllegalArgumentException("Коллекция не может быть null");
        }
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        checkCollectionIsNull(collection);

        for (Object object : collection) {
            if (!contains(object)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return addAll(size, collection);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        checkCollectionIsNull(collection);

        checkInsertIndex(index);

        if (collection.isEmpty()) {
            return false;
        }

        ensureCapacity(size + collection.size());
        System.arraycopy(items, index, items, index + collection.size(), size - index);

        int i = index;

        for (E item : collection) {
            items[i] = item;
            i++;
        }

        size += collection.size();
        modificationsCount++;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        checkCollectionIsNull(collection);

        if (collection.isEmpty()) {
            return false;
        }

        boolean isModified = false;

        for (int i = size - 1; i >= 0; i--) {
            if (collection.contains(items[i])) {
                remove(i);
                isModified = true;
            }
        }

        if (isModified) {
            modificationsCount++;
        }

        return isModified;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        checkCollectionIsNull(collection);

        boolean isModified = false;

        for (int i = size - 1; i >= 0; i--) {
            if (!collection.contains(items[i])) {
                remove(i);
                isModified = true;
            }
        }

        if (isModified) {
            modificationsCount++;
        }

        return isModified;
    }

    @Override
    public void clear() {
        if (size == 0) {
            return;
        }

        Arrays.fill(items, 0, size, null);
        size = 0;
        modificationsCount++;
    }

    @Override
    public E get(int index) {
        checkIndex(index);

        return items[index];
    }

    @Override
    public E set(int index, E item) {
        checkIndex(index);

        E oldItem = items[index];
        items[index] = item;
        return oldItem;
    }

    private void increaseCapacity() {
        if (items.length == 0) {
            //noinspection unchecked
            items = (E[]) new Object[DEFAULT_CAPACITY];
        } else {
            items = Arrays.copyOf(items, items.length * 2);
        }
    }

    @Override
    public void add(int index, E item) {
        checkInsertIndex(index);

        if (size == items.length) {
            increaseCapacity();
        }

        System.arraycopy(items, index, items, index + 1, size - index);
        items[index] = item;
        size++;
        modificationsCount++;
    }

    @Override
    public int indexOf(Object object) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(object, items[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object object) {
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(object, items[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');

        for (int i = 0; i < size; i++) {
            stringBuilder.append(items[i]).append(", ");
        }

        stringBuilder.setLength(stringBuilder.length() - 2);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}
