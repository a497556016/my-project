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
    import {layout} from '@/store/types'

    export default {
        name: "MenuBar",
        props: [],
        data(){
            return {

            }
        },
        computed: {
            panes(){
                return this.$store.state.layout.menuTabs;
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
</style>