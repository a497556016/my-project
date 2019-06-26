import {getters as gettersTypes, mutations as mutationsTypes, actions as actionsTypes} from './types'
import FileManageService from "@/api/file/manage";

const fileManageService = new FileManageService();

const state = {

}

const getters = {

}

const mutations = {

}

const actions = {
    [actionsTypes.QUERY] ({commit}, data) {
        console.log(data);
        return fileManageService.selectPage(data)
    }
}

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}