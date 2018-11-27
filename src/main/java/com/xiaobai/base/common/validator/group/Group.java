package com.xiaobai.base.common.validator.group;

import javax.validation.GroupSequence;

/**
 * 定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 *      参考资料： https://www.jianshu.com/p/a9b1e2f7a749
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.common.validator.group
 * @date 2018/11/20 10:57
 * @modified
 */
@GroupSequence({AddGroup.class, UpdateGroup.class})
public interface Group {

}
