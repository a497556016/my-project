<template>
    <div class="input">
        <a-input :placeholder="placeholder"></a-input>
    </div>
</template>

<script>
    export default {
        name: "Input",
        props: {
            config: Object
        },
        model: {
            prop: 'config',
            event: 'change'
        },
        data(){
            return {
                placeholder: this.config.placeholder
            }
        }
    }
</script>

<style scoped lang="less">
    .input {

    }
</style>