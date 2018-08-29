package com.jiujiu.Listener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserListener extends BaseListener<UserInfo> {


    /**
     * Create a new ApplicationEvent.
     *
     * @param source   the object on which the event initially occurred (never {@code null})
     * @param userInfo
     */
    public UserListener(Object source, UserInfo userInfo) {
        super(source, userInfo);
    }
}
