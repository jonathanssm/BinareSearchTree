package com.binaretree;

public class No<K extends Comparable<K>, V> {

	private K key;
	private V value;
	
	private No<K,V> father;
	private No<K,V> rightNode;
	private No<K,V> leftNode;

	public No() {
	}

	public No(K key) {

		super();

		this.key = key;

	}
	
	public No(K key, V value, No<K,V> father) {

		super();

		this.key = key;
		this.value = value;
		this.father = father;
		rightNode = leftNode = null;

	}

	public K getKey() {

		return key;

	}

	public void setKey(K key) {

		this.key = key;

	}

	public No<K,V> getLeftNode() {

		return leftNode;

	}

	public void setLeftNode(No<K,V> leftNode) {

		this.leftNode = leftNode;

	}

	public No<K,V> getRigthNode() {

		return rightNode;

	}

	public void setRightNode(No<K,V> rightNode) {

		this.rightNode = rightNode;

	}
	

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	} 

	public No<K, V> getFather() {
		return father;
	}

	public void setFather(No<K, V> father) {
		this.father = father;
	}

	@Override

	public String toString() {

		return "Node [value=" + key + "]";

	}

}
