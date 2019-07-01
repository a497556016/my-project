<template>
    <div class="my-nav-bar">
        <van-nav-bar :title="title" :left-arrow="leftArrow" :fixed="fixed" :border="border" @click-left="onClickLeft" @click-right="onClickRight">
            <template slot="left">
                <slot name="left"></slot>
            </template>
            <template slot="title">
                <slot name="title"></slot>
            </template>
            <template slot="right">
                <slot name="right"></slot>
            </template>
        </van-nav-bar>
    </div>
</template>

<script>
    import {NavBar} from 'vant'
    export default {
        name: "MyNavBar",
        components: {
            [NavBar.name]: NavBar
        },
        props: {
            title: String,
            leftArrow: Boolean,
            fixed: Boolean,
            border: Boolean
        },
        methods: {
            onClickLeft(){
                this.$emit('click-left');
            },
            onClickRight(){
                this.$emit('click-right');
            }
        }
    }
</script>

<style scoped lang="less">
    @import "../../assets/style/color";
    .my-nav-bar {
        .van-nav-bar {
            background: @primary-color;
            .van-nav-bar__title {
                color: @primary-text-color;
            }
            .van-icon {
                color: @primary-text-color;
                font-size: 1.6em;
            }
        }
    }
</style>