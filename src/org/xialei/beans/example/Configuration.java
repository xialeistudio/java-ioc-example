package org.xialei.beans.example;

import org.xialei.beans.annotation.Bean;

/**
 * Configuration class
 */
public class Configuration {
    @Bean
    public Sword sword() {
        return new Sword();
    }
}
