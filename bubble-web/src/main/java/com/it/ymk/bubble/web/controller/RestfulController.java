package com.it.ymk.bubble.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.it.ymk.bubble.dao.pojo.Car;
import com.it.ymk.bubble.web.service.rest.RestServiceDemoImpl;

/**
 * resful风格
 *
 * @author yanmingkun
 * @date 2018-01-08 17:45
 */
@RestController
@RequestMapping("/v1")
public class RestfulController {

    @Autowired
    RestServiceDemoImpl restServiceDemo;

    /**
     *添加资源，POST方法,/server单数
     **/
    @RequestMapping(path = "/car", method = { RequestMethod.POST })
    public ResponseEntity addCar(@RequestBody Car car) throws Exception {
        return null;
    }

    /**
     *获取资源的列表，用复数的形式，GET方法
     **/
    @RequestMapping(path = "/cars", method = { RequestMethod.GET })
    public List<HashMap> getCarList(Car car) throws Exception {
        //方法名可以根据业务等取名,查询是的参数放入对象中，但是在开发的时候遇到的问题是获取列表的种类多种多样，
        //获取的时候，需要在service中进行复杂判断
        List<HashMap> resultList = new ArrayList();
        HashMap hashMap = new HashMap();
        hashMap.put("name", "yuhui");
        resultList.add(hashMap);
        return resultList;
    }

    /**
     *获取某一个资源，用单数的形式，GET方法
     **/
    @RequestMapping(path = "/car", method = { RequestMethod.GET })
    public ResponseEntity getCar(Car car) throws Exception {
        return null;
    }

    /**
     *修改资源的属性，PUT方法，通过URL拼接的方式，
     *URL中包含要修改字段的名称，修改的值通过消息体传递，
     *这一点感觉不匹配因为改一个字段就要传递一个对象消耗有点大，
     *所以低版本的Http可以使用patch方法，但是Http规范并不怎么支持
     **/
    @RequestMapping(path = "/car/{id}/status", method = { RequestMethod.PUT })
    public ResponseEntity carStatus(@PathVariable("id") String id, @RequestBody Car car) {
        return null;
    }

    /**
     *修改资源，PUT方法，全量修改的话传递对象就比较适合
     **/
    @RequestMapping(path = "/car", method = { RequestMethod.PUT })
    public ResponseEntity editCar(@RequestBody Car car) throws Exception {
        return null;
    }

    /**
     *删除某一个资源，用单数的形式，DELETE方法，通过URL拼接的方式
     **/
    @RequestMapping(path = "/car/{id}", method = { RequestMethod.DELETE })
    public ResponseEntity deleteCar(@PathVariable("id") String id) throws Exception {
        //开发的时候的问题是，可能会有批量删除，delete在Http规范中不支持传送消息体，如果批量删除那就得改用post方法
        return null;
    }
}
