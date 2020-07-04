/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.wautsns.okauth.core.client.builtin.wechatworkcorp.model;

import com.github.wautsns.okauth.core.assist.http.kernel.model.basic.DataMap;
import com.github.wautsns.okauth.core.client.builtin.BuiltInOpenPlatformNames;
import com.github.wautsns.okauth.core.client.kernel.model.OAuth2User;
import com.github.wautsns.okauth.core.client.kernel.openplatform.OpenPlatform;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * WechatWorkCorp oauth2 user.
 *
 * <pre>
 * {
 *     "userid": "zhangsan",
 *     "name": "李四",
 *     "department": [1, 2],
 *     "order": [1, 2],
 *     "position": "后台工程师",
 *     "mobile": "13800000000",
 *     "gender": "1",
 *     "email": "zhangsan@gzdev.com",
 *     "is_leader_in_dept": [1, 0],
 *     "avatar": "http://wx.qlogo.cn/mmopen/ajNVdqHZLLA3WJ6DSZUfiakYe37PKnQhBIeOQBO4czqrnZDS79FH5Wm5m4X69TBicnHFlhiafvDwklOpZeXYQQ2icg/0",
 *     "thumb_avatar": "http://wx.qlogo.cn/mmopen/ajNVdqHZLLA3WJ6DSZUfiakYe37PKnQhBIeOQBO4czqrnZDS79FH5Wm5m4X69TBicnHFlhiafvDwklOpZeXYQQ2icg/100",
 *     "telephone": "020-123456",
 *     "alias": "jackzhang",
 *     "address": "广州市海珠区新港中路",
 *     "open_userid": "xxxxxx",
 *     "main_department": 1,
 *     "extattr": {
 *         "attrs": [
 *             {
 *                 "type": 0,
 *                 "name": "文本名称",
 *                 "text": {
 *                     "value": "文本"
 *                 }
 *             },
 *             {
 *                 "type": 1,
 *                 "name": "网页名称",
 *                 "web": {
 *                     "url": "http://www.test.com",
 *                     "title": "标题"
 *                 }
 *             }
 *         ]
 *     },
 *     "status": 1,
 *     "qr_code": "https://open.work.weixin.qq.com/wwopen/userQRCode?vcode=xxx",
 *     "external_position": "产品经理",
 *     "external_profile": {
 *         "external_corp_name": "企业简称",
 *         "external_attr": [{
 *                 "type": 0,
 *                 "name": "文本名称",
 *                 "text": {
 *                     "value": "文本"
 *                 }
 *             },
 *             {
 *                 "type": 1,
 *                 "name": "网页名称",
 *                 "web": {
 *                     "url": "http://www.test.com",
 *                     "title": "标题"
 *                 }
 *             },
 *             {
 *                 "type": 2,
 *                 "name": "测试app",
 *                 "miniprogram": {
 *                     "appid": "wx8bd80126147dFAKE",
 *                     "pagepath": "/index",
 *                     "title": "my miniprogram"
 *                 }
 *             }
 *         ]
 *     }
 * }
 * </pre>
 *
 * @author wautsns
 * @since May 23, 2020
 */
public class WechatWorkCorpOAuth2User extends OAuth2User {

    private static final long serialVersionUID = -8284465889855788132L;

    /**
     * Construct a WechatWorkCorp oauth2 user.
     *
     * @param originalDataMap original data map
     */
    public WechatWorkCorpOAuth2User(DataMap originalDataMap) {
        super(originalDataMap);
    }

    @Override
    public OpenPlatform getOpenPlatform() {
        return BuiltInOpenPlatformNames.WECHAT_WORK_CORP;
    }

    public String getUserid() {
        return getOriginalDataMap().getAsString("userid");
    }

    public String getName() {
        return getOriginalDataMap().getAsString("name");
    }

    public List<Integer> getDepartment() {
        return getOriginalDataMap().getAs("department");
    }

    public List<Integer> getOrder() {
        return getOriginalDataMap().getAs("order");
    }

    public String getPosition() {
        return getOriginalDataMap().getAsString("position");
    }

    public String getMobile() {
        return getOriginalDataMap().getAsString("mobile");
    }

    @Override
    public Gender getGender() {
        String gender = getOriginalDataMap().getAsString("gender");
        if ("1".equals(gender)) {
            return Gender.MALE;
        } else if ("2".equals(gender)) {
            return Gender.FEMALE;
        } else {
            return Gender.UNKNOWN;
        }
    }

    @Override
    public String getEmail() {
        return getOriginalDataMap().getAsString("email");
    }

