package Springinterview.interviews.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    @Autowired
    private MyComponent myComponent;

    public void useComponent() {
        myComponent.doSomething();
    }
}