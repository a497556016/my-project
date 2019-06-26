<template>
    <a-checkbox :checked="value" @change="onChange"></a-checkbox>
</template>

<script>
    export default {
        name: "CheckboxCell",
        model: {
            prop: 'value',
            event: 'change'
        },
        props: {
            value: {
                type: Boolean
            }
        },
        data(){
            return {

            }
        },
        methods: {
            onChange(e){
                this.$emit('change', e.target.checked);
            }
        }
    }
</script>

<style scoped>

</style>