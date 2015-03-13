package com.assets.test.findtree;
public class Node implements ITree{
    private String id;
 
    private String nodeName;
 
    private String parentId;
 
    Node(String id, String nodeName, String parentId) {
        this.id = id;
        this.nodeName = nodeName;
        this.parentId = parentId;
    }
    @Override
    public String getId() {
        return id;
    }
 
    public void setId(String id) {
        this.id = id;
    }
    @Override
    public String getParentId() {
        return parentId;
    }
 
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
 
    public String getNodeName() {
        return nodeName;
    }
 
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}