package com.leyou.search.listener;

import com.leyou.search.service.IndexService;
import com.leyou.search.service.SearchService;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Gray
 */
@Component
public class GoodsListener {

    @Autowired
    private IndexService indexService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "ly.create.index.queue", durable = "true"),
            exchange = @Exchange(
                    value = "ly.item.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"item.insert", "item.update"}))
    public void listenCreate(Long id) throws Exception{
        if(id == null) return;

        indexService.createIndex(id);
    }

    /**
     * 处理delete的消息
     *
     * @param id
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "leyou.delete.index.queue",durable="true"),
    exchange = @Exchange(
            value = "ly.item.exchange",
            ignoreDeclarationExceptions = "true",
            type = ExchangeTypes.TOPIC),
            key = {"item.delete"}))
    public void listenDelete(Long id){
        if(id == null){
            return;
        }
        this.indexService.deleteIndex(id);
    }


}
