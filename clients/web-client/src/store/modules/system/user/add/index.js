import * as types from './types'

export default {
    namespaced: true,
    state: {
        addModalVisible: false
    },
    mutations: {
        [types.OPEN_ADD] (state) {
            state.addModalVisible = true;
        },
        [types.CLOSE_ADD] (state) {
            state.addModalVisible = false;
        }
    }
}