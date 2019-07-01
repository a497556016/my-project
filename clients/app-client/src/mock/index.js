import account from './account'
import data from './data'

import Mock from 'mockjs'

Mock.mock(/\/account-server\/login/, 'get', account.loginByAccount);

Mock.mock(RegExp(/^\/account-server\/login\/phone$/), 'get', account.loginByPhoneCode)

Mock.mock(RegExp(/^\/file-server\/getTopImages$/), 'get', data.getTopImages);

export default Mock;