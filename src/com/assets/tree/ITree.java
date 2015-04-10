package com.assets.tree;

import java.util.List;

/**
 * 树形结构数据规范
 * @ClassName:  ITree   
 * @Description:TODO   
 * @author: sunshine  
 * @date:   2014年8月14日 上午10:15:40
 */
public interface ITree {
   Object getId();
   Object getParentId();
   List<ITree> getChildren();
   void setChildren(List<ITree> children);
}
