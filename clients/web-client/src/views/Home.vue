<template>
    <a-layout style="height: 100%;">
        <layout-left :width="250"></layout-left>
        <a-layout>
            <layout-header></layout-header>
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

            }
        },
        computed: {

        },
        methods: {

        }
    }
</script>

<style scoped>

</style>