<template>
    <div class="select">
        <a-select :style="{width: '100%'}">
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

</style>