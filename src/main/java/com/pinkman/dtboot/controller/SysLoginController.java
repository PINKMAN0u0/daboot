package com.pinkman.dtboot.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.pinkman.dtboot.utils.R;
import com.pinkman.dtboot.utils.ShiroUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.aspectj.apache.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;


/**
 * @description:
 * @author: PINKMAN
 * @create: 2019-09-17 22:59
 **/
@Controller
public class SysLoginController{

    @Autowired
    private Producer producer;

    /**
     * @description: 生成验证码
     * @param response
     * @return: void
     */
    @RequestMapping("/kaptcha.jpg")
    public void kaptcha(HttpServletResponse response) throws IOException {

        //关闭缓存
        response.setHeader("Cache-Control","no-store，no-cache");
        response.setContentType("image/jpeg");

        //生成验证码文字
        String text = producer.createText();

        //生成验证码图片
        BufferedImage image = producer.createImage(text);

        //保存在session中
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY,text);

        //响应给客户端
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        out.flush();
    }

    /**
     * @description: 登陆验证
     * @param map
     * @return: com.pinkman.dtboot.utils.R
     */
    @ResponseBody
    @RequestMapping(value = "/sys/login", method = RequestMethod.POST)
    public R login(@RequestBody(required=true) Map map){

        System.out.println(map);
        String username = map.get("username").toString();
        String password = map.get("password").toString();
        String kaptcha = map.get("kaptcha").toString();


        //获取系统中的验证码信息
        String kaptchaReal = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);

        //如果输入验证码不正确
        if (!(kaptchaReal.equalsIgnoreCase(kaptcha))) {
            return R.error("验证码不正确");
        }

        Subject subject = ShiroUtils.getSubject();

        try {
            //MD5加盐加密
            String md5Password = new Md5Hash(password,username,1024).toHex();
            //生成令牌
            UsernamePasswordToken token = new UsernamePasswordToken(username, md5Password);
            subject.login(token);
        } catch (UnknownAccountException e) {
            return R.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return R.error(e.getMessage());
        } catch (LockedAccountException e) {
            return R.error(e.getMessage());
        } catch (AuthenticationException e) {
            return R.error("账户验证失败");
        }


        return R.ok();
    }

}
