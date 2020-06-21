# jwt-spring-boot-starter

> 一个简单的jwt框架，适用于单应用的前后端分离项目



# 快速上手：

1. 实现`UserDetails`接口

2. 实现`GrantedAuthority`接口

3. 实现`UserDetailService<T extends UserDetails>`接口
4. 配置权限

```java
@Configuration
public class MyAuthorityConfiguration implements AuthorityConfigure {

    @Override
    public void configure(AuthorityManager authorityManager) {
        authorityManager.anyRequest().authenticated();
    }
}
```

