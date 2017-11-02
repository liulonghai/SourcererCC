package com.mondego.framework.pipeline;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mondego.framework.workers.Worker;

public class Pipe {
    private static final Logger logger = LogManager
            .getLogger(Pipe.class);
    int size;
    public Map<String,IChannel> channels;
    
    public Pipe(){
        this.channels = new LinkedHashMap<>();
    }
    
    public <U,T extends Worker<U>> void registerChannel(String key, ThreadedChannel<U,T> channel){
        this.channels.put(key,channel);
        this.size+=1;
    }
    
    public <U,T extends Worker<U>> void deregisterChannel(String key){
        if(null!=this.channels.remove(key)){
            this.size-=1;
        }
    }
    public IChannel getChannel(String key){
        return this.channels.get(key);
    }

    public void shutdown() {
        for (Entry<String,IChannel> entry: this.channels.entrySet()){
            logger.info("shutting down "+entry.getKey()+", " + System.currentTimeMillis());
            this.getChannel(entry.getKey()).shutdown();
        }
        // TODO Auto-generated method stub
        
    }
    
}