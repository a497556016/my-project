<template>
    <div class="select">

        <a-select ref="select" :style="{width: '100%'}">
            <a-select-option v-for="item in options" :value="item.value">{{item.label}}</a-select-option>
        </a-select>

    </div>
</template>

<script>
    export default {
        name: "Select",
        components: {},
        props: {
            seq: Number,
            option: Object
        },
        model: {
            prop: 'option',
            event: 'change'
        },
        data(){
            return {
                options: this.option.options,
            }
        },
        watch: {
            options(v){
                this.$emit('change', {
                    options: v
                });
            }
        },
        methods: {

        }
    }
</script>

<style scoped lang="less">
    .edit-actions {
        background: #ffffff;
        border: 1px solid #cccccc;
        height: 36px;
        line-height: 36px;
        padding-right: 5px;
        position: absolute;
        .action {
            width: 100px;
            float: left;
            margin-left: 5px;
            cursor: pointer;
        }
    }
</style>