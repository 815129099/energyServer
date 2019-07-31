package com.test;

import com.example.demo.DemoApplication;
import com.example.demo.mapper.user.UserDao;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DemoApplication.class)
@SpringBootTest
public class plus {
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserDao userDao;
/*
    @Test
    //@Transactional
    public void testCURD(){
        User user1 = userDao.selectById(1);
        LOGGER.info(user1.toString());
        LOGGER.info("-------------测试select--------------");
    }

    @Test
    public void testSelect(){
        //selectById
        LOGGER.info(userDao.selectById(1).toString());
        LOGGER.info("------------------------selectById-------------------------");

        //selectOne
        User u = new User();
        u.setPhone("18650118603");
        User user1 = userDao.selectOne(u);
        LOGGER.info(user1.toString());
        LOGGER.info("------------------------selectOne-------------------------");

        //selectByMap
        Map<String,Object> map = new HashMap<>();
        map.put("geName","张三");
        List<User> userList = userDao.selectByMap(map);
        for(User user:userList){
            LOGGER.info(user.toString());
        }
        LOGGER.info("------------------------selectByMap-------------------------");

        List<Integer> list = new ArrayList();
        list.add(1);
        list.add(2);
        List<User> uList = userDao.selectBatchIds(list);
        for(User user:uList){
            LOGGER.info(user.toString());
        }
        LOGGER.info("------------------------selectBatchIds-------------------------");

        EntityWrapper<User> uWrapper = new EntityWrapper<User>();
        uWrapper.like("phone","1865011860");
        LOGGER.info(userDao.selectCount(uWrapper).toString());
        LOGGER.info("------------------------selectCount-------------------------");

        EntityWrapper<User> userWrapper = new EntityWrapper<User>();
        User uL = new User();
        uL.setGeName("张三");
        uL.setPhone("18650118602");
        userWrapper.setEntity(uL);
        List<User> userList1 = userDao.selectList(userWrapper);
        for(User user:userList1){
            LOGGER.info(user.toString());
        }
        LOGGER.info("------------------------selectList-------------------------");


    }

    @Test
    public void testUpdate(){
        User user = new User();
        user.setCode("123123");
        userDao.update(user,new EntityWrapper<User>().eq("phone","18650118602"));
    }

*/
}
