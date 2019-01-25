package com.it.ymk.bubble.web.taobao;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import com.it.ymk.bubble.web.pojo.Item;
import com.it.ymk.bubble.web.pojo.Shop;

public class TmallCrawler {
    static {
        // 设置
        // String dir = System.getProperty("user.dir");
        System.setProperty("webdriver.firefox.bin", "D:/Program Files (x86)/Mozilla Firefox/firefox.exe");
        System.setProperty("webdriver.gecko.driver",
            "D:/Program Files (x86)/Mozilla Firefox/webdriver/geckodriver.exe");
        // 设置chromedriver.exe的环境变量,浏览器版本52.0.2743.116 m
        System.setProperty("webdriver.chrome.driver",
            "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
    }

    @Deprecated
    static String RegexString(String targetStr, String patternStr) {
        // 定义一个样式模板，此中使用正则表达式，括号中是要抓的内容
        // 相当于埋好了陷阱匹配的地方就会掉下去
        Pattern pattern = Pattern.compile(patternStr);
        // 定义一个matcher用来做匹配
        Matcher matcher = pattern.matcher(targetStr);
        // 如果找到了
        if (matcher.find()) {
            // 打印出结果
            return matcher.group(3);
        }
        return "Nothing";
    }

    /**
     * 登录一次获取cookie
     *
     * @param name
     * @param pd
     * @return
     */
    public static String getLoginCookie(String name, String pd) {
        WebDriver webDriver = null;
        String cookieStr = "";
        try {
            /*
             * WebDriver driver = new ChromeDriver(); File file = new File(
             * "C:\\Users\\yanmingkun\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\444b1cpt.default"
             * ); FirefoxProfile profile = new FirefoxProfile(file); WebDriver
             * webDriver = new FirefoxDriver(profile);
             */
            webDriver = new ChromeDriver();
            webDriver.get(
                "https://login.com.com.it.ymk.bubble.web.taobao.com/member/login.jhtml?qrLogin=false");
            // 输入用户名
            webDriver.findElement(By.id("TPL_username_1")).clear();
            webDriver.findElement(By.id("TPL_username_1")).sendKeys(name);

            // 输入密码
            webDriver.findElement(By.id("TPL_password_1")).clear();
            webDriver.findElement(By.id("TPL_password_1")).sendKeys(pd);

            // 点击登录按钮
            webDriver.findElement(By.id("J_SubmitStatic")).click();
            webDriver.switchTo().defaultContent();
            Boolean findCaptcha = true;
            Boolean findphoneValid = true;
            try {
                WebElement we = webDriver.findElement(By.id("nc_1_n1t"));
            } catch (NoSuchElementException e) {
                findCaptcha = false;
            }
            try {
                WebElement we = webDriver.findElement(By.id("J_Phone_Checkcode"));
            } catch (NoSuchElementException e) {
                findphoneValid = false;
            }
            if (findphoneValid) {
                // 点击获取验证码
                webDriver.findElement(By.id("J_GetCode")).click();
                webDriver.switchTo().defaultContent();
                webDriver.getCurrentUrl();
                System.out.print(webDriver.getCurrentUrl());
            }
            // 滑块验证码
            if (findCaptcha) {
                WebElement source = webDriver.findElement(By.id("nc_1_n1z"));
                String slideWidth = webDriver.findElement(By.id("nc_1_n1t")).getCssValue("width");
                String slide = webDriver.findElement(By.id("nc_1_n1z")).getCssValue("width");
                int w = Integer.parseInt(slideWidth.substring(0, slideWidth.indexOf("px")))
                        - Integer.parseInt(slide.substring(0, slide.indexOf("px")));

                // WebElement target = webDriver.findElement(element));
                Actions actions = new Actions(webDriver);
                int x = source.getLocation().getX();
                int y = source.getLocation().getY();
                actions.clickAndHold(source);
                actions.moveByOffset(x + w, y).perform();
                actions.release();
                // actions.dragAndDropBy(source, x + w, y);
                // actions.release();
                // 输入用户名
                webDriver.findElement(By.id("TPL_username_1")).clear();
                webDriver.findElement(By.id("TPL_username_1")).sendKeys(name);

                // 输入密码
                webDriver.findElement(By.id("TPL_password_1")).clear();
                webDriver.findElement(By.id("TPL_password_1")).sendKeys(pd);

                // 点击登录按钮
                webDriver.findElement(By.id("J_SubmitStatic")).click();
                webDriver.switchTo().defaultContent();
            }
            try {

                // 不停的检测，一旦当前页面URL不是登录页面URL，就说明浏览器已经进行了跳转
                while (true) {
                    Thread.sleep(500L);
                    if (!webDriver.getCurrentUrl()
                        .startsWith(
                            "https://login.com.com.it.ymk.bubble.web.taobao.com/member/login.jhtml?qrLogin=false")) {
                        break;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 获取cookie
            Set<Cookie> cookies = webDriver.manage().getCookies();
            System.out.println("1 Page title is: " + webDriver.getTitle());

            for (Cookie cookie : cookies) {
                cookieStr += cookie.getName() + "=" + cookie.getValue() + "; ";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // webDriver.quit();
        } finally {
            // webDriver.quit();
        }
        // 退出，关闭浏览器
        // webDriver.quit();
        return cookieStr;

    }

    /**
     * 获取收藏夹中的店铺
     *
     * @param cookieStr
     * @return
     * @throws Exception
     */
    public static List<Shop> getShops(String cookieStr) throws Exception {

        // List<NameValuePair> params = new ArrayList<NameValuePair>();
        // params.add(new BasicNameValuePair("cookie", cookieStr));
        CloseableHttpClient httpClient = TaobaoHttpLogin.createSSLClientDefault(true);
        String collection = "https://shoucang.com.com.it.ymk.bubble.web.taobao.com/shop_collect_n.htm?type=0&tab=0&ifAllTag=0";
        HttpGet hg1 = new HttpGet(collection);
        hg1.addHeader("cookie", cookieStr);
        TaobaoHttpLogin.headerWrapper(hg1);
        HttpResponse httpresponse1 = httpClient.execute(hg1);
        HttpEntity entity1 = httpresponse1.getEntity();
        String body1 = EntityUtils.toString(entity1);
        Document document = Jsoup.parse(body1);
        List<Shop> list = new ArrayList<Shop>();
        Element favlist = document.getElementById("fav-list");
        if (favlist == null) {
            throw new Exception("你还没收藏过店铺哦！");
        }
        Elements datas = favlist.getElementsByClass("J_FavListItem");
        for (Element e : datas) {
            Element shopName = e.getElementsByClass("shop-name-link").get(0);
            Shop shop = new Shop();
            shop.setID(e.attr("data-id"));
            shop.setSrc(getRedirectUrl(shopName.attr("href")));
            shop.setName(shopName.text());
            list.add(shop);
        }
        return list;
    }

    /**
     * 使用cookie查询
     *
     * @param url
     * @param cookieStr
     * @return
     * @throws Exception
     * @throws IOException
     */
    public static String getByCookie(String url, String cookieStr) throws Exception {
        CloseableHttpClient httpClient = TaobaoHttpLogin.createSSLClientDefault(true);
        // String collection =
        // "https://shoucang.taobao.com/shop_collect_n.htm?type=0&tab=0&ifAllTag=0";
        HttpGet hg1 = new HttpGet(url);
        hg1.addHeader("cookie", cookieStr);
        TaobaoHttpLogin.headerWrapper(hg1);
        HttpResponse httpresponse1 = httpClient.execute(hg1);
        HttpEntity entity1 = httpresponse1.getEntity();
        String body1 = EntityUtils.toString(entity1);
        return body1;

    }

    /**
     * 获取重定向地址
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String getRedirectUrl(String url) throws IOException {
        Document doc = Jsoup.connect("https:" + url).get();
        return doc.baseUri();
    }

    /**
     * 获取所有商品
     *
     * @param shop
     * @param cookie
     * @return
     * @throws Exception
     */
    public static List<Item> fetchItemsByShop(Shop shop, String cookie) throws Exception {
        String redirect = shop.getSrc();
        List<Item> listitem = new ArrayList<Item>();
        // String itemrul = redirect.substring(0, redirect.indexOf("com")+3) +
        // "/search.htm?search=y";
        // String items = getByCookie(itemrul, cookie);
        // Document document = Jsoup.parse(items);
        // System.out.println(items);
        // List<Item> listitem = new ArrayList<Item>();
        // Elements datas =
        // document.getElementsByClass("J_TItems").get(0).getElementsByClass("item5line1");
        // for (Element e : datas) {
        // Elements rowItems = e.getElementsByClass("item");
        // for (Element rowItem : rowItems) {
        // Element item = rowItem.getElementsByClass("item-name").get(0);
        // Element cprice = rowItem.getElementsByClass("c-price").get(0);
        // Element symbol = rowItem.getElementsByClass("symbol").get(0);
        // Item i = new Item();
        // i.setID(rowItem.attr("data-id"));
        // i.setName(item.text());
        // i.setSrc(item.attr("href"));
        // i.setPritice(cprice.text() + symbol.text());
        // listitem.add(i);
        // }
        // }
        String pageurl = redirect.substring(0, redirect.indexOf("com") + 3)
                         + "/search.htm?search=y&tsearch=y#anchor&pageNo=" + 1;
        String pageContent = getByCookie(pageurl, cookie);
        Document pagedo = Jsoup.parse(pageContent);
        Element asycEle = pagedo.getElementById("J_ShopAsynSearchURL");
        if (asycEle == null) {
            return listitem;
        }
        String asycUrl = asycEle.attr("value");
        for (int i = 1; i < Integer.MAX_VALUE; i++) {

            // 分页内容
            String pageitems = getByCookie(redirect.substring(0, redirect.indexOf("com") + 3)
                                           + asycUrl.substring(asycUrl.indexOf("/i") + 2) + "&pageNo=" + i,
                cookie);
            Document asycpage = Jsoup.parse(pageitems);

            // Elements pagedatas =
            // asycpage.getElementsByClass("J_TItems").get(0).getElementsByClass("item5line1");
            Elements pagedatas = asycpage.getElementById("J_ShopSearchResult").getElementsByClass("J_TItems").get(0)
                .children();
            Elements e = asycpage.getElementById("J_ShopSearchResult").getElementsByClass("J_TItems").get(0)
                .getElementsByClass("pagination");
            if (!e.hasText()) {
                break;
            }
            Elements filteElement = new Elements();
            ListIterator<Element> iter = pagedatas.listIterator();
            while (iter.hasNext()) {
                Element elet = iter.next();
                if (elet.attr("class").equalsIgnoreCase("pagination")) {
                    break;
                }
                filteElement.add(elet);
            }
            for (Element ele : pagedatas) {
                Elements rowItems = ele.getElementsByClass("item");
                for (Element rowItem : rowItems) {
                    Element item = rowItem.getElementsByClass("item-name").get(0);
                    Element cprice = rowItem.getElementsByClass("c-price").get(0);
                    Element symbol = rowItem.getElementsByClass("symbol").get(0);
                    Item pageitem = new Item();
                    pageitem.setID(rowItem.attr("data-id"));
                    pageitem.setName(item.text());
                    pageitem.setSrc(item.attr("href"));
                    pageitem.setPritice(cprice.text() + symbol.text());
                    listitem.add(pageitem);
                }
            }

        }

        return listitem;

    }

    /**
     * 自动获取所有收藏店铺中的商品
     *
     * @param use
     * @param name
     * @return
     * @throws Exception
     */
    public static List<Shop> getAllFavourite(String use, String name) throws Exception {
        String cookie = getLoginCookie(use, name);
        System.out.println(cookie);
        // String cookieStore = "thw=cn; _l_g_=Ug%3D%3D; lgc=wondershoter;
        // cookie1=BxuSTUC2ZQgzcZGXOe1ZT6wfwfJ0%2FxJ0UG%2FQpbPsVlQ%3D;
        // cookie2=1ce31b6ce1a7f7c585598c47968280f6;
        // existShop=MTQ3NDUyMzQ0OA%3D%3D;
        // uss=VTtBp4vyD%2BmpuutLaCJ3exJbSksmFgvEvs%2FfhQk0hMLe57LlyVA5rzu6bts%3D;
        // sg=r6b; cna=NV9qECdYKwQCAXjFdYbB4NIb; _mw_us_time_=1474523451613;
        // skt=12b23abec3439f20; _tb_token_=3774847b67ee5;
        // uc1=cookie14=UoWwILNLlAJXtg%3D%3D&lng=zh_CN&cookie16=WqG3DMC9UpAPBHGz5QBErFxlCA%3D%3D&existShop=false&cookie21=VFC%2FuZ9ainBZ&tag=2&cookie15=VFC%2FuZ9ayeYq2g%3D%3D&pas=0;
        // uc3=sg2=WvcVOt%2F2poXqgzcsoFyiM9ycqKXC8lbfL0HHDS0hSl4%3D&nk2=FPawzGaJGPY5mGUe&id2=UNGfHyJmJTeA&vt3=F8dAS1HpUXaF6FJAhb0%3D&lg2=URm48syIIVrSKA%3D%3D;
        // tracknick=wondershoter; mt=ci=0_1; unb=319999376;
        // l=Avb2Gj7vnY1esvhMdGfFqtU3xib4FzpR; _cc_=VT5L2FSpdA%3D%3D;
        // cookie17=UNGfHyJmJTeA; _nk_=wondershoter; tg=0;
        // t=1d865374eb7bffe9904fdd250e4ab950; v=0;
        // isg=Au3tsDibRq9qAiLD7ryieo8x_InNoa2HTZdh2C_yKQTzpg1Y95ox7Dvw5s27; ";

        List<Shop> list = getShops(cookie);

        for (Shop s : list) {
            if (s.getSrc().indexOf("tmall.com") > 0) {
                List<Item> listitem = fetchItemsByShop(s, cookie);
                s.setListItem(listitem);
                System.out.println(listitem);
            }
        }
        return list;
    }

    /**
     * 获取商品的价格信息
     *
     * @return
     * @throws Exception
     */
    @Deprecated
    public static Map<String, Object> getItemByUrl() throws Exception {
        // List<NameValuePair> params = new ArrayList<NameValuePair>();
        CloseableHttpClient httpClient = TaobaoHttpLogin.createSSLClientDefault(true);
        // HttpGet httpGet = new
        // HttpGet("https://detail.tmall.com/item.htm?spm=a220m.1000858.1000725.25.L9vk3J&id=531977192076&skuId=3173113378812&cat_id=2&rn=9b949e43091eda408777622fe6c99ea1&standard=1&user_id=881078398&is_b=1");
        HttpGet httpGet = new HttpGet(
            "https://mdskip.com.com.it.ymk.bubble.web.taobao.com/core/initItemDetail.htm?tmallBuySupport=true&isForbidBuyItem=false&isApparel=false&sellerPreview=false&household=false&isPurchaseMallPage=false&queryMemberRight=true&itemId=531977192076&isRegionLevel=false&offlineShop=false&tryBeforeBuy=false&cartEnable=true&service3C=true&isSecKill=false&cachedTimestamp=1474347776168&isUseInventoryCenter=false&addressLevel=2&isAreaSell=false&showShopProm=false");
        TaobaoHttpLogin.headerWrapper(httpGet);
        httpGet.setHeader("accept-Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpGet.addHeader("Referer",
            "https://list.tmall.com/search_product.htm?q=%C1%AA%CF%EB+thinkpad+%C9%CC%CE%F1%B1%CA%BC%C7%B1%BE&type=p&spm=a220m.1000858.a2227oh.d100&xl=%C1%AA%CF%EB+th_3&from=.list.pc_1_suggest");
        // params.add(new BasicNameValuePair("TPL_password", TPL_password));
        // params.add(new BasicNameValuePair("TPL_username", TPL_username));
        // params.add(new BasicNameValuePair("newlogin", "0"));
        // params.add(new BasicNameValuePair("callback", "1"));

        // params.add(new BasicNameValuePair("loginsite", "0"));
        // params.add(new BasicNameValuePair("keyLogin", "false"));
        // params.add(new BasicNameValuePair("qrLogin", "true"));

        // httpGet.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
        // 发送请求
        HttpResponse httpresponse = httpClient.execute(httpGet);
        HttpEntity entity = httpresponse.getEntity();
        String body = EntityUtils.toString(entity);
        JSONObject object = new JSONObject(body);
        JSONObject object1 = new JSONObject(object.get("defaultModel").toString());
        JSONObject object2 = new JSONObject(object1.get("itemPriceResultDO").toString());
        JSONObject object3 = new JSONObject(object2.get("priceInfo").toString());
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Iterator<?> it = object3.keys();
        // 遍历jsonObject数据，添加到Map对象
        while (it.hasNext()) {
            String key = String.valueOf(it.next());
            Object value = object3.get(key);
            dataMap.put(key, value);
        }
        System.out.println(dataMap);
        return dataMap;

    }

    /**
     * 输入cookie获取所有收藏店铺中的商品
     *
     * @param use
     * @param name
     * @return
     * @throws Exception
     */
    public static List<Shop> getAllFavouriteByCookie(String use, String name, String cookieStore) throws Exception {

        List<Shop> list = getShops(cookieStore);

        for (Shop s : list) {
            if (s.getSrc().indexOf("tmall.com") > 0) {
                List<Item> listitem = fetchItemsByShop(s, cookieStore);
                s.setListItem(listitem);
                System.out.println(listitem);
            }
        }
        return list;
    }

    public static void main(String[] args) throws Exception {
        String str = getRedirectUrl(
            "//store.com.com.it.ymk.bubble.web.taobao.com/?shop_id=254566010&scm=");
        System.out.println(str);
        /*
         * String cookieStore =
         * "thw=cn; _l_g_=Ug%3D%3D; lgc=wondershoter; cookie1=BxuSTUC2ZQgzcZGXOe1ZT6wfwfJ0%2FxJ0UG%2FQpbPsVlQ%3D; cookie2=1ce31b6ce1a7f7c585598c47968280f6; existShop=MTQ3NDUyMzQ0OA%3D%3D; uss=VTtBp4vyD%2BmpuutLaCJ3exJbSksmFgvEvs%2FfhQk0hMLe57LlyVA5rzu6bts%3D; sg=r6b; cna=NV9qECdYKwQCAXjFdYbB4NIb; _mw_us_time_=1474523451613; skt=12b23abec3439f20; _tb_token_=3774847b67ee5; uc1=cookie14=UoWwILNLlAJXtg%3D%3D&lng=zh_CN&cookie16=WqG3DMC9UpAPBHGz5QBErFxlCA%3D%3D&existShop=false&cookie21=VFC%2FuZ9ainBZ&tag=2&cookie15=VFC%2FuZ9ayeYq2g%3D%3D&pas=0; uc3=sg2=WvcVOt%2F2poXqgzcsoFyiM9ycqKXC8lbfL0HHDS0hSl4%3D&nk2=FPawzGaJGPY5mGUe&id2=UNGfHyJmJTeA&vt3=F8dAS1HpUXaF6FJAhb0%3D&lg2=URm48syIIVrSKA%3D%3D; tracknick=wondershoter; mt=ci=0_1; unb=319999376; l=Avb2Gj7vnY1esvhMdGfFqtU3xib4FzpR; _cc_=VT5L2FSpdA%3D%3D; cookie17=UNGfHyJmJTeA; _nk_=wondershoter; tg=0; t=1d865374eb7bffe9904fdd250e4ab950; v=0; isg=Au3tsDibRq9qAiLD7ryieo8x_InNoa2HTZdh2C_yKQTzpg1Y95ox7Dvw5s27; "
         * ; getAllFavourite("wondershoter", "-h68s1eQ"); //
         * System.out.println(cookie); getAllFavouriteByCookie("wondershoter",
         * "-h68s1eQ", cookieStore);
         */

    }
}