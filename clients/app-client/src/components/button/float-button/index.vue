<template>
    <div class="float-button" :style="style" @click="onClick($event)">
        <van-icon :name="icon"></van-icon>
    </div>
</template>

<script>
    import {Icon} from 'vant'
    export default {
        name: "FloatButton",
        components: {
            [Icon.name]: Icon
        },
        props: {
            icon: {
                type: String,
                default: 'plus'
            },
            position: {
                type: Object,
                default: function () {
                    return {
                        left: 0,
                        top: 0,
                        right: 20,
                        bottom: 58
                    }
                }
            }
        },
        computed: {
            style(){
                const style = {};
                if(this.position.left) {
                    style.left = this.position.left+'px';
                }
                if(this.position.right) {
                    style.right = this.position.right+'px';
                }
                if(this.position.top) {
                    style.top = this.position.top+'px';
                }
                if(this.position.bottom) {
                    style.bottom = this.position.bottom+'px';
                }
                return style;
            }
        },
        methods: {
            onClick(e){
                this.$emit('click', e);
            }
        }
    }
</script>

<style scoped lang="less">
    @import "../../../assets/style/index";
    @import "~vant/lib/style/var";
    .float-button {
        position: fixed;
        width: 50px;
        height: 50px;
        line-height: 60px;
        border-radius: 25px;
        border: #cccccc;
        background: @green;
        color: @primary-text-color;
        font-size: 28px;
        text-align: center;
        z-index: 999;
        cursor: pointer;
    }
</style>