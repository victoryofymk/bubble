package com.it.ymk.bubble.springboot.web.web.service.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jws.WebService;

import org.springframework.stereotype.Component;

import com.it.ymk.bubble.springboot.web.web.service.ArrayListWrapper;

/**
 * @author yanmingkun
 * @date 2018-09-12 11:39
 */
@Component //由Spring管理
@WebService
public class WebserviceImpl {
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
