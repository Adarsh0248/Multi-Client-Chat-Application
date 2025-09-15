import java.util.*;
public class Tree{
    class TreeNode{
        int data;
        TreeNode left;
        TreeNode right;
        TreeNode(int data){
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }
    private TreeNode root;
    public Tree(){
        this.root = null;
    }
    private void populate(int[] arr){
        int i = 1;
        int n = arr.length;
        Queue<TreeNode> q = new LinkedList<>();
        root = new TreeNode(arr[0]);
        q.offer(root);
        while(!q.isEmpty()){
            TreeNode node = q.poll();
            if(i<n && node.left==null){
                node.left = new TreeNode(arr[i++]);
                q.offer(node.left);
            }
            if(i<n && node.right==null){
                node.right = new TreeNode(arr[i++]);
                q.offer(node.right);
            }

        }


    }
    public void inOrder(){
        inOrderHelper(root);
    }
    public void inOrderHelper(TreeNode node){
        if(node == null) return;
        inOrderHelper(node.left);
        System.out.print(node.data+" ");
        inOrderHelper(node.right);
    }



    public static void main(String [] args){
        Tree tree = new Tree();
        tree.populate(new int[]{1,2,3,4,5});
        tree.inOrder();

    }
}