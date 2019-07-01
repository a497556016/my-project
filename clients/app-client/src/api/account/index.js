import http from '../../net/http'

import {ErrorResult} from '../common'

const loginByAccount = (loginUser) => {
    if(!loginUser.account || !loginUser.password) {
        return ErrorResult('请输入用户名和密码！');
    }

    return http.get('/account-server/login', {
        params: loginUser
    });
}

const loginByPhoneCode = (phone, code) => {
    if(!phone) {
        return ErrorResult('非法的手机号码！');
    }

    return http.get('/account-server/login/phone', {
        params: {code}
    })
}

export default {
    loginByAccount,
    loginByPhoneCode
}