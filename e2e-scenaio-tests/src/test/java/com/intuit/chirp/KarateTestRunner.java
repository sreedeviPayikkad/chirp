package com.intuit.chirp;

import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

public class KarateTestRunner {

    @Test
    Karate all() {
        return Karate.run("classpath:/stories/");
    }

}
