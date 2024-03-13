package ru.aston.kurlukov.array_list_main;

import ru.aston.kurlukov.array_list.ArrayList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("element1");
        arrayList.add("element2");
        arrayList.add("element3");
        arrayList.add("element4");
        arrayList.add("element1");

        System.out.println("Содержимое списка: " + arrayList);

        System.out.println("Список пустой: " + arrayList.isEmpty());

        int index1 = 2;
        arrayList.add(index1, "element5");
        System.out.println("После вставки элемента по индексу " + index1 + ": " + arrayList);

        String element1 = "element1";
        System.out.println("Индекс элемента " + element1 + " в списке: " + arrayList.indexOf(element1));

        String element2 = "element2";
        System.out.println("Индекс последнего вхождения элемента " + element2 + " в списке: " + arrayList.lastIndexOf(element2));

        System.out.println("Элемент " + element2 + " удалён из списка: " + arrayList.remove(element2));
        System.out.println("Элемент " + arrayList.remove(2) + " удалён из списка");

        System.out.println("Список содержит элемент " + element1 + ": " + arrayList.contains(element1));

        int index2 = 2;
        arrayList.remove(index2);
        System.out.println("После удаления элемента по индексу " + index2 + ": " + arrayList);

        int index3 = 2;
        System.out.println("Элемент по индексу " + index3 + ": " + arrayList.get(index3));

        System.out.println("Размер списка: " + arrayList.size());

        int minCapacity = 10;
        arrayList.ensureCapacity(minCapacity);
        System.out.println("Содержимое списка после обеспечения ему минимальной вместимости " + minCapacity + ": " + arrayList);

        arrayList.trimToSize();
        System.out.println("Содержимое списка после уменьшения его вместимости до текущего размера: " + arrayList);

        int index4 = 1;
        System.out.println("Старое значение элемента списка по индексу " + index4 + ": " + arrayList.set(index4, "newElement"));
        System.out.println("После замены элемента по индексу " + index4 + ": " + arrayList);

        String[] array = new String[arrayList.size()];
        array = arrayList.toArray(array);
        System.out.println("Элементы массива: " + Arrays.toString(array));

        arrayList.clear();
        System.out.println("Содержимое списка после очистки: " + arrayList);

        Collection<String> collection1 = Arrays.asList("1", "2", "3", "4", "5");
        System.out.println("Все элементы из коллекции " + collection1 + " добавлены в список: " + arrayList.addAll(collection1));
        System.out.println("Содержимое списка: " + arrayList);

        int index5 = 0;
        System.out.println("Все элементы из коллекции " + collection1 + " добавлены в список по индексу " + index5 + ": " + arrayList.addAll(index5, collection1));
        System.out.println("Содержимое списка: " + arrayList);

        Collection<String> collection2 = Arrays.asList("a", "b", "c", "d", "e");
        arrayList.addAll(collection2);
        System.out.println("Содержимое списка: " + arrayList);

        System.out.println("Все элементы, кроме элементов из коллекции " + collection2 + ", были успешно удалены из списка: " + arrayList.retainAll(collection2));
        System.out.println("Содержимое списка: " + arrayList);

        System.out.println("Элементы из коллекции " + collection2 + " содержатся в списке: " + arrayList.containsAll(collection2));

        System.out.println("Элементы из коллекции " + collection2 + " были найдены в списке и успешно удалены: " + arrayList.removeAll(collection2));
        System.out.println("Содержимое списка: " + arrayList);

        ArrayList<String> newArrayList = new ArrayList<>();
        newArrayList.add("а");
        newArrayList.add("б");
        newArrayList.add("в");

        Iterator<String> iterator = newArrayList.iterator();

        while (iterator.hasNext()) {
            String element = iterator.next();
            System.out.println("Элемент списка: " + element);
        }

        iterator = newArrayList.iterator();

        while (iterator.hasNext()) {
            String element = iterator.next();

            if (element.equals("б")) {
                iterator.remove();
            }
        }

        System.out.println("Содержимое списка " + newArrayList);
        iterator.remove();
    }
}
