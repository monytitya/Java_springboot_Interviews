package Springinterview.interviews.components;

import org.springframework.stereotype.Component;

@Component("customBeanName")
public class TestComponent {

    public void execute() {
        System.out.println("Executing...");
    }
}