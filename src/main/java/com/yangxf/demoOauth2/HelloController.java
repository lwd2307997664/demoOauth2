/**
 * FileName: HelloController
 * Author:   linwd
 * Date:     2021/3/31 12:57
 * Description: 测试控制器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yangxf.demoOauth2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉<br>
 * 〈测试控制器〉
 *
 * @author linwd
 * @create 2021/3/31
 * @since 1.0.0
 */
@RestController
public class HelloController {

    @GetMapping("/admin/hello")
    public String admin() {
        return "admin";
    }

    @GetMapping("/user/hello")
    public String user() {
        return "user";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
