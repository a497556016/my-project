import {chat as types, user as userTypes} from '../types'

import api from '../../api/chat'

const RECORD_DATAS = "record_datas";

const getCurLineHisRecords = function (loginUserInfo, lineUserInfo) {
    let hisRecordsData = {};
    const json = localStorage.getItem(RECORD_DATAS);
    if(json) {
        // console.log(json);
        try {
            const data = JSON.parse(json);
            console.log('缓存的聊天记录：', data);
            hisRecordsData = data;
        }catch (e) {
            localStorage.removeItem(RECORD_DATAS);
        }
    }

    console.log(hisRecordsData);
    //获取当前登录用户的聊天记录
    let curLogs = hisRecordsData[loginUserInfo.username];
    if(!curLogs){
        hisRecordsData[loginUserInfo.username] = curLogs = {};
    }
    //获取当前连线用户的聊天记录
    let curLineLogs = curLogs[lineUserInfo.username];
    if(!curLineLogs){
        curLogs[lineUserInfo.username] = curLineLogs = [];
    }

    return {curLineLogs, hisRecordsData};
}

const state = {
    //当前显示的历史消息
    hisChatRecords: [],

    //当前显示的聊天记录数据
    chatRecords: [],

    //新接收的消息
    newReceiveMessages: [],

    isConnected: false
}

const getters = {
    [types.GET_CUR_CHAT_RECORDS](state, getters, rootState, rootGetters){
        const loginUserInfo = rootGetters['user/'+userTypes.GET_LOGIN_USER];
        const lineUserInfo = rootGetters['user/'+userTypes.GET_LINE_USER_INFO];
        return state.chatRecords.filter(record =>
            (record.to.username == loginUserInfo.username && record.from.username == lineUserInfo.username) ||
            (record.from.username == loginUserInfo.username && record.to.username == lineUserInfo.username)
        );
    }
}

const mutations = {
    [types.SET_CONNECT](state, connected) {
        state.isConnected = connected;
    },
    [types.SET_CUR_CHAT_RECORDS](state, records){
        state.chatRecords = records;
    }
}

const actions = {
    [types.ADD_CHAT_RECORD]({state, getters, rootGetters}, record){
        //获取当前连线用户的聊天记录
        const loginUserInfo = rootGetters['user/'+userTypes.GET_LOGIN_USER];
        const lineUserInfo = rootGetters['user/'+userTypes.GET_LINE_USER_INFO];
        const {curLineLogs, hisRecordsData} = getCurLineHisRecords(loginUserInfo, lineUserInfo);
        curLineLogs.push(record);
        localStorage.setItem(RECORD_DATAS, JSON.stringify(hisRecordsData));


        state.chatRecords.push(record);
    },
    async [types.LOAD_HIS_CHAT_RECORDS] ({commit, state, getters, rootGetters}) {
        const loginUserInfo = rootGetters['user/'+userTypes.GET_LOGIN_USER];
        const lineUserInfo = rootGetters['user/'+userTypes.GET_LINE_USER_INFO];
        state.hisChatRecords = getCurLineHisRecords(loginUserInfo, lineUserInfo).curLineLogs;;
        if(!state.hisChatRecords){
            state.hisChatRecords = [];
        }

        const res = await api.queryNotReceiveMessages({current: 1, size: 10, loginUsername: loginUserInfo.username, lineUsername: lineUserInfo.username});
        if(res.code == 1 && res.data.content){
            state.newReceiveMessages.push(...res.data.content.reverse());

            const {curLineLogs, hisRecordsData} = getCurLineHisRecords(loginUserInfo, lineUserInfo)
            curLineLogs.push(...res.data.content);
            localStorage.setItem(RECORD_DATAS, JSON.stringify(hisRecordsData));
        }
    }
}

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}