    public List<Integer> getIsLeaderInDept() {
        return getOriginalDataMap().getAs("is_leader_in_dept");
    }

    public String getAvatar() {
        return getOriginalDataMap().getAsString("avatar");
    }

    public String getThumbAvatar() {
        return getOriginalDataMap().getAsString("thumb_avatar");
    }

    public String getTelephone() {
        return getOriginalDataMap().getAsString("telephone");
    }

    public String getAlias() {
        return getOriginalDataMap().getAsString("alias");
    }

    public String getAddress() {
        return getOriginalDataMap().getAsString("address");
    }

    public String getOpenUserid() {
        return getOriginalDataMap().getAsString("open_userid");
    }

    public Integer getMainDepartment() {
        return getOriginalDataMap().getAsInteger("main_department");
    }

    public ExtAttr getExtattr() {
        return new ExtAttr(getOriginalDataMap().getAsDataMap("extattr"));
    }

    @RequiredArgsConstructor
    public enum Status {

        ACTIVATED(1), DISABLED(2), INACTIVATED(3), EXIT(4);

        /** Value. */
        public final Integer value;

        // #################### utils #######################################################

        private static final Status[] VALUES = values();

        public static Status getByValue(Integer value) {
            for (Status status : VALUES) {
                if (status.value.equals(value)) {
                    return status;
                }
            }
            return null;
        }

    }

    public Status getStatus() {
        return Status.getByValue(getOriginalDataMap().getAsInteger("status"));
    }

    public String getQrCode() {
        return getOriginalDataMap().getAsString("qr_code");
    }

    public String getExternalPosition() {
        return getOriginalDataMap().getAsString("external_position");
    }

    public ExternalProfile getExternalProfile() {
        return new ExternalProfile(getOriginalDataMap().getAsDataMap("external_profile"));
    }

    // #################### amendment ###################################################

    @Override
    public String getOpenid() {
        return getUserid();
    }

    @Override
    public String getUnionid() {
        return getUserid();
    }

    @Override
    public String getUid() {
        return getUserid();
    }

    @Override
    public String getNickname() {
        return getName();
    }

    @Override
    public String getAvatarUrl() {
        return getAvatar();
    }

    @Override
    public String getCellphone() {
        return getMobile();
    }

    // #################### external ####################################################

    @Data
    public static class ExternalAttr {

        /** Original data map. */
        private final DataMap originalDataMap;

        public Integer getType() {
            return originalDataMap.getAsInteger("type");
        }

        public String getName() {
            return originalDataMap.getAsString("name");
        }

        public Text getText() {
            return new Text(originalDataMap.getAsDataMap("text"));
        }

        public Web getWeb() {
            return new Web(originalDataMap.getAsDataMap("web"));
        }

        public Miniprogram getMiniprogram() {
            return new Miniprogram(originalDataMap.getAsDataMap("miniprogram"));
        }

        @Data
        public static class Text {

            /** Original data map. */
            private final DataMap originalDataMap;

            public String getValue() {
                return originalDataMap.getAsString("value");
            }

        }

        @Data
        public static class Web {

            /** Original data map. */
            private final DataMap originalDataMap;

            public String getUrl() {
                return originalDataMap.getAsString("url");
            }

            public String getTitle() {
                return originalDataMap.getAsString("title");
            }

        }

        @Data
        public static class Miniprogram {

            /** Original data map. */
            private final DataMap originalDataMap;

            public String getAppid() {
                return originalDataMap.getAsString("appid");
            }

            public String getPagepath() {
                return originalDataMap.getAsString("pagepath");
            }

            public String getTitle() {
                return originalDataMap.getAsString("title");
            }

        }

    }

    @Data
    public static class ExtAttr {

        /** Original data map. */
        private final DataMap originalDataMap;

        public List<ExternalAttr> getAttrs() {
            List<Map<String, Serializable>> attrs = originalDataMap.getAs("attrs");
            return attrs.stream()
                    .map(DataMap::new)
                    .map(ExternalAttr::new)
                    .collect(Collectors.toCollection(LinkedList::new));
        }

    }

    @Data
    public static class ExternalProfile {

        /** Original data map. */
        private final DataMap originalDataMap;

        public String getExternalCorpName() {
            return originalDataMap.getAsString("external_corp_name");
        }

        public List<ExternalAttr> getExternalAttr() {
            List<Map<String, Serializable>> externalAttr = originalDataMap.getAs("external_attr");
            return externalAttr.stream()
                    .map(DataMap::new)
                    .map(ExternalAttr::new)
                    .collect(Collectors.toCollection(LinkedList::new));
        }

    }

}
