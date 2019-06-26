<template>
    <div class="input">
        <a-input :placeholder="config.placeholder" v-model="config.value"></a-input>
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

            }
        }
    }
</script>

<style scoped lang="less">
    .input {

    }
</style>