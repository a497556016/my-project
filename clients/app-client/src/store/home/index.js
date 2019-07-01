import * as types from '../types'

import homeApi from '../../api/home'

const state = {
    headerBar: {
        placeholder: '水弹枪',
        searchWords: ''
    },
    topImages: [

    ],
    menuLayout: {
        cols: 3
    },
    menuItems: [
        {icon: 'like', color: '#00f', title: '喜欢'},
        {icon: 'star', color: '#ff3136', title: '收藏'},
        {icon: 'shopping-cart', color: '#35ff9b', title: '购物车'},
        {icon: 'shopping-cart', color: '#4b91ff', title: '购物车'}
    ]
}

const getters = {
    [types.GET_MENU_ROW_COLS] (state){
        let start = 0, end = state.menuLayout.cols;
        let span = 24 / state.menuLayout.cols - 1;
        const result = [];
        while(end <= state.menuItems.length) {
            result.push(state.menuItems.slice(start, end));
            start = end;
            end = start + state.menuLayout.cols;
        }

        if(start < state.menuItems.length && end > state.menuItems.length) {
            result.push(state.menuItems.slice(start));
        }

        result.forEach(items => {
            items.forEach(item => item.span = span);
        })

        return result;
    }
}

const mutations = {
    [types.SET_TOP_IMAGES] (state, images) {
        state.topImages = images;
    }
}

const actions = {
    [types.LOAD_TOP_IMAGES] ({commit, state}) {
        homeApi.getTopImages().then(res => {
            if(res.code == 1){
                commit(types.SET_TOP_IMAGES, res.data);
            }
        })
    }
}

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}