package com.xiaobai.base.modules.sys.sysusertoken.service;

import com.baomidou.mybatisplus.service.IService;
import com.xiaobai.base.common.utils.ReturnResultUtils;
import com.xiaobai.base.modules.sys.sysusertoken.entity.SysUserTokenEntity;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.sys.sysusertoken.service
 * @date 2018/11/20 16:25
 * @modified
 */
public interface SysUserTokenService extends IService<SysUserTokenEntity> {

	/**
	 * 生成token
	 * @param userId  用户ID
	 */
	ReturnResultUtils createToken(long userId);

	/**
	 * 退出，修改token值
	 * @param userId  用户ID
	 */
	void logout(long userId);

}

