package com.jiujiu.Listener;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class BaseListener<T> extends ApplicationEvent {

     private T t;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public BaseListener(Object source, T t) {
        super(source);
        this.t = t;
    }
}
