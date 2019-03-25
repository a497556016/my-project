<template>
    <a-layout-sider :trigger="null" :width="width" collapsible :collapsed="collapsed">
        <layout-logo></layout-logo>
        <a-menu
                theme="dark"
                mode="inline"
                :selectedKeys="[$store.state.layout.activeMenuKey]"
                :style="{ borderRight: 0 }"
        >
            <template v-for="menu in menus">
                <a-sub-menu v-if="menu.children" :key="menu.id">
                    <span slot="title"><a-icon v-if="menu.icon" :type="menu.icon" />{{menu.title}}</span>
                    <a-menu-item v-for="item in menu.children" :key="item.id" @click="onMenuClick(item)">
                        <a-icon v-if="item.icon" :type="item.icon" />{{item.title}}
                    </a-menu-item>
                </a-sub-menu>
                <a-menu-item v-else :key="menu.id" @click="onMenuClick(menu)">
                    <a-icon v-if="menu.icon" :type="menu.icon" />{{menu.title}}
                </a-menu-item>
            </template>

        </a-menu>
    </a-layout-sider>
</template>

<script>
    import Logo from './Logo'

    import {mapState} from 'vuex'

    import {layout} from '@/store/types'
    export default {
        name: "Left",
        components: {
            'layout-logo': Logo
        },
        props: {

            width: {
                type: Number,
                default: 200
            }
        },
        data(){
            return {

            }
        },
        computed: {
            ...mapState({
                collapsed: state => state.layout.collapsed,
                menus: state => state.layout.menus
            })
        },
        methods: {
            onMenuClick(menu){
                if(menu.component){
                    console.log(this.$store)
                    this.$store.commit(layout.ADD_MENU_TAB, menu);
                }
            }
        }
    }
</script>

<style scoped>

</style>