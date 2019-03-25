<template>
    <a-tabs
            id="menu-bar"
            hideAdd
            v-model="$store.state.layout.activeMenuKey"
            type="editable-card"
            @edit="onEdit"
    >
        <a-tab-pane class="content-card" :style="{background: '#fff', borderRadius: '5px', padding: '16px'}" v-for="pane in panes" :tab="pane.title" :key="pane.id" :closable="!pane.noCloseable">
            <keep-alive>
                <component v-bind:is="pane.component"></component>
            </keep-alive>
        </a-tab-pane>
    </a-tabs>
</template>

<script>
    import {mapState} from 'vuex'
    import {layout} from '@/store/types'

    export default {
        name: "MenuBar",
        props: [],
        data(){
            return {

            }
        },
        computed: {
            ...mapState({
                collapsed: state => state.layout.collapsed
            }),
            panes(){
                return this.$store.state.layout.menuTabs;
            }
        },
        watch: {
            collapsed(v) {
                const tabList = document.getElementById('menu-bar').firstChild;
                // console.log('改变tab bar宽度：', v, tabList)
                tabList.style.left = v?'96px': '266px';
                // document.getElementById('menu-bar')[0].getElementsByClassName('.ant-tabs-bar').forEach(el => {
                //     console.log('改变tab bar宽度：', v, el);
                // })
            }
        },
        methods: {
            onEdit(targetKey, action){
                this[action](targetKey)
            },
            remove (targetKey){
                this.$store.commit(layout.REMOVE_MENU_TAB, targetKey);
            },
            hover (e){
                // console.log(e);
            }
        }
    }
</script>

<style lang="less" scoped>
    .content-card:hover{
        box-shadow: 0 0 10px #ddd;
    }
    #menu-bar::-webkit-scrollbar {
        display: none;
    }
</style>