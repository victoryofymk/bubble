import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 *  spring源码解析-从配置文件加载applicationContext
 *
 * @author Yanmingkun
 * @version $v:1.0, $time:2017-07-03, $id:com.it.ymk.spring.SimpleBeanFactory.java, Exp $
 */
public class SimpleBeanFactory {
    public static void main(String[] args) {
        ClassPathResource resource = new ClassPathResource("applicationContext.xml");
        BeanFactory beanFactory = new XmlBeanFactory(resource);
        //        ReceiveMailVO message = beanFactory.getBean("receiveMailVO", ReceiveMailVO.class); //Message是自己写的测试类
        //        System.out.println(message);
    }
}