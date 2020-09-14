package com.estranger.www.controleller;

import com.estranger.www.common.enumeration.ResponseStatusEnum;
import com.estranger.www.service.DelayService;
import com.estranger.www.service.DistributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.estranger.www.common.base.ResponseBase;


/**
 * Created by：Estranger
 * Description：类
 * Date：2020/9/11 16:01
 */
@RestController
@RequestMapping("/distribute")
public class DistributeController {

    @Autowired
    private DistributeService distributeService;
    @Autowired
    private DelayService delayService;

    @RequestMapping("/expire")
    public ResponseBase expireKey(){
        distributeService.expireKey();
        return ResponseBase.create().buildStatus(ResponseStatusEnum.NORMAL_RETURNED);
    }

    @RequestMapping("/delay")
    public ResponseBase delayKey(){
        delayService.delayKey();
        return ResponseBase.create().buildStatus(ResponseStatusEnum.NORMAL_RETURNED);
    }
}
