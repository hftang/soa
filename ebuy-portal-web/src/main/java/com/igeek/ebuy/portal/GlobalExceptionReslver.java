package com.igeek.ebuy.portal;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.Log4jEntityResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hftang
 * @date 2019-07-23 10:08
 * @desc 全局的异常处理器
 */
public class GlobalExceptionReslver implements HandlerExceptionResolver {
    Logger logger = Logger.getLogger(GlobalExceptionReslver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        logger.debug(e.getMessage());

        ModelAndView modelAndView=new ModelAndView("error");
        modelAndView.addObject("e",e.getMessage());

        return modelAndView;
    }
}
