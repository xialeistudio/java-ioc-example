# ioc container example

Simple ioc container example project, like Spring framework.

[中文文档](README-CN.md)

## Features

+ Configuration class supported
+ Bean dependency(Partial supported)

## Example

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
+ [ ] Complex dependency inject