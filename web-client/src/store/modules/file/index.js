import Vue from 'vue'
import types from './types'

import FileService from '@/service/file'

const fileService = new FileService();

const state = {
    uploadedFile: {}
}

const getters = {
    [types.getters.FILE_DOWNLOAD_PATH] (state) {
        if(!state.uploadedFile.path){
            return '';
        }
        const encodedPath = encodeURI(state.uploadedFile.path);
        return Vue.config.custom.baseURL + '/file-server/download?path='+encodedPath;
    }
}

const mutations = {
    [types.mutations.SET_UPLOADED_FILE] (state, file) {
        state.uploadedFile = file;
    }
}

const actions = {
    [types.actions.UPLOAD] ({commit}, option) {
        const file = option.file;
        const formData = new FormData();
        formData.append('file', file);

        fileService.upload(formData).then(res => {
            commit(types.mutations.SET_UPLOADED_FILE, res.data);
        });
    }
}

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}