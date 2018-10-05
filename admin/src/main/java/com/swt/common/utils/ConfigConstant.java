/**
 *
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.swt.common.utils;

import com.swt.modules.sys.entity.SysFileEntity;
import org.springframework.beans.factory.annotation.Value;

import java.time.format.DateTimeFormatter;

/**
 * 系统参数相关Key
 *
 * @author Mark @shuweitech.com
 * @since 1.2.0 2017-03-26
 */
public class ConfigConstant {

    /**
     * 项目路径
     */
    public static final String CONTENT_PATH = SysFileEntity.class.getResource("/").getPath();

    /**
     * 资源路径
     */
    public static final String RESOURCE_PATH = "/statics/";

    /**
     * 时间格式
     */
    public static DateTimeFormatter time = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS");

    /**
     * 时间格式
     */
    public static DateTimeFormatter time2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
}
