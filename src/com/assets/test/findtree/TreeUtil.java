package com.assets.test.findtree;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 遍历树形结构数据
 * @ClassName:  TreeUtil   
 * @Description:TODO   
 * @author: sunshine  
 * @date:   2014年8月14日 上午10:13:20
 */
public class TreeUtil {
    /**
     * 根据父节点的ID获取所有子节点
     * @param list 分类表
     * @param typeId 传入的父节点ID
     * @return String
     */
    public static List<ITree> getAllChildNodes(List<ITree> list, Object nodeId) {
        if(list == null || nodeId == null) return null;
        List<ITree> returnList = new ArrayList<ITree>();
        Iterator<ITree> iterator = list.iterator(); 
        while(iterator.hasNext()) {
            ITree node = iterator.next();
            if (nodeId.equals(node.getId())) {
                recursionFn(list, node,returnList);
            }
        }
        return returnList;
    }
    /**
     * 递归遍历
     * @Title: recursionFn 
     * @author: sunshine  
     * @param: @param list
     * @param: @param node      
     * @return: void      
     */
    private static void  recursionFn(List<ITree> list, ITree node,List<ITree> returnList) {
        List<ITree> childList = getChildList(list, node);
        if (hasChild(list, node)) {
            returnList.add(node);
            Iterator<ITree> it = childList.iterator();
            while (it.hasNext()) {
            	ITree n = it.next();
                recursionFn(list, n,returnList);
            }
        } else {
            returnList.add(node);
        }
    }
    /**
     * 得到子节点列表
     * @Title: getChildList 
     * @author: sunshine  
     * @param: @param list
     * @param: @param node
     * @param: @return      
     * @return: List<ITree>      
     */
    public static List<ITree> getChildList(List<ITree> list, ITree node) {
        List<ITree> nodeList = new ArrayList<ITree>();
        Iterator<ITree> it = list.iterator();
        while (it.hasNext()) {
            ITree n = it.next();
            if (n.getParentId() == node.getId()) {
                nodeList.add(n);
            }
        }
        return nodeList;
    }
    /**
     * 判断是否有子节点
     * @Title: hasChild 
     * @author: sunshine  
     * @param: @param list
     * @param: @param node
     * @param: @return      
     * @return: boolean      
     */
    private static boolean hasChild(List<ITree> list, ITree node) {
        return getChildList(list, node).size() > 0 ? true : false;
    }
    public static void main(String[] args) {
    	List<ITree> nodeList = new ArrayList<ITree>();
        ITree node0 = new Node("0", "蔬菜", "");
        ITree node1 = new Node("1", "蔬菜", "0");
        ITree node2 = new Node("2", "水产", "0");
        ITree node3 = new Node("3", "畜牧", "0");
        ITree node4 = new Node("4", "瓜类", "1");
        ITree node5 = new Node("5", "叶类", "1");
        ITree node6 = new Node("6", "丝瓜", "4");
        ITree node7 = new Node("7", "黄瓜", "4");
        ITree node8 = new Node("8", "白菜", "1");
        ITree node9 = new Node("9", "虾", "2");
        ITree node10 = new Node("10", "鱼", "2");
        ITree node11 = new Node("11", "牛", "3");
        nodeList.add(node0);
        nodeList.add(node1);
        nodeList.add(node2);
        nodeList.add(node3);
        nodeList.add(node4);
        nodeList.add(node5);
        nodeList.add(node6);
        nodeList.add(node7);
        nodeList.add(node8);
        nodeList.add(node9);
        nodeList.add(node10);
        nodeList.add(node11);
        System.out.println(TreeUtil.getAllChildNodes(nodeList,"4").size());
    }
}