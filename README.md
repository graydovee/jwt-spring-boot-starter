# jwt-spring-boot-starter

> 一个简单的jwt框架，适用于单应用的前后端分离项目



# 快速上手：

1. 使用mvn install命令将项目安装至本地maven仓库，并导入依赖
2. 实现`UserDetails`接口

3. 实现`GrantedAuthority`接口
4. 实现`UserDetailService<T extends UserDetails>`接口，并使用`@Service`注解
5. 配置权限，可通过接口批量配置，也可通过注解单独配置

```java
@Configuration
public class MyAuthorityConfiguration implements AuthorityConfigure {

    @Override
    public void configure(AuthorityManager authorityManager) {
        authorityManager
                .antMatchers("/register").permitAll()
                .antMatchers("/public/**").permitAll()
                .antMatchers(RequestMethod.POST,"/post").permitAll()
                .anyRequest().authenticated();
    }
}

@RestController
@Authorization(permitAll = true) //注解加在类上，会配置该类中所有接口的权限
public class TestController {

    @RequestMapping("/test1")
    public String test1(){
        return "put3";
    }

    @Authorization //注解加在方法上，可单独配置接口权限，不加参数默认为需要登录
    @PostMapping("/test2")
    public String test2(){
        return "test2";
    }

    @Authorization("USER") //可配置所需权限
    @PutMapping("/test3")
    public String test3(){
        return "test3";
    }

}

```

6. 若要在controller中获取用户信息，可使用`@CurrentUser`注解，示例如下：
   
```java
@RestController
public class MyController {
    @GetMapping("/info")
    public User getInfo(@CurrentUser User user){
        return user;
    }
}
```

7. 若要修改认证返回结果，可重写并注册以下接口：

   1. 登陆成功：实现`AuthenticationSuccessHandler`接口
   2. 登陆失败：继承并重写`AuthenticationDenyHandler`抽象类
   3. 访问拒绝，因未登录：继承并重写`UnauthorizedHandler`抽象类
   4. 访问拒绝，因未无权限：继承并重写`DenyHandler`抽象类

8. 可配置`TokenGetter`，修改Token获取方式，已有`CookieBearerTokenGetter`和`HeaderBearerTokenGetter`两个实现，默认使用`CookieBearerTokenGetter`，配置示例如下
```java
@Configuration
public class MyConfig {
    @Bean
    public TokenGetter tokenGetter() {
        return new CookieBearerTokenGetter();
    }
}
```
9 可实现并注册`CookieTokenSetter`接口，自定义写入Cookie的token内容。建议配合实现并重写`AbstractCookieTokenGetter`的`getTokenFromCookie`方法使用

### 后续更新计划：
1. 增加csrf过滤器，目前没有csrf防御功能，建议使用Header方式。