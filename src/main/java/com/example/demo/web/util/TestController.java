package com.example.demo.web.util;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("/api/util")
public class TestController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public ModelAndView index(){
        return  new ModelAndView("/index");
    }

    @RequestMapping("/introduce")
    public ModelAndView introduce(){
        return  new ModelAndView("/introduce");
    }

    @RequestMapping({"/exportUser.do"})
    public void exportAllege(HttpServletResponse response) throws Exception {
        /*
       // String u = new String(userState.getBytes("ISO-8859-1"), "UTF-8");
     //   System.out.println(begin + "," + end + "," + u);
     //   List<Record> list = this.userService.getRecordByTime(begin, end, u);
        List<User> list = this.userService.selectList(new EntityWrapper<User>().eq("id",1));
        String[] heads = new String[]{"序号", "id", "工号", "书名", "状态"};
        List<String[]> data = new LinkedList();
        for(int i = 0; i < list.size(); ++i) {
            User entity = (User) list.get(i);
            String[] temp = new String[]{String.valueOf(i + 1), entity.getId().toString(), entity.getGeName(), entity.getGeNumber(), entity.getEmail()};
            data.add(temp);
        }
        ExcelParam param = (new ExcelParam.Builder("借阅记录表")).headers(heads).data(data).build();
        ExcelUtil.export(param, response);*/
    }
/*
    @RequestMapping(value = "/api/exportTest.do", method = {RequestMethod.GET})
    public Result exportAllege1() throws Exception {
        // String u = new String(userState.getBytes("ISO-8859-1"), "UTF-8");
        //   System.out.println(begin + "," + end + "," + u);
        //   List<Record> list = this.userService.getRecordByTime(begin, end, u);
        List<User> list = this.userService.selectList(new EntityWrapper<User>().eq("id",1));
        String[] heads = new String[]{"序号", "id", "工号", "书名", "状态"};
        List<String[]> data = new LinkedList();
        for(int i = 0; i < list.size(); ++i) {
            User entity = (User) list.get(i);
            String[] temp = new String[]{String.valueOf(i + 1), entity.getId().toString(), entity.getGeName(), entity.getGeNumber(), entity.getEmail()};
            data.add(temp);
        }
      //  ExcelParam param = (new ExcelParam.Builder("借阅记录表")).headers(heads).data(data).build();
       // byte[] array = ExcelUtil.exportStream(param);
        Result result = ResultUtil.success();
       // result.setData(array);
        return result;
    }


    // 生成导入模板（含3条示例数据）
    @RequestMapping(value = "/api/downUser.do", method = RequestMethod.GET)
    public void downTemplate(HttpServletResponse response) {
        List<User> userList = userService.testUserList();
       // userList.stream().forEach((user)->System.out.println(user.getGeNumber()+","+user.getGeName()));
        ExcelKit.$Export(User.class, response).downXlsx(userList, true);
    }
*/
}
