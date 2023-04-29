package com.example.shipping.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.shipping.entity.GoodsDao;
import com.example.shipping.service.CarService;
import com.example.shipping.service.DriverService;
import com.example.shipping.service.GoodsService;
import com.example.shipping.service.OrderService;
import com.example.shipping.service.UserService;

/**
 * Controller
 * 界面跳转
 */
@RestController
public class ViewController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private CarService carService;
    @Autowired
    private DriverService driverService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register-view", method = RequestMethod.GET)
    public ModelAndView registerView() {
        return new ModelAndView("register");
    }

    @RequestMapping(value = "/user/relogin", method = RequestMethod.GET)
    public ModelAndView reLoginView() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/user/addOrder", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('consigner')")
    public ModelAndView addOrderView() {
        return new ModelAndView("addOrder");
    }

    @RequestMapping(value = "user/viewOrderUser",method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('consigner')")
    public ModelAndView orderUserView(){
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("orders", orderService.getOrdersByUser());
        return new ModelAndView("viewOrderUser",attributes);
    }

    @RequestMapping(value = "user/trackOrder", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('consigner')")
    public ModelAndView trackOrderView() {
        return new ModelAndView("trackOrder");
    }

    @RequestMapping(value = "user/personalCenterUser", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('consigner')")
    public ModelAndView personUserView() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("user", userService.getUserByUserId());
        return new ModelAndView("personalCenterUser",attributes);
    }

    @RequestMapping(value = "user/viewOrderShop", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('company')")
    public ModelAndView orderShopView() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("orders", orderService.getOrdersByShop());
        return new ModelAndView("viewOrderShop",attributes);
    }

    @RequestMapping(value = "user/acceptOrder", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('company')")
    public ModelAndView acceptOrderView() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("goods", goodsService.getAllUntranspotedGoods());
        return new ModelAndView("acceptOrder",attributes);
    }

    @RequestMapping(value = "user/addCapacity", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('company')")
    public ModelAndView addCapacityView() {
        return new ModelAndView("addCapacity");
    }

    @RequestMapping(value = "user/updateOrder", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('company')")
    public ModelAndView updateOrderView() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("orders", orderService.getOrdersByShop());
        return new ModelAndView("updateOrder",attributes);
    }

    @RequestMapping(value = "user/personalCenterShop", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('company')")
    public ModelAndView personShopView() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("user", userService.getUserByUserId());
        attributes.put("cars", carService.getAllCarsByCompanyId());
        attributes.put("drivers", driverService.getAllDriversByCompayId());
        return new ModelAndView("personalCenterShop",attributes);
    }

}
