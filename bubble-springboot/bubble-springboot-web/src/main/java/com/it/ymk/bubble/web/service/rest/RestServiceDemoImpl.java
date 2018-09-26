package com.it.ymk.bubble.web.service.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.it.ymk.bubble.web.service.ArrayListWrapper;

/**
 * @author yanmingkun
 * @date 2018-01-08 19:25
 */
@Path("/restService")
public class RestServiceDemoImpl {
    /**
     *获取资源的列表，用复数的形式，GET方法
     **/
    @Path("/getCarList")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<HashMap> getCarList() throws Exception {
        //方法名可以根据业务等取名,查询是的参数放入对象中，但是在开发的时候遇到的问题是获取列表的种类多种多样，
        //获取的时候，需要在service中进行复杂判断
        ArrayListWrapper w = new ArrayListWrapper();
        List<HashMap> resultList = new ArrayList();
        HashMap hashMap = new HashMap();
        hashMap.put("name", "yuhui");
        resultList.add(hashMap);
        w.myArray = resultList;
        return resultList;
    }

}
