<template>
    <a-select v-model="v" :style="{width: '100%'}">
        <a-select-option v-for="item in items" :value="item.value" :key="item.value">{{item.name}}</a-select-option>
    </a-select>
</template>

<script>
    export default {
        name: "SelectCell",
        model: {
            prop: 'value',
            event: 'change'
        },
        props: {
            value: {
                type: String
            },
            items: Array
        },
        data(){
            return {
                v: this.value
            }
        },
        watch: {
            v(val){
                this.$emit('change', val);
            }
        }
    }
</script>

<style scoped>

</style>