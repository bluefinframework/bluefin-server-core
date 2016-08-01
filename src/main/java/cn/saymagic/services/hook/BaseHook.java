package cn.saymagic.services.hook;

import org.springframework.stereotype.Service;

/**
 * Created by saymagic on 16/6/10.
 */
@Service
public abstract class BaseHook {

    protected int type;

    public abstract void onHook(Object object);

}
