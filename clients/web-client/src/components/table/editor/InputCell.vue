<template>
    <a-input v-model="text"></a-input>
</template>

<script>
    export default {
        name: "InputCell",
        model: {
            prop: 'value',
            event: 'change'
        },
        props: {
            value: {
                type: String
            }
        },
        data(){
            return {
                text: this.value
            }
        },
        watch: {
            text(val){
                this.$emit('change', val);
            }
        }
    }
</script>

<style scoped>

</style>