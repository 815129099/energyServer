package com.example.demo.util.JSON;

import com.example.demo.mapper.util.UtilDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JsonUtil {

    @Autowired
    private UtilDao menuDao;
/*
    public String getMenuJson(){
        List<Map> menuList = menuDao.getMenuList();
        //用于判断是否已存在当前节点
        List<Object> strList = new ArrayList<Object>();
        List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
        //遍历menuList
        for (Map<String, Object> m : menuList){
            //遍历map，并创建treeNode
            for (String k : m.keySet()){
                // System.out.println(k + " : " + m.get("FactoryName"));
                if(!strList.contains(m.get(k))){
                    if(k.equals("FactoryName")){
                        //创建节点
                        TreeNode treeNode = new TreeNode();
                        treeNode.setIcon("el-icon-menu");
                        treeNode.setPath("/"+m.get(k));
                        treeNode.setName(m.get(k).toString());
                        //把节点拼到节点树上
                        treeNodeList.add(treeNode);
                        //放到已存在节点表里
                        strList.add(m.get(k));
                    }else if(k.equals("EStationName")){
                        //创建节点
                        TreeNode treeNode = new TreeNode();
                        treeNode.setIcon("el-icon-menu");
                        treeNode.setPath("/"+m.get(k));
                        treeNode.setName(m.get(k).toString());
                        //把节点拼到节点树上
                        for (TreeNode t: treeNodeList) {
                            if(t.getName().equals(m.get("FactoryName"))){
                                //   if(t.getChildren().size()<=0){
                                if(StringUtils.isEmpty(t.getChildren())){
                                    List<TreeNode> list = new ArrayList<TreeNode>();
                                    list.add(treeNode);
                                    t.setChildren(list);
                                }else {
                                    t.getChildren().add(treeNode);
                                }
                            }
                        }
                        //放到已存在节点表里
                        strList.add(m.get(k));
                    }else if(k.equals("EMeterName")){
                        //创建节点
                        TreeNode treeNode = new TreeNode();
                        treeNode.setIcon("el-icon-menu");
                        treeNode.setPath("/"+m.get(k));
                        treeNode.setName(m.get(k).toString());
                        //把节点拼到节点树上
                        for (TreeNode t: treeNodeList) {
                            if(t.getName().equals(m.get("FactoryName"))){
                                for(TreeNode t1:t.getChildren()){
                                    if(t1.getName().equals(m.get("EStationName"))){
                                        // if(t1.getChildren().size()<=0){
                                        if(StringUtils.isEmpty(t1.getChildren())){
                                            List<TreeNode> list = new ArrayList<TreeNode>();
                                            list.add(treeNode);
                                            t1.setChildren(list);
                                        }else {
                                            t1.getChildren().add(treeNode);
                                        }
                                    }
                                }
                            }
                        }
                        //放到已存在节点表里
                        strList.add(m.get(k));
                    }
                }
            }

        }
        String jsonObject = JSONObject.toJSONString(treeNodeList);
        System.out.println(jsonObject);
        return jsonObject;
    }*/
}
