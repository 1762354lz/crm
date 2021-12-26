package com.lz.crm.web.listener;


import com.lz.crm.settings.domain.DicValue;
import com.lz.crm.settings.service.DicService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.*;

@WebListener
public  class cache implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
    //数据库查询
        WebApplicationContext applicationContext =    WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
        DicService dicService=(DicService)applicationContext.getBean("dicService");

        Map<String, List<DicValue>> map=dicService.getDicAll();
        ServletContext servletContext=event.getServletContext();
      Set<String> set=map.keySet();
      for (String vkey:set)
        {servletContext.setAttribute(vkey,map.get(vkey));
        }

      //
        //
        //          数据放进去



        Map<String,String> stringMap=new HashMap<String, String>();
        ResourceBundle resourceBundle=ResourceBundle.getBundle("Stage2Possibility");
      Enumeration<String> enumeration=resourceBundle.getKeys();
      while (enumeration.hasMoreElements())
      {String key=enumeration.nextElement();
          String value=resourceBundle.getString(key);
          stringMap.put(key,value);
      }
      System.out.println("1111111"+stringMap);
        System.out.println("22222"+resourceBundle);
      servletContext.setAttribute("stringMap",stringMap);
    }

}
