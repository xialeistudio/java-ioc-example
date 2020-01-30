package org.xialei.beans.example;

import org.xialei.beans.annotation.Autowired;

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
