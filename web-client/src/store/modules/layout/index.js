import * as types from './types'
import util from '@/util'

export default {
    namespaced: true,
    state: {
        menuTree: {},

        menus: [],
        menuTabs: [],
        activeMenuKey: '',

        collapsed: false,

        loading: false,
        loadingProcess: 0,

        activeHeadMenu: 'system',
        headerMenus: [
            {name: '管理系统', route: 'system'}, //route 对应的在目录@/route/routes下的文件名称，新增的子系统需要添加对应的路由描述文件，默认的index不能改
            {name: '文件系统', route: 'file'},
            {name: '自定义表单系统', route: 'form'}
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
        [types.TOGGLE_COLLAPSED] (state) {
            state.collapsed = !state.collapsed;
        },
        [types.SET_MENUS_BY_ROUTE] (state, route) {
            const {menus, menuTabs} = state.menuTree[route];
            state.menus = menus;
            state.menuTabs = menuTabs;
            state.activeMenuKey = menuTabs.length > 0 ? menuTabs[0].id : '';
        },
        [types.ADD_MENU_TAB] (state, menu) {
            if(typeof menu === 'string') {
                if(menu.includes('/')){
                    const arr = menu.split('/');
                    const route = arr[0], name = arr[1];
                    if(state.menuTree[route]) {
                        menu = state.menuTree[route].menus.find(m => m.id == name);
                        // state.activeHeadMenu = route;
                    }
                }else {
                    menu = state.menus.find(m => m.id == menu);
                }
            }
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
        [types.CREATE_MENU_TREE_DATA] ({commit, state}) {
            const promises = [];
            state.headerMenus.forEach((hm, index) => {
                promises.push(new Promise(resolve => {
                    import('@/route/menus/'+hm.route+'.js').then(v => {
                        const routes = v.default;
                        console.log('加载的路由：', routes);

                        const menuTree = util.buildMenuFromRoutes(routes);
                        state.menuTree[hm.route] = menuTree;

                        //生成第一个系统栏目
                        if(index == 0) {
                            commit(types.SET_MENUS_BY_ROUTE, hm.route);
                        }
                        resolve(menuTree);
                    })
                }));
            })
            return Promise.all(promises);
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