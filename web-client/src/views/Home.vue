<template>
    <a-layout style="height: 100%;">
        <layout-left :collapsed="collapsed" :width="250"></layout-left>
        <a-layout>
            <layout-header :menus="headerMenus" :defaultSelectedMenus="[defaultSelectedMenus]" :collapsed="collapsed" @collapseChange="collapseChange"></layout-header>
            <layout-content></layout-content>
        </a-layout>
    </a-layout>
</template>

<script>
    import LayoutContent from '@/components/layout/Content'
    import LayoutHeader from '@/components/layout/Header'
    import LayoutLeft from '@/components/layout/Left'

    import {mapState} from 'vuex'

    export default {
        name: "Home",
        components: {
            'layout-content': LayoutContent,
            'layout-header': LayoutHeader,
            'layout-left': LayoutLeft
        },
        data(){
            return {
                collapsed: false
            }
        },
        computed: {
            ...mapState({
                headerMenus: state => state.layout.headerMenus
            }),
            defaultSelectedMenus(){
                if(this.headerMenus.length > 0) {
                    return this.headerMenus[0].id;
                }else{
                    return null;
                }
            }
        },
        methods: {
            collapseChange(collapsed){
                this.collapsed = collapsed;
            }
        }
    }
</script>

<style scoped>

</style>