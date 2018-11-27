package com.xiaobai.base.modules.sys.sysusertoken.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xiaobai.base.modules.sys.sysusertoken.entity.SysUserTokenEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.sys.sysusertoken.dao
 * @date 2018/11/20 16:25
 * @modified
 */
@Mapper
public interface SysUserTokenDao extends BaseMapper<SysUserTokenEntity> {

	SysUserTokenEntity queryByToken(String token);

}

