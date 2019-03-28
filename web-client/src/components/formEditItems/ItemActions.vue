<template>
    <div class="item-actions" @mouseleave="onMoveOut" :style="{display: visible?'block':'none', position: 'absolute', left: position.left+'px', top: position.top+'px'}">
        <div class="action" @click="handler('plus')"><a-icon type="plus"></a-icon></div>
        <div class="action" @click="handler('up')"><a-icon type="arrow-up"></a-icon></div>
        <div class="action" @click="handler('down')"><a-icon type="arrow-down"></a-icon></div>
        <div class="action" @click="handler('delete')"><a-icon type="delete"></a-icon></div>
    </div>
</template>

<script>
    export default {
        name: "ItemActions",
        props: {
            visible: {
                type: Boolean,
                default: false
            },
            position: {
                type: Object,
                default: {
                    left: 0,
                    top: 0
                }
            }
        },
        methods: {
            onMoveOut(e){
                this.$emit('mouseleave', e);
            },
            handler(type) {
                this.$emit('handler', type)
            }
        }
    }
</script>

<style scoped lang="less">
    .item-actions {
        border: 1px solid #cccccc;
        z-index: 1000;
        background: #ffffff;
        .action {
            float: left;
            width: 36px;
            height: 29px;
            line-height: 29px;
            text-align: center;
            cursor: pointer;
            &:not(:first-child) {
                border-left: 1px solid #cccccc;
            }
        }
    }
</style>