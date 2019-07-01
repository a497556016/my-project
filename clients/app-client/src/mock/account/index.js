import Mock from 'mockjs'
import utils from '../../utils'

const Random  = Mock.Random;

const loginByAccount = function (options) {
    console.log(options)
    const params = utils.common.getUrlParams(options.url);
    if(params.account != 'admin' || params.password != '123456') {
        return {
            code: 0,
            msg: '用户名或密码错误！'
        }
    }
    return {
        code: 1,
        data: {
            account: params.account,
            username: '小何',
            password: params.password,
            phone: '13249833007',
            gender: 1,
            avatar: Random.dataImage('80x80', '头像')
        }
    }
}

const loginByPhoneCode = function (options) {
    const params = utils.common.getUrlParams(options.url);
    if(params.code == '1234') {
        return {
            code: 1,
            data: {
                account: 'admin',
                username: '小何',
                password: '123456',
                phone: params.phone,
                gender: 0,
                avatar: Random.dataImage('80x80', '头像')
            }
        }
    }else {
        return {
            code: 0,
            msg: '验证码错误！'
        }
    }
}

export default {
    loginByAccount,
    loginByPhoneCode
}