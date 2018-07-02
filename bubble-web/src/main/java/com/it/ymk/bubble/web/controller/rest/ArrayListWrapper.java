package com.it.ymk.bubble.web.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author yanmingkun
 * @date 2018-01-08 20:22
 */
@XmlRootElement
public class ArrayListWrapper {
    public List<HashMap> myArray = new ArrayList<HashMap>();
}
