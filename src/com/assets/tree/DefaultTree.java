package com.assets.tree;

import java.util.List;

public abstract class DefaultTree implements ITree {
    private String id;
    private String nodeName;
    private String parentId;
    private List<ITree> children;
    
    public DefaultTree() {
    	
    }
	public DefaultTree(String id, String nodeName, String parentId) {
		super();
		this.id = id;
		this.nodeName = nodeName;
		this.parentId = parentId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getParentId() {
		return parentId;
	}
	
    public String getNodeName() {
        return nodeName;
    }
 
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
    
	public List<ITree> getChildren() {
		return children;
	}
	
	public void setChildren(List<ITree> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "{\"id\":\"" + id + "\",\"nodeName\":\"" + nodeName + "\",\"parentId\":\"" + parentId + "\",\"children\":"+children+"}";
	}
	
}
