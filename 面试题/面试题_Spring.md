### 1.SpringBoot的核心注解? 主要有哪几个注解组成的?

```
@SpringBootApplication是SpringBoot的核心注解, 主要包含以下三个注解:
	@SpringBootConfiguration: 组合了@Configuration注解, 实现配置文件的功能.
	@EnableAutoConfiguration: 打开自动配置的功能, 也可以关闭某个自动配置的选项
    @ComponentScan: Spring组件扫描
```

### 2.Spring框架的常用注解有哪些?

```
@Controller: 用于标注控制层组件
@RestController: 相当于@Controller和@ResponseBody的组合效果
@Component: 泛指当前组件不好归类时, 可以使用这个注解进行标注
@Repository: 用于注解DAO层, 在DAOImpl类上面注解
@Service: 用于注解业务层组件
@ResponseBody: 异步请求
@RequestMapping: 用来处理请求地址映射的注解, 可用于类或方法上. 用于类上, 表示层中的所有响应请求的方法都是以此地址作为父路径
@AutoWired: 它可以作为对类成员变量, 方法及构造函数进行标注, 完成自动装配工作.
@PathVariable: 用于将请求URL中的模板变量映射到功能处理方法的参数, 即取出URL模板变量中的变量作为参数
@RequestParam: 主要用于在SpringMVC后台控制层获取的参数
@Valid: 实体数据校验, 可以结合Hibernate validator一起使用
```