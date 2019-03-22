import * as types from './types'
import util from '@/util'

export default {
    namespaced: true,
    state: {
        menus: [],
        menuTabs: [],
        activeMenuKey: '',

        loading: false,
        loadingProcess: 0,

        headerMenus: [
            {id: 1, name: '管理系统', route: 'index'},
            {id: 2, name: '文件系统', route: 'file'}
        ],

        noticeBoxVisible: false,
        noticeLoading: false,
        noticesData: [
            {title: '重大消息！！！', description: 'wscnmsl!!!!'},
            {title: '哈哈哈哈哈！！', description: '中华人民共和国万岁！！'}
        ],
        hasMore: true
    },
    getters: {

    },
    mutations: {
        [types.SET_MENUS_BY_ROUTES] (state, routes) {
            const {menus, menuTabs} = util.buildMenuFromRoutes(routes);
            state.menus = menus;
            state.menuTabs = menuTabs;
            state.activeMenuKey = menuTabs.length > 0 ? menuTabs[0].id : '';
        },
        [types.ADD_MENU_TAB] (state, menu) {
            const tab = state.menuTabs.filter(menuTab => menuTab.id == menu.id);
            if(tab.length == 0){
                state.menuTabs.push(menu);
            }
            state.activeMenuKey = menu.id;
        },
        [types.REMOVE_MENU_TAB] (state, key) {
            let activeKey = state.activeMenuKey
            let lastIndex;
            state.menuTabs.forEach((pane, i) => {
                if (pane.id === key) {
                    lastIndex = i - 1
                }
            })
            const panes = state.menuTabs.filter(pane => pane.id !== key)
            if (lastIndex >= 0 && activeKey === key) {
                activeKey = panes[lastIndex].id;
            }
            state.menuTabs = panes;
            state.activeMenuKey = activeKey;
        },
        [types.LOADING_START] (state) {
            state.loading = true;

            (function setProcess(){
                let incre = 1;
                const i = setInterval(() => {
                    if(state.loadingProcess >= 90){
                        clearInterval(i);
                    }

                    state.loadingProcess+=incre;

                    if(incre > 0.3) {
                        incre -= (state.loadingProcess / 1000);
                    }
                }, 50);
            })();
        },
        [types.LOADING_END] (state) {
            state.loadingProcess = 100;
            setTimeout(() => {
                state.loading = false;
            }, 200);
        },
        [types.SHOW_NOTICE_BOX] (state) {
            state.noticeBoxVisible = true;
        },
        [types.HIDE_NOTICE_BOX] (state) {
            state.noticeBoxVisible = false;
        }
    },
    actions: {
        [types.CHANGE_SUB_MENUS] ({commit}, route) {
            import('@/route/routes/'+route+'.js').then(v => {
                const routes = v.default;
                console.log('加载的路由：', routes);
                commit(types.SET_MENUS_BY_ROUTES, routes);
            });

        },
        [types.LOAD_MORE_NOTICES] ({commit, state}) {
            state.noticeLoading = true;
            setTimeout(() => {

                state.noticesData = state.noticesData.concat([
                    {title: '又有新消息啦！', description: '让我们来看看是什么'},
                    {title: '完啦完啦！', description: '小明和小红。。。'}
                ]);

                state.noticeLoading = false;

                if(state.noticesData.length > 6) {
                    state.hasMore = false;
                }
            }, 1000);
        }
    }
}