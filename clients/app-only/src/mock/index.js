const Mock = require('mockjs');

import account from './account'
import chat from './chat'

Mock.mock('/only-chat-server/getLineUserInfo', 'get', account.getLineUserInfo);
Mock.mock('/only-chat-server/selectUserList', 'get', account.selectUserList);
Mock.mock(/^\/only-chat-server\/chat\/queryHisRecordList/, 'get', chat.queryHisRecordList);