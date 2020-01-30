# 依赖注入容器示例

简单的依赖注入容器示例项目，参考Spring framework实现。

## 功能
+ Configuration类支持
+ Bean依赖注入(部分支持)

## 示例

+ Configuration class
    
    ```java
    public class Configuration {
        @Bean
        public Sword sword() {
            return new Sword();
        }
    }
    ```
  
+ Sword class
    
    ```java
    public class Sword {
        public void use() {
            System.out.println("use sword");
        }
    }
    ```
  
+ Knight class with dependency

    ```java
    public class Knight {
        private Sword sword;
    
        @Autowired
        public Knight(Sword sword) {
            this.sword = sword;
        }
    
        public void use() {
            this.sword.use();
        }
    }
    ```

+ Main class

    ```java
    public class Main {
        public static void main(String[] args) throws Exception {
            BeanFactory beanFactory = new AnnotationBeanFactory(Configuration.class);
            Knight knight = beanFactory.getBean(Knight.class);
            knight.use();
        }
    }
    ```
  
+ Output

    ```text
    use sword
    ```
  
## Todo

+ [ ] 复杂的依赖注入处理