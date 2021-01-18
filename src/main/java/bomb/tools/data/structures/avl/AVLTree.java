package bomb.tools.data.structures.avl;

import java.util.List;

/**
 *
 * @param <E>
 */
public class AVLTree<E extends Comparable<E>> {
    /**
     *
     * @param <E>
     */
    static class Node<E>{
        private final E element;
        private short balanceFactor;
        private final String value;
        private Node<E> left, right;

        Node(E element, String value){
            this.element = element;
            this.value = value;
            balanceFactor = 0;
            left = right = null;
        }

        boolean isLeaf(){
            return left == null && right == null;
        }
    }

    private Node<E> root;

    /**
     *
     */
    public AVLTree(){
        root = null;
    }

    /**
     *
     *
     * @param initArray
     * @param values
     * @throws UnbalancedEntriesException
     */
    public AVLTree(E[] initArray, String[] values) throws UnbalancedEntriesException {
        if (initArray.length == values.length) {
            for (int i = 0; i < initArray.length; i++)
                addNode(initArray[i], values[i]);
        } else throw new UnbalancedEntriesException(initArray.length, values.length);
    }

    /**
     *
     * @param list
     * @param values
     * @throws UnbalancedEntriesException
     */
    public AVLTree(List<E> list, String[] values) throws UnbalancedEntriesException {
        if (list.size() == values.length){
            @SuppressWarnings("Unchecked cast")
            E[] array = (E[]) list.toArray();
            for (int i = 0; i< values.length; i++)
                addNode(array[i], values[i]);
        } else throw new UnbalancedEntriesException(list.size(), values.length);
    }

    /**
     *
     * @param element
     * @param value
     */
    public void addNode(E element, String value){
        root = addNode(element, value, root);
        root = rescale(root);
    }

    /**
     *
     * @param element
     * @param value
     * @param stem
     * @return
     * @throws IllegalArgumentException
     */
    private Node<E> addNode(E element, String value, Node<E> stem)
            throws IllegalArgumentException {
        if (stem == null) return new Node<>(element, value);

        if (element.compareTo(stem.element) == 0)
            throw new IllegalArgumentException();
        else if (element.compareTo(stem.element) < 0)
            stem.left = addNode(element, value, stem.left);
        else
            stem.right = addNode(element, value, stem.right);
        return stem;
    }

    /**
     *
     * @param stem
     * @return
     */
    private Node<E> rescale(Node<E> stem){
        if (stem.isLeaf()) {
            stem.balanceFactor = 0;
            return stem;
        }

        stem.balanceFactor = tiltScale(stem);
        if (notNull(stem.left)) stem.left = rescale(stem.left);
        if (notNull(stem.right)) stem.right = rescale(stem.right);
        if (Math.abs(stem.balanceFactor) > 1) stem = algorithm(stem);

        return stem;
    }

    /**
     *
     * @param stem
     * @return
     */
    private short tiltScale(Node<E> stem){
        return (short) (height(stem.right) - height(stem.left));
    }

    /**
     *
     * @param stem
     * @return
     */
    private Node<E> algorithm(Node<E> stem){
        if (stem.balanceFactor > 1){
            stem = (stem.right.balanceFactor > 0)?
                    doubleRight(stem): //Stem BF is 2 and Stem.right is positive - RR Tree
                    rightLeft(stem); //Stem BF is 2 and Stem.right is negative - RL Tree
        } else if (stem.balanceFactor < -1){
            stem = (stem.left.balanceFactor < 0)?
                    doubleLeft(stem): //Stem BF is 2 and Stem.left is negative - LL Tree
                    leftRight(stem); //Stem BF is 2 and Stem.left is positive - LR Tree
        }
        return stem;
    }

    /**
     *
     * @param search
     * @return
     */
    public String dig(E search){
        return digDug(search, root);
    }

    /**
     *
     * @param search
     * @param stem
     * @return
     */
    private String digDug(E search, Node<E> stem){
        if (search.equals(stem.element)) return stem.value;
        if (stem.isLeaf()) return null;

        return search.compareTo(stem.element) < 0 ?
                digDug(search, stem.left) :
                digDug(search, stem.right);
    }

    /**
     *
     */
    public void traverse(){
        System.out.println(traversePreOrder(root));
    }

    /**
     *
     * @param root
     * @return
     */
    private String traversePreOrder(Node<E> root) {
        if (root == null) return "";

        StringBuilder sb = new StringBuilder();
        sb.append(root.value);

        String pointerRight = "└──";
        String pointerLeft = (notNull(root.right)) ? "├──" : "└──";

        traverseNodes(sb, "", pointerLeft, root.left, notNull(root.right));
        traverseNodes(sb, "", pointerRight, root.right, false);

        return sb.toString();
    }

    /**
     *
     * @param sb
     * @param padding
     * @param pointer
     * @param node
     * @param hasRightSibling
     */
    private void traverseNodes(StringBuilder sb, String padding, String pointer, Node<E> node,
                               boolean hasRightSibling) {
        if (notNull(node)) {
            sb.append("\n");
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.value);

            String paddingForBoth = padding + (hasRightSibling ? "│  " : "   ");
            String pointerRight = "└──";
            String pointerLeft = (notNull(node.right)) ? "├──" : "└──";

            traverseNodes(sb, paddingForBoth, pointerLeft, node.left, notNull(node.right));
            traverseNodes(sb, paddingForBoth, pointerRight, node.right, false);
        }
    }

    /**
     *
     * @return
     */
    public int getTotalHeight(){
        return height(root);
    }

    /**
     *
     * @param stem
     * @return
     */
    private int height(Node<E> stem) {
        if (!notNull(stem)) return 0;
        return 1 + Math.max(height(stem.left), height(stem.right));
    }

    /**
     *
     * @param root
     * @return
     */
    private Node<E> doubleLeft(Node<E> root){
        return rotateRight(root);
    }

    /**
     *
     * @param root
     * @return
     */
    private Node<E> doubleRight(Node<E> root){
        return rotateLeft(root);
    }

    /**
     *
     * @param root
     * @return
     */
    private Node<E> leftRight(Node<E> root){
        root.left = rotateLeft(root.left);
        return rotateRight(root);
    }

    /**
     *
     * @param root
     * @return
     */
    private Node<E> rightLeft(Node<E> root){
        root.right = rotateRight(root.right);
        return rotateLeft(root);
    }

    /**
     *
     *
     * @param root
     * @return
     */
    private Node<E> rotateRight(Node<E> root){
        Node<E> temp = root.left;
        root.left = temp.right;
        temp.right = root;
        return temp;
    }

    /**
     *
     * @param root
     * @return
     */
    private Node<E> rotateLeft(Node<E> root) {
        Node<E> temp = root.right;
        root.right = temp.left;
        temp.left = root;
        return temp;
    }

    /**
     *
     * @param stem
     * @return
     */
    private boolean notNull(Node<E> stem){
        return stem != null;
    }
}