package com.lz.crm.settings.web;

import com.lz.crm.settings.domain.User;
import com.lz.crm.settings.service.UserService;
import com.lz.crm.utils.UuidUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
        @RequestMapping("/settings/user")
public class UserController {
    @Resource
    private UserService userService;
    @RequestMapping("/login.do")
    @ResponseBody
    public Map<String,Object> login(String loginAct, String loginPwd, HttpServletRequest request){
      System.out.println("usercontroller----------login-----");

        String ip= request.getRemoteAddr();
        System.out.println(ip+",,,,,,,");
        loginPwd= DigestUtils.md5DigestAsHex(loginPwd.getBytes());
Map<String,Object> map=new HashMap<String, Object>();

try {


    User user = userService.login(loginAct, loginPwd, ip);
    request.getSession().setAttribute("user",user);
    map.put("success",true);
    return map;


}
catch (Exception e){
    e.printStackTrace();
   String msg= e.getMessage();
    map.put("success",false);
    map.put("msg",msg);
    return map;

}

}
    @RequestMapping("/activeUser")
    @ResponseBody
    private String activeUser(HttpServletRequest request, HttpServletResponse response) {
        //aop：log1：是否后端问题，before
        // 2：返回前端数据是否正确，即service方法结果，service方法和dao方法是否有异常，around
        System.out.println("---999---activeUser请求//usercontroller");
        String uid=request.getParameter("code");
        String msg="";
        //2
        try {

            if (uid == null) {
                msg = "激活码不存在";
            }//边界条件是为了后面代码正常，为了后面代码正常，判断后面代码哪块自己能控制。哪块用户决定的
            else {
                msg = userService.activeUser(uid);//为了这句，正常uid不为空，并且自己不可控，用户所决定。
            }
        }
        catch (Exception e){
            System.out.println("???333???userService.activeUser(uid)");
            e.printStackTrace();
        }
        finally {
            System.out.println("msg="+msg+"！！！aaa！！！msg=激活成功//userService.activeUser(uid)");
        }
        return msg;
    }
    @RequestMapping("/registUser")
    @ResponseBody
    private Boolean registUser(HttpServletRequest request, HttpServletResponse response) {
       //log1
        System.out.println("---888---registUser请求//usercontroller");//bug区分前端还是后端
        User user=new User();
        Boolean bl=false;
        user.setUsername(request.getParameter("username"));
        user.setLoginPwd(request.getParameter("password"));
        user.setEmail(request.getParameter("email"));
        user.setName(request.getParameter("name"));
        user.setBirthday(request.getParameter("birthday"));
        user.setSex(request.getParameter("sex"));
        user.setTelephone(request.getParameter("telephone"));
        user.setId(UuidUtil.getUUID());
        System.out.println("~~~"+user.getId());
        user.setStatus("N");
       //log2
        try{//因为无法把controller整个try catch，aop整个好使但中间try应该就不好使了，想整个try，
            // 可以在调用处catch，所以只能把出现异常可能性大的地方try一下,try调用的用aop
        bl=userService.regist(user);}//判断userservice是否有bug
        catch (Exception e)
        {e.printStackTrace();}
        finally {
            System.out.println("bl="+bl+"!!!bbb!!!bl=true//userService.regist(user)");
            //是否bug不在这，有时是逻辑错误，无异常但结果不对
        }
        return bl;


    }

}
