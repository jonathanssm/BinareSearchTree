package com.binaretree;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Tree<K extends Comparable<K>, V> implements Iterable<V> {

	private Queue<No<K, V>> queue = new LinkedList<No<K, V>>();

	private int length;
	private No<K, V> root;

	public Tree() {
		length = 0;
		root = null;
	}

	private class TreeIterator implements Iterator<V> {

		private Queue<No<K, V>> queue;

		public TreeIterator() {
			queue = new LinkedList<>();
			queue.add(root);
		}

		@Override
		public boolean hasNext() {
			return !queue.isEmpty();
		}

		@Override
		public V next() {

			No<K, V> node = queue.poll();

			if (node.getLeftNode() != null) {
				queue.add(node.getLeftNode());
			}
			if (node.getRigthNode() != null) {
				queue.add(node.getRigthNode());
			}

			return node.getValue();
		}

	}

	@Override
	public Iterator<V> iterator() {
		return new TreeIterator();
	}

	public boolean isLeaf(No<K, V> node) {
		return node.getLeftNode() == null && node.getRigthNode() == null;
	}

	public boolean isRoot(No<K, V> node) {
		return node.getFather() == null;
	}

	public boolean isEmpty() {
		if (root == null) {
			return true;
		}
		return false;
	}

	public int getHeight() {
		return getHeight(this.root, 0);
	}

	private int getHeight(No<K, V> root, int height) {
		if (root == null) {
			return height;
		}
		int lftHgt = getHeight(root.getLeftNode(), height + 1);
		int rgtHgt = getHeight(root.getRigthNode(), height + 1);
		return Math.max(lftHgt, rgtHgt);
	}

	public int getQtdNode() {
		return getQtdNode(root, 0);
	}

	private int getQtdNode(No<K, V> root, int qtd) {
		if (root == null) {
			return qtd;
		}
		int leftQtdNode = getQtdNode(root.getLeftNode(), qtd + 1);
		int rightQtdNode = getQtdNode(root.getRigthNode(), qtd + 1);
		return leftQtdNode + rightQtdNode;
	}

	public void preOrder() {
		if (this.root == null)
			System.out.println("Empity Tree");
		else
			preOrder(this.root);
	}

	private void preOrder(No<K, V> node) {
		System.out.println("Nó: " + node.getKey());
		if (node.getLeftNode() != null) {
			postOrder(node.getLeftNode());
		}
		if (node.getRigthNode() != null) {
			postOrder(node.getRigthNode());
		}
	}

	public void postOrder() {
		if (this.root == null)
			System.out.println("Empity Tree");
		else
			postOrder(this.root);
	}

	private void postOrder(No<K, V> node) {
		if (node.getLeftNode() != null) {
			postOrder(node.getLeftNode());
		}
		if (node.getRigthNode() != null) {
			postOrder(node.getRigthNode());
		}
		System.out.println("Nó: " + node.getKey());
	}

	public void printTreeInWidth() {

		if (root == null) {
			System.out.println("Empity Tree");
		} else {
			queue.clear();
			queue.add(root);
			queue.add(null);

			while (!queue.isEmpty()) {

				No<K, V> node = queue.remove();

				if (node == null) {
					if (queue.size() == 0) {
						return;
					}
					queue.add(null);
					System.out.println();
					continue;
				}

				System.out.print(node.getValue() + "|");

				if (node.getLeftNode() != null) {
					queue.add(node.getLeftNode());
				}
				if (node.getRigthNode() != null) {
					queue.add(node.getRigthNode());
				}
			}
		}

	}

	public List<V> symmetrical() {
		List<V> list = new ArrayList<>();
		symmetrical(root, list);
		return list;
	}

	private void symmetrical(No<K, V> node, List<V> list) {
		if (node != null) {
			symmetrical(node.getLeftNode(), list);
			list.add(node.getValue());
			symmetrical(node.getRigthNode(), list);
		}
	}

	public boolean insert(K key, V value) {

		if (key == null) {
			throw new IllegalArgumentException("The key can not be null");
		}

		No<K, V> node = root;

		if (root == null) {
			root = new No<K, V>(key, value, null);
		} else {
			while (!node.getKey().equals(key)) {

				if (key.compareTo(node.getKey()) > 0 && node.getRigthNode() == null) {
					node.setRightNode(new No<K, V>(key, value, node));
					break;
				} else if (key.compareTo(node.getKey()) < 0 && node.getLeftNode() == null) {
					node.setLeftNode(new No<K, V>(key, value, node));
					break;
				}
				node = key.compareTo(node.getKey()) > 0 ? node.getRigthNode() : node.getLeftNode();
			}

			if (node.getKey().equals(key)) {
				return false;
			}
		}

		length++;
		return true;

	}

	public boolean remove(K key) {

		No<K, V> node = getNode(key);

		if (node == null) {
			return false;
		}

		if (length == 1) {
			root = null;
		} // case 1
		else if (node.getLeftNode() == null && node.getRigthNode() == null) {
			if (node.equals(node.getFather().getLeftNode())) {
				node.getFather().setLeftNode(null);
			} else {
				node.getFather().setRightNode(null);
			}

		} // case 2
		else if (node.getLeftNode() != null && node.getRigthNode() == null) {
			if (node.getFather() == null) {

				root = node.getLeftNode();
				root.setFather(null);

				// return true;
			} else if (node.equals(node.getFather().getLeftNode())) {
				node.getFather().setLeftNode(node.getLeftNode());
			} else {
				node.getFather().setRightNode(node.getLeftNode());
			}
		} else if (node.getLeftNode() == null && node.getRigthNode() != null) {
			if (node.getFather() == null) {

				root = node.getRigthNode();
				root.setFather(null);

				// return true;
			} else if (node.equals(node.getFather().getLeftNode())) {
				node.getFather().setLeftNode(node.getRigthNode());
			} else {
				node.getFather().setRightNode(node.getRigthNode());
			}
		} // case 3
		else {
			No<K, V> substitute = node;

			while (substitute.getLeftNode() != null) {
				substitute = substitute.getLeftNode();
			}

			node.setKey(substitute.getKey());
			node.setValue(substitute.getValue());

			substitute.getFather().setLeftNode(null);
		}

		length--;
		return true;
	}

	public V search(K el) {
		No<K, V> node = search(root, el);
		return node != null ? node.getValue() : null;
	}

	protected No<K, V> search(No<K, V> p, K el) {
		while (p != null) {

			if (el.compareTo(p.getKey()) == 0)
				return p;

			else if (el.compareTo(p.getKey()) < 0)
				return this.search(p.getLeftNode(), el);

			else
				return this.search(p.getRigthNode(), el);
		}

		return null;
	}

	private No<K, V> getNode(K key) {

		if (key == null) {
			throw new IllegalArgumentException("The key can not be null");
		}

		No<K, V> node = root;

		while (node != null && !node.getKey().equals(key)) {
			node = key.compareTo(node.getKey()) > 0 ? node.getRigthNode() : node.getLeftNode();
		}
		return node;
	}

	public boolean contais(V value) {

		if (length == 0) {
			throw new EmptyStackException();
		}

		Queue<No<K, V>> queue = new LinkedList<>();
		queue.add(root);
		No<K, V> node = root;

		while (!queue.isEmpty() && !node.getValue().equals(value)) {

			node = queue.poll();

			if (node.getLeftNode() != null) {
				queue.add(node.getLeftNode());
			}
			if (node.getRigthNode() != null) {
				queue.add(node.getRigthNode());
			}
		}

		return node.getValue().equals(value);
	}

}
