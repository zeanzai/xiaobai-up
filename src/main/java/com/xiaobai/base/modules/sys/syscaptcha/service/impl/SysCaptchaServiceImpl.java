package com.xiaobai.base.modules.sys.syscaptcha.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.code.kaptcha.Producer;
import com.xiaobai.base.common.exception.ReturnRuntimeException;
import com.xiaobai.base.common.utils.DateUtils;
import com.xiaobai.base.modules.sys.syscaptcha.dao.SysCaptchaDao;
import com.xiaobai.base.modules.sys.syscaptcha.entity.SysCaptchaEntity;
import com.xiaobai.base.modules.sys.syscaptcha.service.SysCaptchaService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Date;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.sys.syscaptcha.service.impl
 * @date 2018/11/20 16:08
 * @modified
 */
@Service("sysCaptchaService")
public class SysCaptchaServiceImpl extends ServiceImpl<SysCaptchaDao, SysCaptchaEntity> implements SysCaptchaService {
	@Autowired
	private Producer producer;

	@Override
	public BufferedImage getCaptcha(String uuid) {
		if(StringUtils.isBlank(uuid)){
			throw new ReturnRuntimeException("uuid不能为空");
		}
		//生成文字验证码
		String code = producer.createText();

		SysCaptchaEntity captchaEntity = new SysCaptchaEntity();
		captchaEntity.setUuid(uuid);
		captchaEntity.setCode(code);
		//5分钟后过期
		captchaEntity.setExpireTime(DateUtils.addDateMinutes(new Date(), 5));
		this.insert(captchaEntity);

		return producer.createImage(code);
	}

	@Override
	public boolean validate(String uuid, String code) {
		SysCaptchaEntity captchaEntity = this.selectOne(new EntityWrapper<SysCaptchaEntity>().eq("uuid", uuid));
		if(captchaEntity == null){
			return false;
		}

		//删除验证码
		this.deleteById(uuid);

		if(captchaEntity.getCode().equalsIgnoreCase(code) && captchaEntity.getExpireTime().getTime() >= System.currentTimeMillis()){
			return true;
		}

		return false;
	}
}

