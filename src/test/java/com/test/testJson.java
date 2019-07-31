package com.test;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.DemoApplication;
import com.example.demo.entity.TreeNode;
import com.example.demo.mapper.util.UtilDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DemoApplication.class)
@SpringBootTest
public class testJson {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UtilDao menuDao;
    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void testMenu() {

        Object o;
        if (redisTemplate.opsForValue().get("menuList") == null) {
            System.out.println("--------------------------------------------------------");
        List<LinkedHashMap> menuList = menuDao.getMenuList();
        //用于判断是否已存在当前节点
        List<Object> strList = new ArrayList<Object>();
        List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
        //遍历menuList
        for (Map<String, Object> m : menuList) {
            //遍历map，并创建treeNode
            for (String k : m.keySet()) {
                System.out.println(k + " : " + m.get(k));
                if (!strList.contains(m.get(k))) {
                    if (k.equals("FactoryName")) {
                        //创建节点
                        TreeNode treeNode = new TreeNode();
                        treeNode.setIcon("el-icon-menu");
                        treeNode.setPath("/" + m.get(k));
                        treeNode.setName(m.get(k).toString());
                        //把节点拼到节点树上
                        treeNodeList.add(treeNode);
                        //放到已存在节点表里
                        strList.add(m.get(k));
                    } else if (k.equals("EStationName")) {
                        //创建节点
                        TreeNode treeNode = new TreeNode();
                        treeNode.setIcon("el-icon-menu");
                        treeNode.setPath("/" + m.get(k));
                        treeNode.setName(m.get(k).toString());
                        //把节点拼到节点树上
                        for (TreeNode t : treeNodeList) {
                            if (t.getName().equals(m.get("FactoryName"))) {
                                //   if(t.getChildren().size()<=0){
                                if (StringUtils.isEmpty(t.getChildren())) {
                                    List<TreeNode> list = new ArrayList<TreeNode>();
                                    list.add(treeNode);
                                    t.setChildren(list);
                                } else {
                                    t.getChildren().add(treeNode);
                                }
                            }
                        }
                        //放到已存在节点表里
                        strList.add(m.get(k));
                    } else if (k.equals("EMeterName")) {
                        //创建节点
                        TreeNode treeNode = new TreeNode();
                        treeNode.setIcon("el-icon-menu");
                        treeNode.setPath("/" + m.get(k));
                        treeNode.setName(m.get(k).toString());
                        //把节点拼到节点树上

                        for (TreeNode t : treeNodeList) {
                            if (t.getName().equals(m.get("FactoryName"))) {
                                for (TreeNode t1 : t.getChildren()) {
                                    if (t1.getName().equals(m.get("EStationName"))) {
                                        // if(t1.getChildren().size()<=0){
                                        if (StringUtils.isEmpty(t1.getChildren())) {
                                            List<TreeNode> list = new ArrayList<TreeNode>();
                                            list.add(treeNode);
                                            t1.setChildren(list);
                                        } else {
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
        Object jsonObject = JSONObject.toJSON(treeNodeList);
        redisTemplate.opsForValue().set("menuList",jsonObject);
        o = jsonObject;
    }else{
        o = redisTemplate.opsForValue().get("menuList");
    }
        System.out.println(o);
    }


    public void testMapJson(){
        Map<String,Object> map = new HashMap<String, Object>();

        String jsonObject = JSONObject.toJSONString(map);
        System.out.println(jsonObject);
        //JSONObject jsonObject = JSONObject
    }



    public void testCon(){
        TreeNode treeNode = new TreeNode();
        treeNode.setPath("/1");
        treeNode.setIcon("el-icon-menu");
        treeNode.setName("导航1");

        TreeNode treeNode1 = new TreeNode();
        treeNode1.setPath("/1");
        treeNode1.setIcon("el-icon-menu");
        treeNode1.setName("导航1");


        List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
        treeNodeList.add(treeNode);
        for (TreeNode t :treeNodeList) {
            System.out.println();
        }

    }
}
