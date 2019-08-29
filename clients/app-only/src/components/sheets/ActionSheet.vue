<template>
    <div v-if="visible">
        <div class="actions-sheet" :style="position">
            <div class="item" v-for="item in items" @click="onSelect(item)">
                <i :class="[item.icon]"></i>&nbsp;&nbsp;{{item.text}}
            </div>
        </div>
        <div class="modal" @click="cancel"></div>
    </div>
</template>

<script>
    export default {
        name: "ActionSheet",
        props: {
            visible: Boolean,
            /**
             * [{text: '切换对象', icon: 'fa fa-circle-o', index: 0}]
             */
            items: Array,
            position: Object
        },
        model: {
            prop: 'visible',
            event: 'change'
        },
        methods: {
            cancel(){
                this.$emit('change', false);
            },
            onSelect(item){
                console.log(item)
                this.$emit('select', item);
            }
        }
    }
</script>

<style scoped lang="less">
    .actions-sheet{
        position: absolute;
        /*left: 0px;
        top: 0px;*/
        display: inline-block;
        z-index: 100;
        padding: 5px 10px;
        background: white;
        .item {
            font-size: 13px;
            padding: 10px 0;
            &:not(:last-child){
                border-bottom: 1px solid #d2dde6;
            }
        }
    }
    .modal {
        z-index: 99;
        position: fixed;
        left: 0;
        right: 0;
        top: 0;
        bottom: 0;
        background: #d2dde6;
        opacity: 0.3;
    }
</style>