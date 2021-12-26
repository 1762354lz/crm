package com.lz.crm.settings.service.iml;

import com.lz.crm.exception.LoginException;
import com.lz.crm.settings.dao.UserDao;
import com.lz.crm.settings.domain.User;
import com.lz.crm.settings.service.UserService;
import com.lz.crm.utils.SendEmailThread;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    public User login(String loginAct, String loginPwd, String ip) throws LoginException {
        System.out.println(ip);
        Map<String,String> map=new HashMap<String, String>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);

       Date dat=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh;mm;ss");
        String date=sdf.format(dat);
        User user=userDao.login(map);
        if (user==null)
            throw new LoginException("账号密码错误");
        if (!user.getAllowIps().contains(ip))

            throw new LoginException("ip地址受限");
       if (user.getExpireTime().compareTo(date)<0) {
           System.out.println(date);
           throw new LoginException("账号已失效");

       }
        if ("0".equals(user.getLockState()))
            throw new LoginException("账号已经锁定");



        return user;
    }

    public List<User> getUserList() {

      List<User> userList= userDao.getUserList();
      return userList;

    }

    public Boolean regist(User user) {
        System.out.println(user+"user属性值为用户输入");
            SendEmailThread sendEmail = new SendEmailThread(user);

            Thread sendEmailThread = new Thread(sendEmail);
            ExecutorService threadPool = new ThreadPoolExecutor(1, 20,
                    1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(3),
                    Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
        threadPool.execute(sendEmailThread);
        //log3
        try {
            if (userDao.get(user.getUsername()) != null) {
                return false;

            } else if (userDao.save(user) != 1) {
                return false;
            } else {
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("???111???userDao.get()和userDao.save()");
            return false;
        }


    }


    public String activeUser(String uid) {

            int num = -1;
            //log3
            try {
                num = userDao.update(uid);//dao层经常出错，所以try一下，判断update方法是否有bug
            }
            catch (Exception e)
            {
                System.out.println("???222???userDao.update()");// 为了把控制台异常信息对应上代码位置
                //数字为了控制台搜索通过数字，找到全部错误，不需要全篇一点点找
                e.printStackTrace();
            }
            if (num != 1) {
                return "激活失败";
            } else {
                return "激活成功";
            }

    }
}
