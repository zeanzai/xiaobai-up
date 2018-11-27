package com.xiaobai.base.modules.sys.syscaptcha.service;

import com.baomidou.mybatisplus.service.IService;
import com.xiaobai.base.modules.sys.syscaptcha.entity.SysCaptchaEntity;

import java.awt.image.BufferedImage;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.sys.syscaptcha.service
 * @date 2018/11/20 16:08
 * @modified
 */
public interface SysCaptchaService extends IService<SysCaptchaEntity> {

	/**
	 * 获取图片验证码
	 */
	BufferedImage getCaptcha(String uuid);

	/**
	 * 验证码效验
	 * @param uuid  uuid
	 * @param code  验证码
	 * @return  true：成功  false：失败
	 */
	boolean validate(String uuid, String code);
}
