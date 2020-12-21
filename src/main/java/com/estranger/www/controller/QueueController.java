package com.estranger.www.controller;

import com.estranger.www.common.base.ResponseBase;
import com.estranger.www.common.enumeration.ResponseStatusEnum;
import com.estranger.www.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by：Estranger
 * Description：测试队列controller
 * Date：2020/12/1 16:14
 */
@RequestMapping("/queue")
@RestController
public class QueueController {

    @Autowired
    private QueueService queueService;

    /**
     * Author：Estranger
     * Description：往队列中塞数据
     * Date：2020/12/1
     */
    @RequestMapping("/delay")
    public ResponseBase delay() {
        try {
            queueService.delay();
            return ResponseBase.create().buildStatus(ResponseStatusEnum.NORMAL_RETURNED);
        } catch (Exception e) {
            return ResponseBase.create().buildStatus(ResponseStatusEnum.SERVER_ERROR);
        }


    }
}
