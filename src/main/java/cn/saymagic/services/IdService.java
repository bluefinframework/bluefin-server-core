package cn.saymagic.services;

import cn.saymagic.generater.IdGenerater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by saymagic on 16/8/31.
 */
@Service
public class IdService {

    @Autowired
    IdGenerater idGenterater;

    public String generate(String origin){
        return idGenterater.generate(origin);
    }

    public String restore(String cipher){
        return idGenterater.restore(cipher);
    }
}
