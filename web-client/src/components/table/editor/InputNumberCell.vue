<template>
    <a-input-number v-model="number"></a-input-number>
</template>

<script>
    export default {
        name: "InputNumberCell",
        model: {
            prop: 'value',
            event: 'change'
        },
        props: {
            value: {
                type: Number
            }
        },
        data(){
            return {
                number: this.value
            }
        },
        watch: {
            number(val){
                this.$emit('change', val);
            }
        }
    }
</script>

<style scoped>

</style>