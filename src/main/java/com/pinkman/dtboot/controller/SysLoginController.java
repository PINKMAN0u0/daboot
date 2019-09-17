package com.pinkman.dtboot.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.pinkman.dtboot.utils.ShiroUtils;
import org.aspectj.apache.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * @description:
 * @author: PINKMAN
 * @create: 2019-09-17 22:59
 **/
@Controller
public class SysLoginController{

    @Autowired
    private Producer producer;

    @RequestMapping("/captcha.jpg")
    public void kaptcha(HttpServletResponse response) throws IOException {

        //关闭缓存
        response.setHeader("Cache-Control","no-store");
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

}
