<template>
    <div>
    <div class="loading-bar" :style="{display: loading?'block':'none'}">
        <div class="progress" :style="{width: loadingProcess+'%'}"></div>
    </div>
    </div>
    <!--<a-progress :percent="50" status="active" :style="{display: 'block'}"></a-progress>-->
</template>

<script>
    import {mapState} from 'vuex';
    export default {
        name: "LoadingBar",
        data(){
            return {

            }
        },
        computed: mapState({
            loading: state => state.layout.loading,
            loadingProcess: state => state.layout.loadingProcess
        })
    }
</script>

<style lang="less" scoped>
    /*@import 'ant-design-vue/lib/style/themes/default';*/
    .loading-bar {
        position: absolute;
        height: 1px;
        width: 100%;
        background: #ffffff;
        z-index: 9999;

        .progress{
            height: 100%;
            background: @primary-color;
        }
    }
</style>