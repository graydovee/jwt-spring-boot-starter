# jwt-spring-boot-starter

> 一个简单的jwt框架，适用于单应用的前后端分离项目



# 快速上手：

1. 导入maven
2. 实现`UserDetails`接口

3. 实现`GrantedAuthority`接口
4. 实现`UserDetailService<T extends UserDetails>`接口，并使用`@Service`注解
5. 配置权限，如下

```java
@Configuration
public class MyAuthorityConfiguration implements AuthorityConfigure {

    @Override
    public void configure(AuthorityManager authorityManager) {
        authorityManager.anyRequest().authenticated();
    }
}
```

6. 若要在controller中获取用户信息，可使用`@CurrentUser`注解，示例如下：
   
```java
@RestController
public class Mycontroller {
    @GetMapping("/info")
    public User getInfo(@CurrentUser User user){
        return user;
    }
}
```

7. 若要修改认证返回结果，可重写并注册以下接口：

   1. 登陆成功：实现`AuthenticationSuccessHandler`接口
   2. 登陆失败：继承并重写`AuthenticationDenyHandler`抽象类
   3. 访问拒绝，因未登录：继承并重写`AuthenticationDenyHandler`抽象类
   4. 访问拒绝，因未无权限：继承并重写`AuthenticationDenyHandler`抽象类
