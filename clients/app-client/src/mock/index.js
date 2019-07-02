import account from './account'
import data from './data'

import Mock from 'mockjs'

const requests = [
    {url: RegExp(/^\/account-server\/login\/account/), type: 'get', call: account.loginByAccount},
    {url: RegExp(/^\/account-server\/login\/phone/), type: 'get', call: account.loginByPhoneCode},
    {url: RegExp(/^\/file-server\/getTopImages$/), type: 'get', call: data.getTopImages},
    {url: RegExp(/^\/bbs-server\/getRecommendPosts/), type: 'get', call: data.getRecommendPosts}
]

requests.forEach(request => {
    Mock.mock(request.url, request.type, request.call);
})

export default Mock;