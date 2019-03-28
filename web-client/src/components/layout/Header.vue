<template>
    <div>
        <a-layout-header style="background: #fff; padding: 0">
            <a-icon
                    style="float: left;line-height: 64px;margin: 0 24px;cursor: pointer;"
                    :type="collapsed ? 'menu-unfold' : 'menu-fold'"
                    @click="toggleCollapsed"
            />
            <a-menu
                    mode="horizontal"
                    :defaultSelectedKeys="[activeHeadMenu]"
                    :style="{ lineHeight: '64px', float: 'left' }"
            >
                <a-menu-item v-for="menu in menus" :key="menu.route" @click="menuHandler(menu)">{{menu.name}}</a-menu-item>
            </a-menu>

            <div style="float: right; display: flex; flex-flow: row;">
                <a @click="showNoticeBox">
                    <a-badge  :count="1" :style="{margin: '10px'}">
                        <a-avatar :style="{ background: '#eee' }" :size="40" :src="avatar" />
                    </a-badge>
                </a>
                <!--<img :style="{borderRadius: '25px', width: '40px', height: '40px', background: '#eee', margin: '10px'}" src="../../assets/logo.png">-->
                <a-dropdown :trigger="['click']" :style="{margin: '0 10px'}">
                    <a class="ant-dropdown-link" href="#">
                        {{userInfo?userInfo.username:''}} &nbsp;&nbsp;<a-icon type="down" />
                    </a>
                    <a-menu slot="overlay">
                        <a-menu-item>
                            <a @click="addMenuTab">个人中心</a>
                        </a-menu-item>
                        <a-menu-item>
                            <a @click="logout()">退出登录</a>
                        </a-menu-item>
                    </a-menu>
                </a-dropdown>
            </div>
        </a-layout-header>

        <notice-box></notice-box>
    </div>
</template>

<script>
    import {mapState, mapGetters, mapMutations, mapActions} from 'vuex'
    import {account, layout} from '@/store/types'

    import NoticeBox from './NoticeBox'

    export default {
        name: "Header",
        components: {NoticeBox},
        props: {

        },
        data(){
            return {
                // avatar: require('../../assets/logo.png')
            }
        },
        computed: {
            ...mapState({
                collapsed: state => state.layout.collapsed,
                menus: state => state.layout.headerMenus,
                activeHeadMenu: state => state.layout.activeHeadMenu
            }),
            ...mapGetters({
                userInfo: account.getters.GET_USER_INFO,
                avatar: account.getters.GET_AVATAR,
            })
        },
        watch: {

        },
        mounted(){
            //初始化所有栏目组件
            this.createMenuTreeData().then(menuTree => console.log('所有栏目已经初始化完成！',menuTree));
        },
        methods: {
            ...mapMutations({
                logout: account.mutations.LOGOUT,
                showNoticeBox: layout.SHOW_NOTICE_BOX,
                toggleCollapsed: layout.TOGGLE_COLLAPSED,
                setMenusByRoute: layout.SET_MENUS_BY_ROUTE,
            }),
            ...mapActions({
                createMenuTreeData: layout.CREATE_MENU_TREE_DATA
            }),
            menuHandler(menu) {
                this.setMenusByRoute(menu.route);
            },
            addMenuTab(){
                this.$store.commit(layout.ADD_MENU_TAB, 'system/UserCenter');
            }
        }
    }
</script>

<style scoped>

</style>