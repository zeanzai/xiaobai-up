package com.xiaobai.base.modules.sys.sysusertoken.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xiaobai.base.common.utils.ReturnResultUtils;
import com.xiaobai.base.common.utils.TokenGeneratorUtils;
import com.xiaobai.base.modules.sys.sysusertoken.dao.SysUserTokenDao;
import com.xiaobai.base.modules.sys.sysusertoken.entity.SysUserTokenEntity;
import com.xiaobai.base.modules.sys.sysusertoken.service.SysUserTokenService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.sys.sysusertoken.service.impl
 * @date 2018/11/20 16:26
 * @modified
 */
@Service("sysUserTokenService")
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenDao, SysUserTokenEntity> implements SysUserTokenService {
	//12小时后过期
	private final static int EXPIRE = 3600 * 12;


	@Override
	public ReturnResultUtils createToken(long userId) {
		//生成一个token
		String token = TokenGeneratorUtils.generateValue();

		//当前时间
		Date now = new Date();
		//过期时间
		Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

		//判断是否生成过token
		SysUserTokenEntity tokenEntity = this.selectById(userId);
		if(tokenEntity == null){
			tokenEntity = new SysUserTokenEntity();
			tokenEntity.setUserId(userId);
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			//保存token
			this.insert(tokenEntity);
		}else{
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			//更新token
			this.updateById(tokenEntity);
		}

		ReturnResultUtils r = ReturnResultUtils.ok().put("token", token).put("expire", EXPIRE);

		return r;
	}

	@Override
	public void logout(long userId) {
		//生成一个token
		String token = TokenGeneratorUtils.generateValue();

		//修改token
		SysUserTokenEntity tokenEntity = new SysUserTokenEntity();
		tokenEntity.setUserId(userId);
		tokenEntity.setToken(token);
		this.updateById(tokenEntity);
	}
}

