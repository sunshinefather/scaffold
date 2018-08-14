package com.assets.tree;
public class Node extends DefaultTree{
	
    private String rid;
    private String name;
    private String pid;
	private Integer sort;
	
	public Node(String id, String nodeName, String parentId) {
		super(id, nodeName, parentId);
		this.rid=id;
		this.name=nodeName;
		this.pid=parentId;
	}
	
	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@Override
	public String toString() {
		return "{\"rid\":\"" + getRid() + "\",\"name\":\"" + getName() + "\",\"pid\":\"" + getPid() + "\",\"sort\":\"" + getSort() +"\",\"children\":"+getChildren()+"}";
	}
}