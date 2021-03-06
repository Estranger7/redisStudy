package com.estranger.www.controller;

import com.estranger.www.common.base.ResponseBase;
import com.estranger.www.common.enumeration.ResponseStatusEnum;
import com.estranger.www.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by：Estranger
 * Description：
 * Date：2020/11/18 17:50
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * Author：Estranger
     * Description：测试简单限流
     * 设定一个用户60s内最多只能回复3个帖子
     * Date：2020/11/18
     */
    @RequestMapping("/reply")
    public ResponseBase reply(@RequestParam String userId) {
        try {
            String msg = messageService.reply(userId);
            return ResponseBase.create().buildStatus(ResponseStatusEnum.NORMAL_RETURNED).buildData(msg);
        } catch (Exception e) {
            return ResponseBase.create().buildStatus(ResponseStatusEnum.SERVER_ERROR);
        }


    }
}
