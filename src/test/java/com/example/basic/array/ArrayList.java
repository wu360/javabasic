package com.example.basic.array;


import java.util.*;

class ArrayListPro<E> extends ArrayList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable {

    //实现Serializable接口，生成的序列版本号：
    private static final long serialVersionUID = 8683452581122892189L;

    //ArrayList初始容量大小：在无参构造中不使用了
    private static final int DEFAULT_CAPACITY = 10;

    //空数组对象：初始化中默认赋值给elementData
    private static final Object[] EMPTY_ELEMENTDATA = {};

    //ArrayList中实际存储元素的数组：
    private transient Object[] elementData;

    //集合实际存储元素长度：
    private int size;

    //ArrayList有参构造：容量大小
    public ArrayListPro(int initialCapacity) {
        //即父类构造：protected AbstractList() {}空方法
        super();
        //如果传递的初始容量小于0 ，抛出异常
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        //初始化数据：创建Object数组
        this.elementData = new Object[initialCapacity];
    }

    //ArrayList无参构造：
    public ArrayListPro() {
        //即父类构造：protected AbstractList() {}空方法
        super();
        //初始化数组：空数组，容量为0
        this.elementData = EMPTY_ELEMENTDATA;
    }

    //ArrayList有参构造：Java集合
    public ArrayListPro(Collection<? extends E> c) {
        //将集合转换为数组：
        elementData = c.toArray();
        //设置数组的长度：
        size = elementData.length;
        if (elementData.getClass() != Object[].class)
            elementData = Arrays.copyOf(elementData, size, Object[].class);
    }


    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

//    private void grow(int minCapacity) {
//        // overflow-conscious code
//        int oldCapacity = elementData.length;
//        int newCapacity = oldCapacity + (oldCapacity >> 1);
//        if (newCapacity - minCapacity < 0)
//            newCapacity = minCapacity;
//        if (newCapacity - MAX_ARRAY_SIZE > 0)
//            newCapacity = hugeCapacity(minCapacity);
//        // minCapacity is usually close to size, so this is a win:
//        elementData = Arrays.copyOf(elementData, newCapacity);
//    }


    private static int calculateCapacity(Object[] elementData, int minCapacity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        return minCapacity;
    }

//    private static int hugeCapacity(int minCapacity) {
//        if (minCapacity < 0) // overflow
//            throw new OutOfMemoryError();
//        return (minCapacity > MAX_ARRAY_SIZE) ?
//                Integer.MAX_VALUE :
//                MAX_ARRAY_SIZE;
//    }
//    private void ensureExplicitCapacity(int minCapacity) {
//        modCount++;
//
//        // overflow-conscious code
//        if (minCapacity - elementData.length > 0)
//            grow(minCapacity);
//    }

//    private void ensureCapacityInternal(int minCapacity) {
//        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
//    }


    //https://www.jianshu.com/p/63b01b6379fb
    //添加元素e
    public boolean add(E e) {
        ensureCapacityInternal(size + 1);
        //将对应角标下的元素赋值为e：
        elementData[size++] = e;
        return true;
    }
    //得到最小扩容量
    private void ensureCapacityInternal(int minCapacity) {
        //如果此时ArrayList是空数组,则将最小扩容大小设置为10：
        if (elementData == EMPTY_ELEMENTDATA) {
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        //判断是否需要扩容：
        ensureExplicitCapacity(minCapacity);
    }
    //判断是否需要扩容
    private void ensureExplicitCapacity(int minCapacity) {
        //操作数+1
        modCount++;
        //判断最小扩容容量-数组大小是否大于0：
        if (minCapacity - elementData.length > 0)
            //扩容：
            grow(minCapacity);
    }
    //ArrayList动态扩容的核心方法:
    private void grow(int minCapacity) {
        //获取现有数组大小：
        int oldCapacity = elementData.length;
        //位运算，得到新的数组容量大小，为原有的1.5倍：
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        //如果新扩容的大小依旧小于传入的容量值，那么将传入的值设为新容器大小：
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;

        //如果新容器大小，大于ArrayList最大长度：
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            //计算出最大容量值：
            newCapacity = hugeCapacity(minCapacity);
        //数组复制：
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
    //计算ArrayList最大容量：
    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0)
            throw new OutOfMemoryError();
        //如果新的容量大于MAX_ARRAY_SIZE。将会调用hugeCapacity将int的最大值赋给newCapacity:
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }
    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

    private void rangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    @SuppressWarnings("unchecked")
    E elementData(int index) {
        return (E) elementData[index];
    }

    //在ArrayList的移除index位置的元素
    public E remove(int index) {
        //检查角标是否合法：不合法抛异常
        rangeCheck(index);
        //操作数+1：
        modCount++;
        //获取当前角标的value:
        E oldValue = elementData(index);
        //获取需要删除元素 到最后一个元素的长度，也就是删除元素后，后续元素移动的个数；
        int numMoved = size - index - 1;
        //如果移动元素个数大于0 ，也就是说删除的不是最后一个元素：
        if (numMoved > 0)
            // 将elementData数组index+1位置开始拷贝到elementData从index开始的空间
            System.arraycopy(elementData, index+1, elementData, index, numMoved);
        //size减1，并将最后一个元素置为null
        elementData[--size] = null;
        //返回被删除的元素：
        return oldValue;
    }

    //在ArrayList的移除对象为O的元素，不返回被删除的元素：
    public boolean remove(Object o) {
        //如果o==null，则遍历集合，判断哪个元素为null：
        if (o == null) {
            for (int index = 0; index < size; index++)
                if (elementData[index] == null) {
                    //快速删除，和前面的remove（index）一样的逻辑
                    fastRemove(index);
                    return true;
                }
        } else {
            //同理：
            for (int index = 0; index < size; index++)
                if (o.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
        }
        return false;
    }

    //快速删除：
    private void fastRemove(int index) {
        //操作数+1
        modCount++;
        //获取需要删除元素 到最后一个元素的长度，也就是删除元素后，后续元素移动的个数；
        int numMoved = size - index - 1;
        //如果移动元素个数大于0 ，也就是说删除的不是最后一个元素：
        if (numMoved > 0)
            // 将elementData数组index+1位置开始拷贝到elementData从index开始的空间
            System.arraycopy(elementData, index+1, elementData, index, numMoved);
        //size减1，并将最后一个元素置为null
        elementData[--size] = null;
    }
}
