package com.assets.tree;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.voovan.tools.json.JSON;

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
     * @Title: getAllChildNodes
     * @param list 分类表
     * @param typeId 传入的父节点ID
     * @param: @return      
     * @return: List<ITree>
     * @author: sunshine  
     * @throws
     */
    public static List<ITree> getAllChildNodes(List<ITree> list, ITree parentNode) {
        if(list == null || parentNode == null) return null;
        List<ITree> returnList = new ArrayList<ITree>();
        Iterator<ITree> iterator = list.iterator(); 
        while(iterator.hasNext()) {
            ITree node = iterator.next();
            if (node.getParentId().equals(parentNode.getId())) {
                recursion(list, node,returnList);
            }else if(node.getId().equals(parentNode.getId())) {
            	returnList.add(node);
            }
        }
        return returnList;
    }
    /**
     * 转换成树形结构
     * @Title: getTreeNodes
     * @Description: TODO  
     * @param: @param menuList
     * @param: @return      
     * @return: List<ITree>
     * @author: sunshine  
     * @throws
     */
    public static List<ITree> listToTree(List<ITree> menuList) {
    	List<ITree> nodeList = new ArrayList<ITree>();
    	for(ITree node1 : menuList){  
    	    boolean mark = false;  
    	    for(ITree node2 : menuList){  
    	        if(node1.getParentId()!=null && node1.getParentId().equals(node2.getId())){  
    	            mark = true;  
    	            if(node2.getChildren() == null){
    	            	node2.setChildren(new ArrayList<ITree>());  
    	            }
    	            node2.getChildren().add(node1);   
    	            break;  
    	        }  
    	    }  
    	    if(!mark){  
    	        nodeList.add(node1);
    	    }  
    	} 
    	return nodeList;
    }
    /**
     * 递归遍历
     * @Title: recursion 
     * @author: sunshine  
     * @param: @param list
     * @param: @param node      
     * @return: void      
     */
    private static void  recursion(List<ITree> list, ITree node,List<ITree> returnList) {
    	returnList.add(node);
        List<ITree> childList = getDirectChildList(list, node);
        if (hasChild(list, node)) {
            Iterator<ITree> it = childList.iterator();
            while (it.hasNext()) {
            	ITree n = it.next();
                recursion(list, n,returnList);
            }
        }
        
    }
    /**
     * 得到直系子节点列表
     * @Title: getChildList 
     * @author: sunshine  
     * @param: @param list
     * @param: @param node
     * @param: @return      
     * @return: List<ITree>      
     */
    public static List<ITree> getDirectChildList(List<ITree> list, ITree parentNode) {
    	if(list == null || parentNode == null) return null;
        List<ITree> nodeList = new ArrayList<ITree>();
        Iterator<ITree> it = list.iterator();
        while (it.hasNext()) {
            ITree n = it.next();
            if (n.getParentId() == parentNode.getId()) {
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
    	boolean rt = false;
    	if(list == null || node == null) return rt;
        Iterator<ITree> it = list.iterator();
        while (!rt && it.hasNext()) {
            ITree n = it.next();
            if (n.getParentId() == node.getId()) {
              rt = true;
            }
        }
        return rt;
    }
    
    public static void main(String[] args) {
    	List<ITree> nodeList = new ArrayList<ITree>();
        ITree node0 = new Node("0", "蔬菜", "-1");
        ITree node1 = new Node("1", "瓜果", "0");
        ITree node2 = new Node("2", "水产", "0");
        ITree node3 = new Node("3", "畜牧", "0");
        ITree node4 = new Node("4", "瓜类", "1");
        ITree node5 = new Node("5", "叶类", "1");
        ITree node6 = new Node("6", "丝瓜", "4");
        ITree node7 = new Node("7", "黄瓜", "4");
        ITree node8 = new Node("8", "白菜", "1");
        ITree node9 = new Node("9", "虾", "2");
        ITree node10 = new Node("10", "鱼", "2");
        ITree node11 = new Node("11", "牛", "7");
        /*
        nodeList.add(node0);
        */
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
        List<ITree> nodeList2 =null;
        /*
        nodeList2 =  TreeUtil.getAllChildNodes(nodeList,node0);
        for(ITree tree:nodeList) {
        	System.out.println(tree);
        }
        System.out.println("===============================================");
        for(ITree tree:nodeList2) {
        	System.out.println(tree.toString());
        }
        */
        /*
        System.out.println(TreeUtil.getDirectChildList(nodeList, node0).size());
        */
        
        /***/
        nodeList2 =  TreeUtil.listToTree(nodeList);
        for(ITree tree:nodeList) {
        	System.out.println(tree);
        }
        System.out.println("===============================================");
        for(ITree tree:nodeList2) {
        	System.out.println(JSON.toJSON(tree));
        }
        
    }
}