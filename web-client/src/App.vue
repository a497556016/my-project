<template>
    <a-locale-provider :locale="zh_CN">
        <div id="app">
            <loading-bar></loading-bar>
            <transition name="fade" mode="out-in">
                <router-view></router-view>
            </transition>
        </div>
    </a-locale-provider>
</template>

<script>
    import LoadingBar from '@/components/layout/LoadingBar'

    import zh_CN from 'ant-design-vue/lib/locale-provider/zh_CN';
    import moment from 'moment';
    import 'moment/locale/zh-cn';

    moment.locale('zh-cn');
    export default {
        name: 'app',
        components: {
            LoadingBar
        },
        data() {
            return {
                zh_CN,
            }
        }
    }
</script>

<style>
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        /*text-align: center;*/
        color: #2c3e50;
        /*margin-top: 60px;*/
        height: 100%;
    }
</style>
