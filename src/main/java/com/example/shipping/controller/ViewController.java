package com.example.shipping.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller
 * 界面跳转
 */
@RestController
public class ViewController {
    
    @RequestMapping(value = "/register-view", method = RequestMethod.GET)
    public ModelAndView registerView() {
        return new ModelAndView("register");
    }

    @RequestMapping(value = "/relogin-view", method = RequestMethod.GET)
    public ModelAndView reLoginView() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/business-goodssubmit-view", method = RequestMethod.GET)
    public ModelAndView addOrderView() {
        return new ModelAndView("addOrder");
    }

    @RequestMapping(value = "/business-order-view",method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('consigner')")
    public ModelAndView orderUserView(){
        return new ModelAndView("viewOrderUser");
    }


    @RequestMapping(value = "/business-infor-view", method = RequestMethod.GET)
    public ModelAndView personUserView() {
        return new ModelAndView("personalCenterUser");
    }


    @RequestMapping(value = "/company-order-view", method = RequestMethod.GET)
    public ModelAndView orderShopView() {
        return new ModelAndView("viewOrderShop");
    }


    @RequestMapping(value = "/company-goods-view", method = RequestMethod.GET)
    public ModelAndView acceptOrderView() {
        return new ModelAndView("acceptOrder");
    }


    @RequestMapping(value = "/company-transubmit-view", method = RequestMethod.GET)
    public ModelAndView addCapacityView() {
        return new ModelAndView("addCapacity");
    }

    @RequestMapping(value = "/company-orderstatus-view", method = RequestMethod.GET)
    public ModelAndView updateOrderView() {
        return new ModelAndView("updateOrder");
        // 需要携带orders数据返回
    }
    

    @RequestMapping(value = "/company-info-view", method = RequestMethod.GET)
    public ModelAndView personShopView() {
        return new ModelAndView("personalCenterShop");
    }


}
