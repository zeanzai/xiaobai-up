package com.xiaobai.base.modules.job.schedulejob.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xiaobai.base.modules.job.schedulejob.entity.ScheduleJobEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.job.dao
 * @date 2018/11/20 18:32
 * @modified
 */
@Mapper
public interface ScheduleJobDao extends BaseMapper<ScheduleJobEntity> {

	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
}

