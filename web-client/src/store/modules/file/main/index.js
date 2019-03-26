import {actions as actionsTypes, mutations as mutationsTypes} from './types'
import FileCountService from "@/api/file/main";

const fileCountService = new FileCountService();

const state = {
    csData: {
        diskLeftSpace: '0',
        fileUsedSpace: '0',
        todayUploadTimes: 0,
        todayUploadSize: '0',
        todayDownloadTimes: 0
    }
}

const mutations = {
    [mutationsTypes.SET_CLASSIFIED_STATISTICAL_DATA] (state, data) {
        state.csData = Object.assign(state.csData, data);
    }
}

const actions = {
    [actionsTypes.LOAD_CLASSIFIED_STATISTICAL_DATA] ({commit}) {
        fileCountService.getClassifiedStatistical().then(res => {
            commit(mutationsTypes.SET_CLASSIFIED_STATISTICAL_DATA, res.data);
        });
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}