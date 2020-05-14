package com.tiaedu.springcloud.controller;

import com.tiaedu.springcloud.entities.CommonResult;
import com.tiaedu.springcloud.entities.Payment;
import com.tiaedu.springcloud.service.PaymentService;
import com.tiaedu.springcloud.service.impl.PaymentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ResponseBody 添加到返回值中的每个@RequestMapping方法，Spring将<context:component-scan>
 * 和 <mvc:annotation-driven /> 标记添加到Spring配置文件中。
 *      <context:component-scan> 激活注释并扫描包以在应用程序上下文中查找和注册bean。
 *      <mvc:annotation-driven/> 如果Jackson/JAXB库在类路径上，则添加对读写JSON / XML的支持。
 *      对于JSON格式，包括jackson-databind jar，对于XML，包括项目类路径的jaxb-api-osgi jar。
 * 可在任何服务器（例如，Tomcat）上部署并运行应用程序。
 *      http://localhost:8080/Example/rest/emp/Bob  显示输出JSON.
 *      http://localhost:8080/Example/rest/emp/Bob.xml 输出XML
 */

/**
 * Spring 4.0引入了@RestController，这是一个控制器的专用版本，它是一个方便的注释，
 * 除了自动添加@Controller和@ResponseBody注释之外没有其他新技术。
 * 通过使用@RestController批注对控制器类进行注释，不再需要将@ResponseBody添加到所有请求
 * 映射方法中。@ResponseBody注释默认处于活动状态。
*/
@RestController
@Slf4j
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    /** @PathVariable
     * 作用：
     *  处理request url部分（这里指url template中variable，不含queryString部分）的注解
     *
     *  当使用@RequestMapping URI template 样式映射时， 即 someUrl/{paramId}, 这时的
     *  paramId可通过 @Pathvariable注解绑定它传过来的值到方法的参数上。
     *
     *  若方法参数名称和需要绑定的uri template中变量名称不一致，需要在@PathVariable("name")
     *  指定uri template中的名称。
     */
    /**@RequestBody
    作用：
        i) 该注解用于读取Request请求的body部分数据，使用系统默认配置的
            HttpMessageConverter进行解析，然后把相应的数据绑定到要返回的对象上；
        ii) 再把HttpMessageConverter返回的对象数据绑定到 controller中方法的参数上。

    使用时机：
    A) GET、POST方式提时， 根据request header Content-Type的值来判断:
        application/x-www-form-urlencoded，可选（即非必须，因为这种情况的数据@RequestParam, @ModelAttribute也可以处理，当然@RequestBody也能处理）；
        multipart/form-data, 不能处理（即使用@RequestBody不能处理这种格式的数据）；
        其他格式， 必须（其他格式包括application/json, application/xml等。这些格式的数据，必须使用@RequestBody来处理）；
    B) PUT方式提交时， 根据request header Content-Type的值来判断:
        application/x-www-form-urlencoded， 必须；
        multipart/form-data, 不能处理；
        其他格式， 必须；
    说明：request的body部分的数据编码格式由header部分的Content-Type指定；
     */
    /**
     *  @GetMapping
     *  用于将HTTP get请求映射到特定处理程序的方法注解
     *  是一个组合注解，是@RequestMapping(method = RequestMethod.GET)的缩写。
     *
     *  @PostMapping
     *  用于将HTTP post请求映射到特定处理程序的方法注解
     *  是一个组合注解，是@RequestMapping(method = RequestMethod.POST)的缩写。
     */
    @PostMapping(value = "/payment/create")
    //@GetMapping(value = "/payment/post")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("***insert result:" + result);
        if(result > 0){
            return new CommonResult(200, "insert successful!", result);
        }else{
            return new CommonResult(400, "insert fail!", null);
        }
    }
    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("***select result:" + payment);
        if(payment != null){
            return new CommonResult(200, "select successful!", payment);
        }else{
            return new CommonResult(400, "select fail!", null);
        }
    }
}
